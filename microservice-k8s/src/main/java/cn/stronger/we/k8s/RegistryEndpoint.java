package cn.stronger.we.k8s;


import com.alibaba.cloud.nacos.registry.NacosServiceRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.stereotype.Component;

/**
 * 实现无痕上下线的上线服务的原理：
 * 1.项目启动时，首先启动tomcat容器
 * 2.确保容器启动完成之后，利用k8s的健康检查的"就绪状态检查"操作调用/actuator/registry 接口
 * 3.就绪检查的时候调用"/actuator/registry"时再将服务发布到Nacos注册中心中去
 * 4.这样就能确保注册到注册中心中的服务是就绪状态，可以被立即访问的
 * 注意事项：
 * 1.management.endpoints.web.exposure.include='*' 配置必须配置开放post start
 * 2.k8s中就绪状态检查的"运行多少时间扣开始检测"配置必须比服务的启动时间更长才能确保服务无问题
 *
 * @author WQ
 * @version 1.0.0
 * @description registry
 * 项目启动的时候手动调用post start将服务注册至Nacos中
 * @department Platform R&D
 * @date 2022/11/23 14:48
 */
@Slf4j
@Component
@Endpoint(id = "registry")
@ConditionalOnProperty(prefix = "spring.cloud.nacos", name = "server-addr")
@ConditionalOnClass({NacosServiceRegistry.class, Registration.class})
public class RegistryEndpoint {

    /**
     * 这里使用的是K8S 就绪状态检查回调去注册服务到注册中心的
     * 当服务启动 首次获取就绪状态时 将服务注册到配置中心上
     * 一旦注册成功后就会像 /actuator/health 一样返回成功即可
     */
    private static boolean IS_INIT = false;

    @Value("${spring.application.name}")
    private String application;

    private final NacosServiceRegistry nacosServiceRegistry;
    private final Registration registration;
    private final HealthEndpoint healthEndpoint;

    public RegistryEndpoint(NacosServiceRegistry nacosServiceRegistry, Registration registration, HealthEndpoint healthEndpoint) {
        this.nacosServiceRegistry = nacosServiceRegistry;
        this.registration = registration;
        this.healthEndpoint = healthEndpoint;
    }

    @ReadOperation
    public String registry() {
        /*
         * 返回结果：与"/actuator/health"接口返回成功结果一样
         */
        String suc = "{\"status\":\"UP\",\"groups\":[\"liveness\",\"readiness\"]}";
        if (IS_INIT) {
            return suc;
        }

        HealthComponent health = healthEndpoint.health();
        if(!org.springframework.boot.actuate.health.Status.UP.equals(health.getStatus())){
            return "{\"status\":\"UNKNOWN\",\"groups\":[\"liveness\",\"readiness\"]}";
        }

        log.info("将[{}] 服务注册至注册中心 registry into !", application);
        nacosServiceRegistry.register(registration);
        log.info("将[{}] 服务注册至注册中心 registry success !", application);
        IS_INIT = true;
        return suc;
    }
}
