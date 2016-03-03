package first;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Client {
	public static void main(String[] args) throws IOException {      
        Socket client = new Socket("127.0.0.1", 20011);  
        //客户端请求与本机在20011端口建立TCP连接 
        client.setSoTimeout(10000);             
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in)); 
      //获取键盘输入        
        PrintStream out = new PrintStream(client.getOutputStream());          
        //获取Socket的输出流，用来发送数据到服务端                  
        BufferedReader buf =  new BufferedReader(new InputStreamReader(client.getInputStream()));  
        //获取Socket的输入流，用来接收从服务端发送过来的数据 
        boolean flag = true;  
        while(flag){  
            System.out.print("输入信息：");  
            String str = input.readLine();  
            out.println(str);  
            //发送数据到服务端   
            if("bye".equals(str)){  
                flag = false;  
            }else{  
                try{  
                    //从服务器端接收数据有个时间限制（系统自设，也可以自己设置），超过了这个时间，便会抛出该异常  
                    String echo = buf.readLine();  
                    System.out.println(echo);  
                }catch(SocketTimeoutException e){  
                    System.out.println("Time out, No response");  
                }  
            }  
        }  
        input.close();  
        if(client != null){  
            //如果构造函数建立起了连接，则关闭套接字，如果没有建立起连接，自然不用关闭  
            client.close(); //只关闭socket，其关联的输入输出流也会被关闭  
        }  
    }  

}
