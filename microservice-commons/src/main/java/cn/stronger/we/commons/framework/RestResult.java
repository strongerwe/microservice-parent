package cn.stronger.we.commons.framework;

import cn.stronger.we.commons.exception.CustomException;
import cn.stronger.we.commons.framework.request.RestRequestI;
import cn.stronger.we.commons.framework.response.RestResponseDataI;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author qiang.w
 * @version 1.0.0
 * @class 标准响应结果
 * @department Platform Center
 * @date 2024-07-27 23:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RestResult<DATA> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 响应代号
     */
    private String code;
    /**
     * 响应信息
     */
    private String message;
    /**
     * 响应数据
     */
    private DATA data;
    /**
     * 业务执行器
     */
    private String execute;
    /**
     * 请求ID
     */
    private String requestId;

    /**
     * 无DATA响应静态成功方法
     *
     * @return {@link RestResult }<{@link Void }>
     */
    public static RestResult<Void> voidSuccess() {
        return new RestResult<>(ResultErrCodeI.SUCCESS_CODE, ResultErrCodeI.SUCCESS_MSG);
    }

    /**
     * 无DATA响应静态成功方法(Exe模式)
     *
     * @param request request
     * @return {@link RestResult }<{@link Void }>
     */
    public static RestResult<Void> voidSuccess(RestRequestI request) {
        return voidSuccess().setExecute(request.getInterfaceExecute()).setRequestId(request.getInterfaceRequestId());
    }

    /**
     * 响应成功
     *
     * @return {@link RestResult }<{@link T }>
     */
    public static <T> RestResult<T> success() {
        return new RestResult<>(ResultErrCodeI.SUCCESS_CODE, ResultErrCodeI.SUCCESS_MSG, null);
    }

    /**
     * 有响应Data静态成功方法
     *
     * @param data data
     * @return {@link RestResult }<{@link ? }>
     */
    public static RestResult<RestResponseDataI> success(RestResponseDataI data) {
        return new RestResult<>(ResultErrCodeI.SUCCESS_CODE, ResultErrCodeI.SUCCESS_MSG, data);
    }


    /**
     * 有响应Data静态成功方法(Exe模式)
     *
     * @param data data
     * @return {@link RestResult }<{@link ? }>
     */
    public static RestResult<?> success(RestResponseDataI data, RestRequestI request) {
        return success(data).setRequestId(request.getInterfaceRequestId()).setExecute(request.getInterfaceExecute());
    }

    /**
     * 有响应Data静态成功方法
     *
     * @param data data
     * @return {@link RestResult }<{@link DATA }>
     */
    public static <DATA> RestResult<DATA> success(DATA data) {
        return new RestResult<>(ResultErrCodeI.SUCCESS_CODE, ResultErrCodeI.SUCCESS_MSG, data);
    }

    /**
     * 有响应Data静态成功方法
     *
     * @param data    data
     * @param request request
     * @return {@link RestResult }<{@link DATA }>
     */
    public static <DATA> RestResult<DATA> success(DATA data, RestRequestI request) {
        return new RestResult<>(ResultErrCodeI.SUCCESS_CODE, ResultErrCodeI.SUCCESS_MSG, data)
                .setRequestId(request.getInterfaceRequestId()).setExecute(request.getInterfaceExecute());
    }

    /**
     * Void响应静态失败方法
     *
     * @param resultErrCode resultErrCode
     * @return {@link RestResult }<{@link Void }>
     */
    public static RestResult<Void> voidFail(ResultErrCodeI resultErrCode) {
        return new RestResult<>(resultErrCode);
    }

    /**
     * Void响应静态失败方法(Exe模式)
     *
     * @param resultErrCode resultErrCode
     * @param request       request
     * @return {@link RestResult }<{@link Void }>
     */
    public static RestResult<Void> voidFail(ResultErrCodeI resultErrCode, RestRequestI request) {
        return voidFail(resultErrCode).setExecute(request.getInterfaceExecute()).setRequestId(request.getInterfaceRequestId());
    }

    /**
     * Spark异常响应(Void)
     *
     * @param exception exception
     * @return {@link RestResult }<{@link Void }>
     */
    public static RestResult<Void> voidException(CustomException exception) {
        return new RestResult<>(exception.getCode(), exception.getMessage());
    }

    /**
     * Spark异常响应
     *
     * @param exception exception
     * @return {@link RestResult }<{@link Void }>
     */
    public static RestResult<Void> exception(CustomException exception) {
        return new RestResult<>(exception.getCode(), exception.getMessage());
    }

    /**
     * 有DATA响应静态失败方法
     *
     * @param resultErrCode resultErrCode
     * @return {@link RestResult }<{@link ? }>
     */
    public static RestResult<?> fail(ResultErrCodeI resultErrCode) {
        return new RestResult<>(resultErrCode);
    }

    /**
     * 有DATA响应静态失败方法(Exe模式)
     *
     * @param resultErrCode resultErrCode
     * @return {@link RestResult }<{@link ? }>
     */
    public static RestResult<?> fail(ResultErrCodeI resultErrCode, RestRequestI request) {
        return fail(resultErrCode).setRequestId(request.getInterfaceRequestId()).setExecute(request.getInterfaceExecute());
    }

    /**
     * 校验响应结果(同时校验响应DATA是否有值)
     *
     * @param restResult restResult
     * @return boolean
     */
    public static boolean validateResult(RestResult<?> restResult) {
        return validateResultCode(restResult) && Objects.nonNull(restResult.getData());
    }

    /**
     * 校验响应结果(仅仅校验响应Code)
     *
     * @param restResult restResult
     * @return boolean
     */
    public static boolean validateResultCode(RestResult<?> restResult) {
        return Objects.nonNull(restResult) && ResultErrCodeI.SUCCESS_CODE.equals(restResult.getCode());
    }

    /* 构造方法 resultCode */
    public RestResult(ResultErrCodeI resultErrCode) {
        this.code = resultErrCode.code();
        this.message = resultErrCode.message();
    }

    public RestResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public RestResult(String code, String message, DATA data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
