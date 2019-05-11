package controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.yychat.model.Message;

public class ServerReceiverThread extends Thread{//必须要有run方法
	Socket s;
	HashMap hmSocket;
	Message mess; 
	String sender;
	ObjectOutputStream oos;
	public ServerReceiverThread(Socket s,HashMap hmSocket){
		this.s=s;
		this.hmSocket=hmSocket;
		
		
	}
	public void run(){
		ObjectInputStream ois;
		
		while(true){
			try {
				//接受信息
				ois=new ObjectInputStream(s.getInputStream());
				mess=(Message)ois.readObject();//接收用户发送来的聊天信息，阻塞
				System.out.println("等待用户发送聊天信息");
				System.out.println(mess.getSender()+"对"+mess.getReceiver()+"说:"+mess.getContent());
				sender=mess.getSender();
				
				//转发信息
				if(mess.getMessageType().equals(Message.message_Common)){
					Socket s1=(Socket)StartServer.hmSocket.get(mess.getReceiver());
					sendMessage(s1,mess);
					System.out.println("服务器转发了信息"+mess.getSender()+"对"+mess.getReceiver()+"说:"+mess.getContent());
				}
				
				//第2步，返回在线好友信息到客户端
				if(mess.getMessageType().equals(Message.message_RequestOnlineFriend)){
					//首先要拿到在线好友信息
					Set friendSet=StartServer.hmSocket.keySet();//得到好友名字集合
					Iterator it=friendSet.iterator();//迭代器，目的是对friendSet集合中的元素进行遍历
					String friendName;
					String friendString=" ";
					while(it.hasNext()){
						friendName=(String)it.next();//获取下一个好友名字
						System.out.println("好友名字："+friendName);
						if(!friendName.equals(mess.getSender())){//排除自己
							System.out.println("运行了该代码");
							friendString=friendString+friendName+" ";//为什么要加空格？
						}
							
					}
					System.out.println("全部好友的名字："+friendString);
					
					
					//给mess赋值
					mess.setContent(friendString);
					mess.setReceiver(sender);
					mess.setSender("Server");
					mess.setMessageType(Message.message_OnlineFriend);
					
					//发送
					Socket s1=(Socket)hmSocket.get(sender);
					sendMessage(s1,mess);
				}
				
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	public void sendMessage(Socket s,Message mess) throws IOException {
		oos=new ObjectOutputStream(s.getOutputStream());
		oos.writeObject(mess);//转发Massage
	}
	
}