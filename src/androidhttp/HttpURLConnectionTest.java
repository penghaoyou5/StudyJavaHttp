package androidhttp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.omg.CORBA.NameValuePair;

/**
 * 进行httpUrl 的demo连接
 * @author bjhl-penghaoyou
 *
 */
public class HttpURLConnectionTest {
	
	static String host = "http://www.baidu.com"; 

	public static void main(String[] args) throws URISyntaxException, IOException {
		//初始化 HttpURLConnect对象
		HttpURLConnection  httpURLConnection;
		URL newURL = new URL(host);
		httpURLConnection = (HttpURLConnection) newURL.openConnection();
		
		//进行 参数设置   请求方式 超时时间   启动输出流    接收输入流  启动输出流 读取超时
		httpURLConnection.setRequestMethod("GET");
		httpURLConnection.setConnectTimeout(1000);
		
//		httpURLConnection.setDoOutput(true);
		httpURLConnection.setDoInput(true);
		httpURLConnection.setReadTimeout(10000);
		
		
//		//进行数据写入    添加请求头  添加参数
//		httpURLConnection.addRequestProperty("Connection", "Keep-Alive");
//		
//		Map<String,String> map = new HashMap<String, String>();
//		
//		map.put("usemane", "peng");
//		map.put("passWord", "guo");
//		
//		writeParams(httpURLConnection.getOutputStream(),map);
//		
		//发起请求
		httpURLConnection.connect();
		//得到输入流
		InputStream inputStream = httpURLConnection.getInputStream();
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		
		StringBuffer stringBuffer = new StringBuffer();
		String line;
		while((line=bufferedReader.readLine())!=null){
			stringBuffer.append(line+"/n");
		}
		
		System.out.println("请求结果 " + stringBuffer);
	}

	private static void writeParams(OutputStream outputStream, Map<String, String> map) throws IOException {
	
		
		StringBuffer sBuffer = new StringBuffer();
		Set<Entry<String, String>> entrySet = map.entrySet();
		for (Iterator iterator = entrySet.iterator(); iterator.hasNext();) {
			Entry<String, String> entry = (Entry<String, String>) iterator.next();
			if(sBuffer!=null){
				sBuffer.append("&");
			}
			sBuffer.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
			sBuffer.append("=");
			sBuffer.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
		}
		
		
		
		
		//为什么要封装成 这样 把对象弄了三层   这里方便了三种层次 功能的区分   这就是封装好处
		BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
		
		//将参数写到输出流
		bufferedWriter.write(sBuffer.toString());
		bufferedWriter.flush();
		bufferedWriter.close();
	
	}
}
