package cn.stronger.we.commons.framework;

import cn.stronger.we.commons.framework.request.RestRequest;

/**
 * @author qiang.w
 * @version 1.0.0
 * @class 抽象Exe类
 * @department Platform Center
 * @date 2024-09-02 21:42
 */
public abstract class AbstractExe<IN, OUT> implements Exe<RestRequest<IN>, RestResult<OUT>>  {

}
