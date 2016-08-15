package com.thinksky.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import com.thinksky.tox.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 根据时间格式，获取时间字符串<BR>
 *
 * TODO 可以考虑引进JodaTime之类的时间库
 */
public class DateUtils {

  /**
   * 日期Format格式"yyyy-MM-dd HH:mm"
   */
  @SuppressLint("SimpleDateFormat")
  public static final SimpleDateFormat TIME_FORMAT_BASE =
      new SimpleDateFormat("yyyy.MM.dd");

  public static final String FORMAT_YEAR_MONTH = "yy-MM";
  public static final String FORMAT_YEAR_MONTH_DAY = "yyyy-MM-dd";
  public static final String FORMAT_HOUR_MINUTE = "HH-mm";
  public static final String FORMAT_FULL_TIME = "yyyy-MM-dd HH:MM";
  public static final String FORMAT_FULL_TIME_CHINESE = "yyyy年MM-dd日";

  /**
   * 转换的时间格式
   */
  private static final String TIMESLOT_FORMAT = "HH:mm";

  /**
   * 1秒
   */
  private static final int ONE_MILLISECOND = 1000;

  /**
   * 一小时的毫秒数
   */
  private static final long ONE_HOUR_IN_MILLISECOND = 60 * 60 * 1000;

  private static final long ONE_MINUTE_IN_MILIISECOND = 60 * 1000;

  /**
   * 根据给定的格式与时间(Date类型的)，返回时间字符串。最常用。<BR>
   *
   * @param date 指定的日期
   * @param format 日期格式字符串
   * @return String 指定格式的日期字符串.
   */
  public static String getFormatDateTime(Date date, String format) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    return sdf.format(date);
  }

  public static String getSimpleDateTime(long time) {
    return TIME_FORMAT_BASE.format(new Date(time));
  }

  /**
   * String的日期格式转化为Date
   *
   * @param format 日期格式
   * @param dateStr 日期字符串
   * @return Date
   */
  public static Date getDateFromString(String format, String dateStr) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    try {
      return sdf.parse(dateStr);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 是否明天
   *
   * @param time 时间
   * @return true:是明天; false:不是明天.
   */
  public static boolean isTomorrow(long time) {
    Calendar c = Calendar.getInstance();
    c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
    Date tomorrow = c.getTime();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    return format.format(tomorrow).equals(format.format(new Date(time)));
  }

  public static boolean isToday(long time) {
    Calendar c = Calendar.getInstance();
    c.set(Calendar.DATE, c.get(Calendar.DATE));
    Date tomorrow = c.getTime();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    return format.format(tomorrow).equals(format.format(new Date(time)));
  }

  /**
   * 判断送达时间是否是今天
   *
   * @param deliverTime 送达时间
   * @return 返回是否是今天
   */
  public static boolean isTodayDelivered(long deliverTime) {
    //当前与服务器同步过的时间
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    //当前配送时间减去1秒，判断是哪一天的
    return format.format(new Date(System.currentTimeMillis()))
        .equals(format.format(new Date(deliverTime - ONE_MILLISECOND)));
  }

  /**
   * 根据截至时间获取1小时的时间段显示<BR>
   *
   * @param endTime 截止时间
   * @return 返回时间段的字符串
   */
  public static String getOneHourTimeSlot(long endTime) {
    String endTimeStr = getFormatDateTime(new Date(endTime), TIMESLOT_FORMAT);
    long startTime = endTime - ONE_HOUR_IN_MILLISECOND;
    String startTimeStr = getFormatDateTime(new Date(startTime), TIMESLOT_FORMAT);
    return startTimeStr + "-" + endTimeStr;
  }

  public static String getDisplayTime(long time, Context context) {
    if (isToday(time)) {
      long timeTemp = System.currentTimeMillis() - time;
      int hour = (int) (timeTemp / ONE_HOUR_IN_MILLISECOND);
      if (hour > 0) {
        return context.getString(R.string.time_format_hour, hour);
      }
      int minute = (int) (timeTemp / ONE_MINUTE_IN_MILIISECOND);
      if (minute > 0) {
        return context.getString(R.string.time_format_minute, minute);
      } else {
        return context.getString(R.string.time_format_just);
      }
    }
    return getFormatDateTime(new Date(time), FORMAT_FULL_TIME);
  }

  /**
   * 聊天时间类型<BR>
   *
   * @author zhaozeyang
   * @version [Paitao Client V20130911, 2013-11-27]
   */
  public enum ChatTimeType {
    /**
     * 今天
     */
    TODAY {
      @Override
      public String getTimeStr(long timeStamp) {
        return TODAY_TIME_FORMAT.format(new Date(timeStamp));
      }
    },
    /**
     * 昨天
     */
    YESTERDAY {
      @Override
      public String getTimeStr(long timeStamp) {
        return TODAY_TIME_FORMAT.format(new Date(timeStamp));
      }
    },
    /**
     * 一周内
     */
    WEEK {
      @Override
      public String getTimeStr(long timeStamp) {
        return WEEK_TIME_FORMAT.format(new Date(timeStamp));
      }
    },
    /**
     * 默认
     */
    DEFAULT {
      @Override
      public String getTimeStr(long timeStamp) {
        return DEFAULT_TIME_FORMAT.format(new Date(timeStamp));
      }
    };

    /**
     * 获得时间字符串<BR>
     *
     * @param timeStamp 时间毫秒值
     * @return 时间字符串
     */
    public abstract String getTimeStr(long timeStamp);

    /**
     * 天的格式化
     */
    private static final SimpleDateFormat TODAY_TIME_FORMAT = new SimpleDateFormat("HH:mm");

    /**
     * 星期的格式化
     */
    private static final SimpleDateFormat WEEK_TIME_FORMAT = new SimpleDateFormat("E HH:mm");

    /**
     * 默认时间的格式化
     */
    private static final SimpleDateFormat DEFAULT_TIME_FORMAT =
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  }
}
