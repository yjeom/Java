package com.bit.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Server extends Thread {
   Socket sock;
   static HashMap<String, Socket> map;
   String name;

   public Server(Socket socket) {

      this.sock = socket;

      InputStream is = null;
      OutputStream os = null;
      PrintWriter pw = null;
      BufferedReader br = null;

      try {
         is = socket.getInputStream();
         os = socket.getOutputStream();
         br = new BufferedReader(new InputStreamReader(is));
         pw = new PrintWriter(os);

         name = br.readLine();
         System.out.println(name + "님이 접속");
         map.put(name, socket);
//         System.out.println(map.size());
         sendUserList(map);
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

   }

   @Override
   public void run() {

      InputStream is = null;
      OutputStream os = null;

      PrintStream pw = null;
      BufferedReader br = null;

      try {
         is = sock.getInputStream();
         os = sock.getOutputStream();

         br = new BufferedReader(new InputStreamReader(is));
         pw = new PrintStream(os);

         while (true) {
            String msg = br.readLine();

            if (msg.equals("exit"))
               break;
           
            System.out.println(msg);

         }

      } catch (IOException e) {
    	  map.remove(name);
          System.out.println(name + "님이 나가셨습니다");
          sendUserList(map);
      }

   }

   public void sendUserList(HashMap<String, Socket> map) {

//      Set<String> set = map.keySet();//kye(name)값
      Iterator<String>nameIter;
      
//      Collection<Socket>so=map.values();//value(socket)값
      Iterator <Socket>sockIter = map.values().iterator();
      OutputStream os=null;
      PrintWriter pw=null;
      while (sockIter.hasNext()) {
         Socket soc=(Socket) sockIter.next();//접속중인 각 사람들의 소켓
         try {
            os=soc.getOutputStream();
            pw=new PrintWriter(os);
            nameIter=map.keySet().iterator();
            pw.println("ULISTSTART//");
            pw.flush();
            while(nameIter.hasNext()){//각 사람에게 사용자 이름 전송
               String key = (String) nameIter.next();
               System.out.println("ULIST//"+key);
               pw.println("ULIST//"+key);
               pw.flush();
            }
            System.out.println("ULISTEND//");
            pw.println("ENDULIST//");
            pw.flush();
         } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
   }

   public static void main(String[] args) {

      ServerSocket server = null;

      try {
         server = new ServerSocket(8080);
         System.out.println("서버시작");
         map = new HashMap<>();
         while (true) {
            Socket socket = server.accept();
            Server thr = new Server(socket);

            thr.start();
         }

      } catch (IOException e) {
         e.printStackTrace();
      }
   }

}
class Room{
   
   String roomName;
   ArrayList<String> roomUser;
}