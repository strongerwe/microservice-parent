package cn.stronger.we.commons.framework;

import cn.stronger.we.commons.framework.request.RestRequest;
import cn.stronger.we.commons.framework.request.RestRequestI;
import cn.stronger.we.commons.utils.StringTools;
import org.springframework.stereotype.Component;

/**
 * @author qiang.w
 * @version 1.0.0
 * @class 默认适配器
 * @department Platform Center
 * @date 2024-09-17 22:20
 */
@Component
public class DefaultExeAdapter implements Adapter<RestRequestI> {

    @Override
    public RestRequest<RestRequestI> adapter(RestRequestI requestI, BizExecuteEnumI bizExecute) {
        RestRequest<RestRequestI> request = new RestRequest<>(requestI);
        request.setBizExecute(bizExecute);
        request.setInterfaceRequestId(requestI.getInterfaceRequestId());
        // version为空时走Base
        request.setInterfaceVersion(StringTools.isEmpty(requestI.getInterfaceVersion()) ? "Base" : requestI.getInterfaceVersion());
        request.setInterfaceExecute(requestI.getInterfaceExecute());
        return request;
    }

    @Override
    public String me() {
        return "DefaultExeAdapter";
    }
}
