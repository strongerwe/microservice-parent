package cn.stronger.we.openfeign;

import cn.stronger.we.commons.exception.CustomException;
import feign.FeignException;
import feign.Request;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author WQ
 * @version 1.0.0
 * @description FeignException 处理
 * @date 2022/11/1 14:22
 */
@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        FeignException feignException = FeignException.errorStatus(methodKey, response);
        Request request = response.request();
        String requestUrl = request != null ? String.format("%s %s", request.httpMethod().name(), request.url()) : "";
        String message = String.format("%s %s", feignException.getMessage(), requestUrl);
        log.error("# framework # OpenFeign调用出现异常 [feign.FeignException] : {}", message);
        return new CustomException(String.valueOf(response.status()), message);
    }

}
