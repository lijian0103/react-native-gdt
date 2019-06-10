package cn.cnlee.commons.gdt.util;


import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Strings {

    private final static Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    /**
     * 检查两个字符串忽略大小写后是否相等
     *
     * @param s1 字符串1
     * @param s2 字符串2
     * @return <pre>
     * Strings.equals(null, null)	==> true
     * Strings.equals(null, "")	==> false
     * Strings.equals("", null)	==> false
     * Strings.equals("", "")	==> true
     * Strings.equals("a", "A")	==> true
     * Strings.equals("a", "a")	==> true
     * </pre>
     */
    public static boolean equalsIgnoreCase(String s1, String s2) {
        return s1 == null ? s2 == null : s1.equalsIgnoreCase(s2);
    }

    /**
     * 检查两个字符串是否相等
     *
     * @param s1 字符串1
     * @param s2 字符串2
     * @return <pre>
     * Strings.equals(null, null)	==> true
     * Strings.equals(null, "")	==> false
     * Strings.equals("", null)	==> false
     * Strings.equals("", "")	==> true
     * Strings.equals("a", "A")	==> false
     * Strings.equals("a", "a")	==> true
     * </pre>
     */
    public static boolean equals(String s1, String s2) {
        return s1 == null ? s2 == null : s1.equals(s2);
    }

    /**
     * 检测字符串是否为手机号码或小灵通号码
     *
     * @param sms
     * @return
     */
    public static boolean isSMS(String sms) {
        if (sms == null || sms.length() != 11) {
            return false;
        }
        return sms.matches("^(1)\\d{10}$");
    }

    /**
     * 判断字符串是否以特殊字符开头
     *
     * @param s 字符串
     * @param c 特殊字符
     * @return 是否以特殊字符开头
     */
    public static boolean startsWithChar(String s, char c) {
        return null != s ? (s.length() == 0 ? false : s.charAt(0) == c) : false;
    }

    /**
     * 判断字符串是否以特殊字符结尾
     *
     * @param s 字符串
     * @param c 特殊字符
     * @return 是否以特殊字符结尾
     */
    public static boolean endsWithChar(String s, char c) {
        return null != s ? (s.length() == 0 ? false : s.charAt(s.length() - 1) == c) : false;
    }

    /**
     * 判断是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        if (isBlank(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher match = pattern.matcher(str);
        if (match.matches() == false) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断字符串是否为空
     *
     * @param cs 字符串
     * @return <pre>
     * Strings.isEmpty(null)	==> true
     * Strings.isEmpty("")	==> true
     * Strings.isEmpty(" ")	==> false
     * </pre>
     */
    public static boolean isEmpty(CharSequence cs) {
        return null == cs || cs.length() == 0;
    }

    /**
     * 判断字符串是否是空白串
     *
     * @param cs 字符串
     * @return <pre>
     * Strings.isBlank(null) ==> true
     * Strings.isBlank("") ==> true
     * Strings.isBlank("    ") ==> true
     * </pre>
     */
    public static boolean isBlank(CharSequence cs) {
        if (null == cs)
            return true;
        int length = cs.length();
        for (int i = 0; i < length; i++) {
            if (!(Character.isWhitespace(cs.charAt(i))))
                return false;
        }
        return true;
    }

    /**
     * 去掉字符串前后空白
     *
     * @param cs 字符串
     * @return 新字符串
     */
    public static String trim(CharSequence cs) {
        if (null == cs)
            return null;
        if (cs instanceof String)
            return ((String) cs).trim();
        int length = cs.length();
        if (length == 0)
            return cs.toString();
        int l = 0;
        int last = length - 1;
        int r = last;
        for (; l < length; l++) {
            if (!Character.isWhitespace(cs.charAt(l)))
                break;
        }
        for (; r > l; r--) {
            if (!Character.isWhitespace(cs.charAt(r)))
                break;
        }
        if (l > r)
            return "";
        else if (l == 0 && r == last)
            return cs.toString();
        return cs.subSequence(l, r + 1).toString();
    }

    /**
     * 对obj进行toString()操作,如果为null返回def中定义的值
     *
     * @param obj
     * @param def 如果obj==null返回的内容
     * @return obj的toString()操作
     */
    public static String sNull(Object obj, String def) {
        return obj != null ? obj.toString() : def;
    }

    /**
     * 对obj进行toString()操作,如果为null返回""
     *
     * @param obj
     * @return obj.toString()
     */
    public static String sNull(Object obj) {
        return sNull(obj, "");
    }

    /**
     * 对obj进行toString()操作,如果为空串返回""
     *
     * @param obj
     * @return obj.toString()
     */
    public static String sBlank(Object obj) {
        return sBlank(obj, "");
    }

    /**
     * 对obj进行toString()操作,如果为空串返回def中定义的值
     *
     * @param obj
     * @param def 如果obj==null返回的内容
     * @return obj的toString()操作
     */
    public static String sBlank(Object obj, String def) {
        if (null == obj)
            return def;
        String s = obj.toString();
        return Strings.isBlank(s) ? def : s;
    }

    /**
     * 截去第一个字符
     * <p>
     * 比如:
     * <ul>
     * <li>removeFirst("12345") => 2345
     * <li>removeFirst("A") => ""
     * </ul>
     *
     * @param str 字符串
     * @return 新字符串
     */
    public static String removeFirst(CharSequence str) {
        if (str == null)
            return null;
        if (str.length() > 1)
            return str.subSequence(1, str.length()).toString();
        return "";
    }

    /**
     * 如果str中第一个字符和 c一致,则删除,否则返回 str
     * <p>
     * 比如:
     * <ul>
     * <li>removeFirst("12345",1) => "2345"
     * <li>removeFirst("ABC",'B') => "ABC"
     * <li>removeFirst("A",'B') => "A"
     * <li>removeFirst("A",'A') => ""
     * </ul>
     *
     * @param str 字符串
     * @param c   第一个个要被截取的字符
     * @return 新字符串
     */
    public static String removeFirst(String str, char c) {
        return (Strings.isEmpty(str) || c != str.charAt(0)) ? str : str.substring(1);
    }

    /***
     * @param array     数组
     * @param separator 分隔符
     *                  (char类型)
     * @return 字符串
     */
    public static String join(Object[] array, char separator) {
        if (array == null) {
            return null;
        }
        int arraySize = array.length;
        int bufSize = arraySize == 0 ? 0 : ((array[0] == null ? 16 : array[0].toString().length()) + 1) * arraySize;
        StringBuffer buf = new StringBuffer(bufSize);

        for (int i = 0; i < arraySize; i++) {
            if (i > 0) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    /***
     * @param array     数组
     * @param separator 分隔符(String类型)
     * @return 字符串
     */
    public static String join(Object[] array, String separator) {
        if (array == null) {
            return null;
        }
        if (separator == null) {
            separator = "";
        }
        int arraySize = array.length;
        int bufSize = arraySize == 0 ? 0 : arraySize * ((array[0] == null ? 16 : array[0].toString().length()) + separator.length());
        StringBuffer buf = new StringBuffer(bufSize);
        for (int i = 0; i < arraySize; i++) {
            if (i > 0) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    /**
     * @param dec 数据库取的dec字段
     * @param str 需要被转换的源数据
     * @return
     */

    public static String pow10(Integer dec, String str) {
        if (str == null) {
            return "0";
        }
        if (dec == null) {
            return str;
        }
        str = str.trim();
        try {
            int n = (int) Math.pow(10, Integer.valueOf(dec).intValue());
            Float result = Float.parseFloat(str) / n;
            int m = 0;
            if (str.indexOf(".") > -1) {
                m = str.length() - str.indexOf(".") - 1;
            }
            return subZeroAndDot(String.format("%." + (dec + m) + "f", result));
        } catch (NumberFormatException e) {
        }
        return str;
    }

    public static String pow10_New(String str) {
        str = str.trim();
        try {
            Float result = Float.parseFloat(str);
            int m = 0;
            if (str.indexOf(".") > -1) {
                m = str.length() - str.indexOf(".") - 1;
            }
            return subZeroAndDot(String.format("%." + m + "f", result));
        } catch (NumberFormatException e) {
        }
        return str;
    }

    public static String pow10_2(Integer dec, String str) {
        if (str == null) {
            return "0";
        }
        if (dec == null) {
            return str;
        }
        str = str.trim();
        try {
            int n = (int) Math.pow(10, Integer.valueOf(dec).intValue());
            Float result = Float.parseFloat(str) / n;
            return split(dec, result);
        } catch (NumberFormatException e) {
        }
        return str;
    }

    public static String pow10_3(Integer dec, String str) {
        if (str == null) {
            return "0";
        }
        if (dec == null) {
            return str;
        }
        str = str.trim();
        try {
            int n = (int) Math.pow(10, Integer.valueOf(dec).intValue());
            Float result = Float.parseFloat(str) / n;
            int m = 0;
            if (str.indexOf(".") > -1) {
                m = str.length() - str.indexOf(".") - 1;
            }
            return subZeroAndDot(String.format("%." + (dec + m) + "f", result));
        } catch (NumberFormatException e) {
        }
        return str;
    }

    public static String split(int d, Float val) {
        if (val != 0) {
            String v = val.toString();
            if (v != null && v.indexOf(".") != -1) {
                if (v.length() - v.indexOf(".") > d) {
                    v = v.substring(0, v.indexOf(".") + d + 1);
                } else if (v.length() - v.indexOf(".") <= 2) {
                    v = v.substring(0, v.indexOf(".") + 2);
                }
                if (v.indexOf(".") > 0) {
                    v = v.replaceAll("0+?$", "");
                    v = v.replaceAll("[.]$", "");
                }
            }
            return v;
        } else {
            return "0";
        }
    }

    public static List<String> getSmsListFromStr(String str) {
        List<String> list = new ArrayList<String>();
        if (isBlank(str)) {
            return list;
        }
        Pattern pattern = Pattern.compile("(?<!\\d)(?:(?:1[358]\\d{9})|(?:861[358]\\d{9}))(?!\\d)");
        Matcher matcher = pattern.matcher(str);

        while (matcher.find()) {
            String number = matcher.group();
            if (number.startsWith("86")) {
                number = "+" + number;
            }
            list.add(number);
        }
        return list;
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     *
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");// 去掉多余的0
            s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
        }
        return s;
    }

    /**
     * 字符串首字母转成大写
     * <p>
     * 比如:
     * <ul>
     * <li>firstToUpperCase("android") => "Android"
     * <li>firstToUpperCase("") => ""
     * <li>firstToUpperCase(Null) => "null"
     * </ul>
     *
     * @param str 字符串
     * @return 新字符串
     */
    public static String firstToUpperCase(String str) {
        return Strings.isEmpty(str) ? str : Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    /**
     * 验证手机号码
     *
     * @param phone
     * @return
     */
    public static boolean validateMoblie(String phone) {
        int len = phone.length();
        boolean rs = false;
        switch (len) {
            case 11:
                if (matchingText("^1[0-9]{10}$", phone)) {
                    rs = true;
                }
                break;
            default:
                rs = false;
                break;
        }
        return rs;
    }

    public static String getSmsStrFromStr(String str) {

        if (str == null) {
            return null;
        }
        Pattern pattern = Pattern.compile("(?<!\\d)(?:(?:1[358]\\d{9})|(?:861[358]\\d{9}))(?!\\d)");
        Matcher matcher = pattern.matcher(str);
        StringBuffer bf = new StringBuffer(64);
        while (matcher.find()) {
            bf.append(matcher.group()).append(",");
        }
        int len = bf.length();
        if (len > 0) {
            bf.deleteCharAt(len - 1);
            return bf.toString();
        } else {
            return null;
        }
    }

    public static String[] getSmsArrayFromStr(String str) {
        //String arrayStr = getSmsStrFromStr(str);
        if (!Strings.isBlank(str)) {
            return str.split("\\^");
        } else {
            return null;
        }
    }

    private static boolean matchingText(String expression, String text) {
        return Pattern.compile(expression).matcher(text).matches();
    }

    /**
     * 查看是否有特殊字符
     *
     * @param str
     * @return
     */
    public static boolean isHasSpecialWords(String str) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 根据一个正则式，将字符串拆分成数组，空元素将被忽略
     *
     * @param s     字符串
     * @param regex 正则式
     * @return 字符串数组
     */
    public static String[] splitIgnoreBlank(String s, String regex) {
        if (null == s)
            return null;
        String[] ss = s.split(regex);
        List<String> list = new LinkedList<String>();
        for (String st : ss) {
            if (isBlank(st))
                continue;
            list.add(trim(st));
        }
        return list.toArray(new String[list.size()]);
    }

    /***
     * 判断对象字段是否有空（仅限各字段同为字符串）
     *
     * @param obj
     * @return
     */
    public static boolean fieldsIsBlank(Object obj) {
        boolean flag = false;
        if (obj == null)
            return !flag;
        for (Method m : obj.getClass().getDeclaredMethods()) {
            String methodName = m.getName();
            System.err.println(methodName + ":");
            if (methodName.startsWith("get")) {
                try {
                    Object value = m.invoke(obj);
                    flag = Strings.isBlank(Strings.sBlank(value));
                    if (flag)
                        break;
                } catch (Exception e) {
                }
            }
        }
        return flag;
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        return emailer.matcher(email).matches();
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 字符串转布尔值
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 对象转浮点数
     *
     * @param b
     * @return 转换异常返回 0
     */
    public static Float toFloat(String b) {
        try {
            return Float.parseFloat(b);
        } catch (Exception e) {
        }
        return 0f;
    }

    /**
     * 对象转浮点数
     *
     * @param b
     * @return 转换异常返回 0
     */
    public static Double toDouble(String b) {
        try {
            return Double.parseDouble(b);
        } catch (Exception e) {
        }
        return 0.0D;
    }

    /**
     * 将一个InputStream流转换成字符串
     *
     * @param is
     * @return
     */
    public static String toConvertString(InputStream is) {
        StringBuffer res = new StringBuffer();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader read = new BufferedReader(isr);
        try {
            String line;
            line = read.readLine();
            while (line != null) {
                res.append(line);
                line = read.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != isr) {
                    isr.close();
                    isr = null;
                }
                if (null != read) {
                    read.close();
                    read = null;
                }
                if (null != is) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
            }
        }
        return res.toString();
    }

    /**
     * 链接截取字段
     *
     * @param is
     * @return
     */
    public static Map<String, String> toConvertMap(String is) {
        Map<String, String> map = new HashMap<>();
        String[] aa = is.split("\\?");
        for (int i = 0; i < aa.length; i++) {
            if (aa.length >= 2) {
                String param = aa[1];
                map = getUrlParams(param);
            }
        }
        return map;
    }

    public static String getKey(String is) {
        String key = "";
        String[] aa = is.split("\\?");
        for (int i = 0; i < aa.length; i++) {
            if (aa.length >= 2) {
                String param = aa[0];
                String[] keyParam = param.split("//");
                if (keyParam.length >= 2) {
                    key = keyParam[1];
                }
            }
        }
        return key;
    }

    /**
     * 将 String 转为 map
     *
     * @param param aa=11&bb=22&cc=33
     * @return
     */
    public static Map<String, String> getUrlParams(String param) {
        Map<String, String> map = new HashMap<String, String>();
        if ("".equals(param) || null == param) {
            return map;
        }
        if (param.contains("&")) {
            String[] params = param.split("&");
            for (int i = 0; i < params.length; i++) {
                String[] p = params[i].split("=");
                if (p.length == 2) {
                    String value = "";
                    try {
                        value = URLDecoder.decode(p[1], "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Log.i(Strings.class.getSimpleName(), "value = " + value);
                    map.put(p[0], value);
                }
            }
        } else {
            String[] p = param.split("=");
            if (p.length == 2) {
                String value = "";
                try {
                    value = URLDecoder.decode(p[1], "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                map.put(p[0], value);
            }
        }

        return map;
    }

}
