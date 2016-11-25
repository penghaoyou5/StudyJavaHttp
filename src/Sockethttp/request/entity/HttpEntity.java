package Sockethttp.request.entity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

import util.TextUtils;

/**
 * @author bjhl-penghaoyou
 * entity 实体
 * 网络请求提供信息要实现的接口
 */
public abstract class HttpEntity {
	
	/**
	 * 得到自定义分割线
	 * @return Content-Type: multipart/form-data; boundary=自定的分割线
	 */
	public static final String mBoundary = "xiaoxiaoxiaopengpeng";
	public static final String CONTENT_TYPE = "Content-Type: ";
	
	public static final String CONSTENT_DISPITION = "Content-Dispition: ";
	//回车换行符
	public static final String NEW_LINE = "\r\n";
	public  String getBoundary() {
		return mBoundary;
	}
	
	//参数开头
	public  String firstBoundary() {
		return "--"+mBoundary;
	}
	
	
	/**
	 * 写一个回车换行符
	 * @param bStream 
	 */
	public void writeEnter(ByteArrayOutputStream bStream) {
		try {
			bStream.write(NEW_LINE.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将参数数据按表格格式写入到字节输出流进行存储
	 * @param bStream 字节输出流
	 * @param paramName 参数名
	 * @param valueByte 参数值的字节数组
	 * @param type 参数值的类型       请求参数Header中的Content-Type属性
	 * @param encodingBytes 编码类型
	 * @param fileName		如果是文件的话 文件名
	 * @return 
	 */
	public void writeParameByte(ByteArrayOutputStream bStream, String paramName,byte[] valueByte,String type,byte[] encodingBytes,String fileName) {
		try {
			bStream.write(firstBoundary().getBytes());
			writeEnter(bStream);
			bStream.write((CONTENT_TYPE+type).getBytes());
			writeEnter(bStream);
			bStream.write(getContentDispositionBytes(paramName,fileName));
			writeEnter(bStream);
			bStream.write(encodingBytes);
			writeEnter(bStream);
			
			writeEnter(bStream);
			bStream.write(valueByte);
			writeEnter(bStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

	/**
	 * 如果是文件 那么文件名不是固定的 所以文件名要作为请求头的一个参数值写入   （参数名是前后台规定好，应该是固定的）
	 * @param paramsName
	 * @param fileName
	 * @return
	 */
	private byte[] getContentDispositionBytes(String paramsName,String fileName) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(CONSTENT_DISPITION+"form-data; name=\""+paramsName+"\"");
		//文本参数没有filename参数，设置为空即可
		if(!TextUtils.isEmpty(fileName)){
			stringBuffer.append("; filename=\""+fileName+"\"");
		}
		return stringBuffer.toString().getBytes();
	}
	
}
