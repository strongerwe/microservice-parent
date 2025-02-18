package cn.stronger.we.logs.method;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author qiang.w
 * @version 1.0.0
 * @class 自定义日志配置参数类
 * @department Platform Center
 * @date 2024-08-21 17:19
 */
@Getter
@Setter
@ConfigurationProperties("custom.method.custom.log")
public class MethodCustomLogProperties {

    /**
     * 配置启用自定义方法日志打印注解 @MethodCustomLog（默认：false.关闭）
     */
    private boolean enabled = false;

    /**
     * 配置@MethodCustomLog 日志打印级别（默认：info）; 当前仅支持 info, debug; 出现异常时打印日志为error级别
     */
    private String level = "INFO";

    /**
     * 日志最大长度
     */
    private int maxLength = 3096;
}
