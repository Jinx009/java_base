package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class Test {

	public static void main(String[] args) {
		try {
		    String pathname = "/Users/jinx/Downloads/heart_log/heart_log_1107_0001170829000019.txt"; 
            File filename = new File(pathname); 
            if(filename.exists()){
            	 InputStreamReader reader = new InputStreamReader(  
                         new FileInputStream(filename)); 
                 BufferedReader br = new BufferedReader(reader); 
                 StringBuilder result = new StringBuilder();
                 String s = null;
                 while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                     result.append(System.lineSeparator()+s);
                 }
                 br.close();  
                 System.out.println(result.toString());
            }else{
            	System.out.println("cuck");
            }
           
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
