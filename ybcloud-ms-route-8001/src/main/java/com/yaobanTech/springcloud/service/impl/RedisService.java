package com.yaobanTech.springcloud.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 * @ClassName: RedisGeneratorCode
 * @Description: 生成日期开头加0001，0002，0003的工具类
 * @author ljh
 * @date 2018/10/09
 */
@Service
public class RedisService {

    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);

    private RedisTemplate  redisTemplate;

    @Autowired
    public RedisService(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     *
     * @Title: generateCode
     * @Description: 生成自定义前缀的类似 HTG201810120001格式的自增数
     * @param key
     * @param prefix
     * @param hasExpire
     * @param minLength
     * @return
     * String
     */
    public String generateCode(String key, String prefix, boolean hasExpire, Integer minLength) {
        return this.createGenerateCode(key, prefix, hasExpire, minLength);
    }

    /**
     *
     * @Title: generateCode
     * @Description: 生成 类似 201810120001格式的自增数
     * @param key
     * @param hasExpire
     * @param minLength
     * @return
     * String
     */
    public String generateCode(String key, boolean hasExpire, Integer minLength) {
        return this.createGenerateCode(key, "", hasExpire, minLength);
    }

    /**
     *
     * @Title: generateCode
     * @Description: 组装符合自己规则的id并设置过期时间
     * @param key	redis中的key值
     * @param prefix	最后编码的前缀
     * @param hasExpire	redis 是否使用过期时间设置自增id
     * @param minLength	redis生成的自增id的最小长度，如果小于这个长度前面补0
     * @return
     * String
     */
    public String createGenerateCode(String key, String prefix, boolean hasExpire, Integer minLength) {
        try {
            Date date = null;
            Long id = null;
            Calendar calendar = Calendar.getInstance();
            if (hasExpire) {
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                calendar.set(Calendar.MILLISECOND, 9999);
                date = calendar.getTime();
            } else {
                calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 10);
                date = calendar.getTime();
            }
            id = this.generateId(key, date);
            if (id != null) {
                return this.format(id, prefix, minLength);
            }
        } catch (Exception e) {
            logger.info("error --> redis 生成自增id出现异常");
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     *
     * @Title: generateId
     * @Description: 使用RedisAtomicLong自增
     * @param key
     * @param date
     * @return
     * Long
     */
    private Long generateId(String key, Date date) {
        RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        // 通过key获取自增并设定过期时间
        counter.expireAt(date);
        return counter.incrementAndGet();
    }

    /**
     *
     * @Title: format
     * @Description: 获取 redis 自增后，生成自定义格式的id
     * @param id	redis 获取的 id值
     * @param prefix	自定义前缀
     * @param minLength	生成数的长度，不满足时 0 补齐
     * @return
     * String
     */
    private String format(Long id, String prefix, Integer minLength) {

        // 拼接的字符串
        StringBuffer sb = new StringBuffer();
        // 当前日期
        Date date = new Date();
        // 自定义前缀
        sb.append(prefix);
        if (date != null) {
            DateFormat df = new SimpleDateFormat("yyyyMM");
            sb.append(df.format(date));
        }

        /* 对不满足长度的id值,使用0补齐 */
        // redis 生成的id值
        String strId = String.valueOf(id);
        // redis 生成id 的长度
        int length = strId.length();
        if (length < minLength) {
            for (int i = 0; i < minLength - length; i++) {
                sb.append("0");
            }
            sb.append(strId);
        } else {
            sb.append(strId);
        }
        return sb.toString();
    }

}
