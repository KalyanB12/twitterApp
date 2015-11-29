package com.api.twitter.web;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

import spark.Request;
import spark.Response;
import spark.Session;
import spark.Spark;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class Login {

	private static String BASE_URL = "/";
	private static String LOGIN_URL = "/login";
	private static String TWEET_URL = "/tweet";

	private static String LOGIN_TEMPLATE = "template/login.ftl";
	private static String WELCOME_TEMPLATE = "template/welcome.ftl";
	private static String TWEETS_TEMPLATE = "template/tweets.ftl";

	private static String FORM_USERNAME = "username";
	private static String FORM_PASSWORD = "password";

	public static void main(String... args) {
		Configuration configuration = new Configuration();
		configuration.setClassForTemplateLoading(Login.class, "/");

		Spark.get(BASE_URL, (Request request, Response response) -> {
			StringWriter writer = new StringWriter();
			Map<String, Object> map = new HashMap<>();
			Template helloTemplate = configuration.getTemplate(LOGIN_TEMPLATE);

			helloTemplate.process(map, writer);
			return writer;
		});

		Spark.post(LOGIN_URL, (Request request, Response response) -> {

			StringWriter writer = new StringWriter();
			Map<String, Object> map = new HashMap<>();

			Template helloTemplate;

			boolean valid = validateAndLoginUser(request);

			if (!valid) {
				return returnLoginScreen(configuration, writer, map);
			}

			String userName = request.queryParams(FORM_USERNAME);

			createSession(request, userName);

			helloTemplate = configuration.getTemplate(WELCOME_TEMPLATE);
			map.put("name", userName);
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

	
	private static String CONSUMER_KEY = "XYZ";
	private static String SECRET = "SECRET";
	private static String ACCESS_TOKEN = "ABC";
	private static String TOKEN_SECRET = "ABCSECRET";
	
//	mvn install && mvn exec:java -pl hbc-example -Dconsumer.key=XYZ -Dconsumer.secret=SECRET -Daccess.token=ABC -Daccess.token.secret=ABCSECRET
	
	private static boolean validateAndLoginUser(Request request) {
		String userName = request.queryParams(FORM_USERNAME);
		String password = request.queryParams(FORM_PASSWORD);
		
		
		Authentication hosebirdAuth = new OAuth1("consumerKey", "consumerSecret", "token", "tokenSecret");	
		

		return true;
	}

	private static void createSession(Request request, final String userName) {
		Session session = request.session(true);
		session.attribute("name", userName);
	}

	/*
	 * private static boolean validateSession(Request request) { Session session
	 * = request.session(false); return session == null ? true : false; }
	 */

}
