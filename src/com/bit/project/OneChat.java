package com.bit.project;

import java.awt.Frame;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class OneChat extends Frame{
	Socket socket;
	String toName;

	public OneChat(Socket socket,String toName) {
		this.socket=socket;
		this.toName=toName;
		toServer();
	
	}
	public static void main(String[]args){
		
	}
	public void toServer(){
		OutputStream os=null;
		PrintWriter pw=null;
		
		try {
			os=socket.getOutputStream();
			pw=new PrintWriter(os);
			
			pw.println(toName+"1:1채팅하고싶어요");
			
			pw.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				pw.close();
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				
	}
}
