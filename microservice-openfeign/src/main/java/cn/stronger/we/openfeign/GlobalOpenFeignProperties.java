package cn.stronger.we.openfeign;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author WQ
 * @version 1.0.0
 * @description GlobalOpenFeign配置
 * @date 2022/11/1 14:34
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@org.springframework.boot.context.properties.ConfigurationProperties("custom.starter.openfeign")
public class GlobalOpenFeignProperties {

    /**
     * openfeign启动配置
     */
    private boolean enabled = true;
}
