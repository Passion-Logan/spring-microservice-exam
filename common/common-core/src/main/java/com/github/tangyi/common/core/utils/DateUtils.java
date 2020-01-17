package com.github.tangyi.common.core.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.esotericsoftware.minlog.Log;

/**
 * 日期工具类
 * <p>
 * 代码描述
 * <p>
 *
 * @author WQL
 * @since 2020年1月17日 0017 10:15
 */
public class DateUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final DateTimeFormatter FORMATTER_MILLIS = DateTimeFormatter.ofPattern("yyyyMMddhhmmssSSS");

    /**
     * 日期转string
     *
     * @param date
     * @return
     */
    public static String localDateToString(LocalDateTime date) {
        return date != null ? date.format(FORMATTER) : "";
    }

    /**
     * 日期转string
     *
     * @param date
     * @return
     */
    public static String localDateMillisToString(LocalDateTime date) {
        return date != null ? date.format(FORMATTER_MILLIS) : "";
    }

    /**
     * LocalDate转Date
     *
     * @param localDate
     * @return
     */
    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDateTime转Date
     *
     * @param localDateTime
     * @return
     */
    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Date转LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Date转LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 两个时间之差
     *
     * @param startTime
     * @param endDate
     * @return 分钟
     */
    public static Integer getBetweenMinutes(Date startTime, Date endDate) {
        int minutes = 0;
        try {
            if (startTime != null && endDate != null) {
                long ss;
                if (startTime.before(endDate)) {
                    ss = endDate.getTime() - startTime.getTime();
                } else {
                    ss = startTime.getTime() - endDate.getTime();
                }
                minutes = (int)(ss / (60 * 1000));
            }
        } catch (Exception e) {
            Log.error(e.getMessage(), e);
        }
        return minutes;
    }

    /**
     * 两个时间之差
     *
     * @param startTime
     * @param endTime
     * @return 秒数
     */
    public static Integer getBetweenSecond(Date startTime, Date endTime) {
        int minutes = 0;
        try {
            if (startTime != null && endTime != null) {
                long ss;
                if (startTime.before(endTime)) {
                    ss = endTime.getTime() - startTime.getTime();
                } else {
                    ss = startTime.getTime() - endTime.getTime();
                }
                minutes = (int)(ss / 1000);
            }
        } catch (Exception e) {
            Log.error(e.getMessage(), e);
        }
        return minutes;
    }
}
