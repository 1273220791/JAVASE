package cn.main;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

	public static void main(String[] args) throws Exception {
		ServerSocket ss = new ServerSocket(10086);
		System.out.println("������: ���Թ���ϵͳ�������ѿ���,�ȴ�������......");
		while(true) {
			//�ͻ�������
			Socket clientSocket = ss.accept();
			System.out.println("����һ���ͻ���~  ");
			new Thread(new SocketThread(clientSocket)).start();
			
		}
	}	
}
