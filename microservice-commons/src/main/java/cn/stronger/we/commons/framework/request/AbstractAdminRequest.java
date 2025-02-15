package cn.stronger.we.commons.framework.request;

import cn.stronger.we.commons.utils.DateTimeTools;
import cn.stronger.we.commons.utils.StringTools;
import lombok.AllArgsConstructor;
import lombok.Data;
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
public abstract class AbstractAdminRequest implements RestRequestI {

    /**
     * 登录操作人ID
     */
    private String loginUserId;

    /**
     * 登录当前域ID（当前机构ID）
     */
    private String loginDomainId;

    /**
     * 请求ID（单次请求唯一）
     */
    private String interfaceRequestId = newRequestId();

    /**
     * 请求执行器
     */
    private String interfaceExecute;

    /**
     * 请求接口版本号(默认Base，即为基础版本)
     */
    private String interfaceVersion = "Base";


    private String newRequestId() {
        return "Adm-" + DateTimeTools.getCurrentDateTimes() + "-" + StringTools.newUuid().substring(0, 6);
    }
}
