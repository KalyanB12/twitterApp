package com.api.twitter.service;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import org.apache.http.client.methods.HttpPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.api.twitter.model.OauthConsumer;

public class OauthInterceptorWithToken extends OauthInterceptorTemplate {

	Logger LOGGER = LoggerFactory.getLogger(OauthInterceptorWithToken.class);

	private OauthConsumer oauthConsumer;

	private String oauthToken;

	public OauthInterceptorWithToken(OauthConsumer oauthConsumer, String oauthToken) {
		this.oauthConsumer = oauthConsumer;
		this.oauthToken = oauthToken;
	}

	@Override
	public OAuthConsumer getOauthConsumer() {
		return new CommonsHttpOAuthConsumer(oauthConsumer.getConsumerKey(), oauthConsumer.getSecret());
	}

	@Override
	public String getOauthHeaderValue(HttpPost httpPost) {
		String headerValue = httpPost.getFirstHeader(OAuth.HTTP_AUTHORIZATION_HEADER).getValue();
		headerValue = appendOauthTokenToAuthorizationHeader(headerValue);
		return headerValue;
	}

	private String appendOauthTokenToAuthorizationHeader(String oauthHeader) {
		return oauthHeader + ", oauth_token=\"" + oauthToken + "\"";
	}

}
