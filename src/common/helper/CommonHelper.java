package common.helper;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;


import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

public class CommonHelper {

	/**
	 * obj里边包含jpa对象会报错，要过滤一次。
	 * 
	 * @return
	 */
	public static String getStringOfObj(Object obj) {
		//SimplePropertyFilter spf = new SimplePropertyFilter();
		//暂时不考虑持JPA对象报错的问题。以后可能需要加上
		
		SerializeWriter sw = new SerializeWriter();
		JSONSerializer serializer = new JSONSerializer(sw);
		//serializer.getPropertyFilters().add(spf);
		serializer.write(obj);
		return sw.toString();
	}
	
	
	public static String getObjectFromRemote(String remoteURL, 
			                                 String absoluteLocalPath, 
			                                 String fixedFileName){
		String suffix = null;
        //BufferedReader in = null;
        //long contentLength = 0;
        byte[] content = null;
        try {
            String urlNameString = remoteURL;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/5.0 (iPhone; CPU iPhone OS 5_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Mobile/9B176 MicroMessenger/4.3.2");
            // 建立实际的连接
            connection.connect();
            String contentType = connection.getContentType();
            String[] ttmp = contentType.split("/");
        	if( ttmp[0].equals("image") ){
        		suffix = ttmp[1];
        	}
            if( connection.getContentLengthLong() != 0 ){
            	content = new byte[1024]; //这个一次读取的buffer太大的话会造成内容丢失的现象
            	ByteArrayOutputStream outputBytes = new ByteArrayOutputStream();
            	int readCount;
            	while (-1 != (readCount = connection.getInputStream().read(content))) {
            		outputBytes.write(content, 0, readCount);
                }
	           content = outputBytes.toByteArray();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
        }
        
        if( suffix == null ){
        	//传过来的不是图片
        	return null;
        }
        
       
        
        String fileToSave;
        File targetPath = new File(absoluteLocalPath);
        if( !targetPath.exists() ){
        	targetPath.mkdirs();
        }
        if( fixedFileName != null){
        	fileToSave = absoluteLocalPath+"/"+fixedFileName;
        }else{
        	SimpleDateFormat formatter; 
        	formatter = new SimpleDateFormat ("yyyyMMddHHmmssSSS");
        	String filename = formatter.format(Calendar.getInstance().getTime());
        	fileToSave = absoluteLocalPath+filename;
        }
        fileToSave = fileToSave+"."+suffix;
        File targetFile = new File(fileToSave);
        if(!targetFile.exists()){
        	try {
				targetFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
                
        try {
        	FileOutputStream fs = new FileOutputStream(targetFile);
        	fs.write(content);
        	fs.flush();
        	fs.close();
		} catch ( IOException e) {
			e.printStackTrace();
		}
        
        return fileToSave;
	}
	
}
