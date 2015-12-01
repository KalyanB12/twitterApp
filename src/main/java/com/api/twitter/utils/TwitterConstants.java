package com.api.twitter.utils;

public interface TwitterConstants {
	public static final String CONSUMER_KEY = "T827qx2XhJpJzj6qmEQPMiiFq";
	public static final String CONSUMER_SECRET = "T3xw4Xj3LgwLJD4XF7p6gSaiNgk04nI5rKl2BQ3lnIHRw7kVDv";

	public static final String OAUTH_TOKEN_STR = "oauth_token";
	public static final String OAUTH_TOKEN_SECRET_STR = "oauth_token_secret";
	public static final String OAUTH_CALLBACK_STR = "oauth_callback_confirmed";

	
	public static final String ROOT_PATH = "/";
	public static final String BASE_URL = "https://twitter.com";

	public static final String REQUEST_TOKEN_URL = "/oauth/request_token";
	public static final String AUTHENTICATE_URL = "/oauth/authorize";

	public static String LOGIN_URL = "/login";
	public static String TWEET_URL = "/tweet";

	public static String BASE_TEMPLATE = "template/basetemplate.ftl";
	public static String LOGIN_TEMPLATE = "template/login.ftl";
	public static String WELCOME_TEMPLATE = "template/welcome.ftl";
	public static String TWEETS_TEMPLATE = "template/tweets.ftl";

	public static String FORM_USERNAME = "username";
	public static String FORM_PASSWORD = "password";


}
