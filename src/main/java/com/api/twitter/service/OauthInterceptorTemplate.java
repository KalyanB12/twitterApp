package com.api.twitter.service;

import java.io.IOException;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public abstract class OauthInterceptorTemplate implements ClientHttpRequestInterceptor {
	
	public ClientHttpResponse intercept(final HttpRequest request, final byte[] byteArray,
			final ClientHttpRequestExecution execution) throws IOException {

		OAuthConsumer consumer = getOauthConsumer();

		try {
			HttpPost httpPost = new HttpPost(request.getURI());
			ByteArrayEntity myEntity = new ByteArrayEntity(byteArray);
			httpPost.setEntity(myEntity);
			consumer.sign(httpPost);
			String oauthHeaderValue = getOauthHeaderValue(httpPost);
			
			System.out.println("header value : " + oauthHeaderValue);

			request.getHeaders().add(OAuth.HTTP_AUTHORIZATION_HEADER, oauthHeaderValue);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return execution.execute(request, byteArray);
	}

	public abstract OAuthConsumer getOauthConsumer();
	
	public abstract String getOauthHeaderValue(HttpPost httpPost);
	
	
}
