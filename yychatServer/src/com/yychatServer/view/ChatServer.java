package com.yychatServer.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import controller.StartServer;

public class ChatServer extends JFrame implements ActionListener{//实现接口
	JButton jb1;
	JButton jb2;
	JPanel jp;
	
	public ChatServer(){
		jb1=new JButton("启动服务器");
		jb1.addActionListener(this);//给按钮添加监听器
		jb2=new JButton("停止服务器");
		jb2.addActionListener(this);
		jp=new JPanel();
		jp.add(jb1);
		jp.add(jb2);
		
		
		this.add(jp);
		this.setTitle("YYChat服务器");
		this.setSize(240,240);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
	}

	public static void main(String[] args) {
		ChatServer chatServer=new ChatServer();
		

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource()==jb1) new StartServer();//监听端口，建立和客户端的连接
		if(arg0.getSource()==jb2) System.exit(0);//退出程序
	}

}
