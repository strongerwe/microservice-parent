package cn.stronger.we.redis.lock;

import cn.hutool.core.util.StrUtil;
import cn.stronger.we.commons.exception.CustomException;
import cn.stronger.we.commons.framework.ResultErrCodeI;
import cn.stronger.we.commons.utils.ParserTools;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author qiang.w
 * @version 1.0.0
 * @class 分布式锁注解Aspect
 * @department 平台研发部
 * @date 2024-09-21 09:04
 */
@Slf4j
@Aspect
public class CusRedissonLockAspect {

    private final RedissonClient redissonClient;

    public CusRedissonLockAspect(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Around("@annotation(redissonLock)")
    public Object cacheDataAround(ProceedingJoinPoint pjp, CusRedissonLock redissonLock) throws Throwable {
        String lockKey = ParserTools.parse(redissonLock.keyTemplate(), parse(getMethod(pjp), pjp.getArgs(), redissonLock.suffix()));
        RLock lock = redissonClient.getLock(lockKey);
        try {
            boolean tryLock = lock.tryLock(redissonLock.lockTime(), redissonLock.unit());
            if (tryLock) {
                return pjp.proceed();
            }
        } catch (InterruptedException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("[InterruptedException]tryLock系统异常! 请检查调用和系统日志", e);
            throw new CustomException("[InterruptedException]tryLock系统异常，请稍后重试！");
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        throw new CustomException(ResultErrCodeI.TRANS_LOCK_DOWN_CODE, ResultErrCodeI.TRANS_LOCK_DOWN_MSG);
    }

    private final ExpressionParser parser = new SpelExpressionParser();
    private final LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();


    private Object parse(Method method, Object[] arguments, String spl) {
        String[] params = discoverer.getParameterNames(method);
        EvaluationContext context = new StandardEvaluationContext();
        for (int len = 0; len < Objects.requireNonNull(params).length; len++) {
            context.setVariable(params[len], arguments[len]);
        }
        try {
            Expression expression = parser.parseExpression(spl);
            return expression.getValue(context);
        } catch (Exception e) {
            return StrUtil.EMPTY;
        }
    }

    private static Method getMethod(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        if (method.getDeclaringClass().isInterface()) {
            try {
                method = pjp
                        .getTarget()
                        .getClass()
                        .getDeclaredMethod(pjp.getSignature().getName(),
                                method.getParameterTypes());
            } catch (SecurityException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        return method;
    }

}
