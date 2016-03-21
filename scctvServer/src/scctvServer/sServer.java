package scctvServer;

import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;


public class sServer implements Runnable{
	
	@Override
	public void run(){
		try{
			System.out.println("Starting sCCTV Server");
			ServerSocket serverSocket = new ServerSocket(300);
			System.out.println("Listening port : 300");
			
			while(true){
				Socket socket = serverSocket.accept();
				System.out.println(getTime() + 
						"host: "+ socket.getInetAddress()+
						", port: "+socket.getPort());
				
				try{
					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String str = in.readLine();
					System.out.println("s: Received: '" + str + "'");
				
					PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
					out.println("Server Received " +str);
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					socket.close();
				}
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		
		Thread sThread = new Thread(new sServer());
		sThread.start();
	}
	
	
	static String getTime(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd [hh:mm:ss]");
		return format.format(new Date());
	}
}
