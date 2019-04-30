package com.lovo.fire_company.util;

import java.sql.Timestamp;

public class DateUtil {
    /**
     * 
     * @param time 页面获取的时间(yyyy-MM-ddThh:mm)
     * @return 返回时间戳
     */
    public static Timestamp stringToTimestamp(String time) {
        if (time == null || "".equals(time)) {
            return null;
        } else {
            time = time.replace("T", " ");//2019-12-31 23:59
            StringBuffer stringBuffer = new StringBuffer(time).append(":00.000");
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            try {
                ts = Timestamp.valueOf(new String(stringBuffer));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ts;
        }
    }
}
