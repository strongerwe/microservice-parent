package cn.stronger.we.encrypt;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author qiang.w
 * @version release-1.0.0
 * @class RegulationBeanFactoryPostProcessor.class
 * @department Platform R&D
 * @date 2025/2/16
 * @desc do what?
 */
public class RegulationBeanFactoryPostProcessor implements BeanFactoryPostProcessor, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(RegulationBeanFactoryPostProcessor.class);

    private ConfigurableEnvironment environment;

    RegulationBeanFactoryPostProcessor(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
        logger.info("Post-processing encrypt regulation instances");
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
