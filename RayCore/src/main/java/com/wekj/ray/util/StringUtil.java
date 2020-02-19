package com.wekj.ray.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author keshawn
 * @date 2017/11/8
 */
public final class StringUtil {

    private static final String UNDER_LINE = "_";

    private static final String URL_PREFIX = "/";

    private static final String BLANK = "";

    private static final String SET = "set";

    private static final String GET = "get";

    private static final int TWO = 2;

    private static final String ZERO = "0";

    private static final int RADIX = 16;

    private static final int UNDERLINE = 95;

    private static final String LEFT_SQUARE_BRACKET = "[";

    private static final String LEFT_BRACE = "{";

    private static final String SLASH = "\"";

    private static final int A = 65;

    private static final int Z = 91;

    private static final Pattern TIME_PATTERN = Pattern.compile("^(((20[0-9][0-9]-(0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|(20[0-3][0-9]-(0[2469]|11)-(0[1-9]|[12][0-9]|30))) (20|21|22|23|[0-1][0-9]):[0-5][0-9]:[0-5][0-9])$");

    private StringUtil() {
    }

    public static boolean isEmpty(String string) {
        return string == null || empty(string.trim());
    }

    private static boolean empty(final String cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

    public static String toHex(byte[] hash) {
        StringBuilder buf = new StringBuilder(hash.length * TWO);
        int i;
        for (i = 0; i < hash.length; i++) {
            if (((int) hash[i] & 0xff) < 0x10) {
                buf.append(ZERO);
            }
            buf.append(Long.toString((int) hash[i] & 0xff, RADIX));
        }
        return buf.toString();
    }

    public static boolean checkStringIsAllDigital(String str) {
        if (isEmpty(str)) {
            return false;
        }
        final int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSame(Object first, Object second) {
        if (first == null && second == null) {
            return true;
        }
        if (first != null && second == null) {
            return false;
        }
        if (first == null && second != null) {
            return false;
        } else {
            return first.equals(second);
        }
    }

    public static String firstToLowerCase(String string) {
        if (isEmpty(string)) {
            return BLANK;
        }
        return (new StringBuilder()).append(Character.toLowerCase(string.charAt(0))).append(string.substring(1)).toString();
    }

    public static String castJsonString(Object object) {
        if (object != null) {
            return castJsonString(object.toString());
        } else {
            return BLANK;
        }
    }

    public static String castJsonString(String string) {
        if (isEmpty(string)) {
            return BLANK;
        }
        if (string.startsWith(LEFT_BRACE) || string.startsWith(LEFT_SQUARE_BRACKET)) {
            return string;
        }
        return SLASH + string + SLASH;
    }

    public static boolean isJson(String content) {
        if (isEmpty(content)) {
            return false;
        }
        if (content.startsWith("{") && content.endsWith("}")) {
            return true;
        }
        return content.startsWith("[") && content.endsWith("]");
    }

    public static String getSetMethodName(String fieldName) {
        String fieldFirst = fieldName.substring(0, 1).toUpperCase();
        return SET + fieldFirst + fieldName.substring(1, fieldName.length());
    }

    public static String getGetMethodName(String fieldName) {
        String fieldFirst = fieldName.substring(0, 1).toUpperCase();
        return GET + fieldFirst + fieldName.substring(1, fieldName.length());
    }

    public static String checkUrlPrefix(String requestMapping) {
        String classRequestMapping;
        if (requestMapping.startsWith(URL_PREFIX)) {
            classRequestMapping = requestMapping;
        } else {
            classRequestMapping = URL_PREFIX + requestMapping;
        }
        return classRequestMapping;
    }

    public static String removeLast(String string) {
        if (isEmpty(string)) {
            return BLANK;
        }
        return string.substring(0, string.length() - 1);
    }

    public static boolean checkTimeValid(String patternString) {
        Matcher matcher = TIME_PATTERN.matcher(patternString);
        return matcher.matches();
    }

    public static String camelToUnderlineLowerCase(String camel) {
        return camelToUnderline(camel).toString().toLowerCase();
    }

    public static String camelToUnderlineUpperCase(String camel) {
        return camelToUnderline(camel).toString().toUpperCase();
    }

    private static StringBuilder camelToUnderline(String camel) {
        char[] chars = camel.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            char s = chars[i];
            if (s >= A && s <= Z) {
                // _ = 95
                char underline = UNDERLINE;
                stringBuilder.append(underline).append(s);
                continue;
            }
            stringBuilder.append(s);
        }
        return stringBuilder;
    }

    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
     * 例如：HELLO_WORLD->HelloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String underLineToCamel(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (isEmpty(name)) {
            return BLANK;
        } else if (!name.contains(UNDER_LINE)) {
            // 不含下划线，仅将首字母小写
            return name.substring(0, 1).toLowerCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String[] camels = name.split(UNDER_LINE);
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 处理真正的驼峰片段
            if (result.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel.toLowerCase());
            } else {
                // 其他的驼峰片段，首字母大写
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 以某个特定字符填充字符串到指定长度
     *
     * @param s           要进行填充的字符串
     * @param fillchar    填充字符
     * @param len         要填充到的长度
     * @param isRightFill 填充方向: <code>true</code>向右填充 ;<code>false</code> 向左填充.
     * @return 产生的字符串
     */
    public static String fillString(String s, char fillchar, int len, boolean isRightFill) {
        if (s == null || s.length() >= len) {
            return s;
        }

        int la = len - s.length();
        StringBuilder stringBuilder = new StringBuilder(len);
        if (isRightFill) {
            stringBuilder.append(s);
        }
        for (int i = 0; i < la; i++) {
            stringBuilder.append(fillchar);
        }
        if (!isRightFill) {
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }

    /**
     * 切割字符串到指定长度
     *
     * @param s          要进行切割的字符串
     * @param len        要切割到的长度
     * @param isRightCut 切割方向, <code>true</code>向右切割 ;<code>false</code> 向左切割.
     * @return String 产生的字符串
     */
    public static String cutString(String s, int len, boolean isRightCut) {
        if (s == null || s.length() <= len || len <= 0) {
            return s;
        }
        if (isRightCut) {
            return s.substring(0, len);
        } else {
            return s.substring(s.length() - len);
        }
    }

    /**
     * 填充或切割字符串到指定长度
     *
     * @param s        要进行填充/切割的字符串
     * @param fillchar 填充字符
     * @param len      指定字符串长度
     * @param isRight  填充或切割方向, <code>true</code>向右填充/切割 ;<code>false</code>
     *                 向左填充/切割.
     * @return String 产生的字符串
     * @see #cutString(String, int, boolean)
     * @see #fillString(String, char, int, boolean)
     */
    public static String fillOrCutString(String s, char fillchar, int len, boolean isRight) {
        return fillString(cutString(s, len, isRight), fillchar, len, isRight);
    }

}

