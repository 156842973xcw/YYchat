package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.yychat.model.Message;
import com.yychat.model.User;

public class StartServer {
	ServerSocket ss;
	Socket s;
	Message mess;
	String userName;
	String passWord;
	ObjectOutputStream oos;
	
	
	
	public static HashMap hmSocket=new HashMap<String,Socket>();//泛型，通用类
	
	public StartServer(){
		try {
	        ss=new ServerSocket(3456);//服务器端口监听3456
	        System.out.println("服务器已经启动，监听3456端口...");
	        while(true){//?多线程问题
	        	s=ss.accept();//等待客户端建立连接
	            System.out.println(s);//输出连接对象
	        
	            //字节输入流 包装成 对象输入流
	            ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
	            User user=(User)ois.readObject();//接受用户登录对象user
	            userName=user.getUserName();
	            passWord=user.getPassWord();
	            System.out.println(userName);
	            System.out.println(passWord);
	            
	            //使用数据库来验证用户名和密码
	            //1.加载驱动程序
	            Class.forName("com.mysql.jdbc.Driver");
	            
	            //2.建立连接,默认GBK
	            //String url="jdbc:mysql://127.0.0.1:3306/yychat";
	            String url="jdbc:mysql://127.0.0.1:3306/yychat?useUnicode=true&characterEncoding=UTF-8";
	            String dbuser="root";
	            String dbpass="";
	            Connection conn=DriverManager.getConnection(url,dbuser,dbpass);
	            
	            //3.建立一个preparedStatement
	            String user_Login_Sql="select * from user where username=? and password=?";
	            PreparedStatement ptmt=conn.prepareStatement(user_Login_Sql);
	            ptmt.setString(1,userName);
	            ptmt.setString(2,passWord);
	            
	            
	            //4.执行preparedStatement
	            ResultSet rs=ptmt.executeQuery();
	            
	            //5.判断结果集
	            boolean loginSuccess=rs.next();
	            
	            
	            //Server端验证密码是否“123456”
	            mess=new Message();
	            mess.setSender("Server");
        	    mess.setReceiver(user.getUserName());
	            //if(user.getPassWord().equals("123456")){//不能用“==”，对象比较
        	    if(loginSuccess){
        	    //消息传递，创建Message对象
	        	    mess.setMessageType(Message.message_LoginSuccess);//验证通过
	        	    
	        	    //从数据库relation表中读取好友信息来更新好友列表1、服务器读好友数据出来
	        	    String friend_Relation_Sql="select slaveuser from relation where majoruser=? and relationtype='1'";
	        	    ptmt=conn.prepareStatement(friend_Relation_Sql);
	        	    ptmt.setString(1,userName);
	        	    rs=ptmt.executeQuery();
	        	    String friendString="";
	        	    while(rs.next()){//移动结果集中的指针，一个个的取出好友的名字
	        	    	//rs.getString(1);
	        	    	friendString=friendString+rs.getString("slaveuser")+" ";
	        	    }
	        	    mess.setContent(friendString);
	        	    System.out.println(userName+"的全部好友："+friendString);
	            }
	            else{
	        	    mess.setMessageType(Message.message_LoginFailure);//验证不通过
	            }
	            sendMessage(s,mess);
	            
	            if(loginSuccess){
	            	//激活新上线好友的图标步骤1、向其他所有用户（比该用户先登录）发送信息
	            	mess.setMessageType(Message.message_NewOnlineFriend);
	            	mess.setSender("Server");
	            	mess.setContent(this.userName);//激活图标的用户名
	            	Set friendSet=hmSocket.keySet();
	            	Iterator it=friendSet.iterator();
	            	String friendName;
	            	while(it.hasNext()){
	            		friendName=(String)it.next();//取出一个好友名字
	            		mess.setReceiver(friendName);
	            		Socket s1=(Socket)hmSocket.get(friendName);
	            		/*oos=new ObjectOutputStream(s1.getOutputStream());
	            		 oos.writeObject(mess);*/
	            		sendMessage(s1,mess);
	            	}
	            	
	            	
	            	//保存每一个用户的Socket
	                hmSocket.put(userName, s);
	                System.out.println("保存用户的Socket"+userName+s);
	                //如何接收客户端的聊天信息？另建一个线程来接收聊天信息
	                new ServerReceiverThread(s,hmSocket).start();//创建线程，并让线程就绪
	                System.out.println("启动线程成功");
	            }
	            
	        }

		} catch (IOException | ClassNotFoundException | SQLException e) {
			
			e.printStackTrace();
		}
	}


	private void sendMessage(Socket s,Message mess) throws IOException {
		oos=new ObjectOutputStream(s.getOutputStream());
		oos.writeObject(mess);
	}

	
	public static void main(String[] args) {
		

	}

}
