package cn.yoyo.ws.tools;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Repository
public class QrCreate {
    public static String qrmsg = null;

    //用户类型
    public static final String TYPE = "B#";
    //序列号
    public static final String SERIAL = "RLX-00112236";
    //用户id
    public static String userId = "1";
    //开始时间戳
    public static long beforetime;
    //结束时间戳
    public static long aftertime;
    //有效次数
    public static int effectiveCount = 5;
    //二维码id
    public static int qrId = 15;
    //项目id
    public static int projectId = 1;
    //校验数
    public static long checkCode = 0;

    public static String create() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date beforeDate = new Date();
        //精确到分钟的秒级时间戳
        beforetime = getSecondTimestamp(dateFormat.parse(dateFormat.format(beforeDate)));

        String befretime36 = Long.toString(beforetime, 36);
        Date afterDate = new Date(beforeDate.getTime()+600000);  //+10分钟
        aftertime = getSecondTimestamp(dateFormat.parse(dateFormat.format(afterDate)));

        checkCode = (beforetime+aftertime+effectiveCount+qrId+projectId+6)^9;

        String aftertime36 = Long.toString(aftertime, 36);
        String checkCode36 = Long.toString(checkCode, 36);
        String effectiveCount36 = Long.toString(effectiveCount, 36);
        String qrId36 = Long.toString(qrId, 36);
        String projectId36 = Long.toString(projectId, 36);
        qrmsg = TYPE+"&"+checkCode36+"&"+befretime36+"&"+aftertime36+"&"+effectiveCount36+"&"+qrId36+"&1&"+SERIAL;

        return TYPE+"&"+checkCode36+"&"+befretime36+"&"+aftertime36+"&"+effectiveCount36+"&"+qrId36+"&1&"+SERIAL;
    }

    /**
     *  宽、高：128
     * @param text  二维码内容
     * @param filePath  保存路径
     * @throws WriterException
     * @throws IOException
     */
    public static void generateQRCodeImage(String text, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 128, 128);

        Path path = FileSystems.getDefault().getPath(filePath);

        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    /**
     * 获取精确到秒的时间戳
     * @return
     */
    public static int getSecondTimestamp(Date date){
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime());
        int length = timestamp.length();
        if (length > 3) {
            return Integer.valueOf(timestamp.substring(0,length-3));
        } else {
            return 0;
        }
    }

}
