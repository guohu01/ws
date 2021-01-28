package cn.yoyo.ws.tools;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.*;

public class BASE64Util {

    private static final Log logger = LogFactory.getLog(BASE64Util.class);

    private static final double accuracy = 0.95d;

    public static void main(String[] args) {
//        compressPicForScale("C:\\Users\\Administrator\\Desktop\\a.jpg", 50);
        String s = "data%3Aimage%2Fjpeg%3Bbase64%2CiVBORw0KGgoAAAANSUhEUgAAAIAAAACAAQAAAADrRVxmAAABMElEQVR42u3VsYnEMBAF0DEOlHkb%0D%0AEKgNZWrJbsD2NuBrSZnaMLgBOVMgPPe1LGb3gtMY7rIVBsELhuFLIxP%2FWPSBP4BINLptJk1khLBz%0D%0A7q3uVJ5YDF6P2JLu1QVADXL5EkyBiK4A5yHw8tZ6BZDHEDS%2Bl4Aq8FhmD%2BbtGH6FiLzVtrh2sVI4%0D%0AHC%2B25bQOz07rgLxHhzJ8PGvUAXukfEONZIQQXe5cOzuzKBYC%2B3LjJk%2B9NVJI66jMF9N4tl4Fv7HH%0D%0ApcgNGyHsyKOkzgexEKJD5KXT5syjBnvplO9h7c6idUDq5p4wrGII6y1ss6LGSwErWj0ls4sB09Dj%0D%0AYC0mWwqYuclrXNLoxIDHwCF1DPcFGMpZlcmTw6PTTJalgLeQdBPOMa1DeT8Ytzs3QQqf39p%2Fwzfz%0D%0AXQ59xhd%2FoAAAAABJRU5ErkJggg%3D%3D";
        String s1 = decodeBase64ToImage(s, "D:\\Download\\", "c.png");
        System.out.println(s1);
    }

    /**
     * 根据指定大小压缩图片
     *
     * @param imagePath 源图片地址
     * @param fileSize  指定图片大小，单位kb
     * @return 压缩质量后的图片BASE64转码字符串
     */

    public static String compressPicForScale(String imagePath,String imageUrl, long fileSize) {
        BASE64Encoder encoder = new BASE64Encoder();
        InputStream in = null;
        byte[] data = null;

        if (imagePath==null || imagePath.isEmpty()){
            try {
                URL url = new URL(imageUrl);
                //创建连接对象
                URLConnection urlConnection = url.openConnection();
                urlConnection.setConnectTimeout(1000);
                urlConnection.setReadTimeout(5000);
                urlConnection.connect();
                //获取流
                in = urlConnection.getInputStream();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                in = new FileInputStream(imagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //赋值，关闭流
        try {
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


//        System.out.println("原始数据大小=======" + data.length);
        if (data.length < fileSize * 1024) {
//            System.out.println("小于1024图片：" + encoder.encode(data));
            try {
//                System.out.println("url加密"+URLEncoder.encode(encoder.encode(data),"utf-8"));
                return URLEncoder.encode("data:image/jpeg;base64,"+encoder.encode(data),"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        try {
            while (data.length > fileSize * 1024) {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
                /**
                 * Thumbnails.of(InputStream... inputStreams) 从流读入源;
                 * .scale(double scale) 按比例缩放，0~1缩小，1原比例，>1放大;
                 * .scale(doublescaleWidth, double scaleHeight) 长宽各自设置比例，会拉伸;
                 * .outputQuality(double quality) 质量0.0<=quality<=1.0;
                 * .toOutputStream(OutputStream os) 无返回，写入outputStream里;
                 *
                 */
                Thumbnails.of(inputStream).scale(accuracy)
                        .outputQuality(accuracy).toOutputStream(outputStream);
                data = outputStream.toByteArray();
            }
        } catch (Exception e) {
            logger.error("图片压缩失败=======", e);
        }

        System.out.println("压缩后数据大小=======" + data.length);
//        System.out.println("压缩图片：" + encoder.encode(data));
        try {
            System.out.println("url加密2"+URLEncoder.encode(encoder.encode(data),"utf-8"));
            return URLEncoder.encode(encoder.encode(data),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        return encoder.encode(data);
        return "";
    }

    /**
     * url编码的图片解码为base64
     * base64编码的图片解码，并保存到指定目录
     * @param str url编码数据
     * @param path
     * @param imgName
     */
    public static String decodeBase64ToImage(String str, String path,String imgName) {
        String base64 = null;
        if (str!=null){
            try {
                base64 = URLDecoder.decode(str, "utf-8");
                base64 = base64.split(",")[1];
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        BASE64Decoder decoder = new BASE64Decoder();
        try {
            FileOutputStream write = new FileOutputStream(new File(path
                    + imgName));
            byte[] decoderBytes = decoder.decodeBuffer(base64);
            write.write(decoderBytes);
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path+imgName;
    }

}
