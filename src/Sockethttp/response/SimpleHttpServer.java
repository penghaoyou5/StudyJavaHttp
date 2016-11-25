package Sockethttp.response;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 进行http请求接受的服务器
 * @author bjhl-penghaoyou
 */
public class SimpleHttpServer extends Thread {
	public static final int HTTP_PORT = 8000;
	
	ServerSocket mSocket;
	
	@SuppressWarnings("resource")
	public SimpleHttpServer(){
		try {
			mSocket =  new ServerSocket(HTTP_PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(mSocket==null){
			throw new RuntimeException("服务器Socket初始化失败");
		}
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		
		while (true) {  // 无限循环中，进入等待连接状态
			System.out.println("等待连接中");
			//一旦接受到连接请求，构建一个线程来处理
			try {
				Socket socket = mSocket.accept();
				new DeliverThread(socket).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
