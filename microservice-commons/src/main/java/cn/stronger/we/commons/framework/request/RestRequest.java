package cn.stronger.we.commons.framework.request;

import cn.stronger.we.commons.framework.BizExecuteEnumI;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author qiang.w
 * @version 1.0.0
 * @class 执行器Request
 * @department Platform Center
 * @date 2024-09-01 17:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RestRequest<IN> implements RestRequestI {

    /**
     * 请求入参
     */
    private IN income;

    /**
     * 业务执行器
     */
    private BizExecuteEnumI bizExecute;

    /**
     * 请求ID（单次请求唯一）
     */
    private String interfaceRequestId;

    /**
     * 请求执行器
     */
    private String interfaceExecute;

    /**
     * 请求接口版本号(默认Base，即为基础版本)
     */
    private String interfaceVersion;

    public RestRequest(IN income) {
        this.income = income;
    }
}
