package cn.stronger.we.commons.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * @author qiang.w
 * @version 1.0.0
 * @class do what?
 * @department Platform Center
 * @date 2024-08-05 23:02
 */
public class Md5Tools {

    /**
     * MD5加密32位小写
     *
     * @param context context
     * @return s
     */
    public static String encrypt32(String context) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            // update处理
            md.update(context.getBytes());
            // 调用该方法完成计算
            byte[] encryptContext = md.digest();

            int i;
            StringBuilder stringBuilder = new StringBuilder();
            // 做相应的转化（十六进制）
            for (byte b : encryptContext) {
                i = b;
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    stringBuilder.append("0");
                }
                stringBuilder.append(Integer.toHexString(i));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * MD5加密32位大写
     *
     * @param context context
     * @return s
     */
    public static String encrypt32ToUpperCase(String context) {
        return Objects.requireNonNull(encrypt32(context)).toUpperCase();
    }

    /**
     * MD5加密16位小写
     *
     * @param context context
     * @return s
     */
    public static String encrypt16(String context) {
        return Objects.requireNonNull(encrypt32(context)).substring(8, 24);
    }

    /**
     * MD5加密16位大写
     *
     * @param context context
     * @return s
     */
    public static String encrypt16ToUpperCase(String context) {
        return Objects.requireNonNull(encrypt32(context)).toUpperCase();
    }
}
