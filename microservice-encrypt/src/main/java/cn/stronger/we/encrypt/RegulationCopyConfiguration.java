package cn.stronger.we.encrypt;


import com.ulisesbocchio.jasyptspringboot.EncryptablePropertySource;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;

/**
 * @author qiang.w
 * @version release-1.0.0
 * @class RegulationCopyConfiguration.class
 * @department Platform R&D
 * @date 2025/2/16
 */
@Configuration
public class RegulationCopyConfiguration {

    StandardEnvironment copy = new StandardEnvironment();

    RegulationCopyConfiguration(ConfigurableEnvironment environment) {
        environment.getPropertySources().forEach((ps) -> {
            PropertySource<?> original = ps instanceof EncryptablePropertySource ? ((EncryptablePropertySource<?>) ps).getDelegate() : ps;
            this.copy.getPropertySources().addLast(original);
        });
    }

    public ConfigurableEnvironment get() {
        return this.copy;
    }
}