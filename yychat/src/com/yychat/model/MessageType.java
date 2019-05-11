package com.yychat.model;

public interface MessageType {
	String message_LoginFailure="0";//字符串常量
	String message_LoginSuccess="1";
	String message_Common="2";
	String message_RequestOnlineFriend="3";//客户端请求获得在线好友信息 
	String message_OnlineFriend="4";//服务器返回在线好友信息
	String message_NewOnlineFriend="5";

}
