package cn.datawin.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("unused")
public class DateUtil {

    /**
     * 判断是否是今天
     */
    public static boolean isToday(Date day) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(day).equals(sdf.format(new Date()));
    }

    /**
     * 将某一时间调整到凌晨
     */
    public static Date getTheDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 将某一时间调整到凌晨
     *
     * @throws ParseException
     */
    public static Date getTheDay(String daydate) throws ParseException {
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = dFormat.parse(daydate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 今天的最后一秒
     *
     * @throws ParseException
     */
    public static Date getTheEndOfDay(String dayDate) throws ParseException {
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = dFormat.parse(dayDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.add(Calendar.SECOND, -1);
        return cal.getTime();
    }


    /**
     * 这个月的第一天
     */
    public static Date getTheFirstDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public static Date getTheFirstDayOfMonth(String monthDate) throws ParseException {
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMM");
        Date date = dFormat.parse(monthDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }


    /**
     * 这个月的最后一天
     *
     * @throws ParseException
     */
    public static Date getThelastDayOfMonth(String monthDate) throws ParseException {
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMM");
        Date date = dFormat.parse(monthDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.SECOND, -1);
        return cal.getTime();
    }


    /**
     * 这个月的最后一天
     */
    public static Date getThelastDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.SECOND, -1);
        return cal.getTime();
    }

    /**
     * 取得某天+1
     */
    public static Date addOneDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }


    /**
     * 取得某天+days
     */
    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    /**
     * 取得时间+1小时
     */
    public static Date addOneHour(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, 1);
        return cal.getTime();
    }

    /**
     * 取得一定范围内每一天日期集合,包含开始和结束
     *
     * @param begin 开始
     * @param end   结束
     */
    public static List<Date> getDaysBetween(Date begin, Date end) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(begin);
        List<Date> days = new ArrayList<>();
        if (begin.before(end)) {
            days.add(begin);
            while (begin.before(end)) {
                cal.add(Calendar.DATE, 1);
                begin = cal.getTime();
                days.add(begin);
            }
        }
        return days;
    }

    /**
     * 取得一定范围内每一小时时间集合,包含开始和结束
     *
     * @param begin 开始
     * @param end   结束
     */
    public static List<Date> getHourssBetween(Date begin, Date end) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(begin);
        List<Date> hours = new ArrayList<>();
        if (begin.before(end)) {
            hours.add(begin);
            while (begin.before(end)) {
                cal.add(Calendar.HOUR_OF_DAY, 1);
                begin = cal.getTime();
                hours.add(begin);
            }
        }
        return hours;
    }

    //获取某天的开始和结束时间
    public static List<Date> getStarttimeAndEndtime(Date date) {
        List<Date> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date start = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.SECOND, -1);
        Date end = calendar.getTime();
        dates.add(start);
        dates.add(end);
        return dates;
    }

    /**
     * 获取小时数，14:34:23-->14:00:00
     */
    public static Date getTheHour(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 增加数个小时
     */
    public static Date addHour(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, i);
        return cal.getTime();
    }

    /**
     * 增加或减少月
     */
    public static Date addMonth(Date date, int months) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }

    /**
     * 取得时间+1小时
     */
    public static Date addOneHour(Date date, int hour) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, hour);
        return cal.getTime();
    }


    /**
     * 将某一时间调整到凌晨(ISODATE)
     */
    public static Date getTheISODay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.HOUR, -8);
        return cal.getTime();
    }

    /**
     * 给定时间点后转为TimeMillis
     */
    public static long getTimeMillsOfAutoRun(String time) {
        try {
            java.text.DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            java.text.DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
            Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
            return curDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 时间转为TimeMillis
     */
    public static long getTimeMills(String time) {
        try {
            java.text.DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date curDate = dateFormat.parse(time);
            return curDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 时间转为TimeMillis(ISO)
     */
    public static long getISOTimeMills(String time) {
        try {
            java.text.DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            Date curDate = dateFormat.parse(time);
            return curDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 取得某天-1
     */
    public static Date minusOneDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }


    /**
     * 取得某天-1周
     */
    public static Date minusWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        return cal.getTime();
    }

    /**
     * 取得某天-1月
     */
    public static Date minusMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }

    /**
     * 取得某天减去Interval的时间
     */
    public static Date minusInterval(Date date, int interval) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, -interval);
        return cal.getTime();
    }

    /**
     * 取得某时间-xh
     */
    public static Date minusHour(Date date, int x) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, -x);
        return cal.getTime();
    }

    /**
     * 以3个小时区分时段
     */
    public static Date getThreeIntervalBegin(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hourNow = cal.get(Calendar.HOUR_OF_DAY);
        int endHour = hourNow / 3 * 3;
        int beginHour = (endHour == 0) ? 0 : endHour - 3;
        cal.set(Calendar.HOUR_OF_DAY, beginHour);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getThreeIntervalEnd(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hourNow = cal.get(Calendar.HOUR_OF_DAY);
        int endHour = hourNow / 3 * 3;
        //System.out.println(endHour);
        cal.set(Calendar.HOUR_OF_DAY, endHour);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    //设置docBoost延时
    public static long getDelayOfDocBoost() {
        Date now = new Date();
        System.out.println(now);
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        int hourNow = cal.get(Calendar.HOUR_OF_DAY);
        int endHour = hourNow / 3 * 3 + 3;
        cal.set(Calendar.HOUR_OF_DAY, endHour);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        System.out.println(cal.getTime());
        return cal.getTimeInMillis() - now.getTime();
    }

    //设置userBoost延时
    public static long getDelayOfUserBoost() {
        Date now = new Date();
//			System.out.println(now);
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 1);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DAY_OF_YEAR, 1);
//			System.out.println(cal.getTime());
        return cal.getTimeInMillis() - now.getTime();
    }


    /**
     * 取得某天所在的周是这个月的第几周
     */
    public static int getWeekOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_YEAR);

    }

    /**
     * 取得这个月的最后一天的23：59：59
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtil.getTheDay(date));
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.SECOND, -1);
        return cal.getTime();
    }

    /**
     * 取得这个月的第一天
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtil.getTheDay(date));
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * 取得这周的最后一天的23：59：59【从周日--周一-...-周六】
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtil.getTheDay(date));
        cal.set(Calendar.DAY_OF_WEEK, 1);
        cal.add(Calendar.WEEK_OF_YEAR, 1);
        cal.add(Calendar.SECOND, -1);
        return cal.getTime();
    }

    /**
     * 取得这周的第一天的零点
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtil.getTheDay(date));
        cal.set(Calendar.DAY_OF_WEEK, 1);
        return cal.getTime();
    }

    /**
     * 取得日期的周次
     */
    public static String getWeekInYearOfDay(String d) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = sdf.parse(d);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return cn.datawin.util.DateFormat.dateFormat(date, "yyyyww");
    }

    /**
     * 取得日期的月
     */
    public static String getMonthInYearOfDay(String d) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = sdf.parse(d);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return cn.datawin.util.DateFormat.dateFormat(date, "yyyyMM");
    }


    public static Date objectidToDate(String id) {
        String timestamp = id.substring(0, 8);
        return new Date(Long.parseLong(timestamp, 16) * 1000);
    }

    public static String dateToObjectid(Date date) {
        return Long.toHexString(date.getTime() / 1000) + "0000000000000000";
    }

    /**
     * 从今天开始的某个时间的开始
     *
     * @throws ParseException
     */
    public static Date getTheFirstOfDate(int offset) throws ParseException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        date = sdf.parse(sdf.format(date));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DAY_OF_YEAR, offset);
        return calendar.getTime();
    }

    /**
     * 从指定日期开始的某个时间的开始
     */
    public static Date getTheFirstOfDate(Date startdate, int offset) {
        Calendar cal = Calendar.getInstance();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            startdate = sdf.parse(sdf.format(startdate));
            cal.setTime(startdate);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.add(Calendar.DAY_OF_YEAR, offset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cal.getTime();
    }

    /**
     * 获取开始时间的时间阶段,yyMMdd加上0-287   最后三位数字表示以5min位单位分割后的第几个5min<br/>
     * 例如:<br/>150820000表示15年 8月 20号 00:00:00<br/> 150820002表示15年 8月 20号 00:10:00<br/>150820006表示15年 8月 20号 00:30:00
     */
    public static String getDateperiod(Date startTime, int interval) {
        Date begin = new Date(startTime.getTime() - startTime.getTime() % (interval * 60 * 1000L));
        Date stime1 = DateUtil.getTheFirstOfDate(begin, 0);
        String time_start = new SimpleDateFormat("yyMMdd").format(startTime) + "";
        long a = (begin.getTime() - stime1.getTime()) / (5 * 60 * 1000L);
        switch ((a + "").length()) {
            case 1:
                time_start += "00" + a;
                break;
            case 2:
                time_start += "0" + a;
                break;
            default:
                time_start += a;
                break;
        }
        return time_start;
    }

    /**
     * 把时间段转换为Date，period格式：yyMMdd加上0-287
     *
     * @throws ParseException
     */
    public static Date periodToTime(int period) throws ParseException {
        String periodStr = String.valueOf(period);
        String year = periodStr.substring(0, 2);
        String month = periodStr.substring(2, 4);
        String day = periodStr.substring(4, 6);
        String minuteStr = periodStr.substring(6, 9);
        int minute = Integer.parseInt(minuteStr) * 5;
        int hour = minute / 60;
        minute = minute - hour * 60;
        String dateStr = "20" + year + "-" + month + "-" + day + " " + (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute) + ":" + "00";
        return cn.datawin.util.DateFormat.dateFormat(dateStr, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date periodToDate(String period) {
        int pe = Integer.parseInt(period);
        Date date = null;
        try {
            long da = cn.datawin.util.DateFormat.dateFormat(pe / 1000 + "", "yyMMdd").getTime() + (pe % 1000) * 5 * 60 * 1000;
            date = new Date(da);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String decimalFormat(String number, int offset) {
        String result = "";
        String pattern = "0.";
        for (int i = 0; i < offset; i++) {
            pattern += "0";
        }
        try {
            DecimalFormat df = new DecimalFormat(pattern);
            double d = Double.parseDouble(number);
            result = df.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 取得当月天数
     */
    public static int getCurrentMonthLastDay() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        return a.get(Calendar.DATE);
    }

    /**
     * 得到指定月的天数
     */
    public static int getMonthLastDay(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        return a.get(Calendar.DATE);
    }

    public static int daysBetween(Date smdate, Date bdate) throws ParseException
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        smdate=sdf.parse(sdf.format(smdate));
        bdate=sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }
}
