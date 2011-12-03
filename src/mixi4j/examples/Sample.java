package mixi4j.examples;

import java.util.ArrayList;

import mixi4j.Mixi;
import mixi4j.MixiException;
import mixi4j.MixiFactory;
import mixi4j.User_mixi;
import mixi4j.auth.MixiToken;
import mixi4j.conf.Configuration;
import mixi4j.util.MixiTokenUtil;

public class Sample {

	public Sample(){
		Mixi mixi = new MixiFactory().getInstance();
		Configuration conf = mixi.getConfiguration();

		MixiToken token = MixiTokenUtil.readToken();
    	if(token == null)
			token = MixiTokenUtil.getAccessTokenAndRefreshToken(conf);
    	mixi.setOAuthMixiToken(token);

		for(int i = 0; i < 3; i++){
			try {
				User_mixi user = mixi.showUser();
				System.out.println("自分, " + user.getDisplayName());
				break;
			}catch (MixiException e) {
				if(e.getMessage().indexOf("expired_token") != -1) {
					token = MixiTokenUtil.getAccessToken(conf, token.getTokenSecret());
					mixi = new MixiFactory().getInstance(token);
					continue;
				}

				e.printStackTrace();
				System.exit(1);
			}
		}

		for(int i = 0; i < 3; i++){
			try {
				ArrayList<User_mixi> friends = mixi.getFriends();
				for(int j = 0; j < friends.size(); j++){
					User_mixi u = friends.get(j);
					System.out.println("マイミク, " + j + ", " + u.getDisplayName());
				}
				break;
			}catch (MixiException e) {
				if(e.getMessage().indexOf("expired_token") != -1) {
					token = MixiTokenUtil.getAccessToken(conf, token.getTokenSecret());
					mixi = new MixiFactory().getInstance(token);
					continue;
				}

				e.printStackTrace();
				System.exit(1);
			}
		}

	}

	public static void main(String[] args) {
		new Sample();
	}
}
