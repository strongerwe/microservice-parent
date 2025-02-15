package cn.stronger.we.commons.framework;

import cn.stronger.we.commons.exception.CustomException;
import cn.stronger.we.commons.framework.request.RestRequest;
import cn.stronger.we.commons.framework.request.RestRequestI;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author qiang.w
 * @version 1.0.0
 * @class 执行器核心类
 * @department Platform Center
 * @date 2024-09-02 21:28
 */
@Component
public class FrameworkExeCore {

    @Resource
    private ApplicationContext ioc;
    @Resource
    private FrameworkExeAdapters adapters;

    /**
     * invoke执行execute
     *
     * @param request     request
     * @param executeEnum executeEnum
     * @return {@link RestResult }<{@link T }>
     */
    public <T> RestResult<T> execute(RestRequestI request, BizExecuteEnumI executeEnum) {
        RestRequest<RestRequestI> adapter = adapters.adapter(request, executeEnum);
        return this.deal(adapter);
    }

    /**
     * 核心deal
     *
     * @param request ApiRequest
     * @param <T>     Exe对应泛型响应
     * @return RestResult
     */
    public <T> RestResult<T> deal(RestRequest<RestRequestI> request) {
        String apiCmdExe = BizExecuteEnumI.getCmdExe(request.getBizExecute(), request.getInterfaceVersion());
        Exe<RestRequest<RestRequestI>, RestResult<T>> exe = this.get(apiCmdExe);
        request.setInterfaceExecute(apiCmdExe);
        return exe.execute(request);
    }

    /**
     * 获取适配器
     *
     * @param beanName beanName
     * @return Adapter
     */
    private <T> Exe<RestRequest<RestRequestI>, RestResult<T>> get(String beanName) {
        if (ioc.containsBean(beanName)) {
            // 强转方式也可：(Exe<RestRequest<?>, RestResult<T>>) ioc.getBean(beanName);
            return ioc.getBean(beanName, Exe.class);
        }
        throw new CustomException("[404](" + beanName + ")API接口不存在!");
    }

}
