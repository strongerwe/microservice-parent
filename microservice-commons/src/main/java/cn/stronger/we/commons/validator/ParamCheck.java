package cn.stronger.we.commons.validator;

import cn.stronger.we.commons.exception.CustomException;
import cn.stronger.we.commons.framework.ResultErrCodeI;
import cn.stronger.we.commons.utils.StringTools;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * 抛异常工具类 (自由消息格式)
 *
 * @author wuqiang
 * @date 2024/07/28
 */
@NoArgsConstructor
public class ParamCheck {

    /**
     * 验证expression是否为True
     *
     * @param expression expression
     * @param resultCode resultCode
     */
    public static void isTrue(boolean expression, ResultErrCodeI resultCode) {
        if (!expression) {
            throw new CustomException(resultCode);
        }
    }

    /**
     * 验证expression是否为True
     *
     * @param expression expression
     * @param code       code
     * @param message    message
     */
    public static void isTrue(boolean expression, String code, String message) {
        if (!expression) {
            throw new CustomException(code, message);
        }
    }

    /**
     * 验证expression是否非True
     *
     * @param expression expression
     * @param resultCode resultCode
     */
    public static void isFalse(boolean expression, ResultErrCodeI resultCode) {
        if (expression) {
            throw new CustomException(resultCode);
        }
    }

    /**
     * 验证expression是否非True
     *
     * @param expression expression
     * @param code       code
     * @param message    message
     */
    public static void isFalse(boolean expression, String code, String message) {
        if (expression) {
            throw new CustomException(code, message);
        }
    }

    /**
     * 验证object是否为Null
     *
     * @param object     object
     * @param resultCode resultCode
     */
    public static void isNull(Object object, ResultErrCodeI resultCode) {
        if (object != null) {
            throw new CustomException(resultCode);
        }
    }

    /**
     * 验证object是否为Null
     *
     * @param object  object
     * @param code    code
     * @param message message
     */
    public static void isNull(Object object, String code, String message) {
        if (object != null) {
            throw new CustomException(code, message);
        }
    }

    /**
     * 验证object是否非Null
     *
     * @param object     object
     * @param resultCode resultCode
     */
    public static void notNull(Object object, ResultErrCodeI resultCode) {
        if (object == null) {
            throw new CustomException(resultCode);
        }
    }

    /**
     * 验证object是否非Null
     *
     * @param object  object
     * @param code    code
     * @param message message
     */
    public static void notNull(Object object, String code, String message) {
        if (object == null) {
            throw new CustomException(code, message);
        }
    }

    /**
     * 验证str是否Empty
     *
     * @param str        str
     * @param resultCode resultCode
     */
    public static void isEmpty(String str, ResultErrCodeI resultCode) {
        if (StringTools.isNotEmpty(str)) {
            throw new CustomException(resultCode);
        }
    }

    /**
     * 验证str是否Empty
     *
     * @param str     str
     * @param code    code
     * @param message message
     */
    public static void isEmpty(String str, String code, String message) {
        if (StringTools.isNotEmpty(str)) {
            throw new CustomException(code, message);
        }
    }

    /**
     * 验证str是否Empty
     *
     * @param str        str
     * @param resultCode resultCode
     */
    public static void notEmpty(String str, ResultErrCodeI resultCode) {
        if (str == null || str.trim().isEmpty()) {
            throw new CustomException(resultCode);
        }
    }

    /**
     * 验证str是否Empty
     *
     * @param str     str
     * @param code    code
     * @param message message
     */
    public static void notEmpty(String str, String code, String message) {
        if (str == null || str.trim().isEmpty()) {
            throw new CustomException(code, message);
        }
    }

    /**
     * 验证coll不为空
     *
     * @param coll       coll
     * @param resultCode resultCode
     */
    public static void notEmpty(Collection<?> coll, ResultErrCodeI resultCode) {
        if (coll == null || coll.isEmpty()) {
            throw new CustomException(resultCode);
        }
    }

    /**
     * 验证coll不为空
     *
     * @param coll    coll
     * @param code    code
     * @param message message
     */
    public static void notEmpty(Collection<?> coll, String code, String message) {
        if (coll == null || coll.isEmpty()) {
            throw new CustomException(code, message);
        }
    }

    /**
     * 验证集合为空
     *
     * @param coll       coll
     * @param resultCode resultCode
     */
    public static void isEmpty(Collection<?> coll, ResultErrCodeI resultCode) {
        if (coll != null && !coll.isEmpty()) {
            throw new CustomException(resultCode);
        }
    }

    /**
     * 验证集合为空
     *
     * @param coll    coll
     * @param code    code
     * @param message message
     */
    public static void isEmpty(Collection<?> coll, String code, String message) {
        if (coll != null && !coll.isEmpty()) {
            throw new CustomException(code, message);
        }
    }
}
