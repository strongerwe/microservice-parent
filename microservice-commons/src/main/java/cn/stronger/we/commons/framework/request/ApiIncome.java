package cn.stronger.we.commons.framework.request;


import cn.stronger.we.commons.utils.DateTimeTools;
import cn.stronger.we.commons.utils.StringTools;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author qiang.w
 * @version release-1.0.0
 * @class ApiIncome.class
 * @department Platform R&D
 * @date 2025/2/22
 * @desc Api请求入参
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ApiIncome<T> implements Serializable {

    /**
     * 具体的业务对象
     */
    private T request;

    /**
     * 请求系统来源
     */
    private String frameRequestSource;

    /**
     * 请求ID（单次请求唯一）
     */
    private String frameRequestId = newRequestId();

    /**
     * 请求执行器
     */
    private String frameExecute;

    private String newRequestId() {
        return "Api-" + DateTimeTools.getCurrentDateTimes() + "-" + StringTools.newUuid().substring(0, 6);
    }
}
