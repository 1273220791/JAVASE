package cn.main;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

	public static void main(String[] args) throws Exception {
		ServerSocket ss = new ServerSocket(10086);
		System.out.println("服务器: 考试管理系统服务器已开启,等待连接中......");
		while(true) {
			//客户端连接
			Socket clientSocket = ss.accept();
			System.out.println("来了一个客户端~  ");
			new Thread(new SocketThread(clientSocket)).start();
			
		}
	}	
}
