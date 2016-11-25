import java.lang.reflect.Type;

public class ReturnTypeGet {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		String s = getTest();
	}
	
	public static <T>T   getTest()throws Exception{
		T t = null;
		Type type = ReturnTypeGet.class.getMethod("getTest").getGenericReturnType();
		System.out.println(type+"");
		System.out.println(((Class)t).getName());
		return null;
	}

}
