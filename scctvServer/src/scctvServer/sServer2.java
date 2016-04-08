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
 
        // ���� �����忡�� ������ ���̹Ƿ� ����ȭ
        Collections.synchronizedMap(clients);
    }
 
    public void start() {
        try {
            Socket socket;
 
            // ������ ���� ����
            serverSocket = new ServerSocket(450);
            System.out.println("Starts sCCTV Server ");
 
            // Ŭ���̾�Ʈ�� ����Ǹ�
            while (true) {
                // ��� ������ �����ϰ� ������ ����(������ 1:1�θ� ����ȴ�)
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
                // Ŭ���̾�Ʈ�� ������ �����ϸ� ��ȭ�濡 �˸���.
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
                        + socket.getPort() + "]" + "���� ��ȭ�濡 �����Ͽ����ϴ�.");
 
                clients.put(name, output);
                System.out.println(name + "[" + socket.getInetAddress() + ":"
                        + socket.getPort() + "]" + "���� ��ȭ�濡 �����Ͽ����ϴ�.");
                System.out.println("���� " + clients.size() + "���� ��ȭ�濡 ���� ���Դϴ�.");
 
                // �޼��� ����
                while (input != null) {
                    sendToAll(input.readUTF());
                }
                ***/
            } catch (IOException e) {
            	e.printStackTrace();
            } finally {
                // ������ ����Ǹ�
            	/***
                clients.remove(name);
                sendToAll("#" + name + "[" + socket.getInetAddress() + ":"
                        + socket.getPort() + "]" + "���� ��ȭ�濡�� �������ϴ�.");
                System.out.println(name + "[" + socket.getInetAddress() + ":"
                        + socket.getPort() + "]" + "���� ��ȭ�濡�� �������ϴ�.");
                System.out.println("���� " + clients.size() + "���� ��ȭ�濡 ���� ���Դϴ�.");
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