package test;

import java.io.File;



public class Test {

	public static void main(String[] args) {
//		try {
//		    String pathname = "/Users/jinx/Downloads/heart_log/heart_log_1107_0001170829000019.txt"; 
//            File filename = new File(pathname); 
//            if(filename.exists()){
//            	 InputStreamReader reader = new InputStreamReader(  
//                         new FileInputStream(filename)); 
//                 BufferedReader br = new BufferedReader(reader); 
//                 StringBuilder result = new StringBuilder();
//                 String s = null;
//                 while((s = br.readLine())!=null){//使用readLine方法，一次读一行
//                     result.append(System.lineSeparator()+s);
//                 }
//                 br.close();  
//                 System.out.println(result.toString());
//            }else{
//            	System.out.println("cuck");
//            }
//           
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		
//		String pathname = "/Users/jinx/Downloads/xxxx";
//		File fileName = new File(pathname);
//		if (fileName.exists()) {
//			System.out.println(getLog(fileName));
//		} 
		File file=new File("/Users/jinx/Downloads");
        for(File temp:file.listFiles()){
            if(temp.isFile()){
                System.out.println(temp.toString());
            }
            
        }
	}
	
	
//	private static String getLog(File fileName){
//		try {
//			InputStreamReader reader = new InputStreamReader(new FileInputStream(fileName));
//			BufferedReader br = new BufferedReader(reader);
//			StringBuilder result = new StringBuilder();
//			String s = null;
//			while ((s = br.readLine()) != null) {
//				result.append(System.lineSeparator() + s);
//			}
//			br.close();
//			return result.toString();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
}
