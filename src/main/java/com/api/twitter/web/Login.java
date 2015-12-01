package com.api.twitter.web;

import static com.api.twitter.utils.TwitterConstants.BASE_TEMPLATE;
import static com.api.twitter.utils.TwitterConstants.FORM_USERNAME;
import static com.api.twitter.utils.TwitterConstants.LOGIN_TEMPLATE;
import static com.api.twitter.utils.TwitterConstants.LOGIN_URL;
import static com.api.twitter.utils.TwitterConstants.TWEETS_TEMPLATE;
import static com.api.twitter.utils.TwitterConstants.TWEET_URL;
import static com.api.twitter.utils.TwitterConstants.WELCOME_TEMPLATE;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import spark.Request;
import spark.Response;
import spark.Session;
import spark.Spark;

import com.api.twitter.service.TwitterOauth;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class Login {

	private static Logger LOGGER = LoggerFactory.getLogger(Login.class);

	public static void main(String... args) {
		Configuration configuration = new Configuration();
		configuration.setClassForTemplateLoading(Login.class, "/");

		Spark.get("/", (Request request, Response response) -> {
			StringWriter writer = new StringWriter();
			Map<String, Object> map = new HashMap<>();
			Template helloTemplate = configuration.getTemplate(BASE_TEMPLATE);

			helloTemplate.process(map, writer);
			return writer;
		});

		Spark.get(LOGIN_URL, (Request request, Response response) -> {

			StringWriter writer = new StringWriter();
			Map<String, Object> map = new HashMap<>();

			Template helloTemplate;

			TwitterOauth oauth = new TwitterOauth();
			ResponseEntity<String> requestToken = validateAndLoginUser(oauth, request);

			if (requestToken.getStatusCode() == HttpStatus.OK) {
				oauth.populateTokenSecretFromResponse(requestToken);

			}
			System.out.println(oauth.getOauthToken());
			System.out.println(oauth.getOauthTokenSecret());
			System.out.println(oauth.isOauthCallbackConfirmed());

			ResponseEntity<String> authentication = oauth.authenticateUser(oauth.getOauthToken());

			System.out.println(authentication.getStatusCode());
			helloTemplate = configuration.getTemplate(WELCOME_TEMPLATE);
			map.put("name", authentication.getBody());
			helloTemplate.process(map, writer);
			return writer;
		});

		Spark.post(TWEET_URL, (Request request, Response response) -> {

			StringWriter writer = new StringWriter();
			Map<String, Object> map = new HashMap<>();

			Template helloTemplate = configuration.getTemplate(TWEETS_TEMPLATE);

			/*
			 * boolean validSession = validateSession(request); if
			 * (!validSession) { return returnLoginScreen(configuration, writer,
			 * map); }
			 */

			map.put("tweets", new String[] { "tweet1", "tweet2", "tweet3" });

			helloTemplate.process(map, writer);
			return writer;
		});
	}

	private static Object returnLoginScreen(Configuration configuration, StringWriter writer, Map<String, Object> map)
			throws IOException, TemplateException {
		Template helloTemplate;
		helloTemplate = configuration.getTemplate(LOGIN_TEMPLATE);
		helloTemplate.process(map, writer);
		return writer;
	}

	private static ResponseEntity<String> validateAndLoginUser(TwitterOauth twitterOauth, Request request) {

		ResponseEntity<String> response = twitterOauth.getRequestToken();

		if (response.getStatusCode() == HttpStatus.OK) {
			twitterOauth.populateTokenSecretFromResponse(response);
		}
		ResponseEntity<String> authentication = twitterOauth.authenticateUser(twitterOauth.getOauthToken());
		
		System.out.println(authentication.getHeaders().toString());
		System.out.println(authentication.getStatusCode().toString());
		LOGGER.debug(authentication.getStatusCode().toString());

		return response;
	}

	private static void createSession(Request request, final String userName) {
		Session session = request.session(true);
		session.attribute("name", userName);
	}

}
