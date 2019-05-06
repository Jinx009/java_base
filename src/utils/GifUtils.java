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
 * @author jinx
 *
 */
public class GifUtils {
	
	private static Logger log = LoggerFactory.getLogger(GifUtils.class);

	//Windows下 ffmpeg.exe的路径
    //private static String ffmpegEXE = "D:\\Downloads\\ffmpeg-20180528-ebf85d3-win64-static\\bin\\ffmpeg.exe";

    //Linux与mac下  ffmpeg的路径
	// private static String ffmpegEXE = "/usr/local/Cellar/ffmpeg/4.1.2/bin/ffmpeg";

	private static String ffmpegEXE = "/usr/local/bin/ffmpeg";
    /**
     *
     * @param time       截取视频长度
     * @param start      截取开始时间 如果为null表示全部转换为gif
     * @param inputPath  被转换的mp4文件位置
     * @param outPath    转换后gif文件位置
     * @throws Exception
     */
    @SuppressWarnings("unused")
	public static void covMp4(String inputPath) throws Exception {
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
           String outPath = inputPath.split("_.mp4")[0]+".mp4";
           command.add(outPath);
           ProcessBuilder builder = new ProcessBuilder(command);
           Process process = null;
           try {
               process = builder.start();
               log.warn("msg:cov MP4：{}--------------------------------------{}",inputPath,outPath);
           } catch (IOException e) {
           	 log.error("e:{}",e);
           }
           //使用这种方式会在瞬间大量消耗CPU和内存等系统资源，所以这里我们需要对流进行处理
           InputStream errorStream = process.getErrorStream();
           InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
           BufferedReader br = new BufferedReader(inputStreamReader);

           String line = "";
           while ((line = br.readLine()) != null) {
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
           covGif(outPath);
    }
    
   
    
    @SuppressWarnings("unused")
	public static void covGif(String inputPath) throws Exception {
        List<String> command = new ArrayList<String>();
        command.add(ffmpegEXE);
        command.add("-i");
        command.add(inputPath);
        command.add("-r");
        command.add("15");
        command.add("-vf");
        command.add("scale=-1:360");
        command.add(inputPath.split(".mp4")[0]+".gif");
        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = null;
        try {
            process = builder.start();
            log.warn("msg:cov GIF：{}--------------------------------------",inputPath);
        } catch (IOException e) {
        	 log.error("e:{}",e);
        }
        //使用这种方式会在瞬间大量消耗CPU和内存等系统资源，所以这里我们需要对流进行处理
        InputStream errorStream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        BufferedReader br = new BufferedReader(inputStreamReader);

        String line = "";
        while ((line = br.readLine()) != null) {
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
		covMp4("/Users/jinx/Downloads/111_.mp4");
	} catch (Exception e) {
		e.printStackTrace();
	}
 }
	
}