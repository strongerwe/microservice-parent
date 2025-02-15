package cn.stronger.we.commons.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * @author qiang.w
 * @version 1.0.0
 * @class 字符串工具类
 * @department Platform Center
 * @date 2024-07-28 00:13
 */
public class StringTools extends StringUtils {

    /**
     * 获取UUID
     *
     * @return {@link String }
     */
    public static String newUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 短码UUID 8位
     *
     * @return s
     */
    public static String newSmallUuid() {
        return newUuid().substring(0, 8);
    }


    /**
     * 严重是否为空
     *
     * @param o o
     * @return boolean
     */
    public static boolean isNotNullOrNotEmpty(StringBuilder o) {
        if (null == o) {
            return false;
        }
        return StringUtils.isNotEmpty(o.toString());
    }

    /**
     * 按最大长度截取字符串
     *
     * @param str       str
     * @param maxLength maxLength
     * @return {@link String }
     */
    public static String subString(String str, Integer maxLength) {
        if (isEmpty(str)) {
            return str;
        }
        if (str.length() < maxLength) {
            return str;
        }
        return str.substring(0, maxLength);
    }
}
