package cn.stronger.we.commons.framework;

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
     * @return {@link RestResult }<{@link IN }>
     */
    RestResult<IN> adapter(IN in, AbstractApiBizEnum bizExecute);

    /**
     * me
     *
     * @return {@link String }
     */
    String me();
}
