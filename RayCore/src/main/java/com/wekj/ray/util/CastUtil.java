package com.wekj.ray.util;

/**
 * @author liuna
 */
public final class CastUtil {

    private CastUtil() {
    }

    public static String castString(Object object) {
        return castString(object, "");
    }

    public static String castString(Object object, String defaultValue) {
        return object != null
                ? String.valueOf(object)
                : defaultValue;
    }

    public static double castDouble(Object object) {
        return castDouble(object, 0);
    }

    public static double castDouble(Object object, double defaultValue) {
        if (object != null) {
            String stringValue = castString(object);
            if (StringUtil.isNotEmpty(stringValue)) {
                try {
                    return Double.parseDouble(stringValue);
                } catch (NumberFormatException e) {
                    return defaultValue;
                }
            }
        }
        return defaultValue;
    }

    public static long castLong(Object object) {
        return castLong(object, 0);
    }

    public static long castLong(Object object, long defaultValue) {
        if (object != null) {
            String stringValue = castString(object);
            if (StringUtil.isNotEmpty(stringValue)) {
                try {
                    return Long.parseLong(stringValue);
                } catch (NumberFormatException e) {
                    return defaultValue;
                }
            }
        }
        return defaultValue;
    }

    public static int castInt(Object object) {
        return castInt(object, 0);
    }

    public static int castInt(Object object, int defaultValue) {
        if (object != null) {
            String stringValue = castString(object);
            if (StringUtil.isNotEmpty(stringValue)) {
                try {
                    return Integer.parseInt(stringValue);
                } catch (NumberFormatException e) {
                    return defaultValue;
                }
            }
        }
        return defaultValue;
    }

    public static boolean castBoolean(Object object) {
        return castBoolean(object, false);
    }

    public static boolean castBoolean(Object object, boolean defaultValue) {
        boolean booleanValue = defaultValue;
        if (object != null) {
            booleanValue = Boolean.parseBoolean(castString(object));
        }
        return booleanValue;
    }
}
