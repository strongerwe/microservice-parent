package cn.stronger.we.commons.desensitization;

/**
 * @author qiang.w
 * @version 1.0.0
 * @enum 数据脱敏类型注解
 * @department Platform Center
 * @date 2024-09-07 15:49
 */
public enum DesensitizeType {
    /**
     * 自定义
     */
    CUSTOM,
    /**
     * 姓名
     */
    NAME,
    /**
     * 身份证
     */
    ID_CARD,
    /**
     * 手机号码
     */
    MOBILE,
    /**
     * 邮箱
     */
    EMAIL,
    /**
     * 默认不脱敏
     */
    NONE
}
