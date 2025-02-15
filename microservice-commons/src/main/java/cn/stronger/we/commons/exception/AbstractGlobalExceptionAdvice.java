package cn.stronger.we.commons.exception;

import cn.stronger.we.commons.framework.RestResult;
import cn.stronger.we.commons.framework.ResultErrCodeI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.net.SocketTimeoutException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Set;

/**
 * @author qiang.w
 * @version 1.0.0
 * @class 公共异常处理
 * @department Platform Center
 * @date 2024-09-07 15:26
 */
@Slf4j
public abstract class AbstractGlobalExceptionAdvice {

    /**
     * MatchException 处理
     *
     * @param e e
     * @return r
     */
    @ExceptionHandler(value = CustomException.class)
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public RestResult<?> handlerMatchException(CustomException e) {
        return RestResult.exception(e);
    }

    /**
     * 通用异常处理
     *
     * @param request request
     * @param t       t
     * @return r
     */
    @ExceptionHandler(value = Throwable.class)
    public RestResult<?> handlerThrowable(HttpServletRequest request, Throwable t) {
        RestResult<?> response = RestResult.exception(new CustomException("系统异常:" + t.getMessage(), t));
        if (t instanceof HttpRequestMethodNotSupportedException) {
            response.setCode(ResultErrCodeI.HTTP_405_CODE);
            response.setMessage(MessageFormat.format(ResultErrCodeI.HTTP_405_MSG, ((HttpRequestMethodNotSupportedException) t).getMethod()));
        } else {
            log.warn("{} | [{}] |EXCEPTION| :", request.getMethod(), request.getRequestURI(), t);
        }
        return response;
    }

    /**
     * http通讯异常处理
     *
     * @param request request
     * @param ex      ex
     * @return r
     */
    @ExceptionHandler(value = SocketTimeoutException.class)
    public RestResult<?> handlerSocketTimeoutException(
            HttpServletRequest request, SocketTimeoutException ex) {
        RestResult<?> response = RestResult.exception(new CustomException("系统超时异常:" + ex.getMessage(), ex));
        log.warn("{} | [{}] |超时异常| :", request.getMethod(), request.getRequestURI(), ex);
        return response;
    }

    /**
     * 参数异常处理
     *
     * @param ex ex
     * @return r
     */
    @ExceptionHandler(value = ValidationException.class)
    public RestResult<?> handlerValidationException(ValidationException ex) {
        String[] messages = new String[2];
        if (ex instanceof ConstraintViolationException) {
            ConstraintViolationException exs = (ConstraintViolationException) ex;
            Set<ConstraintViolation<?>> violationExceptionSet = exs.getConstraintViolations();
            for (ConstraintViolation<?> c : violationExceptionSet) {
                String message = c.getMessage();
                if (message.length() == message.getBytes().length) {
                    messages[0] = message;
                } else {
                    messages[1] = message;
                }
            }
        }
        return new RestResult<>(messages[0], messages[1]);
    }

    /**
     * 入参异常处理
     *
     * @param ex ex
     * @return r
     */
    @ExceptionHandler(value = BindException.class)
    public RestResult<?> handlerBindException(BindException ex) {
        String[] messages = new String[2];
        List<ObjectError> errorList = ex.getAllErrors();
        return getRestResult(messages, errorList);
    }

    /**
     * 入参异常处理
     *
     * @param ex ex
     * @return r
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public RestResult<?> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String[] messages = new String[2];
        List<ObjectError> errorList = ex.getBindingResult().getAllErrors();
        return getRestResult(messages, errorList);
    }

    /**
     * Exception 处理
     *
     * @param ex ex
     * @return r
     */
    @ExceptionHandler(value = Exception.class)
    public RestResult<?> handleException(Exception ex) {
        return new RestResult<>(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), ex.getMessage());
    }

    /**
     * getRestResult
     *
     * @param messages  messages
     * @param errorList errorList
     * @return {@link RestResult }<{@link ? }>
     */
    private RestResult<?> getRestResult(String[] messages, List<ObjectError> errorList) {
        for (ObjectError c : errorList) {
            String message = c.getDefaultMessage();
            assert message != null;
            if (message.length() == message.getBytes().length) {
                messages[0] = message;
            } else {
                messages[1] = message;
            }
        }
        return new RestResult<>(messages[0], messages[1]);
    }
}
