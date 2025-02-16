package cn.stronger.we.logs.method;

import cn.stronger.we.commons.exception.CustomException;
import cn.stronger.we.commons.utils.JsonTools;
import cn.stronger.we.commons.utils.StringTools;
import cn.stronger.we.logs.MethodCustomLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author qiang.w
 * @version 1.0.0
 * @class AOP实现方法出入参日志打印
 * @department 平台研发部
 * @date 2024-08-21 17:25
 */
@Slf4j
@Aspect
@Order(-201)
@Configuration(value = "MethodCustomLogAutoConfigurer")
@EnableConfigurationProperties(MethodCustomLogProperties.class)
@ConditionalOnProperty(prefix = "custom.method.custom.log", name = "enabled", havingValue = "true")
public class MethodCustomLogAutoConfigurer {

    private final static String LOG_LEVEL_INFO = "INFO";
    private final static String LOG_LEVEL_DEBUG = "DEBUG";
    private final MethodCustomLogProperties properties;

    public MethodCustomLogAutoConfigurer(MethodCustomLogProperties properties) {
        this.properties = properties;
        log.info(">>>>>>>> 已启用自定义方法日志打印注解 @MethodCustomLog! | 日志打印级别：{}", properties.getLevel());
    }

    @Around("@annotation(methodCustomLog)")
    public Object aroundLog(ProceedingJoinPoint joinPoint, MethodCustomLog methodCustomLog) throws Throwable {
        long beginTime = System.currentTimeMillis();
        try {
            StringBuilder methodMsg = getMethodMsg(joinPoint, methodCustomLog);
            if (StringTools.isNotNullOrNotEmpty(methodMsg)) {
                Object[] args = joinPoint.getArgs();
                StringBuilder paramToString = new StringBuilder();
                if (args != null) {
                    for (Object p : args) {
                        try {
                            String paramJson = "";
                            if (p instanceof HttpServletRequest) {
                                HttpServletRequest request = (HttpServletRequest) p;
                                paramJson = request.getParameterMap() != null && !request.getParameterMap().isEmpty() ? JsonTools.toJson(request.getParameterMap()) : "";
                            } else if (!(p instanceof HttpServletResponse)) {
                                paramJson = JsonTools.toJson(p);
                            }
                            paramToString.append(paramJson);
                            paramToString.append("  ");
                        } catch (Exception e) {
                            paramToString.append(p);
                            paramToString.append("  ");
                        }
                    }
                }
                log("{}|请求入参:{}", methodMsg.toString(), subMaxMessage(paramToString));
                Object result = joinPoint.proceed();
                long endTime = System.currentTimeMillis();
                long time = endTime - beginTime;
                log("{}|响应结果[{}ms]:{}", methodMsg.toString(), time, subMaxMessage(JsonTools.toJson(result)));
                return result;
            }
            return joinPoint.proceed();
        } catch (CustomException e) {
            StringBuilder methodMsg = getMethodMsg(joinPoint, methodCustomLog);
            long time = System.currentTimeMillis() - beginTime;
            log.info("{}|业务异常[{}ms]:{}", methodMsg, time, subMaxMessage(JsonTools.toJson((e).restResult())));
            throw e;
        } catch (Throwable e) {
            StringBuilder methodMsg = getMethodMsg(joinPoint, methodCustomLog);
            long time = System.currentTimeMillis() - beginTime;
            log.warn("{}|未知异常[{}ms]:{} ", methodMsg, time, e.getMessage());
            throw e;
        }
    }

    /**
     * 日志打印
     *
     * @param var1 var1
     * @param var2 var2
     */
    private void log(String var1, Object... var2) {
        if (LOG_LEVEL_INFO.equalsIgnoreCase(properties.getLevel())) {
            log.info(var1, var2);
            return;
        }
        if (LOG_LEVEL_DEBUG.equalsIgnoreCase(properties.getLevel())) {
            log.debug(var1, var2);
            return;
        }
    }

    /**
     * 获取执行方法信息
     *
     * @param joinPoint       joinPoint
     * @param methodCustomLog methodCustomLog
     * @return r
     */
    private StringBuilder getMethodMsg(JoinPoint joinPoint,
                                       MethodCustomLog methodCustomLog) {
        Signature signature = joinPoint.getSignature();
        Method method = ((MethodSignature) signature).getMethod();
        StringBuilder methodMsg = null;
        methodMsg = new StringBuilder();
        methodMsg.append(subClassName(method.getDeclaringClass().getName()));
        methodMsg.append(".");
        methodMsg.append(method.getName());
        methodMsg.append("|");
        methodMsg.append(methodCustomLog.msg());
        return methodMsg;
    }

    /**
     * 获取类名
     *
     * @param classPath 类路径
     * @return {@link String}
     */
    private String subClassName(String classPath) {
        if (StringTools.isEmpty(classPath)) {
            return "";
        }
        return classPath.substring(classPath.lastIndexOf(".") + 1);
    }

    /**
     * 截取最大消息
     *
     * @param message 消息
     * @return {@link String}
     */
    private String subMaxMessage(String message) {
        if (StringTools.isEmpty(message)) {
            return "";
        }
        return (message.length() < properties.getMaxLength()) ?
                message : message.substring(0, properties.getMaxLength());
    }

    /**
     * 截取最大消息
     *
     * @param paramToString 参数字符串
     * @return {@link String}
     */
    private String subMaxMessage(StringBuilder paramToString) {
        if (StringTools.isEmpty(paramToString)) {
            return "";
        }
        String message = paramToString.toString();
        return (message.length() < properties.getMaxLength()) ?
                message : message.substring(0, properties.getMaxLength());
    }

}
