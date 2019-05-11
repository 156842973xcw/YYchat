package com.yychat.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;

public class FriendList extends JFrame implements ActionListener,MouseListener {//容器，接口   
	public static HashMap hmFriendChat1=new HashMap<String,FriendChat1>();

	//成员变量
	CardLayout cardLayout;
	//第一张卡片
    JPanel myFriendPanel;
    JButton myFriendButton;//北部
    
    JScrollPane myFriendListJScrollPane;
    JPanel myFriendListJPanel;
    public static final int MYFRIENDCOUNT=51;
	private static final int MYFRIENDCOUNT1 = 0;
    JLabel[] myFriendJLabel=new JLabel[MYFRIENDCOUNT];//50好友数组，对参数组
    
    JPanel	myStrangerBlackListPanel;
    JButton myStrangerButton;
    JButton blackListButton;
    
    //第二个卡片
    JPanel myStrangerPanel;
    //北部
    JPanel myFriendStrangerPanel;
    JButton myFriendButton1;
    JButton myStrangerButton1;
    //中部
    JPanel myStrangerListJPanel;
    public static final int MYFRIENDCOUNT11=51;
    JLabel[] myStrangerJLabel=new JLabel[MYFRIENDCOUNT11];
    JScrollPane myStrangerListJScrollPane;
    
    //南部
    JButton blackListButton1;
	
    
    
    String userName;//成员变量
        
    public  FriendList(String userName){//形参
    	this.userName=userName;
    	//创建第一张卡片
    	myFriendPanel=new JPanel(new BorderLayout());//布局问题，边界布局
    	//System.out.println(myFriendPanel.getLayout());
        
    	//北部
    	myFriendButton= new JButton("我的好友");
    	myFriendPanel.add(myFriendButton,"North");
    	
    	//中部
    	myFriendListJPanel=new JPanel(new GridLayout(MYFRIENDCOUNT-1,1));//网格布局
    	for(int i=1;i<MYFRIENDCOUNT;i++){
			myFriendJLabel[i]=new JLabel(i+"",new ImageIcon("images/yy0.gif"),JLabel.LEFT);
			myFriendJLabel[i].setEnabled(false);
			//激活自己的图标
			//if(Integer.parseInt(userName)==i) myFriendJLabel[i].setEnabled(true);		
			myFriendJLabel[i].addMouseListener(this);
			myFriendListJPanel.add(myFriendJLabel[i]);   
		}
    	//激活自己的图标
    	//myFriendJLabel[Integer.parseInt(userName)].setEnabled(true);
    	/*myFriendListJScrollPane=new JScrollPane();
    	myFriendListJScrollPane.add(myFriendListJPanel);*/
    	myFriendListJScrollPane=new JScrollPane(myFriendListJPanel);
    	myFriendPanel.add(myFriendListJScrollPane);
    	
    	//南部
    	myStrangerBlackListPanel=new JPanel(new GridLayout(2,1));//网格布局
    	//System.out.println(myStrangerBlackListPAnel.getLayout());
    	myStrangerButton=new JButton("我的陌生人");
    	myStrangerButton.addActionListener(this);//事件监听
    	blackListButton=new JButton("黑名单");
    	myStrangerBlackListPanel.add(myStrangerButton);
    	myStrangerBlackListPanel.add(blackListButton);
    	myFriendPanel.add(myStrangerBlackListPanel,"South");
    	
    	
    	//创建第二张卡片
    	myStrangerPanel=new JPanel(new BorderLayout());//布局的问题，边界布局
    	//北部
    	myFriendStrangerPanel=new JPanel(new GridLayout(2,1));
    	myFriendButton1=new JButton("我的好友");
    	myFriendButton1.addActionListener(this);//时间监听
    	myStrangerButton1 = new JButton("我的陌生人");
    	myFriendStrangerPanel.add(myFriendButton1);
    	myFriendStrangerPanel.add(myStrangerButton1);
    	myStrangerPanel.add(myFriendStrangerPanel,"North");
    	
    	
    	//中部
    	myStrangerListJPanel = new JPanel(new GridLayout(MYFRIENDCOUNT-1,1));//网格布局
    	for(int i=1;i<MYFRIENDCOUNT;i++){
			myStrangerJLabel[i]=new JLabel(i+"",new ImageIcon("images/yy4.gif"),JLabel.LEFT);
			myStrangerListJPanel.add(myStrangerJLabel[i]);   
		}
    	/*myStrangerListJScrollPane=new JScrollPane();
    	myStrangerListJScrollPane.add(myFriendListJPanel);*/
    	myStrangerListJScrollPane=new JScrollPane(myStrangerListJPanel);
    	myStrangerPanel.add(myStrangerListJScrollPane);
    	//南部
    	blackListButton1=new JButton("黑名单");
    	myStrangerPanel.add(blackListButton1,"South");
    	//添加两个卡片
    	cardLayout=new CardLayout();//卡片布局    
    	this.setLayout(cardLayout);
    	this.add(myFriendPanel,"1");
    	this.add(myStrangerPanel,"2");
    	
    	
    	this.setSize(150,500);
    	this.setTitle(userName+"的好友列表");
    	this.setIconImage(new ImageIcon("images/yy2.gif").getImage());
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.setLocationRelativeTo(null);
    	this.setVisible(true);
    }
    
    public static void main(String[] args){
		//FriendList friendList=new FriendList("pdh");
    }
    
    public void setEnabledNewOnlineFriend(String newonlineFriend){
    	myFriendJLabel[Integer.parseInt(newonlineFriend)].setEnabled(true);
    }
    
    public void setEnabledOnlineFriend(String onlineFriend){
    	//激活在线好友图标 
    	String[] friendName=onlineFriend.split(" ");
    	//System.out.println("friendName数组中的第一个元素："+friendName[0]);
    	int count=friendName.length;
    	
    	System.out.println("friendName数组中的元素个数："+count);
    	for(int i=1;i<count;i++){
    		System.out.println("friendName数组中的第"+i+"元素:"+friendName[i]);
    		myFriendJLabel[Integer.parseInt(friendName[i])].setEnabled(true);
    	}
    	
    	
    }

	@Override
	public void actionPerformed(ActionEvent e) {//响应事件的方法
		// TODO Auto-generated method stub
		if(e.getSource()==myStrangerButton) cardLayout.show(this.getContentPane(),"2");
		if(e.getSource()==myFriendButton1) cardLayout.show(this.getContentPane(),"1");
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getClickCount()==2){
			JLabel jlbl=(JLabel)arg0.getSource();
			String receiver=jlbl.getText();
			//new FriendChat(this.userName,receiver);
			//new Thread(new FriendChat(this.userName,receiver)).start();
			FriendChat1 friendCaht1=new FriendChat1(this.userName,receiver);//对象名friendCaht1可以引用我们创建的对象  
			hmFriendChat1.put(userName+"to"+receiver,friendCaht1);
		}
		
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		JLabel jlbl1=(JLabel)arg0.getSource();
		jlbl1.setForeground(Color.red);
		
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		JLabel jlbl1=(JLabel)arg0.getSource();
		jlbl1.setForeground(Color.BLACK);
		
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
    
    
    
}
