package yss.com.myrongappication.util;

/**
 * 用于String的工具
 * Created by hliman on 2016/3/25 0025.
 */
public class StringUtil {
    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || "".equals(charSequence);
    }


    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isNotEmpty(CharSequence charSequence) {
        return !isEmpty(charSequence);
    }

    /**
     * 连接多个字符串
     *
     * @param strs
     * @return
     */
    public static String concatString(String... strs) {
        if (strs.length == 1)
            return strs[0];
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < strs.length; i++) {
            if (isNotEmpty(strs[i]))
                stringBuffer.append(strs[i]);
        }
        return stringBuffer.toString();
    }

    /**
     * 获取最后一个非数字的字符，如果没有就返回null
     *
     * @param str
     * @return
     */
    public static String getLastNotNumber(String str) {
        if (isEmpty(str))
            return null;
        char[] strChar = str.toCharArray();
        for (int i = strChar.length - 1; i >= 0; i--) {
            if (!Character.isDigit(strChar[i]))
                if (strChar[i] != '_')
                    return String.valueOf(strChar[i]);
        }
        return null;
    }
}

