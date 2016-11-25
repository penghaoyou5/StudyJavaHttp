package bjzb.simplenetdemo;

import bjzb.simplenetdemo.request.HttpMethod;
import bjzb.simplenetdemo.request.RequestListener;
import bjzb.simplenetdemo.response.StringRequest;

public class SImplenetDemoMainActivity {

	public static void main(String[] args) {
		 String url = "http://www.baidu.com";
	      
	        StringRequest stringRequest = new StringRequest(HttpMethod.GET, url, new RequestListener<String>() {
	            @Override
	            public void onComplete(int code, String result, String errMg) {
	              System.out.println(result);
	            }
	        });
   
	        stringRequest.startRequest();
	}
}
