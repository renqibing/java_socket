package two;
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
 public class Server {
     private Selector selector;
     private ByteBuffer readBuffer = ByteBuffer.allocate(100);
     public void start() throws IOException {
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
             while (iterator.hasNext()) {
            	 SelectionKey key = (SelectionKey) iterator.next();
                 if (!key.isValid()) {
                     continue;
                 }
                 if (key.isAcceptable()) {
                	 ssc = (ServerSocketChannel) key.channel();
                     SocketChannel clientChanel = ssc.accept();
                     clientChanel.configureBlocking(false);
                     clientChanel.register(selector, SelectionKey.OP_READ |SelectionKey.OP_WRITE);
                     System.out.println("客户端链接成功");                
                 }else if(key.isReadable()) {
                	 SocketChannel socketChannel = (SocketChannel) key.channel();
                     this.readBuffer.clear();
                     int readNum = 0;
                    try {
                         readNum = socketChannel.read(this.readBuffer);
                     } catch (IOException e) {
                         key.cancel();
                         socketChannel.close();
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
                         socketChannel.write(readBuffer);
                     }
                 }
             }
             iterator.remove();
         }
     }
 
 
     public static void main(String[] args) throws IOException {
         new Server().start();
     }
 }