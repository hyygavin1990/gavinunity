package cn.datawin.util;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Log4j日志处理类，代理所有log4j日志的打印
 * Created by zl on 2016/12/6.
 */
public class LogUtil {



    public static void info(Object msg) {
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        Logger logger = Logger.getLogger(stack[1].getClassName());
        if (logger.isInfoEnabled()) {
            logger.log(stack[1].getClassName(), Level.INFO, msg, null);
        }
    }

    public static void error(Throwable e) {
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        Logger logger = Logger.getLogger(stack[1].getClassName());
        logger.log(stack[1].getClassName(), Level.ERROR, Level.ERROR.toString(), e);
    }

    public static void error(String msg) {
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        Logger logger = Logger.getLogger(stack[1].getClassName());
        logger.log(stack[1].getClassName(), Level.ERROR, msg, null);
    }

    public static void error(String msg, Throwable e) {
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        Logger logger = Logger.getLogger(stack[1].getClassName());
        logger.log(stack[1].getClassName(), Level.ERROR, msg, e);
    }

    public static void debug(Object msg) {
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        Logger logger = Logger.getLogger(stack[1].getClassName());
        if (logger.isDebugEnabled()) {
            logger.log(stack[1].getClassName(), Level.DEBUG, msg, null);
        }
    }

    public static void warn(Object msg) {
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        Logger logger = Logger.getLogger(stack[1].getClassName());
        logger.log(stack[1].getClassName(), Level.WARN, msg, null);
    }


    public static void trace(Object msg) {
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        Logger logger = Logger.getLogger(stack[1].getClassName());
        if (logger.isTraceEnabled()) {
            logger.log(stack[1].getClassName(), Level.TRACE, msg, null);
        }
    }


}
