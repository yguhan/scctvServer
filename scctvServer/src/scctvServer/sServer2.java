package scctvServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.net.InetAddress; 

public class sServer2{
    private HashMap<InetAddress, Socket> clients;
    private ServerSocket serverSocket;
 
    public static void main(String[] args) {
    	new sServer2().start();
    }
 
    public sServer2() {
        clients = new HashMap<InetAddress, Socket>();
 
        // 여러 스레드에서 접근할 것이므로 동기화
        Collections.synchronizedMap(clients);
    }
 
    public void start() {
        try {
            Socket socket;
 
            // 리스너 소켓 생성
            serverSocket = new ServerSocket(450);
            System.out.println("Starts sCCTV Server ");
 
            // 클라이언트와 연결되면
            while (true) {
                // 통신 소켓을 생성하고 스레드 생성(소켓은 1:1로만 연결된다)
                socket = serverSocket.accept();
                ServerReceiver receiver = new ServerReceiver(socket);
                receiver.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
 
    class ServerReceiver extends Thread {
        Socket socket;
        DataInputStream input;
        DataOutputStream output;
 
        public ServerReceiver(Socket socket) {
            this.socket = socket;
            try {
                input = new DataInputStream(socket.getInputStream());
                output = new DataOutputStream(socket.getOutputStream());
				
                System.out.println(getTime() + 
						"host: "+ socket.getInetAddress()+
						", port: "+socket.getPort());
                clients.put(socket.getInetAddress(), socket);
				
            } catch (IOException e) {
            }
        }
 
        @Override
        public void run() {
       
            try {
                // 클라이언트가 서버에 접속하면 대화방에 알린다.
                //name = input.readUTF();
                
                while(true){
					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					if(in !=null){
						String str = in.readLine();
						System.out.println("s: Received: '" + str + "'");
					
						//PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
						//out.println("Server Received " +str);
					}
					if(!socket.isConnected())
						break;
                }
                
               
                /***
                sendToAll("#" + name + "[" + socket.getInetAddress() + ":"
                        + socket.getPort() + "]" + "님이 대화방에 접속하였습니다.");
 
                clients.put(name, output);
                System.out.println(name + "[" + socket.getInetAddress() + ":"
                        + socket.getPort() + "]" + "님이 대화방에 접속하였습니다.");
                System.out.println("현재 " + clients.size() + "명이 대화방에 접속 중입니다.");
 
                // 메세지 전송
                while (input != null) {
                    sendToAll(input.readUTF());
                }
                ***/
            } catch (IOException e) {
            	e.printStackTrace();
            } finally {
                // 접속이 종료되면
            	/***
                clients.remove(name);
                sendToAll("#" + name + "[" + socket.getInetAddress() + ":"
                        + socket.getPort() + "]" + "님이 대화방에서 나갔습니다.");
                System.out.println(name + "[" + socket.getInetAddress() + ":"
                        + socket.getPort() + "]" + "님이 대화방에서 나갔습니다.");
                System.out.println("현재 " + clients.size() + "명이 대화방에 접속 중입니다.");
				***/
            }
        }
 
        public void sendToAll(String message) {
        	/***
            Iterator<String> it = clients.keySet().iterator();
 
            while (it.hasNext()) {
                try {
                    DataOutputStream dos = clients.get(it.next());
                    dos.writeUTF(message);
                } catch (Exception e) {
                }
            }
            ***/
        }
        
    	public String getTime(){
    		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd [hh:mm:ss]");
    		return format.format(new Date());
    	}
    }
}