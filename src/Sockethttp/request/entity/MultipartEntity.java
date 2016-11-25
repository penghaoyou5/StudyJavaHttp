package Sockethttp.request.entity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.util.Map;

/**
 * @author bjhl-penghaoyou
 * multipart  多部份的，及部分的
 *	进行文件上传要求http为表单格式 ，这里为参数转化拼接
 *
 *请求行
 *请求头
 *请求体
 *
 *
 *表单请求的具体http格式要求
 *请求行
 *请求头 （其中 Content-Type: multipart/form-data; boundary=自定的分割线）
 *												
 *两个横杠加上boundary		//作为一个键值参数的开头
 *请求参数的Header属性
 *
 *参数值
 *。。。。。。。。多个请求参数进行上述重复
 *结束：   两个横杠 ＋ boundary值 ＋ 两个横杠
 */
public class MultipartEntity extends HttpEntity{


	//请求头 Content-Type 参数的值
	//文本参数和字节参数
	public static final String TYPE_TEXT_CHARSET = "text/plain; charset=UTF-8";
	//字节数组参数
	public static final String TYPE_OCTET_STREAM = "application/octet-stream";
	
	
	//字节数组参数
	public static final byte[] BINARY_ENCODING = "Content-Transfer-Encoding;binary".getBytes();
	//文本参数
	public static final byte[] BIT_ENCODING = "Content-Transfer-Encoding;8bit".getBytes();
	
	public String getContentType() {
		return "multipart/form-data; boundary=" + getBoundary();
	}
	
	
	
	//添加文本参数  参数值为文本
	public void addStringPart(ByteArrayOutputStream bStream,String paramName,String value) {
		writeParameByte(bStream,paramName,value.getBytes(),TYPE_TEXT_CHARSET,BIT_ENCODING,"");
	}
	
	
	/**
	 * 添加字节数组参数，例如BitMap的字节流参数
	 * @param bStream 
	 * @param paramName  参数名
	 * @param valueByte	 字节参数值
	 */
	public void addByteArrayPart(ByteArrayOutputStream bStream,String paramName,byte[] valueByte) {
		writeParameByte(bStream, paramName, valueByte, TYPE_OCTET_STREAM, BINARY_ENCODING, "no-file");
	}
	
	/**
	 * 添加文件参数 可以实现文件上传功能 
	 * @param key 参数名
	 * @param file 文件参数
	 */
	public void addFilePart(ByteArrayOutputStream bStream,String paramName,File file){
		ByteArrayOutputStream fileOutputStream = new ByteArrayOutputStream();
		FileInputStream fileInputStream = null;
		try {
			//获取文件字节数组
			fileInputStream = new FileInputStream(file);
			byte[]  tmp = new byte[4096];
			int len = 0;
			while ((len = fileInputStream.read(tmp))!=-1) {
				fileOutputStream.write(tmp,0,len);
			}
			fileOutputStream.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(fileInputStream!=null){
				try {
					fileInputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		byte[] valueByte = fileOutputStream.toByteArray();
		writeParameByte(bStream, paramName, valueByte, TYPE_OCTET_STREAM, BINARY_ENCODING, file.getName());
	}
}
