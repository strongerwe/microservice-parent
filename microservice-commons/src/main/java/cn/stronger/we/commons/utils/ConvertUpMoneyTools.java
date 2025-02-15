package cn.stronger.we.commons.utils;

import cn.stronger.we.commons.exception.CustomException;

/**
 * @author qiang.w
 * @version 1.0.0
 * @class 金额转中文大写
 * @department Platform Center
 * @date 2024-08-05 23:04
 */
public class ConvertUpMoneyTools {

    /**
     * 大写数字
     */
    static final String[] NUMBERS = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    /**
     * 整数部分的单位
     */
    static final String[] UNIT = {"元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟"};
    /**
     * 小数部分的单位
     */
    static final String[] DUNIT = {"角", "分", "厘"};
    /**
     * 金额正则
     */
    static final String MATCHES_PATTERN = "(-)?[\\d]*(.)?[\\d]*";

    /**
     * 0元金额
     */
    static final String ZERO = "0";
    static final String ZERO_POINT1 = "0.0";
    static final String ZERO_POINT2 = "0.00";
    /**
     * 负号"-"
     */
    static final String FU = "-";

    /**
     * 小数点
     */
    static final String DIAN = ".";

    /**
     * 转换为大写的中文金额
     *
     * @param str 字符串类型的 金额数字
     * @return s
     */
    public static String toChinese(String str) {
        // 判断输入的金额字符串是否符合要求
        if (StringTools.isBlank(str) || !str.matches(MATCHES_PATTERN)) {
            throw new NumberFormatException("金额格式不正确");
        }
        if (ZERO.equals(str) || ZERO_POINT1.equals(str) || ZERO_POINT2.equals(str)) {
            return "零元";
        }
        // 判断金额数字中是否存在负号"-"
        boolean flag = false;
        if (str.startsWith(FU)) {
            // 标志位，标志此金额数字为负数
            flag = true;
            str = str.replaceAll(FU, "");
        }
        // 去掉金额数字中的逗号","
        str = str.replaceAll(",", "");
        String integerStr;//整数部分数字
        String decimalStr;//小数部分数字
        // 初始化：分离整数部分和小数部分
        if (str.indexOf(DIAN) > 0) {
            integerStr = str.substring(0, str.indexOf(DIAN));
            decimalStr = str.substring(str.indexOf(DIAN) + 1);
        } else if (str.indexOf(DIAN) == 0) {
            integerStr = "";
            decimalStr = str.substring(1);
        } else {
            integerStr = str;
            decimalStr = "";
        }
        // beyond超出计算能力，直接返回
        if (integerStr.length() > UNIT.length) {
            throw new CustomException("金额超出计算能力");
        }
        // 整数部分数字
        int[] integers = toIntArray(integerStr);
        // 判断整数部分是否存在输入012的情况
        if (integers.length > 1 && integers[0] == 0) {
            throw new NumberFormatException("金额格式不正确");
        }
        // 设置万单位
        boolean isWan = isWan5(integerStr);
        // 小数部分数字
        int[] decimals = toIntArray(decimalStr);
        // 返回最终的大写金额
        String result = getChineseInteger(integers, isWan) + getChineseDecimal(decimals);
        if (flag) {
            // 如果是负数，加上"负"
            return "负" + result;
        } else {
            return result;
        }
    }

    /**
     * 将字符串转为int数组
     *
     * @param number 数字
     * @return s
     */
    private static int[] toIntArray(String number) {
        int[] array = new int[number.length()];
        for (int i = 0; i < number.length(); i++) {
            array[i] = Integer.parseInt(number.substring(i, i + 1));
        }
        return array;
    }

    /**
     * 将整数部分转为大写的金额
     *
     * @param integers 整数部分数字
     * @param isWan    整数部分是否已经是达到【万】
     * @return s
     */
    public static String getChineseInteger(int[] integers, boolean isWan) {
        StringBuilder stringBuilder = new StringBuilder();
        int length = integers.length;
        if (length == 1 && integers[0] == 0) {
            return "";
        }
        for (int i = 0; i < length; i++) {
            String key = "";
            if (integers[i] == 0) {
                if ((length - i) == 13) {
                    //万（亿）
                    key = UNIT[4];
                } else if ((length - i) == 9) {
                    //亿
                    key = UNIT[8];
                } else if ((length - i) == 5 && isWan) {
                    //万
                    key = UNIT[4];
                } else if ((length - i) == 1) {
                    //元
                    key = UNIT[0];
                }
                if ((length - i) > 1 && integers[i + 1] != 0) {
                    key += NUMBERS[0];
                }
            }
            stringBuilder.append(integers[i] == 0 ? key : (NUMBERS[integers[i]] + UNIT[length - i - 1]));
        }
        return stringBuilder.toString();
    }

    /**
     * 将小数部分转为大写的金额
     *
     * @param decimals 小数部分的数字
     * @return s
     */
    private static String getChineseDecimal(int[] decimals) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < decimals.length; i++) {
            if (i == 3) {
                break;
            }
            stringBuilder.append(decimals[i] == 0 ? "" : (NUMBERS[decimals[i]] + DUNIT[i]));
        }
        return stringBuilder.toString();
    }

    /**
     * 判断当前整数部分是否已经是达到【万】
     *
     * @param integerStr 整数部分数字
     * @return s
     */
    private static boolean isWan5(String integerStr) {
        int length = integerStr.length();
        if (length > 4) {
            String subInteger = "";
            if (length > 8) {
                subInteger = integerStr.substring(length - 8, length - 4);
            } else {
                subInteger = integerStr.substring(0, length - 4);
            }
            return Integer.parseInt(subInteger) > 0;
        } else {
            return false;
        }
    }
}
