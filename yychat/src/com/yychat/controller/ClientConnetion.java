package com.yychat.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import com.yychat.model.Message;
import com.yychat.model.User;

public class ClientConnetion {
	//public static Socket s;//静态成员变量
	public Socket s;//非静态成员变量
	public static HashMap hmSocket=new HashMap<String,Socket>();
			public ClientConnetion(){
		try {//异常处理结构
			s=new Socket("127.0.0.1",3456);//本机地址，回测地址
			System.out.println("客户端Socket"+s);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	public Message loginValidate(User user){
		//对象的输入输出流
		ObjectOutputStream oos;
		Message mess=null;
		try {//把字节输出流对象 包装成 对象输出流对象
			 oos=new ObjectOutputStream(s.getOutputStream());
			 oos.writeObject(user);
			 
			 
			 
			 ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
			 mess=(Message)ois.readObject();//接收
			 
			 //登陆成功保存Socket对象到hmSocket中
             if(mess.getMessageType().equals(Message.message_LoginSuccess)){
            	 System.out.println(user.getUserName()+" 号登陆成功");
				 hmSocket.put(user.getUserName(),s);
				 new ClientReceiverThread(s).start();
			 }
			 
		} catch (IOException | ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		return mess;
	}
}
