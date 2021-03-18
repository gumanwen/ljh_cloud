import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TestMp {

    public static void test() throws ParseException {
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String beginTime = "Tue Mar 16 2021 15:36:35 GMT 0800";
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss GMT z", Locale.ENGLISH);
        Date beginDate = sdf.parse(beginTime);
        beginTime = dateFormat.format(beginDate);
        System.out.println("111");
    }

    public static void main(String[] args) throws ParseException {
        test();
    }

}
