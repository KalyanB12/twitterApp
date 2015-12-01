package com.api.twitter.model;

public class OauthConsumer {

	private String consumerKey;
	private String secret;

	public OauthConsumer(String consumerKey, String secret) {
		this.consumerKey = consumerKey;
		this.secret = secret;
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public String getSecret() {
		return secret;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Key : ").append(consumerKey);
		builder.append(" and Secret : ").append(secret);
		return builder.toString();
	}

}
