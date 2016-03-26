package threadpool_socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Handler extends Thread{
	private Socket client; 
	PrintStream out;
	BufferedReader buf;
	public Handler(Socket client){
		this.client = client; 
		try {
			out = new PrintStream(client.getOutputStream());
			buf = new BufferedReader(new InputStreamReader(client.getInputStream())); 
			
		} catch (IOException e) {
			e.printStackTrace();
		}       	    
	} 
	public void run(){
		 try{  
             boolean flag =true;  
             while(flag){                
                 String str =  buf.readLine();  
                 if(str == null || "".equals(str)){  
                     flag = false;  
                 }else{  
                     if("bye".equals(str)){  
                         flag = false;  
                     }else{  
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
}
