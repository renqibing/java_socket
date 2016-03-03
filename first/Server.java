package first;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws Exception{  
        ServerSocket server = new ServerSocket(20011);  
        //服务端在20011端口监听客户端请求的TCP连接  
        Socket client = null;
        boolean f = true;  
        while(f){  
            client = server.accept(); 
            //服务端在调用accept（）等待客户端的连接请求时会阻塞，直到收到客户端发送的连接请求才会继续往下执行代码
            //链接成功后为每个客户端连接开始做出处理 
            System.out.println("客户端链接成功"); 
            try{                    
                PrintStream out = new PrintStream(client.getOutputStream()); 
              //获取Socket的输出流，用来向客户端发送数据        
                BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream())); 
              //获取Socket的输入流，用来接收从客户端发送过来的数据 
                boolean flag =true;  
                while(flag){                
                    String str =  buf.readLine();  
                    //接收从客户端发送过来的数据  
                    if(str == null || "".equals(str)){  
                        flag = false;  
                    }else{  
                        if("bye".equals(str)){  
                            flag = false;  
                        }else{  
                            //将接收到的字符串前面加上echo，发送到对应的客户端
                        	System.out.println("服务器从客户端接受到的数据："+str);
                            out.println("echo:" + str);  
                        }  
                    }  
                }  
                out.close();  
                client.close();  
            }catch(Exception e){  
                e.printStackTrace();  
            }  
        }

        server.close();  
    }  
}
