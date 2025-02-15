package cn.stronger.we.commons.framework.request;

import java.io.Serializable;

/**
 * @author qiang.w
 * @version 1.0.0
 * @interface 请求入参超级类
 * @department Platform Center
 * @date 2024-07-27 23:13
 */
public interface RestRequestI extends Serializable {

    /**
     * 执行器
     */
    String getInterfaceExecute();
    /**
     * API版本
     */
    String getInterfaceVersion();
    /**
     * 请求Id
     */
    String getInterfaceRequestId();

}
