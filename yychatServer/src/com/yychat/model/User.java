package com.yychat.model;

import java.io.Serializable;

public class User implements Serializable{
	private String userName;//成员变量
	private String passWord;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;//局部变量给成员变量赋值
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	
}
