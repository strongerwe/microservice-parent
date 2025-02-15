package cn.stronger.we.commons.framework;

import cn.stronger.we.commons.exception.CustomException;
import cn.stronger.we.commons.framework.request.RestRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author qiang.w
 * @version 1.0.0
 * @class 框架适配器
 * @department Platform Center
 * @date 2024-09-02 21:34
 */
@Component
public class FrameworkExeAdapters {

    @Resource
    private List<Adapter<?>> adapterList;

    public <IN> RestRequest<IN> adapter(IN income, BizExecuteEnumI bizExecute) {
        Adapter<IN> adapter = this.getAdapter(bizExecute);
        return adapter.adapter(income, bizExecute);
    }

    public <IN> Adapter<IN> getAdapter(BizExecuteEnumI bizExecute) {
        if (null == bizExecute) {
            throw new CustomException("Into Param[bizExecute] cloud not be null");
        }
        for (Adapter<?> adapter : adapterList) {
            if (StringUtils.equals(bizExecute.getBizAdapter(), adapter.me())) {
                return (Adapter<IN>) adapter;
            }
        }
        // 找不到定制适配器，按照是否多版本状态寻找默认适配器
        Optional<Adapter<?>> has =
                adapterList.stream().filter(e -> StringUtils.equals("DefaultExeAdapter", e.me())).findAny();
        if (has.isPresent()) {
            return (Adapter<IN>) has.get();
        }
        throw new CustomException("ApiAdapters not found!");
    }
}
