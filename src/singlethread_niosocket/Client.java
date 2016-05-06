package singlethread_niosocket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
 
 public class Client {
     private void start() throws IOException {
         SocketChannel sc = SocketChannel.open();
         sc.configureBlocking(false);
         sc.connect(new InetSocketAddress("localhost", 8002));
         Selector selector = Selector.open();
         Scanner scanner = new Scanner(System.in);
         sc.register(selector, SelectionKey.OP_CONNECT);
         while (true) {
             selector.select();
             Set selectedKeys = selector.selectedKeys();
             Iterator iterator = selectedKeys.iterator();
            while (iterator.hasNext()) {
                 SelectionKey key = (SelectionKey) iterator.next();
                 if (key.isConnectable()) {
                     sc.finishConnect();
                     sc.register(selector, SelectionKey.OP_WRITE);
                     System.out.println("服务器链接成功");
                     break;
                 } else if (key.isWritable()) {
                    System.out.println("输入信息：");
                     String message = scanner.nextLine();
                     ByteBuffer writebufBuffer = ByteBuffer.wrap(message.getBytes());
                     sc.write(writebufBuffer);
                     sc.register(selector, SelectionKey.OP_READ);
                 }else if(key.isReadable()){
                     ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                     int readNum = sc.read(readBuffer);
                     byte[] newBytes = new byte[readNum];
                     System.arraycopy(readBuffer.array(), 0, newBytes, 0, readNum);
                     String message = new String(newBytes);
                     System.out.println(message);
                    sc.register(selector, SelectionKey.OP_WRITE);
                 }
             }
             iterator.remove();
         }
     } 
     public static void main(String[] args) throws IOException {
         new Client().start();
     }
 }
