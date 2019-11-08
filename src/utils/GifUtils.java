package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * mp4转gif
 * 
 * @author jinx
 *
 */
public class GifUtils {

	private static Logger log = LoggerFactory.getLogger(GifUtils.class);

	// Windows下 ffmpeg.exe的路径
	// private static String ffmpegEXE =
	// "D:\\Downloads\\ffmpeg-20180528-ebf85d3-win64-static\\bin\\ffmpeg.exe";

	// Linux与mac下 ffmpeg的路径
	 //private static String ffmpegEXE = "/usr/local/Cellar/ffmpeg/4.1.2/bin/ffmpeg";

	 private static String ffmpegEXE = "/usr/bin/ffmpeg";

	/**
	 * 开辟线程处理流
	 * @param process
	 */
	private static void dealStream(Process process) {
	    if (process == null) {
	        return;
	    }
	    // 处理InputStream的线程
	    new Thread() {
	        @Override
	        public void run() {
	            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
	            String line = null;
	            try {
	                while ((line = in.readLine()) != null) {
	                    log.info("output: " + line);
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            } finally {
	                try {
	                    in.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }.start();
	    // 处理ErrorStream的线程
	    new Thread() {
	        @Override
	        public void run() {
	            BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
	            String line = null;
	            try {
	                while ((line = err.readLine()) != null) {
	                    log.info("err: " + line);
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            } finally {
	                try {
	                    err.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }.start();
	}

	
	/**
	 *
	 * @param time
	 *            截取视频长度
	 * @param start
	 *            截取开始时间 如果为null表示全部转换为gif
	 * @param inputPath
	 *            被转换的mp4文件位置
	 * @param outPath
	 *            转换后gif文件位置
	 * @throws Exception
	 */
	public static boolean covMp4(String inputPath) throws Exception {
		List<String> command = new ArrayList<String>();
		command.add(ffmpegEXE);
		command.add("-i");
		command.add(inputPath);
		command.add("-c:v");
		command.add("copy");
		command.add("-c:a");
		command.add("aac");
		command.add("-q");
		command.add("1");
		String outPath = inputPath.split("_.mp4")[0] + ".mp4";
		command.add(outPath);
		try {
			Process videoProcess = new ProcessBuilder(command).start();
			dealStream(videoProcess);
			videoProcess.waitFor();
			log.warn("msg:cov MP4：{}--------------------------------------{}", inputPath, outPath);
			return true;
		} catch (Exception e) {
			log.error("e:{}", e);
		}
		return false;
		// covGif(outPath);
	}

	/**
	 * mp4转gif
	 * @param inputPath
	 * @throws Exception
	 */
	public static void covGif(String inputPath) throws Exception {
		List<String> command = new ArrayList<String>();
		command.add(ffmpegEXE);
		command.add("-i");
		command.add(inputPath);
		command.add("-r");
		command.add("15");
		command.add("-vf");
		command.add("scale=-1:360");
		command.add(inputPath.split(".mp4")[0] + ".gif");
		ProcessBuilder builder = new ProcessBuilder(command);
		Process process = null;
		try {
			process = builder.start();
			log.warn("msg:cov GIF：{}--------------------------------------", inputPath);
		} catch (IOException e) {
			log.error("e:{}", e);
		}
		// 使用这种方式会在瞬间大量消耗CPU和内存等系统资源，所以这里我们需要对流进行处理
		InputStream errorStream = process.getErrorStream();
		InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
		BufferedReader br = new BufferedReader(inputStreamReader);

		String line = "";
		while ((line = br.readLine()) != null) {
			log.warn("line:{}", line);
		}
		if (br != null) {
			br.close();
		}
		if (inputStreamReader != null) {
			inputStreamReader.close();
		}
		if (errorStream != null) {
			errorStream.close();
		}

	}

	public static void main(String[] args) {
		try {
			covPic("/Users/jinx/Downloads/xxxx","00:00:21","");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 截取一帧作为图片
	 * @param fileName
	 * ffmpeg -i /Users/jinx/Downloads/test.mp4 -y -f image2 -ss 00:00:01 -vframes 1 /Users/jinx/Downloads/test.jpeg
	 * @return
	 */
	public static boolean covPic(String fileName,String time,String outName) {
		List<String> command = new ArrayList<String>();
		command.add(ffmpegEXE);
		command.add("-i");
		command.add(fileName+".mp4");
		command.add("-y");
		command.add("-f");
		command.add("image2");
		command.add("-ss");
		command.add(time);
		command.add("-vframes");
		command.add("1");
		command.add("-s");
		command.add("2688x1520");
		if(StringUtil.isNotBlank(outName)){
			command.add(outName);
		}else{
			command.add(fileName+".jpeg");
		}
		try {
			Process videoProcess = new ProcessBuilder(command).start();
			dealStream(videoProcess);
			videoProcess.waitFor();
			log.warn("msg:cov Pic：{}",fileName);
			try {
				FtpUtils ftp = new FtpUtils();
				String dirPath =  "/"+outName.split("/")[3];
				String ftpFileName =  outName.split("/")[4];
				ftp.uploadFile(dirPath, ftpFileName, outName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		} catch (Exception e) {
			log.error("e:{}", e);
		}
		return false;
	}


	/**
	 * 多文件压缩为一个文件
	 * 
	 * @param srcFiles
	 * @param zipFile
	 */
	public static void zipFiles(File[] srcFiles, File zipFile) {
		// 判断压缩后的文件存在不，不存在则创建
		if (!zipFile.exists()) {
			try {
				zipFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 创建 FileOutputStream 对象
		FileOutputStream fileOutputStream = null;
		// 创建 ZipOutputStream
		ZipOutputStream zipOutputStream = null;
		// 创建 FileInputStream 对象
		FileInputStream fileInputStream = null;

		try {
			// 实例化 FileOutputStream 对象
			fileOutputStream = new FileOutputStream(zipFile);
			// 实例化 ZipOutputStream 对象
			zipOutputStream = new ZipOutputStream(fileOutputStream);
			// 创建 ZipEntry 对象
			ZipEntry zipEntry = null;
			// 遍历源文件数组
			for (int i = 0; i < srcFiles.length; i++) {
				// 将源文件数组中的当前文件读入 FileInputStream 流中
				fileInputStream = new FileInputStream(srcFiles[i]);
				// 实例化 ZipEntry 对象，源文件数组中的当前文件
				zipEntry = new ZipEntry(srcFiles[i].getName());
				zipOutputStream.putNextEntry(zipEntry);
				// 该变量记录每次真正读的字节个数
				int len;
				// 定义每次读取的字节数组
				byte[] buffer = new byte[1024];
				while ((len = fileInputStream.read(buffer)) > 0) {
					zipOutputStream.write(buffer, 0, len);
				}
			}
			zipOutputStream.closeEntry();
			zipOutputStream.close();
			fileInputStream.close();
			fileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	 public static String postFile(String url, String filePath) {  
	        File file = new File(filePath);  
	        if (!file.exists())  
	            return null;  
	        String result = null;  
	        try {  
	            URL url1 = new URL(url);  
	            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();  
	            conn.setConnectTimeout(5000);  
	            conn.setReadTimeout(30000);  
	            conn.setDoOutput(true);  
	            conn.setDoInput(true);  
	            conn.setUseCaches(false);  
	            conn.setRequestMethod("POST");  
	            conn.setRequestProperty("Connection", "Keep-Alive");  
	            conn.setRequestProperty("ts", String.valueOf((new Date().getTime()/1000)));  
	            conn.setRequestProperty("Cache-Control", "no-cache");  
	            String boundary = "-----------------------------" + System.currentTimeMillis();  
	            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);  

	            OutputStream output = conn.getOutputStream();  
	            output.write(("--" + boundary + "\r\n").getBytes());  
	            output.write(  
	                    String.format("Content-Disposition: form-data; name=\"media\"; filename=\"%s\"\r\n", file.getName())  
	                            .getBytes());  
	            output.write("Content-Type: image/jpeg \r\n\r\n".getBytes());  
	            byte[] data = new byte[1024];  
	            int len = 0;  
	            FileInputStream input = new FileInputStream(file);  
	            while ((len = input.read(data)) > -1) {  
	                output.write(data, 0, len);  
	            }  
	            output.write(("\r\n--" + boundary + "\r\n\r\n").getBytes());  
	            output.flush();  
	            output.close();  
	            input.close();  
	            InputStream resp = conn.getInputStream();  
	            StringBuffer sb = new StringBuffer();  
	            while ((len = resp.read(data)) > -1)  
	                sb.append(new String(data, 0, len, "utf-8"));  
	            resp.close();  
	            result = sb.toString();  
	            log.warn(result);  
	        } catch (ClientProtocolException e) {  
	        	log.warn("postFile，不支持http协议"+ e);  
	        } catch (IOException e) {  
	        	log.warn("postFile数据传输失败"+ e);  
	        }  
	        log.warn(result);  
	        return result;  
	    }  


}

