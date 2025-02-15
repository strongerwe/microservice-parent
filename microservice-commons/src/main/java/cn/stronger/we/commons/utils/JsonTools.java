package cn.stronger.we.commons.utils;

import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;

/**
 * @author qiang.w
 * @version 1.0.0
 * @class Json工具类
 * @department Platform Center
 * @date 2024-09-07 17:56
 */
public class JsonTools extends JSONUtil {

    private final static Gson GSON;

    static {
        GSON = new Gson();
    }

    /**
     * toJsonString(Gson实现)
     *
     * @param obj obj
     * @return {@link String }
     */
    public static String toJson(Object obj) {
        return GSON.toJson(obj);
    }

    /**
     * jsonToObject
     *
     * @param json  json
     * @param clazz clazz
     * @return {@link T }
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        return toBean(json, clazz);
    }

}
