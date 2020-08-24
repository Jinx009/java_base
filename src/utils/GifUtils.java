package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

	 private static String ffmpegEXE = "/usr/local/bin/ffmpeg";

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
	
	public static void rtmp() throws Exception {
		String[] cmd = { "sh", "-c", "/Users/jinx/Documents/tools/ffmpeg/ && ./ffmpeg -re -i /Users/jinx/Downloads/1.mp4 -c copy -f flv rtmp://10.0.0.48 " };
		List<String> command = new ArrayList<String>();
		command.add(ffmpegEXE);
		command.add("-re");
		command.add("-i");
		command.add("/data/ftp_pic/1.mp4");
		command.add("-c");
		command.add("copy");
		command.add("-f");
		command.add("flv");
		command.add("rtmp://10.0.0.48");
		try {
			Process p = Runtime.getRuntime().exec(cmd);//创建实例进程执行命令行代码
            p.waitFor();
            p.destroy();
		} catch (Exception e) {
			log.error("e:{}", e);
		}
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

}

