package multithread_niosocket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import threadpool_niosocket.Handler;

public class MultithreadServer {
	 public static void main(String[] args) throws IOException {
    	 Selector selector;
         ByteBuffer readBuffer = ByteBuffer.allocate(100);
         ServerSocketChannel ssc = ServerSocketChannel.open();
         ssc.configureBlocking(false);
         ssc.socket().bind(new InetSocketAddress("localhost", 8002));
         selector = Selector.open();
         ssc.register(selector, SelectionKey.OP_ACCEPT);
         Scanner scanner = new Scanner(System.in);
         while (!Thread.currentThread().isInterrupted()) {
             selector.select();
             Set selectedKeys = selector.selectedKeys();
             Iterator iterator = selectedKeys.iterator();
             while (iterator.hasNext()){
            	 SelectionKey key = (SelectionKey) iterator.next();
                	 //iterator.remove();
                 if (!key.isValid()) {
                     continue;
                 }
                 if (key.isAcceptable()) {
                    System.out.println("服务器端等待客户端发起连接请求");  
                    ssc = (ServerSocketChannel) key.channel();
                    SocketChannel clientChanel = ssc.accept();
                    clientChanel.configureBlocking(false);
                    clientChanel.register(selector, SelectionKey.OP_READ |SelectionKey.OP_WRITE);
                    System.out.println("客户端向服务器端发起了连接请求,且连接成功");             
                  }else if(key.isReadable()) {
                	  SocketChannel socketChannel = (SocketChannel) key.channel();
                	  new Handler(socketChannel,key,readBuffer);
                  }               
             }
             iterator.remove();
         }
    }
}


