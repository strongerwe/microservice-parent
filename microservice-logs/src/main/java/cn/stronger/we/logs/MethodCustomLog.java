package cn.stronger.we.logs;

import java.lang.annotation.*;

/**
 * @author qiang.w
 * @version 1.0.0
 * @annotation 方法出入参日志
 * @department 平台研发部
 * @date 2024-08-21 17:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface MethodCustomLog {

    /**
     * 日志自定义说明
     *
     * @return msg
     */
    String msg() default "";
}
