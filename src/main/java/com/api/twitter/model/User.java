package com.api.twitter.model;

import java.util.Collections;

public class User {

	private String userName;
	private String password = new String();

	public User(String userName) {
		this.userName = userName;
	}

	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

}
