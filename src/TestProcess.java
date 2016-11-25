
public class TestProcess {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long total = 29399;
		for(int size = 0;size<total;size+=374){
			updateProgress(size, total);
		}
	}
	
	 private static void updateProgress(long size, long total) {

	            int percent = (int) (size * 1.0 / total * 100);
	            System.out.println("size="+size+"  total="+total+"   percent="+percent);
//	            if(percent<100)
//	                mProgressBar.setProgress(percent);
//	            Log.e("OfflineDownloadingView","size="+size+"  total="+total+"   percent="+percent);

	    }
}
