package cn.stronger.we.commons.desensitization;

import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Objects;

/**
 * @author qiang.w
 * @version 1.0.0
 * @class 序列化方式实现数值脱敏
 * @department Platform Center
 * @date 2024-09-07 15:50
 */
@NoArgsConstructor
@AllArgsConstructor
public class DesensitizeSerializer extends JsonSerializer<String> implements ContextualSerializer {

    /**
     * 脱敏类型
     */
    private DesensitizeType desensitizeType;

    /**
     * 前几位不脱敏
     */
    private Integer prefixLength;

    /**
     * 最后几位不脱敏
     */
    private Integer suffixLength;

    /**
     * 用什么打码
     */
    private char symbol;


    @Override
    public void serialize(String origin, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        switch (desensitizeType) {
            case CUSTOM:
                int length = origin.length();
                if (prefixLength >= suffixLength || length < prefixLength) {
                    jsonGenerator.writeString(origin);
                    break;
                }
                jsonGenerator.writeString(StrUtil.replace(origin, prefixLength, length > prefixLength && length <= suffixLength
                        ? length : suffixLength, symbol));
                break;
            case NAME:
                jsonGenerator.writeString((origin.length() <= 2) ? DesensitizedUtil.chineseName(origin)
                        : StrUtil.hide(origin, 2, origin.length()));
                break;
            case ID_CARD:
                jsonGenerator.writeString(DesensitizedUtil.idCardNum(origin, 6, 1));
                break;
            case MOBILE:
                jsonGenerator.writeString(DesensitizedUtil.mobilePhone(origin));
                break;
            case EMAIL:
                jsonGenerator.writeString(StrUtil.hide(origin, 0, origin.indexOf("@")));
                break;
            case NONE:
                jsonGenerator.writeString(origin);
                break;
            default:
                throw new IllegalArgumentException("unknown sensitive type enum " + desensitizeType);
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        if (Objects.isNull(beanProperty)) {
            return serializerProvider.findNullValueSerializer(null);
        }
        if (Objects.equals(beanProperty.getType().getRawClass(), String.class)) {
            Desensitized desensitized = beanProperty.getAnnotation(Desensitized.class);
            if (desensitized == null) {
                desensitized = beanProperty.getContextAnnotation(Desensitized.class);
            }
            if (desensitized != null) {
                return new DesensitizeSerializer(desensitized.type(), desensitized.prefixLength(),
                        desensitized.suffixLength(), desensitized.symbol());
            }
        }
        return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
    }
}
