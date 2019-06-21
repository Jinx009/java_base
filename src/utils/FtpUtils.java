package utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FtpUtils {
	
	private static final Logger log = LoggerFactory.getLogger(FtpUtils.class);
	
//    public static final String hostname = "58.246.184.99";
//    public static final String   username = "ftp_user1";
    public static final String hostname = "124.74.252.163";
    public static final String   username = "ftpuser1";
    public static final Integer port = 21 ;
    
    public static final String   password = "Zhanway2017";
    
    public FTPClient ftpClient = null;
    
  

    /**
    * 上传文件
    * @param pathname ftp服务保存地址
    * @param fileName 上传到ftp的文件名
    *  @param originfilename 待上传文件的名称（绝对地址） * 
    * @return
    */
    @SuppressWarnings("static-access")
	public void uploadFile( String pathname, String fileName,String originfilename){
        InputStream inputStream = null;
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("utf-8");
        try{
            inputStream = new FileInputStream(new File(originfilename));
            log.warn("connecting...ftp...server:{},{}",hostname,port);
            ftp.connect(hostname, port); //连接ftp服务器
            ftp.login(username, password); //登录ftp服务器
            int replyCode = ftp.getReplyCode(); //是否成功登录服务器
            if(!FTPReply.isPositiveCompletion(replyCode)){
            	log.warn("connect failed...ftp:{},{}",hostname,port);
            }
            log.warn("connect successfu...ftp:{},{}",hostname,port);
            ftp.setFileType(ftpClient.BINARY_FILE_TYPE);
            CreateDirecroty(ftp,pathname);
            ftp.makeDirectory(pathname);
            ftp.changeWorkingDirectory(pathname);
            ftp.storeFile(fileName, inputStream);
            inputStream.close();
            ftp.logout();
            ftp.disconnect();
            inputStream.close();
        }catch (Exception e) {
        	 log.warn("upload...ftp...error:{}",e);
            e.printStackTrace();
        }
    }
    

    
    /**
     * 改变目录路径
     * @param directory
     * @return
     */
     public boolean changeWorkingDirectory(FTPClient ftp, String directory) {
            boolean flag = true;
            try {
                flag = ftp.changeWorkingDirectory(directory);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            return flag;
        }

    /**
     * 创建多层目录文件，如果有ftp服务器已存在该文件，则不创建，如果无，则创建
     * @param ftp
     * @param remote
     * @return
     * @throws IOException
     */
    public boolean CreateDirecroty(FTPClient ftp,String remote) throws IOException {
        boolean success = true;
        String directory = remote + "/";
        // 如果远程目录不存在，则递归创建远程服务器目录
        if (!directory.equalsIgnoreCase("/") && !changeWorkingDirectory(ftp,new String(directory))) {
            int start = 0;
            int end = 0;
            if (directory.startsWith("/")) {
                start = 1;
            } else {
                start = 0;
            }
            end = directory.indexOf("/", start);
            String path = "";
            String paths = "";
            while (true) {
                String subDirectory = new String(remote.substring(start, end).getBytes("GBK"), "iso-8859-1");
                path = path + "/" + subDirectory;
                if (!existFile(ftp,path)) {
                    if (makeDirectory(ftp,subDirectory)) {
                        changeWorkingDirectory(ftp,subDirectory);
                    } else {
                        changeWorkingDirectory(ftp,subDirectory);
                    }
                } else {
                    changeWorkingDirectory(ftp,subDirectory);
                }

                paths = paths + "/" + subDirectory;
                start = end + 1;
                end = directory.indexOf("/", start);
                // 检查所有目录是否创建完毕
                if (end <= start) {
                    break;
                }
            }
        }
        return success;
    }

  /**
   * 判断ftp服务器文件是否存在    
   * @param ftp
   * @param path
   * @return
   * @throws IOException
   */
    public boolean existFile(FTPClient ftp, String path) throws IOException {
            boolean flag = false;
            FTPFile[] ftpFileArr = ftp.listFiles(path);
            if (ftpFileArr.length > 0) {
                flag = true;
            }
            return flag;
        }
    



	/**
     * 创建目录
     * @param ftp
     * @param dir
     * @return
     */
    public boolean makeDirectory(FTPClient ftp,String dir) {
        boolean flag = true;
        try {
            flag = ftp.makeDirectory(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    
   
   
    
    public static void main(String[] args) {
        FtpUtils ftp =new FtpUtils(); 
        ftp.uploadFile("/20190632/", "1.mp4","/Users/jinx/Downloads/0001180614000233_20190621074125_inCarVideo.mp4");
    }
}