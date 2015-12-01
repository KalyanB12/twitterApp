package com.api.twitter.service;

import java.io.IOException;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import com.api.twitter.model.OauthConsumer;

public class OauthInterceptor implements ClientHttpRequestInterceptor {

	Logger LOGGER = LoggerFactory.getLogger(OauthInterceptor.class);

	private OauthConsumer oauthConsumer;

	public OauthInterceptor(OauthConsumer oauthConsumer) {
		this.oauthConsumer = oauthConsumer;
	}

	@Override
	public ClientHttpResponse intercept(final HttpRequest request, final byte[] byteArray,
			final ClientHttpRequestExecution execution) throws IOException {

		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(oauthConsumer.getConsumerKey(), oauthConsumer.getSecret());

		try {
			HttpPost httpPost = new HttpPost(request.getURI());
			ByteArrayEntity myEntity = new ByteArrayEntity(byteArray);
			httpPost.setEntity(myEntity);
			consumer.sign(httpPost);
			String oauthHeaderValue = httpPost.getFirstHeader(OAuth.HTTP_AUTHORIZATION_HEADER).getValue();

			LOGGER.debug("header value : ", oauthHeaderValue);

			request.getHeaders().add(OAuth.HTTP_AUTHORIZATION_HEADER, oauthHeaderValue);
		} catch (Exception e) {
			LOGGER.error("Exception in interceptor: ", e);
			throw new RuntimeException(e);
		}
		return execution.execute(request, byteArray);
	}
}
