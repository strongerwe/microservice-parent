package cn.stronger.we.commons.framework.request;

import cn.stronger.we.commons.framework.response.SuperResponse;
import cn.stronger.we.commons.utils.DateTimeTools;
import cn.stronger.we.commons.utils.StringTools;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author qiang.w
 * @version 1.0.0
 * @class 管理后台接口请求入参基类
 * @department Platform Center
 * @date 2024-07-27 23:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractAdminRequest<T extends SuperResponse> extends AbstractRequest<T> {

    /**
     * 登录操作人ID
     */
    private String loginUserId;

    /**
     * 登录当前域ID（当前机构ID）
     */
    private String loginDomainId;

    /**
     * 请求执行器
     */
    private String frameExecute;

    /**
     * 请求Id
     */
    private String frameRequestId = newRequestId();

    private String newRequestId() {
        return "Adm-" + DateTimeTools.getCurrentDateTimes() + "-" + StringTools.newUuid().substring(0, 6);
    }
}
