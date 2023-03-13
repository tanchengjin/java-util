package com.tanchengjin.utils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author TanChengjin
 * @Email 18865477815@163.com
 * @Since V1.0.0
 **/
public class MockUtil {

    /**
     * 获取范围内的随机事件
     * @param beginTime  开始时间
     * @param endTime    结束时间
     * @return
     */
    public Date getRandomDate(Date beginTime, Date endTime) {

        Integer randomTime = 10;

        List<Date> randomList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginTime);     //获得当前日期 YYYY-MM-dd HH:mm:ss

        Date tmp2 = beginTime;
        while (tmp2.getTime() < endTime.getTime()) {
            tmp2 = calendar.getTime();
//            String format = sdf.format(tmp2);
//            System.out.println(format);
            // 分钟 + 10
            calendar.add(Calendar.MINUTE, randomTime);
            randomList.add(tmp2);
        }

        // 随机取集合中一位返回
        Random random = new Random();
        int index = random.nextInt(randomList.size());
        return randomList.get(index);
    }
}
