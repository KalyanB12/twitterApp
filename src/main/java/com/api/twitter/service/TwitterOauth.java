package com.api.twitter.service;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.api.twitter.model.OauthConsumer;
import com.google.common.base.Splitter;

import static com.api.twitter.utils.TwitterConstants.*;

@Service
public class TwitterOauth implements Oauth {

	private static Logger LOGGER = LoggerFactory.getLogger(TwitterOauth.class);


	private String oauthToken;
	
	private String oauthTokenSecret;
	
	private boolean oauthCallbackConfirmed;

	public String getOauthToken() {
		return oauthToken;
	}

	public String getOauthTokenSecret() {
		return oauthTokenSecret;
	}

	public boolean isOauthCallbackConfirmed() {
		return oauthCallbackConfirmed;
	}

	public static void main(String... args) {
		TwitterOauth oauth = new TwitterOauth();

		ResponseEntity<String> requestToken = oauth.getRequestToken();
		System.out.println(requestToken);

		System.out.println(requestToken.getStatusCode());
		if (requestToken.getStatusCode() == HttpStatus.OK) {
			oauth.populateTokenSecretFromResponse(requestToken);

		}
		System.out.println(oauth.oauthToken);
		System.out.println(oauth.oauthTokenSecret);
		System.out.println(oauth.oauthCallbackConfirmed);

		ResponseEntity<String> authentication = oauth.authenticateUser(oauth.oauthToken);

	}

	public void populateTokenSecretFromResponse(ResponseEntity<String> requestToken) {
		
		Iterable<String> iterable = Splitter.on("&").trimResults().omitEmptyStrings().split(requestToken.getBody());
		
		for (String str : iterable) {
			Iterable<String> keyValues = Splitter.on("=").trimResults().omitEmptyStrings().split(str);
			Iterator<String> iterator = keyValues.iterator();
			
			switch (iterator.next()) {
			case OAUTH_TOKEN_STR:
				oauthToken = iterator.next();
				break;
			case OAUTH_TOKEN_SECRET_STR:
				oauthTokenSecret = iterator.next();
				break;
			case OAUTH_CALLBACK_STR:
				oauthCallbackConfirmed = Boolean.parseBoolean(iterator.next());
				break;
			}
		}
	}

	public ResponseEntity<String> getRequestToken() {
		RestTemplate template = new RestTemplate();

		template.getInterceptors().add(new OauthInterceptor(new OauthConsumer(CONSUMER_KEY, CONSUMER_SECRET)));

		HttpEntity<String> entity = new HttpEntity<String>(new HttpHeaders());

		ResponseEntity<String> response = template.exchange(BASE_URL + REQUEST_TOKEN_URL, HttpMethod.POST, entity,
				String.class);
		
		LOGGER.debug(response.toString());

		return response;
	}

	public ResponseEntity<String> authenticateUser(String oauthToken) {

		RestTemplate template = new RestTemplate();
		HttpEntity<String> entity = new HttpEntity<String>(new HttpHeaders());
		LOGGER.debug(BASE_URL + AUTHENTICATE_URL + "?oauth_token=" + oauthToken);

		ResponseEntity<String> response = template.exchange(BASE_URL + AUTHENTICATE_URL + "?oauth_token=" + oauthToken,
				HttpMethod.GET, entity, String.class);
		LOGGER.debug("" + response);
		return response;
	}

}
