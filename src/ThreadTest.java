
public class ThreadTest {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i=0;i< 3000;i++){
			new Thread(){
				public void run(){
					while(true){
						System.out.println(getName());
						show("sdfadfadfjalkdjf234234j2kjjkshfdklhkdjfhkjlhdkjfhkjhkrjhjk234hk2j3h4k2hkjhkldfjhskdhfksdhkfhkj23h4h2kj3h4kjh2khwkehkjfhwekjfhwehfhjkewhlkjf");
						//System.out.println("aaa"+"aaaa"+"aaaa"+"aaaaa"+"aaaaaa");
					}
				}
			}.start();
		}
	}
	
	public static void show(String str){
		for(int i=0;i<1000;i++){
			str.substring(0, 3);
			str.codePointBefore(1);
		}
		
	}
}
