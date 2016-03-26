package multithread_socket;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadServer{
	public static void main(String[] args) throws Exception{  
        ServerSocket server = new ServerSocket(20012);  
        Socket client = null;
        while(true){ 
        	System.out.println("服务器端等待客户端发起连接请求");     
            client = server.accept();
            System.out.println("客户端向服务器端发起了连接请求,且连接成功");
            new Handler(client);
        }
	}
}

