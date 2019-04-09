package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * mp4转gif
 * @author jinx
 *
 */
public class GifUtils {

	//Windows下 ffmpeg.exe的路径
    //private static String ffmpegEXE = "D:\\Downloads\\ffmpeg-20180528-ebf85d3-win64-static\\bin\\ffmpeg.exe";

    //Linux与mac下  ffmpeg的路径
    private static String ffmpegEXE = "/usr/local/Cellar/ffmpeg/4.1.2/bin/ffmpeg";


    /**
     *
     * @param time       截取视频长度
     * @param start      截取开始时间 如果为null表示全部转换为gif
     * @param inputPath  被转换的mp4文件位置
     * @param outPath    转换后gif文件位置
     * @throws Exception
     */
    @SuppressWarnings("unused")
	public static void convetor(int time,String start,String inputPath, String outPath) throws Exception {
        List<String> command = new ArrayList<String>();
        command.add(ffmpegEXE);
        if(0!=time){
            command.add("-t");
            command.add(String.valueOf(time));
        }
        if(start!=null&&!"00:00:00".equals(start)){
            command.add("-ss");
            command.add(start);
        }
        command.add("-i");
        command.add(inputPath);
        command.add(outPath);
        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = null;
        try {
            process = builder.start();
        } catch (IOException e) {
            e.printStackTrace();
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
    String videoInputPath = "/Users/jinx/Downloads/1.mp4";
    String coverOutputPath = "/Users/jinx/Downloads/4.gif";
    try {
        convetor(5,"00:00:01",videoInputPath,coverOutputPath);
    } catch (Exception e) {
       e.printStackTrace();
    }
 }
	
}
