package cn.stronger.we.commons.framework.request;

import cn.stronger.we.commons.framework.AbstractApiBizEnum;
import cn.stronger.we.commons.framework.response.SuperResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author qiang.w
 * @version 1.0.0
 * @class 业务API接口请求入参基类
 * @department Platform Center
 * @date 2024-07-28 00:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractApiRequest<T extends SuperResponse> extends AbstractRequest<T> {

    /**
     * 业务API枚举
     */
    private AbstractApiBizEnum frameBizEnum;

    /**
     * 机构Code
     */
    private String orgCode;
}
