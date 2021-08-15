package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.PropertySource;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyFilter;
import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import com.ulisesbocchio.jasyptspringboot.environment.StandardEncryptableEnvironment;

@SpringBootApplication
public class DemoSpringApplication {

    public static void main(String[] args) {
      //   SpringApplication.run(DemoSpringApplication.class, args);
      SpringApplication app = new SpringApplication(DemoSpringApplication.class);
      //CustomJasyptConfig config = new CustomJasyptConfig();
      StandardEncryptableEnvironment environment = new StandardEncryptableEnvironment();
      app.setEnvironment(environment);
      app.run(args);
    }

    // @Configuration
    public static class CustomJasyptConfig {

       // @Bean(name = "encryptablePropertyFilter")
        public EncryptablePropertyFilter getEncryptablePropertyFilter() {
            return new EncryptablePropertyFilter() {
                public boolean shouldInclude(PropertySource<?> source, String name) {
                    return name.endsWith("password") || name.endsWith("pwd");
                }
            };
        }

        //@Bean(name = "encryptablePropertyResolver")
        public EncryptablePropertyResolver getEncryptablePropertyResolver() {
            return new EncryptablePropertyResolver() {

                @Override
                public String resolvePropertyValue(String value) {
                    return value;
                }
            };

        }
    }
}
