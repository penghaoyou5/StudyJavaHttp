package Sockethttp.response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 * 进行线程连接后的操作
 * 
 * 人家封装的方法 好呀
 * 
 * @author bjhl-penghaoyou
 *
 */
public class DeliverThread extends Thread {

	Socket mClientCocket;
	BufferedReader mInputStream;
	
	/**
	 * 是否正还在解析请求头部的阶段
	 */
	boolean isParseHeader = true;
	
	/**
	 * 请求消息的分隔符
	 */
	String boundary;
	
	/**
	 * 输出流
	 */
	PrintStream mOutPutStream;
	
	public DeliverThread(Socket socket){
		mClientCocket = socket;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		try {
			//获取输入流
			InputStream inputStream = mClientCocket.getInputStream();
			mInputStream = new BufferedReader(new InputStreamReader(inputStream));
			//获取输出流  
			OutputStream outputStream = mClientCocket.getOutputStream();
			mOutPutStream = new PrintStream(outputStream);
			//请求解析
			parseRequest();
			//返回responce
			handleResponse();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//关闭流
			try {
				mInputStream.close();
				mOutPutStream.close();
				mClientCocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("服务器一次响应结束");
		}
		
	}

	
	/**
	 *进行请求解析的方法 
	 */
	private void parseRequest() {
		// TODO Auto-generated method stub
		String line;
		int lineNumber=0;
		try {
			while((line=mInputStream.readLine())!=null){
				if(isEnd(line)){
					break;
				}
				
				if(lineNumber==0){
					//如果是第一行 请求行
					parseRequestLine(line);
				}
				
				//解析请求头 header  不是请求头之后的
				if(lineNumber!=0 && isParseHeader){
					parseHeaders(line);
				}
				
				//解析请求体 请求参数  
				if(lineNumber!=0 && !isParseHeader){
					parseRequestParams(line);
				}
				lineNumber++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/**
	 * 判断是否是数据结构的结束行
	 * @param line
	 * @return
	 */
	private boolean isEnd(String line) {
		//请求数据的最后 作为报文的结束格式
		if(line.equals("--"+boundary+"--") ){
			return true;
		}
		return false;
	}
	
	/**
	 * 解析请求行
	 * @param line 
	 */
	private void parseRequestLine(String line) {
		// TODO Auto-generated method stub
		String[] lines = line.split(" ");
		
		System.out.println("请求方式  " + lines[0]);
		System.out.println("请求地址  " + lines[1]);
		System.out.println("请求协议  " + lines[2]);
		
	}
	
	/**
	 * 解析请求头
	 * @param line
	 */
	private void parseHeaders(String line) {
		// TODO Auto-generated method stub
		if(line.equals("")){
			//头部结束标示
			isParseHeader = false;
			System.out.println("－－－－－头部解析完成");
		}else if(line.contains("boundary")){
			//解析包含边界参数  的情况
			boundary = parseSecondField(line);
			System.out.println("边界分割线"+ boundary);
		}else {
			//解析普通的头部
			parseHeaderParam(line);
		}
	}
	
	/**
	 * 解析包含两个参数的头部 这就是包含boundry分割线的情况
	 * @param line
	 */
	private String parseSecondField(String line){
		String[] headers = line.split(";");
		parseHeaderParam(headers[0]);
		String boundary = headers[1].split("=")[1];
		return boundary;
	}
	
	private void parseHeaderParam(String line) {
		// TODO Auto-generated method stub
		String[] header = line.split(":");
		System.out.println("header参数名 "+header[0]+"  header参数值  " + header[1]);
	}
	
	
	

	/**
	 * 进行请求参数的解析   --  这里例子简单就写有一个请求参数的 不带请求参数的格式
	 * @param line
	 * @throws IOException 
	 */
	private void parseRequestParams(String line) throws IOException {
		// TODO Auto-generated method stub
		//这是找到了请求参数开头
		if(line.equals("--"+boundary)){
			//请求参数  这个有两个的
			line = mInputStream.readLine();
			//参数名
			String paramName = parseSecondField(line);
			
			//就算中间有参数描述略过  读取到空行    
			while(!strIsEmpth(line=mInputStream.readLine()));
			
			//读取下一行获取参数值
			String paramValue = line = mInputStream.readLine();

			
			System.out.println("请求参数名 " + paramName+"  参数值  " + line);
		}
		
	}


	private boolean strIsEmpth(String str) {
		return str==null||str.equals("");
	}

	

	/**
	 * 返回值方法  向输出流写数据
	 * 
	 * 遵循响应 格式
	 *  
	 * 响应行
	 * 响应头
	 * 空行
	 * 响应数据
	 * 
	 */
	private void handleResponse() {
		//向输出流写数据
		//响应行
		mOutPutStream.println("HTTP/1.1 200 OK");
		//响应头
		mOutPutStream.println("Content-Type: application/json");
		//空行
		mOutPutStream.println();
		//响应数据
		mOutPutStream.println("{\"stCord\":\"success\"}");
		
	}
	
}
