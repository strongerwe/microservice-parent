package cn.stronger.we.commons.framework;

/**
 * @author qiang.w
 * @version release-1.0.0
 * @enum AbstractApiBizEnum.class
 * @department Platform R&D
 * @date 2025/2/22
 * @desc 抽象业务枚举
 */
public interface AbstractApiBizEnum {

    String DEFAULT_EXE = "Def";
    String EXE = "Exe";
    String ADAPTER = "Adapter";

    String getBiz();

    String getApi();

    String getExe();

    /**
     * 获取适配器自身标识
     *
     * @return {@link String }
     */
    default String getAdapterMe() {
        return this.getBizExe() + ADAPTER;
    }

    /**
     * 获取BizExe
     *
     * @return {@link String }
     */
    default String getBizExe() {
        return this.getBiz() + this.getApi() + this.getExe();
    }
}
