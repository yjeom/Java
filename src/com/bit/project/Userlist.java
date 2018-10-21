package com.bit.project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class Userlist extends JFrame implements Runnable{
	static String name=null;
	static Socket socket=null;
	
	Userlist userlist;
	CardLayout cl;
	Dialog close;
	Panel p;
	Font fontTitle, fontReg, fontBld, fontBig;
	JButton[] btn = new JButton[3];
	List chatList, numList;
	ArrayList userList=null;
	Panel pUser;
	Panel pChat;
	BorderLayout bl =null;
	public void closeDia() {
		close = new Dialog(this);
		close.setTitle("종료");
		Panel cPanel = new Panel(new GridLayout(2,1));
		Label cLabel = new Label("종료하시겠습니까?");
		cLabel.setFont(fontTitle);
		cLabel.setAlignment(Label.CENTER);
		cPanel.add(cLabel);
		Panel cBtnPanel = new Panel();
		Button closeBtnY = new Button("네");
		closeBtnY.setFont(fontBig);
		closeBtnY.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		cBtnPanel.add(closeBtnY);
		Button closeBtnN = new Button("아니오");
		closeBtnN.setFont(fontBig);
		closeBtnN.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				close.setVisible(false);
				btn[2].setBackground(Color.WHITE);
                btn[2].setFont(fontReg);
			}
		});
		cBtnPanel.add(closeBtnN);
		cPanel.add(cBtnPanel);
		close.setSize(300, 150);
		close.setLocation(300, 300);
		close.setVisible(false);
		close.setResizable(false);
		close.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				close.setVisible(false);
			}
		});
		close.add(cPanel);
	}//closeDia
	
	public void userIn(){
		
		p.removeAll();
	    bl = new BorderLayout();
		pUser = new Panel(bl);
        Label userTitle = new Label("접속자");
        userTitle.setFont(fontTitle);
        pUser.add(userTitle, BorderLayout.NORTH);
    	List in=new List();
        for(int i=0;i<userList.size();i++){
        	String inName=userList.get(i).toString();
        	in.add(inName);
        }
        in.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println(in.getSelectedItem());
				OneChat one=new OneChat(socket, name) ;
			}
		});
    	pUser.add(in,BorderLayout.CENTER);
    	p.add(pUser);
    	this.revalidate();
    	this.repaint();
	}
	public void chattingRoom(){
			p.removeAll();
	        Label chatTitle = new Label("채팅방");
	        chatTitle.setFont(fontTitle);
	        pChat.add(chatTitle, BorderLayout.NORTH);
	        bl = new BorderLayout();
	        Panel listPanel = new Panel(bl);
	        Panel chatListTop = new Panel(new GridLayout(1,2));
	        Font fontSTitle = new Font("", Font.BOLD, 12);
	        Label roomTitle = new Label("방명");
	        roomTitle.setFont(fontSTitle);
	        roomTitle.setAlignment(Label.LEFT);
	        Label numTitle = new Label("접속자수");
	        numTitle.setFont(fontSTitle);
	        numTitle.setAlignment(Label.LEFT);
	        chatListTop.add(roomTitle);
	        chatListTop.add(numTitle);
	        listPanel.add(chatListTop, BorderLayout.NORTH);
	        
	        Panel chatListBottom = new Panel(new GridLayout(1,2));
	        chatList = new List();
	        chatList.add("방1");
	        chatList.add("방2");
	        chatList.add("방3");
	        numList = new List();
	        numList.add("3");
	        numList.add("1");
	        numList.add("2");
	        chatList.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					numList.select(chatList.getSelectedIndex());
				}	
	        });
	        numList.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					chatList.select(numList.getSelectedIndex());
				}	
	        });
	        
	        chatListBottom.add(chatList);
	        chatListBottom.add(numList);
	        listPanel.add(chatListBottom, BorderLayout.CENTER);
	        pChat.add(listPanel, BorderLayout.CENTER);
	        p.add(pChat);
	        this.revalidate();
	    	this.repaint();
	}
    public Userlist() {
    	
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.getImage("./logo.png");
        setIconImage(img);
        setTitle("BitTalk");
        fontReg = new Font("", Font.PLAIN, 12);
        fontBld = new Font("", Font.BOLD, 12);
        fontTitle = new Font("", Font.BOLD, 14);
        fontBig = new Font("", Font.PLAIN, 14);
        BorderLayout bl = new BorderLayout();
        cl = new CardLayout();
        Panel p0 = new Panel(bl);
        Panel pMenu = new Panel(new GridLayout(1,5));
        p = new Panel(cl);
        bl = new BorderLayout();
        pUser = new Panel(bl);
        bl = new BorderLayout();
        pChat = new Panel(bl);
//        Panel pUserList = new Panel();
//        Panel pChatList = new Panel();
        closeDia();

        String[] btnLabel = {"접속자", "채팅방", "종료"};
        for(int i=0; i<btnLabel.length; i++){
        	btn[i] = new JButton(btnLabel[i]);
        }//for
        btnLook(0);
        btn[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	cl.first(p);
            	btnLook(0);
            	userIn();
            }
        });
        btn[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	cl.first(p);
            	btnLook(1);
            	chattingRoom();
            }
        });
        btn[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	btnLook(2);
            	close.setVisible(true);
            }
        });
        pMenu.add(btn[0]);
        pMenu.add(btn[1]);
        pMenu.add(btn[2]);
        Label blankMenu = new Label("");
        blankMenu.setBackground(Color.LIGHT_GRAY);
        pMenu.add(blankMenu);
        blankMenu = new Label("");
        blankMenu.setBackground(Color.LIGHT_GRAY);
        pMenu.add(blankMenu);
        
        p0.add(pMenu, BorderLayout.NORTH);

        
        userList = new ArrayList();
        
        userIn();

        
        
        addWindowListener(new WindowAdapter() {
        	@Override
        	public void windowClosing(WindowEvent e) {
        		close.setVisible(true);
        	}
        });
        setAlwaysOnTop(true);
        p0.add(p, BorderLayout.CENTER);
        add(p0);
        setBounds(200, 200, 400, 550);
        setVisible(true);
    }//Userlist

    public void btnLook(int a) {
    	if(a!=2){
	    	for(int i=0; i<btn.length; i++){
	        	if(a==i){
	        		btn[i].setBackground(new Color(0,168,255));
	                btn[i].setFont(fontBld);
	        	}else{
	        		btn[i].setBackground(Color.WHITE);
	                btn[i].setFont(fontReg);
	        	}//if else
	        }//for
    	}else{
    		btn[2].setBackground(new Color(255,0,0));
	        btn[2].setFont(fontBld);
    	}//if else
	}//btnLook

	public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("닉네임:");
        name=sc.nextLine();
        
        InputStream in=null;
        OutputStream out=null;
        
        PrintStream ps=null;
        BufferedReader br=null;
    	
        try {
            socket=new Socket("localhost",8080);
            
            out=socket.getOutputStream();
            in=socket.getInputStream();
            ps=new PrintStream(out);
            br=new BufferedReader(new InputStreamReader(in));
            
            ps.println(name);
            ps.flush();
            
       
         } catch (UnknownHostException e) {
            e.printStackTrace();
         } catch (IOException e) {
            e.printStackTrace();
         }

      
		
		
		
        Userlist me = new Userlist();
        Thread thr=new Thread(me);
        thr.start();
    }//main
	
	public void receiveUserList(String msg) {
		      String temp=msg.replace("ULIST//", "");
//		      System.out.println("userlist"+temp);
		      userList.add(temp);
		   }
	@Override
	public void run() {

		InputStream is=null;
		BufferedReader br=null;
		Thread thr=new Thread(userlist);
		try {
			is=socket.getInputStream();
			br=new BufferedReader(new InputStreamReader(is));
			String username=null;
			while((username=br.readLine())!=null){
				
				if(username.equals("ULISTSTART//")){
					userList=new ArrayList();
					}//"ULIST//"이 붙으면 접속자 리스트
				else if(username.contains("ULIST//")){
		        	  if(username.equals("ENDULIST//")){
		        		 userIn();
		        	  }//list end
		        	  else{
		        	  receiveUserList(username);
		        	  }
		            }
			

			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				br.close();
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
	}
	
	
}//class Userlist



