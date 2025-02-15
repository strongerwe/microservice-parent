package cn.stronger.we.commons.desensitization;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author qiang.w
 * @version 1.0.0
 * @annotation 自定义脱敏注解
 * @department Platform Center
 * @date 2024-09-07 15:49
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = DesensitizeSerializer.class)
public @interface Desensitized {

    /**
     * 从哪里开始脱敏
     */
    int prefixLength() default 3;

    /**
     * 脱敏至哪里结束
     */
    int suffixLength() default 4;

    /**
     * 脱敏替换字符
     */
    char symbol() default '*';

    /**
     * 脱敏数据类型
     */
    DesensitizeType type() default DesensitizeType.NONE;
}
