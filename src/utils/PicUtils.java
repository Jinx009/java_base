package utils;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class PicUtils {

	public static void map(String[] picPath,String fileName) {
		try {
			int width = 2688;// 图片宽度
			int height = 1520;// 图片高度

			File file_1 = new File(picPath[0]);
			BufferedImage image_1 = ImageIO.read(file_1);
			int[] array_1 = new int[width * height];
			array_1 = image_1.getRGB(0, 0, width, height, array_1, 0, width);

			File file_2 = new File(picPath[1]);
			BufferedImage image_2 = ImageIO.read(file_2);
			int[] array_2 = new int[width * height];
			array_2 = image_2.getRGB(0, 0, width, height, array_2, 0, width);

			File file_3 = new File(picPath[2]);
			BufferedImage image_3 = ImageIO.read(file_3);
			int[] array_3 = new int[width * height];
			array_3 = image_3.getRGB(0, 0, width, height, array_3, 0, width);

			File file_4 = new File(picPath[3]);
			BufferedImage image_4 = ImageIO.read(file_4);
			int[] array_4 = new int[width * height];
			array_4 = image_4.getRGB(0, 0, width, height, array_4, 0, width);

			// 生成新图片
			BufferedImage ImageNew = new BufferedImage(2 * width, 2 * height, BufferedImage.TYPE_INT_RGB);
			ImageNew.setRGB(0, 0, width, height, array_1, 0, width);
			ImageNew.setRGB(width, 0, width, height, array_4, 0, width);
			ImageNew.setRGB(0, height, width, height, array_2, 0, width);
			ImageNew.setRGB(width, height, width, height, array_3, 0, width);
			File outFile = new File(fileName);
			ImageIO.write(ImageNew, "jpeg", outFile);// 写图片

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 合成图片
	 * @param fileName
	 */
	public  static void checkPics(String fileName){
		String[] picPath = new String[]{fileName+"_step1.jpeg",fileName+"_step2.jpeg",fileName+"_step3.jpeg",fileName+"_step4.jpeg"};
		File file1 = new File(picPath[0]);
		File file2 = new File(picPath[1]);
		File file3 = new File(picPath[2]);
		File file4 = new File(picPath[3]);
		if(file1.exists()&&file2.exists()&&file3.exists()&&file4.exists()){
			map(picPath, fileName+"_steadyCarImg.jpeg");
		}
	}

}
