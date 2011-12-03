package mixi4j.auth;

public class ConsumerToken {

	private String consumerKey;
	private String consumerSecret;

	public ConsumerToken(String consumerKey, String consumerSecret){
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;

	}

	public String getConsumerKey() {
		return consumerKey;
	}
	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}
	public String getConsumerSecret() {
		return consumerSecret;
	}
	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}
}
