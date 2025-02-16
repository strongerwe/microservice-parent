package cn.stronger.we.openfeign;

import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;

/**
 * @author WQ
 * @version 1.0.0
 * @description OpenFeign调用全局异常处理
 * @date 2022/11/1 14:23
 */
@Order(-200)
@Configuration(value = "globalOpenFeignAutoConfiguration")
@EnableConfigurationProperties(GlobalOpenFeignProperties.class)
@ConditionalOnProperty(value = "custom.starter.openfeign.enabled", havingValue = "true")
public class GlobalOpenFeignAutoConfiguration {

    @Resource
    private ObjectFactory<HttpMessageConverters> messageConverters;

    @Bean
    public Decoder feignDecoder() {
        return new CustomDecoder(new ResponseEntityDecoder(new SpringDecoder(this.messageConverters)));
    }

    @Bean(name = "OpenFeignLocalDateTimeSerializerConfig")
    public LocalDateTimeSerializerConfig localDateTimeSerializerConfig() {
        return new LocalDateTimeSerializerConfig();
    }

    @Bean
    public ErrorDecoder feignErrorDecoder() {
        return new CustomErrorDecoder();
    }
}
