import com.alibaba.fastjson.JSONArray;
import com.yaobanTech.springcloud.entity.BizHiddenDangerPointEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TestMp {

    public static void test() throws ParseException {
        /*impleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String beginTime = "Tue Mar 16 2021 15:36:35 GMT 0800";
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss GMT z", Locale.ENGLISH);
        Date beginDate = sdf.parse(beginTime);
        beginTime = dateFormat.format(beginDate);
        System.out.println("111");*/
        BizHiddenDangerPointEntity p = new BizHiddenDangerPointEntity(10, 11);
        Object obj = JSONArray.toJSON(p);
        String json = obj.toString();
        System.out.println(json);
    }

    public static void main(String[] args) throws ParseException {
        test();
    }

}
