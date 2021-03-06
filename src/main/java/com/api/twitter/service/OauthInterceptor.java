package com.api.twitter.service;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import org.apache.http.client.methods.HttpPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.api.twitter.model.OauthConsumer;

public class OauthInterceptor extends OauthInterceptorTemplate {

	Logger LOGGER = LoggerFactory.getLogger(OauthInterceptor.class);

	private OauthConsumer oauthConsumer;

	public OauthInterceptor(OauthConsumer oauthConsumer) {
		this.oauthConsumer = oauthConsumer;
	}

	@Override
	public OAuthConsumer getOauthConsumer() {
		return new CommonsHttpOAuthConsumer(oauthConsumer.getConsumerKey(), oauthConsumer.getSecret());
	}

	@Override
	public String getOauthHeaderValue(HttpPost httpPost) {
		return httpPost.getFirstHeader(OAuth.HTTP_AUTHORIZATION_HEADER).getValue();
	}
}
