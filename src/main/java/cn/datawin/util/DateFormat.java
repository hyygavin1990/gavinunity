package cn.datawin.util;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@SuppressWarnings("unused")
public class DateFormat {
    /**
     * 得到当前日期
     * 标准格式的时间2011-03-11 星期五 03:23:39
     * yyyy-MM-dd EEE hh:mm:ss
     *
     * @param date 日期
     */
    public static String getCurrentTime(Date date) {
        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dFormat.format(date);
    }

    /**
     * 根据所给的格式转换成STR--格式参考API(simpleDateFromat)
     *
     * @param date   输入的日期
     * @param format 需要转化的格式
     * @return 日期
     */
    public static String dateFormat(Date date, String format) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat dFromat = new SimpleDateFormat(format);
        return dFromat.format(date);
    }

    /**
     * 根据所给格式将STR转换成DATE, str为null或""返回null
     *
     * @param str 必须和所给的格式相同   dateFormat("2010-10-10", "yyyy-MM-dd")
     * @throws ParseException
     */
    public static Date dateFormat(String str, String format) throws ParseException {
        if (str != null && !str.equals("")) {
            SimpleDateFormat dFormat = new SimpleDateFormat(format);
            return dFormat.parse(str);
        }
        return null;
    }

    /**
     * 将字符串转换成Date格式
     *
     * @param str 字符串格式为(2011-10-01)
     * @throws ParseException
     */
    public static Date strToDate(String str) throws ParseException {
        return dateFormat(str, "yyyy-MM-dd");
    }

    /**
     * 将日期格式化成中国式时间
     */
    public static String getDate_cn(Date date) {
        return dateFormat(date, "yyyy年MM月dd日  HH:mm:ss");
    }

    /**
     * 日期显示成中文 yyyy年MM月dd日[ HH:mm:ss]
     *
     * @param _sdate 支持 yyyy-MM-dd yyyyMMdd 等加长的格式
     */
    public static String getDate_cn(String _sdate) {
        String s = "";
        if (_sdate == null || _sdate.equals("")) {
            return s;
        }
        _sdate = getDateStr_8(_sdate.trim());

        if (_sdate.length() == 4) {
            s = _sdate.substring(0, 4) + "年";
        } else if (_sdate.length() == 6) {
            s = _sdate.substring(0, 4) + "年" + _sdate.substring(4, 6) + "月";
        } else if (_sdate.length() == 8) {
            s = _sdate.substring(0, 4) + "年" + _sdate.substring(4, 6) + "月" + _sdate.substring(6, 8) + "日";
        } else if (_sdate.length() == 12) {
            s = _sdate.substring(0, 4) + "年" + _sdate.substring(4, 6) + "月" + _sdate.substring(6, 8) + "日 "
                    + _sdate.substring(8, 10) + ":" + _sdate.substring(10, 12);
        } else if (_sdate.length() >= 14) {
            s = _sdate.substring(0, 4) + "年" + _sdate.substring(4, 6) + "月" + _sdate.substring(6, 8) + "日 "
                    + _sdate.substring(8, 10) + ":" + _sdate.substring(10, 12) + ":" + _sdate.substring(12, 14);
        } else {
            s = _sdate;
        }
        return s;
    }


    /**
     * 将日期转换成yyyy-MM-dd的格式
     */
    public static String getDate_10(Date date) {
        return dateFormat(date, "yyyy-MM-dd");
    }

    /**
     * 返回yyyyMMdd的日期
     */
    public static String getDateStr_8(String _date) {
        _date = _date.replaceAll("-", "");
        _date = _date.replaceAll("年", "");
        _date = _date.replaceAll("月", "");
        _date = _date.replaceAll("日", "");
        return _date;
    }

    /**
     * 返回10位日期  yyyy-MM-dd
     */
    public static String getDateStr_10(String _date) {
        if (_date == null || _date.length() < 8) {
            return _date;
        }
        _date = getDateStr_8(_date);
        _date = _date.substring(0, 4) + "-" + _date.substring(4, 6) + "-" + _date.substring(6, 8);
        return _date;
    }

    /**
     * 根据输入日期返回 加/减n后的日期
     *
     * @param _s String 输入的日期 (yyyy-MM-dd或yyyyMMdd)
     * @param _n int 要加、减的天数
     * @return String 结果日期 (yyyy-MM-dd)
     */
    public static String getTheDate(String _s, int _n) {
        Date dt = null;
        if (_s == null || _s.trim().equals("")) {
            dt = new Date();
        } else {
            _s = getDateStr_8(_s);
            try {
                dt = new SimpleDateFormat("yyyyMMdd").parse(_s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Calendar cd = new GregorianCalendar();
        if (dt != null) {
            cd.setTime(dt);
        }
        cd.add(GregorianCalendar.DATE, _n);

        return dateFormat(cd.getTime(), "yyyy-MM-dd");
    }

    /**
     * 根据输入日期返回 加/减n后的日期
     *
     * @param _s      String 输入的日期 (yyyy-MM-dd或yyyyMMdd)
     * @param _n      int 要加、减的天数
     * @param pattern 结果日期 (yyyy-MM-dd)
     * @return String
     */
    public static String getTheDatePa(String _s, int _n, String pattern) {
        Date dt = null;
        if (_s == null || _s.trim().equals("")) {
            dt = new Date();
        } else {
            _s = getDateStr_8(_s);
            try {
                dt = new SimpleDateFormat("yyyyMMdd").parse(_s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Calendar cd = new GregorianCalendar();
        if (dt != null) {
            cd.setTime(dt);
        }
        cd.add(GregorianCalendar.DATE, _n);

        return dateFormat(cd.getTime(), pattern);
    }

    /**
     * 根据输入日期返回 加/减n后的日期
     *
     * @param _s String 输入的日期 (yyyy-MM-dd或yyyyMMdd)
     * @param _n int 要加、减的天数
     * @return String 结果日期 (yyyy-MM-dd)
     */
    public static String getTheDate(String _s, int _n, String pattern) {
        Date dt = null;
        if (_s == null || _s.trim().equals("")) {
            dt = new Date();
        } else {
            _s = getDateStr_8(_s);
            try {
                dt = new SimpleDateFormat(pattern).parse(_s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Calendar cd = new GregorianCalendar();
        if (dt != null) {
            cd.setTime(dt);
        }
        cd.add(GregorianCalendar.DATE, _n);

        return dateFormat(cd.getTime(), pattern);
    }


    /**
     * 取某2天间的天数
     * date:yyyy-MM-dd或yyyyMMdd
     * 2天相等返回1天
     */
    public static int getDiffDays(String _date1, String _date2) {
        try {
            if (!_date1.contains("-")) {
                _date1 = getDateStr_10(_date1);
            }
            if (!_date2.contains("-")) {
                _date2 = getDateStr_10(_date2);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			Date d1 = new Date(); 为防止抛空指针异常
            Date d1 = sdf.parse(_date1);
//			Date d2 = new Date();
            Date d2 = sdf.parse(_date2);
            long daytime = 24 * 60 * 60 * 1000; //一天的毫秒数
            //不知道为什么加1,不加正确,加了多一天
            return (int) Math.abs((d2.getTime() - d1.getTime()) / daytime) + 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 取2个月间的月差(2月相等返回1)
     * month: yyyy-MM
     */
    public static int getDiffMonths(String _month1, String _month2) {
        try {
            if (!_month1.contains("-")) {
                _month1 = getDateStr_10(_month1);
            }
            _month1 = _month1.substring(0, 7);
            if (!_month2.contains("-")) {
                _month2 = getDateStr_10(_month2);
            }
            _month2 = _month2.substring(0, 7);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = sdf.parse(_month1 + "-01");
            Date d2 = sdf.parse(_month2 + "-01");
            int months = 0;
            GregorianCalendar gc1 = new GregorianCalendar(); //前日期
            GregorianCalendar gc2 = new GregorianCalendar(); //后日期
            if (d1.before(d2)) {
                gc1.setTime(d1);
                gc2.setTime(d2);
            } else {
                gc1.setTime(d2);
                gc2.setTime(d1);
            }
            while (gc1.before(gc2)) {
                gc1.add(GregorianCalendar.MONTH, 1);
                months++;
            }

            months++;//加了月差多一天,不加正好

            return months;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 取年初到参数的天数
     * date:yyyy-MM-dd或yyyyMMdd或yyyy-mm或yyyymm
     */
    public static int getYearDays(String _date) {
        int days;
        if (_date.length() >= 8) {    //yyyy-MM-dd或yyyyMMdd
            days = getDiffDays(_date.substring(0, 4) + "-01-01", _date);
        } else {    //yyyy-mm或yyyymm
            days = getDiffDays(_date.substring(0, 4) + "-01-01", _date + getMonthDays(_date));
        }
        return days;
    }

    /**
     * 取某年某月的天数
     * date:yyyy-MM或yyyyMM
     */
    @SuppressWarnings("deprecation")
    public static int getMonthDays(String _date) {
        /*GregorianCalendar(year, month, dayofmonth)
         * 这里的month从0开始计数，所以假如要取4月的天数只要传year和4即可
		 */
        try {
            if (!_date.contains("-")) {
                _date = _date.substring(0, 4) + "-" + _date.substring(4);
            }
            GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(_date.substring(0, 4)), Integer.parseInt(_date.substring(5, 7)), 1);
            gc.add(Calendar.DATE, -1);
            return gc.getTime().getDate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 取当季天数
     *
     * @param _date yyyy-MM-dd或yyyyMMdd或yyyy-mm或yyyymm
     */
    public static int getSeasonDays(String _date) {
        int days;
        _date = getDateStr_8(_date);    //去除-
        String year = _date.substring(0, 4);    //年份
        String month = _date.substring(4, 6);    //月份
        String month_jc;    //月份-季初
        if ("01,02,03".contains(month)) {    //一季度
            month_jc = "01";
        } else if ("04,05,06".contains(month)) {    //二季度
            month_jc = "04";
        } else if ("07,08,09".contains(month)) {    //三季度
            month_jc = "07";
        } else {    //四季度
            month_jc = "10";
        }
        if (_date.length() >= 8) {    //yyyy-MM-dd或yyyyMMdd
            days = getDiffDays(year + month_jc + "01", _date);
        } else {    //yyyy-mm或yyyymm
            days = getDiffDays(year + month_jc + "01", _date + getMonthDays(_date));
        }
        return days;
    }

    /**
     * 取参数月的前一个月
     * month: yyyy-MM或yyyyMM
     */
    public static String getPreMonth(String _month) {
        String preMonth = "";
        if (_month.length() == 6) {
            _month = _month.substring(0, 4) + "-" + _month.substring(4);
        }
        if (_month.length() >= 7) {
            if (_month.substring(5, 7).equals("01")) { //1月
                preMonth = (Integer.parseInt(_month.substring(0, 4)) - 1) + "-12";
            } else {
                int im = Integer.parseInt(_month.substring(5, 7)) - 1;
                if (im < 10) {
                    preMonth = _month.substring(0, 4) + "-0" + im;
                } else {
                    preMonth = _month.substring(0, 4) + "-" + im;
                }
            }
        }

        return preMonth;
    }

    /**
     * 取参数月的下一个月
     * month: yyyy-MM或yyyyMM
     */
    public static String getNextMonth(String _month) {
        String nextMonth = "";
        if (_month.length() == 6) {
            _month = _month.substring(0, 4) + "-" + _month.substring(4);
        }
        if (_month.length() >= 7) {
            if (_month.substring(5, 7).equals("12")) { //12月
                nextMonth = (Integer.parseInt(_month.substring(0, 4)) + 1) + "-01";
            } else {
                int im = Integer.parseInt(_month.substring(5, 7)) + 1;
                if (im < 10) {
                    nextMonth = _month.substring(0, 4) + "-0" + im;
                } else {
                    nextMonth = _month.substring(0, 4) + "-" + im;
                }
            }
        }
        return nextMonth;
    }

    /**
     * 取得参数日期的上季度最后一个月
     *
     * @param _date 格式:yyyy-MM或yyyyMM
     * @return 格式:yyyy-MM
     */
    public static String getPreSeasonLastMonth(String _date) {
        String preSeason;
        int currYear;
        int currMonth;

        _date = getDateStr_8(_date);
        if (_date == null || _date.length() < 6) return "";

        currYear = Integer.parseInt(_date.substring(0, 4));
        currMonth = Integer.parseInt(_date.substring(4, 6));

        switch (currMonth) {
            case 1:
            case 2:
            case 3:
                preSeason = (currYear - 1) + "-12"; //此处修改可以得到此季度,上季度,下季度的任意每个月
                break;
            case 4:
            case 5:
            case 6:
                preSeason = currYear + "-03";
                break;
            case 7:
            case 8:
            case 9:
                preSeason = currYear + "-06";
                break;
            case 10:
            case 11:
            case 12:
                preSeason = currYear + "-09";
                break;
            default:
                preSeason = currYear + "-" + currMonth + "";
        }

        return preSeason;
    }


    /**
     * 判断年月是否正确
     *
     * @param _ny yyyyMM或yyyy-MM
     * @throws Exception
     */
    public static boolean isNY(String _ny) throws Exception {
        _ny = getDateStr_8(_ny);
        if (_ny.length() != 6) {
            return false;
        }
        try {
            int i = Integer.parseInt(_ny);
        } catch (Exception e) {
            return false;
        }
        return !(_ny.substring(4, 6).compareTo("01") < 0 || _ny.substring(4, 6).compareTo("12") > 0);
    }

    public static String encode(String url) {
        try {
            return URLEncoder.encode(url, "utf8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static void main(String[] args) throws ParseException {
        Date date = new Date();
        System.out.println(getCurrentTime(date));
        System.out.println(dateFormat(date, "yyyyMMdd "));
        System.out.println(dateFormat("20101010", "yyyyMMdd"));
        System.out.println(strToDate("2011-10-09"));
        System.out.println(getDate_cn(date));
        System.out.println(getDate_cn("2011-11-11"));
        System.out.println(getDate_10(date));
        System.out.println(getDateStr_10("20111103"));
        System.out.println(getDateStr_8("2011-11-03"));
        System.out.println("-------------");
        System.out.println(getDiffDays("2011-01-01", "2011-01-02"));
        System.out.println(getMonthDays("2011-03"));
        System.out.println(getNextMonth("2010-12"));
        System.out.println(getSeasonDays("2011-01-01"));
        System.out.println(getTheDate("2011-01-01", -30));
        System.out.println(getYearDays("2011-01-30"));
        System.out.println(getDiffMonths("20110505", "20110402"));

        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));


    }
}
