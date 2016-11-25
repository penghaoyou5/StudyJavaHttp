package Sockethttp.request;

public class ClientRequest {

	public static void main(String[] args) {
		//新建客户端请求对象
		HttpPost httpPost = new HttpPost("127.0.0.1");
		
		//添加请求参数
		httpPost.addParam("username", "mr.sinple");
		httpPost.addParam("pwd", "mu_pwd_213");
		
		//执行请求
		httpPost.execute();
	}
}
