

/home/sunyan/Pictures/Screenshot from 2016-03-03 08:16:18.png
/home/sunyan/Pictures/Screenshot from 2016-03-03 08:17:53.png
/home/sunyan/Pictures/Screenshot from 2016-03-03 08:19:31.png
/home/sunyan/Pictures/Screenshot from 2016-03-03 08:20:10.png



![6.PNG](/home/sunyan/Pictures/Screenshot from 2016-03-03 08:16:18.png)
#socket
socket,应用在TCP/IP协议族中，通常也称作"套接字"，用于描述IP地址和端口，是网络上运行的两个程序间双向通讯的一端，它既可以接受请求，也可以发送请求，利用它可以较为方便的编写网络上的数据的传递,主要类型为流套接字（使用TCP协议）和数据报套接字（使用UDP协议）。
Socket通讯过程：服务端监听某个端口是否有连接请求，客户端向服务端发送连接请求，服务端收到连接请求向客户端发出接收消息，这样一个连接就建立起来了。客户端和服务端都可以相互发送消息与对方进行通讯。
Socket在java中，有专门的socket类来处理用户的请求和响应。利用SOCKET类的方法，就可以实现两台计算机之间的通讯。
##阻塞socket
在Java中,一个 socket实例对象代表了TCP连接的一个客户端，一个ServerSocket实例对象代表了TCP连接的一个服务器端，这两个对象均有两个关键的方法，一个是getInputStream方法，另 一个是getOutputStream方法。getInputStream方法可以得到一个输入流，客户端的Socket对象上的 getInputStream方法得到的输入流其实就是从服务器端发回的数据流；服务器端的输入流就是接受客户端发来的数据流。GetOutputStream方法得到一个输出流，客户端Socket 对象上的getOutputStream方法返回的输出流就是将要发送到服务器端的数据流，服务器端的输出流就是发给客户端的数据流。通常，我们还要对这两种方法获取的数据进行封装，以便更方便的使用。

