
public class MathTest {

	public static void main(String[] args) {
		int ci = 2;
		
		//一个数平 ci方
		double d =Math.pow(16, ci);
		System.out.println("d="+d);
		
		//一个数开ci方
		double e =Math.pow(16, 1.0/ci);
		System.out.println("e="+e);
	}
}
