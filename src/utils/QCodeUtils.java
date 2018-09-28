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
	            "{\"mac\":\"004A7702FD000010\",},\n" +
	            "{\"mac\":\"004A7702FD000011\",},\n" +
	            "{\"mac\":\"004A7702FD000012\",},\n" +
	            "{\"mac\":\"004A7702FD000013\",},\n" +
	            "{\"mac\":\"004A7702FD000014\",},\n" +
	            "{\"mac\":\"004A7702FD000015\",},\n" +
	            "{\"mac\":\"004A7702FD000016\",},\n" +
	            "{\"mac\":\"004A7702FD000017\",},\n" +
	            "{\"mac\":\"004A7702FD000018\",},\n" +
	            "{\"mac\":\"004A7702FD000019\",},\n" +
	            "{\"mac\":\"004A7702FD00001A\",},\n" +
	            "{\"mac\":\"004A7702FD00001B\",},\n" +
	            "{\"mac\":\"004A7702FD00001C\",},\n" +
	            "{\"mac\":\"004A7702FD00001D\",},\n" +
	            "{\"mac\":\"004A7702FD00001E\",},\n" +
	            "{\"mac\":\"004A7702FD00001F\",},\n" +
	            "{\"mac\":\"004A7702FD000020\",},\n" +
	            "{\"mac\":\"004A7702FD000021\",},\n" +
	            "{\"mac\":\"004A7702FD000022\",},\n" +
	            "{\"mac\":\"004A7702FD000023\",},\n" +
	            "{\"mac\":\"004A7702FD000024\",},\n" +
	            "{\"mac\":\"004A7702FD000025\",},\n" +
	            "{\"mac\":\"004A7702FD000026\",},\n" +
	            "{\"mac\":\"004A7702FD000027\",},\n" +
	            "{\"mac\":\"004A7702FD000028\",},\n" +
	            "{\"mac\":\"004A7702FD000029\",},\n" +
	            "{\"mac\":\"004A7702FD00002A\",},\n" +
	            "{\"mac\":\"004A7702FD00002B\",},\n" +
	            "{\"mac\":\"004A7702FD00002C\",},\n" +
	            "{\"mac\":\"004A7702FD00002D\",},\n" +
	            "{\"mac\":\"004A7702FD00002E\",},\n" +
	            "{\"mac\":\"004A7702FD00002F\",},\n" +
	            "{\"mac\":\"004A7702FD000030\",},\n" +
	            "{\"mac\":\"004A7702FD000031\",},\n" +
	            "{\"mac\":\"004A7702FD000032\",},\n" +
	            "{\"mac\":\"004A7702FD000033\",},\n" +
	            "{\"mac\":\"004A7702FD000034\",},\n" +
	            "{\"mac\":\"004A7702FD000035\",},\n" +
	            "{\"mac\":\"004A7702FD000036\",},\n" +
	            "{\"mac\":\"004A7702FD000037\",},\n" +
	            "{\"mac\":\"004A7702FD000038\",},\n" +
	            "{\"mac\":\"004A7702FD000039\",},\n" +
	            "{\"mac\":\"004A7702FD00003A\",},\n" +
	            "{\"mac\":\"004A7702FD00003B\",},\n" +
	            "{\"mac\":\"004A7702FD00003C\",},\n" +
	            "{\"mac\":\"004A7702FD00003D\",},\n" +
	            "{\"mac\":\"004A7702FD00003E\",},\n" +
	            "{\"mac\":\"004A7702FD00003F\",},\n" +
	            "{\"mac\":\"004A7702FD000040\",},\n" +
	            "{\"mac\":\"004A7702FD000041\",},\n" +
	            "{\"mac\":\"004A7702FD000042\",},\n" +
	            "{\"mac\":\"004A7702FD000043\",},\n" +
	            "{\"mac\":\"004A7702FD000044\",},\n" +
	            "{\"mac\":\"004A7702FD000045\",},\n" +
	            "{\"mac\":\"004A7702FD000046\",},\n" +
	            "{\"mac\":\"004A7702FD000047\",},\n" +
	            "{\"mac\":\"004A7702FD000048\",},\n" +
	            "{\"mac\":\"004A7702FD000049\",},\n" +
	            "{\"mac\":\"004A7702FD00004A\",},\n" +
	            "{\"mac\":\"004A7702FD00004B\",},\n" +
	            "{\"mac\":\"004A7702FD00004C\",},\n" +
	            "{\"mac\":\"004A7702FD00004D\",},\n" +
	            "{\"mac\":\"004A7702FD00004E\",},\n" +
	            "{\"mac\":\"004A7702FD00004F\",},\n" +
	            "{\"mac\":\"004A7702FD000050\",},\n" +
	            "{\"mac\":\"004A7702FD000051\",},\n" +
	            "{\"mac\":\"004A7702FD000052\",},\n" +
	            "{\"mac\":\"004A7702FD000053\",},\n" +
	            "{\"mac\":\"004A7702FD000054\",},\n" +
	            "{\"mac\":\"004A7702FD000055\",},\n" +
	            "{\"mac\":\"004A7702FD000056\",},\n" +
	            "{\"mac\":\"004A7702FD000057\",},\n" +
	            "{\"mac\":\"004A7702FD000058\",},\n" +
	            "{\"mac\":\"004A7702FD000059\",},\n" +
	            "{\"mac\":\"004A7702FD00005A\",},\n" +
	            "{\"mac\":\"004A7702FD00005B\",},\n" +
	            "{\"mac\":\"004A7702FD00005C\",},\n" +
	            "{\"mac\":\"004A7702FD00005D\",},\n" +
	            "{\"mac\":\"004A7702FD00005E\",},\n" +
	            "{\"mac\":\"004A7702FD00005F\",},\n" +
	            "{\"mac\":\"004A7702FD000060\",},\n" +
	            "{\"mac\":\"004A7702FD000061\",},\n" +
	            "{\"mac\":\"004A7702FD000062\",},\n" +
	            "{\"mac\":\"004A7702FD000063\",},\n" +
	            "{\"mac\":\"004A7702FD000064\",},\n" +
	            "{\"mac\":\"004A7702FD000065\",},\n" +
	            "{\"mac\":\"004A7702FD000066\",},\n" +
	            "{\"mac\":\"004A7702FD000067\",},\n" +
	            "{\"mac\":\"004A7702FD000068\",},\n" +
	            "{\"mac\":\"004A7702FD000069\",},\n" +
	            "{\"mac\":\"004A7702FD00006A\",},\n" +
	            "{\"mac\":\"004A7702FD00006B\",},\n" +
	            "{\"mac\":\"004A7702FD00006C\",},\n" +
	            "{\"mac\":\"004A7702FD00006D\",},\n" +
	            "{\"mac\":\"004A7702FD00006E\",},\n" +
	            "{\"mac\":\"004A7702FD00006F\",},\n" +
	            "{\"mac\":\"004A7702FD000070\",},\n" +
	            "{\"mac\":\"004A7702FD000071\",},\n" +
	            "{\"mac\":\"004A7702FD000072\",},\n" +
	            "{\"mac\":\"004A7702FD000073\",},\n" +
	            "{\"mac\":\"004A7702FD000074\",},\n" +
	            "{\"mac\":\"004A7702FD000075\",},\n" +
	            "{\"mac\":\"004A7702FD000076\",},\n" +
	            "{\"mac\":\"004A7702FD000077\",},\n" +
	            "{\"mac\":\"004A7702FD000078\",},\n" +
	            "{\"mac\":\"004A7702FD000079\",},\n" +
	            "{\"mac\":\"004A7702FD00007A\",},\n" +
	            "{\"mac\":\"004A7702FD00007B\",},\n" +
	            "{\"mac\":\"004A7702FD00007C\",},\n" +
	            "{\"mac\":\"004A7702FD00007D\",},\n" +
	            "{\"mac\":\"004A7702FD00007E\",},\n" +
	            "{\"mac\":\"004A7702FD00007F\",},\n" +
	            "{\"mac\":\"004A7702FD000080\",},\n" +
	            "{\"mac\":\"004A7702FD000081\",},\n" +
	            "{\"mac\":\"004A7702FD000082\",},\n" +
	            "{\"mac\":\"004A7702FD000083\",},\n" +
	            "{\"mac\":\"004A7702FD000084\",},\n" +
	            "{\"mac\":\"004A7702FD000085\",},\n" +
	            "{\"mac\":\"004A7702FD000086\",},\n" +
	            "{\"mac\":\"004A7702FD000087\",},\n" +
	            "{\"mac\":\"004A7702FD000088\",},\n" +
	            "{\"mac\":\"004A7702FD000089\",},\n" +
	            "{\"mac\":\"004A7702FD00008A\",},\n" +
	            "{\"mac\":\"004A7702FD00008B\",},\n" +
	            "{\"mac\":\"004A7702FD00008C\",},\n" +
	            "{\"mac\":\"004A7702FD00008D\",},\n" +
	            "{\"mac\":\"004A7702FD00008E\",},\n" +
	            "{\"mac\":\"004A7702FD00008F\",},\n" +
	            "{\"mac\":\"004A7702FD000090\",},\n" +
	            "{\"mac\":\"004A7702FD000091\",},\n" +
	            "{\"mac\":\"004A7702FD000092\",},\n" +
	            "{\"mac\":\"004A7702FD000093\",},\n" +
	            "{\"mac\":\"004A7702FD000094\",},\n" +
	            "{\"mac\":\"004A7702FD000095\",},\n" +
	            "{\"mac\":\"004A7702FD000096\",},\n" +
	            "{\"mac\":\"004A7702FD000097\",},\n" +
	            "{\"mac\":\"004A7702FD000098\",},\n" +
	            "{\"mac\":\"004A7702FD000099\",},\n" +
	            "{\"mac\":\"004A7702FD00009A\",},\n" +
	            "{\"mac\":\"004A7702FD00009B\",},\n" +
	            "{\"mac\":\"004A7702FD00009C\",},\n" +
	            "{\"mac\":\"004A7702FD00009D\",},\n" +
	            "{\"mac\":\"004A7702FD00009E\",},\n" +
	            "{\"mac\":\"004A7702FD00009F\",},\n" +
	            "{\"mac\":\"004A7702FD0000A0\",},\n" +
	            "{\"mac\":\"004A7702FD0000A1\",},\n" +
	            "{\"mac\":\"004A7702FD0000A2\",},\n" +
	            "{\"mac\":\"004A7702FD0000A3\",},\n" +
	            "{\"mac\":\"004A7702FD0000A4\",},\n" +
	            "{\"mac\":\"004A7702FD0000A5\",},\n" +
	            "{\"mac\":\"004A7702FD0000A6\",},\n" +
	            "{\"mac\":\"004A7702FD0000A7\",},\n" +
	            "{\"mac\":\"004A7702FD0000A8\",},\n" +
	            "{\"mac\":\"004A7702FD0000A9\",},\n" +
	            "{\"mac\":\"004A7702FD0000AA\",},\n" +
	            "{\"mac\":\"004A7702FD0000AB\",},\n" +
	            "{\"mac\":\"004A7702FD0000AC\",},\n" +
	            "{\"mac\":\"004A7702FD0000AD\",},\n" +
	            "{\"mac\":\"004A7702FD0000AE\",},\n" +
	            "{\"mac\":\"004A7702FD0000AF\",},\n" +
	            "{\"mac\":\"004A7702FD0000B0\",},\n" +
	            "{\"mac\":\"004A7702FD0000B1\",},\n" +
	            "{\"mac\":\"004A7702FD0000B2\",},\n" +
	            "{\"mac\":\"004A7702FD0000B3\",},\n" +
	            "{\"mac\":\"004A7702FD0000B4\",},\n" +
	            "{\"mac\":\"004A7702FD0000B5\",},\n" +
	            "{\"mac\":\"004A7702FD0000B6\",},\n" +
	            "{\"mac\":\"004A7702FD0000B7\",},\n" +
	            "{\"mac\":\"004A7702FD0000B8\",},\n" +
	            "{\"mac\":\"004A7702FD0000B9\",},\n" +
	            "{\"mac\":\"004A7702FD0000BA\",},\n" +
	            "{\"mac\":\"004A7702FD0000BB\",},\n" +
	            "{\"mac\":\"004A7702FD0000BC\",},\n" +
	            "{\"mac\":\"004A7702FD0000BD\",},\n" +
	            "{\"mac\":\"004A7702FD0000BE\",},\n" +
	            "{\"mac\":\"004A7702FD0000BF\",},\n" +
	            "{\"mac\":\"004A7702FD0000C0\",},\n" +
	            "{\"mac\":\"004A7702FD0000C1\",},\n" +
	            "{\"mac\":\"004A7702FD0000C2\",},\n" +
	            "{\"mac\":\"004A7702FD0000C3\",},\n" +
	            "\n" +
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
