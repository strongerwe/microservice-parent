package cn.stronger.we.commons.framework;

/**
 * @author qiang.w
 * @version 1.0.0
 * @interface 业务配置基础枚举
 * @department Platform Center
 * @date 2024-07-28 00:08
 */
public interface BizExecuteEnumI {

    String EXE = "Exe";
    String ADAPTER = "Adapter";

    String getBiz();

    String getApi();

    String getExe();

    String getApiName();

    /**
     * 获取API BeanName
     *
     * @param api     api
     * @param version version
     * @return {@link String}
     */
    static String getCmdExe(BizExecuteEnumI api, String version) {
        return api.getBiz() + api.getApi() + version + api.getExe() + EXE;
    }

    /**
     * 获取适配器自身标识
     *
     * @return {@link String }
     */
    default String getBizAdapter() {
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
