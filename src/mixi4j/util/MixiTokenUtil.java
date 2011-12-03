package mixi4j.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import mixi4j.MixiException;
import mixi4j.auth.MixiToken;
import mixi4j.conf.Configuration;
import mixi4j.internal.org.json.JSONException;
import mixi4j.internal.org.json.JSONObject;

/**
 * mixiのアクセストークン類を扱うために作ったユーティリティクラス。<br>
 * 実装はむちゃくちゃ適当…。
 * @author user
 *
 */
public class MixiTokenUtil {

    private static final String TOKEN_ENDPOINT = "https://secure.mixi-platform.com/2/token";

	/**
	 * とりあえず現時点での実装では、アクセストークンはカレントディレクトリのファイルに保存します
	 */
	private static final String TOKEN_FILE_NAME = "token.txt";

	/**
	 * Authorization Codeを利用してアクセストークンを取得する
	 * @param authorizationCode
	 * @throws IOException
	 */
    public static MixiToken getAccessTokenAndRefreshToken(Configuration conf) {
    	MixiToken token = null;
		String params = buildParamsForAuthorization(conf.getOAuthConsumerKey(), conf.getOAuthConsumerSecret(), conf.getOAuthRedirectURL(), conf.getOAuthAuthorizationCode());
		try {
			token = authorize(params);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MixiException e) {
			e.printStackTrace();
		}

		return token;
	}

	/**
	 * アクセストークンの有効期限が切れていた場合は、リフレッシュトークンを利用してアクセストークンを再取得する
	 * @param refreshToken
	 * @throws IOException
	 */
    public static MixiToken getAccessToken(Configuration conf, String refreshToken) {
    	MixiToken token = null;
		String params = buildParamsForReAuthorization(conf.getOAuthConsumerKey(), conf.getOAuthConsumerSecret(), conf.getOAuthRedirectURL(), refreshToken);
		try {
			token = authorize(params);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MixiException e) {
			e.printStackTrace();
		}

		return token;
	}

	/**
	 * アクセストークンを取得する
	 * @param params
	 * @return
	 * @throws IOException
	 */
    private static MixiToken authorize(String params) throws MixiException, IOException {
		PrintWriter out = null;
		MixiToken token = null;
		try {
			URL url = new URL(TOKEN_ENDPOINT);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			out = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
			out.print(params);
			out.flush();
			int responseCode = connection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				String responseBody = readStream(connection.getInputStream());
				token = setToken(responseBody);
			} else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
				System.err.println("認証が失敗している, たぶん、authorization codeの有効期限(3分間)が切れています。\n" +
						"https://mixi.jp/connect_authorize.pl?client_id=[自分のCLIENT_ID]&response_type=code&scope=r_profile%20w_profile%20r_voice%20w_voice%20r_updates%20w_share%20r_photo%20w_photo%20r_message%20w_message%20w_diary%20r_checkin%20w_checkin%20\n" +
						"↑このURLにアクセスして、authorization codeを取得し直してください。\n" +
						"responsebody:\n\t" + readStream(connection.getErrorStream()) + ",\n" +
						"header:\n\t" + connection.getHeaderFields() + ",\n" +
						"http parameters:\n\t" + params + "\n");

				throw new MixiException("たぶんauthorization codeの有効期限切れ");
			} else {
				String responseBody = readStream(connection.getErrorStream());
				throw new IOException(responseCode + ": " + responseBody);
			}
		} finally {
			if (out != null) {
				out.close();
			}
		}

		return token;
	}

	/**
	 * パラメータを連結する
	 * @param authorizationCode
	 * @return
	 */
	private static String buildParamsForAuthorization(String clientId, String clientSecret, String redirectURL, String authorizationCode) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("client_id=");
			sb.append(clientId);
			sb.append("&client_secret=");
			sb.append(clientSecret);
			sb.append("&redirect_uri=");
			sb.append(URLEncoder.encode(redirectURL, "UTF-8"));
			sb.append("&code=");
			sb.append(authorizationCode);
			sb.append("&grant_type=authorization_code");
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * パラメータを連結する
	 * @param refreshToken
	 * @return
	 */
	private static String buildParamsForReAuthorization(String clientId, String clientSecret, String redirectURL, String refreshToken) {
		StringBuilder sb = new StringBuilder();
		sb.append("client_id=");
		sb.append(clientId);
		sb.append("&client_secret=");
		sb.append(clientSecret);
		sb.append("&refresh_token=");
		sb.append(refreshToken);
		sb.append("&grant_type=refresh_token");
		return sb.toString();
	}

	/**
	 * InputStreamを読んで、中身を返す
	 * @param in
	 * @return
	 * @throws IOException
	 */
    private static String readStream(InputStream in) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
		String line = null;
		StringBuilder content = new StringBuilder();
		while ((line = br.readLine()) != null) {
			content.append(line);
		}
		return content.toString();
	}

	/**
	 * HTTPで取得したレスポンスボディからトークン情報を取り出してファイルに保存する
	 * @param responseBody
	 * @return
	 */
    private static MixiToken setToken(String responseBody){
		String accessToken = "", refreshToken = "", expiration = "";
		try {
			JSONObject json =  new JSONObject(responseBody);
			accessToken = json.getString("access_token");
			refreshToken = json.getString("refresh_token");
			expiration = "" + json.getInt("expires_in");
			writeToken(accessToken, refreshToken, expiration);
		} catch (JSONException e) {
			System.err.println(responseBody);
			e.printStackTrace();
		}
		System.out.println("accessToken: " + accessToken + ", refreshToken: " + refreshToken + ", expiration: " + expiration);
		return new MixiToken(accessToken, refreshToken);
	}

	/**
	 * トークンをファイルに書き込む
	 * @param accessToken
	 * @param refreshToken
	 * @param expiration
	 */
    public static void writeToken(String accessToken, String refreshToken, String expiration){
		try {
			File file = new File(TOKEN_FILE_NAME);
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			pw.print(accessToken + "," + refreshToken + "," + expiration);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * トークンをファイルから読み込む
	 * @return
	 */
    public static MixiToken readToken(){
		try {
			File file = new File(TOKEN_FILE_NAME);
            if (!file.exists() || !file.isFile())
				return null;

			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			if(line == null)
				return null;

			if(line.split(",").length != 3)
				return null;

			String accessToken = line.split(",")[0];
			String refreshToken = line.split(",")[1];
			String expiration = line.split(",")[2];
			MixiToken token = new MixiToken(accessToken, refreshToken);

			return token;
		} catch (FileNotFoundException e) {
			System.err.println(TOKEN_FILE_NAME + " が存在しませんでした");
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
