package cn.stronger.we.commons.framework;

/**
 * @author qiang.w
 * @version 1.0.0
 * @interface Exe
 * @department Platform Center
 * @date 2024-07-27 23:10
 */
public interface Exe<IN, OUT> {

    /**
     * execute
     *
     * @param in request
     * @return {@link OUT }
     */
    OUT execute(IN in);

    /**
     * validate
     *
     * @param in in
     */
    void validate(IN in);
}
