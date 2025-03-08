package cn.stronger.we.commons.utils;

import cn.hutool.json.JSONUtil;
import cn.stronger.we.commons.exception.CustomException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qiang.w
 * @version 1.0.0
 * @class Json工具类
 * @department Platform Center
 * @date 2024-09-07 17:56
 */
public class JsonTools extends JSONUtil {

    private final static ObjectMapper mapper = new ObjectMapper();

    private JsonTools() {
    }

    static {
        SimpleModule module = new SimpleModule();
        mapper.registerModule(module);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }

    /**
     * Object转JSON
     */
    public static String toJson(Object object) {
        try {
            if (object instanceof String) {
                return (String) object;
            }
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new CustomException("Json解析失败", e);
        }
    }

    /**
     * JSON转成Object
     */
    @SuppressWarnings("unchecked")
    public static <T> T getObject(String json, Class<T> clazz) {
        try {
            if (clazz == String.class) {
                return (T) json;
            }
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new CustomException("Json解析失败", e);
        }
    }

    /**
     * JSON转HashMap
     */
    public static Map<String, Object> getHashMap(String json) {
        JavaType mapType = mapper.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class);
        try {
            return mapper.readValue(json, mapType);
        } catch (IOException e) {
            throw new CustomException("Json解析失败", e);
        }
    }

    /**
     * JSON转List
     */
    public static <T> List<T> getList(String json, Class<T> t) {
        JavaType listType = mapper.getTypeFactory().constructCollectionType(List.class, t);
        try {
            return mapper.readValue(json, listType);
        } catch (IOException e) {
            throw new CustomException("Json解析失败", e);
        }
    }

    /**
     * JSON转List
     */
    public static List<?> getList(String json) {
        try {
            return mapper.readValue(json, List.class);
        } catch (IOException e) {
            throw new CustomException("Json解析失败", e);
        }
    }

    /**
     * Map转Object
     */
    public static <T> T mapToObj(Map<String, Object> fromMap, Class<T> toClazz) {
        return mapper.convertValue(fromMap, toClazz);
    }

    /**
     * Object转Map
     */
    public static <T> Map<String, Object> objToMap(T t) {
        JavaType mapType = mapper.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class);
        return mapper.convertValue(t, mapType);
    }

    /**
     * Object转Object
     */
    public static <T> T objToObj(Object obj, Class<T> toClazz) {
        return mapper.convertValue(obj, toClazz);
    }

    /**
     * List里的Map转成Object
     */
    public static <T> List<T> collMapToObj(List<Map> mapList, Class<T> clazz) {
        JavaType listType = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
        return mapper.convertValue(mapList, listType);
    }

    /**
     * List里的Object转成Map
     */
    public static <T> List<Map<String, Object>> collObjToMap(List<T> objList) {
        JavaType mapType = mapper.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class);
        JavaType listType = mapper.getTypeFactory().constructCollectionType(List.class, mapType);
        return mapper.convertValue(objList, listType);
    }

    public static Map<String, Object> data(String[] keyArr, Object[] objs) {
        Map<String, Object> map = new HashMap<>(keyArr.length);
        for (int i = 0; i < keyArr.length; i++) {
            map.put(keyArr[i], objs[i]);
        }
        return map;
    }
}
