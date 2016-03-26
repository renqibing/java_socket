package multithread_niosocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class Handler extends Thread{
	SocketChannel socketChannel;
	 SelectionKey key ;
	 ByteBuffer readBuffer = ByteBuffer.allocate(100);
	 public Handler( SocketChannel socketChannel, SelectionKey key, ByteBuffer readBuffer ){
		 this.socketChannel=socketChannel;
		 this.key=key;
		 this.readBuffer=readBuffer;
		 start();
	 }
	 public void run(){
		 readBuffer.clear();
         int readNum = 0;
        try {
             readNum = socketChannel.read(readBuffer);
         } catch (IOException e) {
             key.cancel();
             try {
				socketChannel.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
             return;
         }
         if (readNum > 0) {
             byte[] newBytes = new byte[readNum];
             System.arraycopy(readBuffer.array(), 0, newBytes, 0, readNum);
             String message = new String(newBytes);
             System.out.println("服务器从客户端接受到的数据："+message);
            message = "echo：" + message;
             readBuffer.flip();
             readBuffer = ByteBuffer.wrap(message.getBytes());
             try {
				socketChannel.write(readBuffer);
			} catch (IOException e) {
				e.printStackTrace();
			}
         }
	 }
}
