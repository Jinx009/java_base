package utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.jbarcode.JBarcode;
import org.jbarcode.encode.Code128Encoder;
import org.jbarcode.encode.InvalidAtributeException;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.WidthCodedPainter;
 
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
 


public class QCodeUtils {
	public static void main(String[] arge){
	    String a = "[\n" +
	            "{\"mac\":\"004A7702FD000012\",},\n" +
	            "{\"mac\":\"004A7702FD000013\",},\n" +
	            "{\"mac\":\"004A7702FD000014\",},\n" +
	            "{\"mac\":\"004A7702FD000016\",},\n" +
	            "{\"mac\":\"004A7702FD000017\",},\n" +
	            "{\"mac\":\"004A7702FD000018\",},\n" +
	            "{\"mac\":\"004A7702FD000019\",},\n" +
	            "{\"mac\":\"004A7702FD00001A\",},\n" +
	            "{\"mac\":\"004A7702FD00001E\",},\n" +
	            "{\"mac\":\"004A7702FD00001F\",},\n" +
	            "{\"mac\":\"004A7702FD000020\",},\n" +
	            "{\"mac\":\"004A7702FD000024\",},\n" +
	            "{\"mac\":\"004A7702FD000025\",},\n" +
	            "{\"mac\":\"004A7702FD000027\",},\n" +
	            "{\"mac\":\"004A7702FD000028\",},\n" +
	            "{\"mac\":\"004A7702FD000029\",},\n" +
	            "{\"mac\":\"004A7702FD00002A\",},\n" +
	            "{\"mac\":\"004A7702FD00002B\",},\n" +
	            "{\"mac\":\"004A7702FD00002C\",},\n" +
	            "{\"mac\":\"004A7702FD00002E\",},\n" +
	            "{\"mac\":\"004A7702FD00002F\",},\n" +
	            "{\"mac\":\"004A7702FD000031\",},\n" +
	            "{\"mac\":\"004A7702FD000032\",},\n" +
	            "{\"mac\":\"004A7702FD000033\",},\n" +
	            "{\"mac\":\"004A7702FD000034\",},\n" +
	            "{\"mac\":\"004A7702FD000035\",},\n" +
	            "{\"mac\":\"004A7702FD00003B\",},\n" +
	            "{\"mac\":\"004A7702FD00003E\",},\n" +
	            "{\"mac\":\"004A7702FD000045\",},\n" +
	            "{\"mac\":\"004A7702FD00004E\",},\n" +
	            "{\"mac\":\"004A7702FD000053\",},\n" +
	            "{\"mac\":\"004A7702FD000055\",},\n" +
	            "{\"mac\":\"004A7702FD000056\",},\n" +
	            "{\"mac\":\"004A7702FD000059\",},\n" +
	            "{\"mac\":\"004A7702FD00005A\",},\n" +
	            "{\"mac\":\"004A7702FD00005B\",},\n" +
	            "{\"mac\":\"004A7702FD00005D\",},\n" +
	            "{\"mac\":\"004A7702FD000061\",},\n" +
	            "{\"mac\":\"004A7702FD000069\",},\n" +
	            "{\"mac\":\"004A7702FD00006A\",},\n" +
	            "{\"mac\":\"004A7702FD00006B\",},\n" +
	            "{\"mac\":\"004A7702FD000074\",},\n" +
	            "{\"mac\":\"004A7702FD000078\",},\n" +
	            "{\"mac\":\"004A7702FD00007E\",},\n" +
	            "{\"mac\":\"004A7702FD000084\",},\n" +
	            "{\"mac\":\"004A7702FD000088\",},\n" +
	            "{\"mac\":\"004A7702FD00008B\",},\n" +
	            "{\"mac\":\"004A7702FD00008E\",},\n" +
	            "{\"mac\":\"004A7702FD000090\",},\n" +
	            "{\"mac\":\"004A7702FD000091\",},\n" +
	            "{\"mac\":\"004A7702FD00009A\",},\n" +
	            "{\"mac\":\"004A7702FD0000A6\",},\n" +
	            "{\"mac\":\"004A7702FD0000AF\",},\n" +
	            "{\"mac\":\"004A7702FD0000B3\",},\n" +
	            "{\"mac\":\"004A7702FD0000B5\",},\n" +
	            "{\"mac\":\"004A7702FD0000B8\",},\n" +
	            "{\"mac\":\"004A7702FD0000BB\",},\n" +
	            "{\"mac\":\"004A7702FD0000BF\",},\n" +
	            "{\"mac\":\"004A7702FD0000C1\",},\n" +
	            "{\"mac\":\"004A7702FD0000C3\",},\n" +
	            "{\"mac\":\"004A7702FD0000C4\",},\n" +
	            "{\"mac\":\"004A7702FD0000C5\",},\n" +
	            "{\"mac\":\"004A7702FD0000C6\",},\n" +
	            "{\"mac\":\"004A7702FD0000C7\",},\n" +
	            "{\"mac\":\"004A7702FD0000C8\",},\n" +
	            "{\"mac\":\"004A7702FD0000C9\",},\n" +
	            "{\"mac\":\"004A7702FD0000CA\",},\n" +
	            "{\"mac\":\"004A7702FD0000CB\",},\n" +
	            "{\"mac\":\"004A7702FD0000CC\",},\n" +
	            "{\"mac\":\"004A7702FD0000CD\",},\n" +
	            "{\"mac\":\"004A7702FD0000CE\",},\n" +
	            "{\"mac\":\"004A7702FD0000CF\",},\n" +
	            "{\"mac\":\"004A7702FD0000D0\",},\n" +
	            "{\"mac\":\"004A7702FD0000D1\",},\n" +
	            "{\"mac\":\"004A7702FD0000D2\",},\n" +
	            "{\"mac\":\"004A7702FD0000D3\",},\n" +
	            "{\"mac\":\"004A7702FD0000D4\",},\n" +
	            "{\"mac\":\"004A7702FD0000D5\",},\n" +
	            "{\"mac\":\"004A7702FD0000D6\",},\n" +
	            "{\"mac\":\"004A7702FD0000D7\",},\n" +
	            "{\"mac\":\"004A7702FD0000D8\",},\n" +
	            "{\"mac\":\"004A7702FD0000D9\",},\n" +
	            "{\"mac\":\"004A7702FD0000DA\",},\n" +
	            "{\"mac\":\"004A7702FD0000DB\",},\n" +
	            "{\"mac\":\"004A7702FD0000DC\",},\n" +
	            "{\"mac\":\"004A7702FD0000DD\",},\n" +
	            "{\"mac\":\"004A7702FD0000DE\",},\n" +
	            "{\"mac\":\"004A7702FD0000DF\",},\n" +
	            "{\"mac\":\"004A7702FD0000E0\",},\n" +
	            "{\"mac\":\"004A7702FD0000E1\",},\n" +
	            "{\"mac\":\"004A7702FD0000E2\",},\n" +
	            "{\"mac\":\"004A7702FD0000E3\",},\n" +
	            "{\"mac\":\"004A7702FD0000E4\",},\n" +
	            "{\"mac\":\"004A7702FD0000E5\",},\n" +
	            "{\"mac\":\"004A7702FD0000E6\",},\n" +
	            "{\"mac\":\"004A7702FD0000E7\",},\n" +
	            "{\"mac\":\"004A7702FD0000E8\",},\n" +
	            "{\"mac\":\"004A7702FD0000E9\",},\n" +
	            "{\"mac\":\"004A7702FD0000EA\",},\n" +
	            "{\"mac\":\"004A7702FD0000EB\",},\n" +
	            "{\"mac\":\"004A7702FD0000EC\",},\n" +
	            "{\"mac\":\"004A7702FD0000ED\",},\n" +
	            "{\"mac\":\"004A7702FD0000EE\",},\n" +
	            "{\"mac\":\"004A7702FD0000EF\",},\n" +
	            "{\"mac\":\"004A7702FD0000F0\",},\n" +
	            "{\"mac\":\"004A7702FD0000F1\",},\n" +
	            "{\"mac\":\"004A7702FD0000F2\",},\n" +
	            "{\"mac\":\"004A7702FD0000F3\",},\n" +
	            "{\"mac\":\"004A7702FD0000F4\",},\n" +
	            "{\"mac\":\"004A7702FD0000F5\",},\n" +
	            "{\"mac\":\"004A7702FD0000F6\",},\n" +
	            "{\"mac\":\"004A7702FD0000F7\",},\n" +
	            "{\"mac\":\"004A7702FD0000F8\",},\n" +
	            "{\"mac\":\"004A7702FD0000F9\",},\n" +
	            "{\"mac\":\"004A7702FD0000FA\",},\n" +
	            "{\"mac\":\"004A7702FD0000FB\",},\n" +
	            "{\"mac\":\"004A7702FD0000FC\",},\n" +
	            "{\"mac\":\"004A7702FD0000FD\",},\n" +
	            "{\"mac\":\"004A7702FD0000FE\",},\n" +
	            "{\"mac\":\"004A7702FD0000FF\",}\n" +
	            "]";
	       JSONArray array = JSONObject.parseArray(a);
	       System.out.println("总计："+array.size());
	       for(int i =0;i<array.size();i++){
	    	   create(array.getJSONObject(i).getString("mac"));
	       }
    }
	
	public static void create(String mac){
		JBarcode localJBarcode = new JBarcode(Code128Encoder.getInstance(), WidthCodedPainter.getInstance(), BaseLineTextPainter.getInstance());
        localJBarcode.setEncoder(Code128Encoder.getInstance());
        localJBarcode.setPainter(WidthCodedPainter.getInstance());
        localJBarcode.setTextPainter(BaseLineTextPainter.getInstance());
        localJBarcode.setShowCheckDigit(false);
 
        BufferedImage localBufferedImage = null;
        try {
            localBufferedImage = localJBarcode.createBarcode(mac);
        } catch (InvalidAtributeException e) {
            e.printStackTrace();
        }
        OutputStream jos = null;
        try {
            jos = new FileOutputStream("/Users/jinx/Downloads/test/"+mac+".jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(jos);
        JPEGEncodeParam jpegEP = JPEGCodec.getDefaultJPEGEncodeParam(localBufferedImage);
        jpegEP.setQuality((float) 1, true);
        try {
            encoder.encode(localBufferedImage, jpegEP);
            jos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                jos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
}
