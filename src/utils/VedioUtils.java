package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.helper.StringUtil;
import ws.schild.jave.MultimediaInfo;
import ws.schild.jave.MultimediaObject;

public class VedioUtils {

	// Linux与mac下 ffmpeg的路径
//	private static String ffmpegEXE = "/usr/local/Cellar/ffmpeg/4.1.4_1/bin/ffmpeg";

	 private static String ffmpegEXE = "/usr/local/ffmpeg/bin/ffmpeg";
	 
	 private static final Logger log = LoggerFactory.getLogger(VedioUtils.class);

	/**
	 * 开辟线程处理流
	 * 
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
	                    log.warn("output: " + line);
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
	                    log.warn("output: " + line);
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

	public static void main(String[] args) {
		System.out.println(getVedioTime(new File(
				"/Users/jinx/Documents/jobs/git_mine/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/java_base/themes/upload_files/vedio/19042804521310107745_1564456102642/19042804521310107745_1564456102642.mp4")));
	}

	/**
	 * 获取视频时长：秒
	 * 
	 * @param file
	 * @return
	 */
	public static Long getVedioTime(File file) {
		try {
			MultimediaObject instance = new MultimediaObject(file);
			MultimediaInfo result = instance.getInfo();
			long ls = result.getDuration() / 1000;
			return ls;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0l;
	}
	
	

	/**
	 * 截取一帧作为图片
	 * 
	 * @param fileName
	 *            ffmpeg -i /Users/jinx/Downloads/test.mp4 -y -f image2 -ss
	 *            00:00:01 -vframes 1 /Users/jinx/Downloads/test.jpeg
	 * @return
	 */
	public static boolean covPic(String fileName, String time, String outName, String size) {
		List<String> command = new ArrayList<String>();
		command.add(ffmpegEXE);
		command.add("-i");
		command.add(fileName);
		command.add("-y");
		command.add("-f");
		command.add("image2");
		command.add("-ss");
		command.add(time);
		command.add("-vframes");
		command.add("1");
		command.add("-s");
		command.add(size);
		if (StringUtil.isNotBlank(outName)) {
			command.add(outName);
		} else {
			command.add(fileName + ".jpeg");
		}
		try {
			Process videoProcess = new ProcessBuilder(command).start();
			dealStream(videoProcess);
			videoProcess.waitFor();
			return true;
		} catch (Exception e) {
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

}
