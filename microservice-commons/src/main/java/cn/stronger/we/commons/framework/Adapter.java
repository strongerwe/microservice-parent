package cn.stronger.we.commons.framework;

import cn.stronger.we.commons.framework.request.RestRequest;

/**
 * @author qiang.w
 * @version 1.0.0
 * @interface 抽象适配器接口
 * @department Platform Center
 * @date 2024-09-02 21:45
 */
public interface Adapter<IN> {

    /**
     * 入参适配器
     *
     * @param in         in
     * @param bizExecute bizExecute
     * @return {@link RestRequest }<{@link IN }>
     */
    RestRequest<IN> adapter(IN in, BizExecuteEnumI bizExecute);

    /**
     * me
     *
     * @return {@link String }
     */
    String me();
}
