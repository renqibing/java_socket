package threadpool_socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class ThreadPoolServer {
	public static void main(String[] args) {
		ServerSocket server;
		ExecutorService executor=Executors.newFixedThreadPool(2);
		try {
			server = new ServerSocket(20012);
			   Socket client = null;
		        while(true){ 
		        	System.out.println("服务器端等待客户端发起连接请求");     
		            client = server.accept();
		            System.out.println("客户端向服务器端发起了连接请求,且连接成功");
		            executor.execute(new Handler(client));		           
		        }
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
}
