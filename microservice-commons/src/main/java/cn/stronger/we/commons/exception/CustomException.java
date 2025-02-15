package cn.stronger.we.commons.exception;

import cn.stronger.we.commons.framework.RestResult;
import cn.stronger.we.commons.framework.ResultErrCodeI;
import lombok.Getter;

/**
 * @author qiang.w
 * @version 1.0.0
 * @class 自定义异常
 * @department Platform Center
 * @date 2024-07-28 00:21
 */
@Getter
public class CustomException extends RuntimeException {
    private static final long serialVersionUID = -112334205699475132L;
    /**
     * 响应状态码
     */
    private final String code;

    public CustomException(ResultErrCodeI errCode) {
        super(errCode.message());
        this.code = errCode.code();
    }

    public CustomException(String message) {
        super("【MatchException】" + message);
        this.code = ResultErrCodeI.EXCEPTION;
    }

    public CustomException(String code, String message) {
        super(message);
        this.code = code;
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
        this.code = ResultErrCodeI.EXCEPTION;
    }

    public RestResult<?> restResult() {
        return RestResult.exception(this);
    }

    public RestResult<Void> voidRestResult() {
        return RestResult.voidException(this);
    }
}
