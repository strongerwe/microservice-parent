package cn.stronger.we.k8s;

import com.alibaba.cloud.nacos.registry.NacosServiceRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.stereotype.Component;

/**
 * 实现无痕上下线的下线服务的原理：
 *      1.k8s中配置 preStop的执行配置，在服务定制的时候执行该脚本，调用pre stop（原理：lifecycle 是启动和停止前执行脚本，postStart是启动前，preStop是停止前)
 *         lifecycle:
 *           preStop:
 *             exec:
 *               command:
 *               - /bin/sh
 *               - -c
 *               - curl http://localhost/actuator/prestop
 *               - sleep 5
 *      2.pre stop中调用nacosServiceRegistry.deregister(registration); 先将服务从注册中心中下线
 *      3.然后设置一定的等待时间preStropSleep（单位ms)这里是因为ribbon会缓存注册中心服务列表，默认缓存时间是30s 这里的时间最好大于ribbon缓存时间 确保不会再有请求进来才返回成功！
 *      4.确保不会再有请求进入该服务中后，利用graceful实现服务优雅下线
 * 注意事项：
 *      1.服务shutdown必须配置为graceful（server.shutdown: graceful）
 *      2.这里的等待时间必须要大于ribbon注册列表的缓存时间，确保openfeign和gateway调用的API不会再打到将要下线的服务后才结束pre stop进程，允许服务shutdown
 * @author WQ
 * @version 1.0.0
 * @description pre stop
 * @department Platform R&D
 * @date 2022/11/23 14:52
 */
@Slf4j
@Component
@Endpoint(id = "prestop")
@ConditionalOnProperty(prefix = "spring.cloud.nacos", name = "server-addr")
@ConditionalOnClass({NacosServiceRegistry.class, Registration.class})
public class PreStopEndpoint {

    @Value("${pro.stop.sleep:35000}")
    private long preStropSleep;

    @Value("${spring.application.name}")
    private String application;

    NacosServiceRegistry nacosServiceRegistry;

    Registration registration;

    public PreStopEndpoint(NacosServiceRegistry nacosServiceRegistry, Registration registration) {
        this.nacosServiceRegistry = nacosServiceRegistry;
        this.registration = registration;
    }

    @ReadOperation
    public String preStop() {
        log.info("将[{}] 服务下线注册中心 pre stop into !", application);
        nacosServiceRegistry.deregister(registration);
        log.info("将[{}] 服务下线注册中心 pre stop success !", application);
        try {
            // 因为ribbon会缓存注册中心服务列表，默认缓存时间是30s 这里的时间最好大于ribbon缓存时间 确保不会再有请求进来才返回成功！
            Thread.sleep(preStropSleep);
        } catch (Exception ex) {
            log.error("error when pre stop sleep", ex);
        }
        log.info("将[{}] 服务下线注册中心 pre stop sleep end !", application);
        return "ok";
    }
}
