
public class EnmuTest {

//	  public static  enum a
//	  {
//		  a,b,c,d,e
//		  
//		  
////	    static
////	    {
////	      a[] arrayOfa = new a[5];
////	      arrayOfa[0] = a;
////	      arrayOfa[1] = b;
////	      arrayOfa[2] = c;
////	      arrayOfa[3] = d;
////	      arrayOfa[4] = e;
////	      f = arrayOfa;
////	    }
//	  }
	  
	  public static class a
	  {
	    public String a;
	    public String b;

	    public a(String paramString1, String paramString2)
	    {
	      this.a = paramString1;
	      this.b = paramString2;
	    }

	    public String a(String paramString)
	    {
	      if (this.a != null)
	        paramString = this.a;
	      return paramString;
	    }

	    public String b(String paramString)
	    {
	      if (this.b != null)
	        paramString = this.b;
	      return paramString;
	    }
	  }
}
