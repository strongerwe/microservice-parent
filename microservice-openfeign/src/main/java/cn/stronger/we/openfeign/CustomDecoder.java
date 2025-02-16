package cn.stronger.we.openfeign;

import cn.stronger.we.commons.framework.RestResult;
import cn.stronger.we.commons.framework.ResultErrCodeI;
import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Optional;

/**
 * @author WQ
 * @description feign调用异常处理, code != 0
 * @date 2022/11/1 14:17
 */
public class CustomDecoder implements Decoder {

    private final Decoder delegate;

    public CustomDecoder(Decoder delegate) {
        Objects.requireNonNull(delegate, "Decoder must not be null. ");
        this.delegate = delegate;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {
        if (response.status() == HttpStatus.NOT_FOUND.value()
                || response.status() == HttpStatus.NO_CONTENT.value()) {
            return Optional.empty();
        }
        Object target = delegate.decode(response, type);
        if (target instanceof RestResult) {
            RestResult<?> result = (RestResult<?>) target;
            // code不为0，并且intercept为null 或者 true,直接抛出异常
            boolean b = !ResultErrCodeI.SUCCESS_CODE.equals(result.getCode());
            if (b) {
                throw new RuntimeException(result.getMessage());
            }
        }
        return target;
    }
}
