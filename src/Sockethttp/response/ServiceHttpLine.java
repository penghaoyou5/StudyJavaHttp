package Sockethttp.response;

/**
 * @author bjhl-penghaoyou
 * 进行服务器的启动
 */
public class ServiceHttpLine {

	public static void main(String[] args) {
		//启动服务          
		new SimpleHttpServer().start();
	}
}
