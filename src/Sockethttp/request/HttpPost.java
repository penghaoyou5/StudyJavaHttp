package Sockethttp.request;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import Sockethttp.response.SimpleHttpServer;

/**
 * @author bjhl-penghaoyou
 * 进行客户端网络请求
 */
public class HttpPost {

	
	String host;
	int port = SimpleHttpServer.HTTP_PORT;
	BufferedReader mInputStream;
	PrintStream mOutputStream;
	
	Map<String, String> mParamsMap = new HashMap<>();
	
	String url;

	public HttpPost(String url) {
		this.url = url;
	}
	
	public void addParam(String key,String value) {
		mParamsMap.put(key, value);
	}


	
	
	public void execute() {
		Socket socket = null;
		try {
			//创建socket连接
			socket = new Socket(host, port);
			//获取输入流
			InputStream inputStream = socket.getInputStream();
			mInputStream = new BufferedReader(new InputStreamReader(inputStream));
			
			//获取输出流
			OutputStream outputStream = socket.getOutputStream();
			mOutputStream = new PrintStream(outputStream);
			
			//写入数据
			writeData();
			//获取数据
			getData();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}


	private void writeData() {
	
		//定义分割线
		final String boundary = "gxp_bounda_haoooohaha";
		//写入请求行与请求头   line Header
		writeHeader(boundary);
		//写入请求参数
		writeParams(boundary);
		
	}


	private void writeHeader(String boundary) {
		//请求行
		mOutputStream.println("POST login HTTP/1.1");
		//请求头部 header
		mOutputStream.println("content-length:123");
		mOutputStream.println("Host:"+url+":"+port);
		//这里写键为ContentType的头  加上了分割线 boundary
		mOutputStream.println("ContentType:multipart/form-data;boundary="+boundary);
		mOutputStream.println("Userp-Agent:android");
		//头部完成   增加一个空行作为分割
		mOutputStream.println();
	}


	
	/**
	 * 完成请求参数的写出
	 * @param boundary 
	 */
	private void writeParams(String boundary) {
		// TODO Auto-generated method stub
		Iterator<Entry<String, String>> iterator = mParamsMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<java.lang.String, java.lang.String> entry = (Map.Entry<java.lang.String, java.lang.String>) iterator
					.next();
			//开始标志  两个横杠加boundary值 后面是一个参数的属性信息
			mOutputStream.println("--"+boundary);
			//参数的属性信息
			mOutputStream.println("Content-Dispositions:form-data;name="+entry.getKey());
			//空行
			mOutputStream.println();
			//参数的值
			mOutputStream.println(entry.getValue());
			System.out.println("请求参数 key = "+entry.getValue()+" value = "+entry.getValue());
		}
		//结束符
		mOutputStream.println("--"+boundary+"--");
	}


	private void getData() throws IOException {
		// TODO Auto-generated method stub
		System.out.println("请求结果");
		String responseLine =  mInputStream.readLine();
		while(responseLine==null && !responseLine.contains("HTTP")){
			responseLine = mInputStream.readLine();
		}
		while ((responseLine=mInputStream.readLine())!=null) {
			System.out.println(responseLine);
		}
		System.out.println("请求结束");
	}
}
