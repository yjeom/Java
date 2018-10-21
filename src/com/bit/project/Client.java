package com.bit.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client extends Thread{
	static Socket sock=null;
	static Thread thr=null;
   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);
      
      System.out.print("닉네임:");
      String name=sc.nextLine();
      
      InputStream in=null;
      OutputStream out=null;
      
      PrintStream ps=null;
      BufferedReader br=null;
      
      try {
         sock=new Socket("localhost",8080);
//         ClientThread thr=new ClientThread(sock);
//         Client me=new Client();
//         Userlist userlist=new Userlist(sock);
//         thr=new Thread(userlist);
//         me.start();
         thr.start();
         
         out=sock.getOutputStream();
         in=sock.getInputStream();
         ps=new PrintStream(out);
         br=new BufferedReader(new InputStreamReader(in));
         
         ps.println(name);
         ps.flush();
         
//         while(true){
//            
//            System.out.print('>');
//            String msg=sc.nextLine();
//            
//            ps.println(msg);
//            if(msg.equals("exit"))break;
//            System.out.println(br.readLine());
//            
//            
//            
//         }
      } catch (UnknownHostException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }

   }
   
   
   
//   @Override
//	public void run() {
//	      InputStream is=null;
//	      BufferedReader br=null;
//	      try {
//	         is=sock.getInputStream();
//	         br=new BufferedReader(new InputStreamReader(is));
//	         String msg=null;
//	         while((msg=br.readLine())!=null){
//	            if(msg.contains("ULIST//")){//"ULIST//"이 붙으면 접속자 리스트
////	               receiveUserList(msg);
//	            }
//	            else{
//	            System.out.println(msg);
//	            }
//	         }
//	         
//	         
//	         
//	         
//	      } catch (IOException e) {
//	         // TODO Auto-generated catch block
//	         e.printStackTrace();
//	      }finally{
//	         try {
//	            br.close();
//	            is.close();
//	            if(sock!=null)
//	            sock.close();
//	         } catch (IOException e) {
//	            // TODO Auto-generated catch block
//	            e.printStackTrace();
//	         }
//	      }
//	   
//	}

}
//class ClientThread extends Thread{
//   Socket socket =null;
//   
//   public ClientThread(Socket sock) {
//      this.socket=sock;
//   }
//
//
//   @Override
//   
//   public void run() {
//
//      InputStream is=null;
//      BufferedReader br=null;
//      try {
//         is=socket.getInputStream();
//         br=new BufferedReader(new InputStreamReader(is));
//         String msg=null;
//         while((msg=br.readLine())!=null){
//            if(msg.contains("ULIST//")){//"ULIST//"이 붙으면 접속자 리스트
////               receiveUserList(msg);
//               
//            }
//            else{
//            System.out.println(msg);
//            }
//         }
//         
//         
//         
//         
//      } catch (IOException e) {
//         // TODO Auto-generated catch block
//         e.printStackTrace();
//      }finally{
//         try {
//            br.close();
//            is.close();
//            if(socket!=null)
//            socket.close();
//         } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//         }
//      }
//   
//   
//   }
//
//
////   private void receiveUserList(String msg) {
////      String temp=msg.replace("ULIST//", "");
////      System.out.println("접속:"+temp);
////      
////   }
//}