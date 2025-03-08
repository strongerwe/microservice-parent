package cn.stronger.we.commons.framework;

/**
 * @author qiang.w
 * @version 1.0.0
 * @interface 错误响应码枚举基类
 * @department Platform Center
 * @date 2024-07-27 23:20
 */
public interface ResultErrCodeI {
    String SUCCESS_CODE = "0";
    String SUCCESS_MSG = "success";
    String EXCEPTION = "500";
    String EXCEPTION_MSG = "exception";
    String HTTP_405_CODE = "HTTP-405";
    String HTTP_405_MSG = "当前请求方式{0}不支持!";
    String NOT_EMPTY_CODE = "FRAME-400";
    String NOT_EMPTY_MSG = "请求入参[{0}]不能为空";
    /**
     * 分页最大单页数量
     */
    Integer MAX_PAGE_SIZE = 100;
    String PAGE_UP_MAX_SIZE_CODE = "FRAME-400";
    String PAGE_UP_MAX_SIZE_MSG = "分页查询的分页Size超过最大值[300]！";

    String TRANS_LOCK_DOWN_CODE = "FRAME-403";
    String TRANS_LOCK_DOWN_MSG = "当前提交正在处理中, 请勿频繁操作!";
    /**
     * 响应Code
     *
     * @return {@link String }
     */
    String code();

    /**
     * 响应Message
     *
     * @return {@link String }
     */
    String message();
}
