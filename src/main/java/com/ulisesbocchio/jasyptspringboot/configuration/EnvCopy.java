package com.ulisesbocchio.jasyptspringboot.configuration;

import java.util.Optional;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertySource;

/**
 * Need a copy of the environment without the Enhanced property sources to avoid
 * circular dependencies.
 */
public class EnvCopy {
  ConfigurableEnvironment copy;

  ConfigurableEnvironment old;

  @SuppressWarnings({ "rawtypes", "ConstantConditions" })
  public EnvCopy(final ConfigurableEnvironment environment) {
//    copy = new AbstractEnvironment() {
//    };
    copy = new StandardEnvironment();
    old = environment;
    clonePropertySources(environment);
  }

  private void clonePropertySources(final ConfigurableEnvironment environment) {
    MutablePropertySources propertySources = environment.getPropertySources();
//    if (propertySources == null) {
//      if (environment instanceof StandardEncryptableEnvironment) {
//        propertySources = ((StandardEncryptableEnvironment) environment).getOriginalPropertySources();
//      } else if (environment instanceof StandardEncryptableServletEnvironment) {
//        propertySources = ((StandardEncryptableServletEnvironment) environment).getOriginalPropertySources();
//      }
//    }
    MutablePropertySources propertySourcesNew = copy.getPropertySources();
    for (PropertySource<?> source : propertySourcesNew) {
      propertySourcesNew.remove(source.getName());
    }
    Optional.ofNullable(propertySources).ifPresent(sources -> sources.forEach(ps -> {
      final PropertySource<?> original = ps instanceof EncryptablePropertySource
          ? ((EncryptablePropertySource) ps).getDelegate()
          : ps;
      propertySourcesNew.addLast(original);
    }));
  }

  public ConfigurableEnvironment get() {
    return copy;
  }

  public MutablePropertySources getPropertySources(String propertySourceName) {
    final MutablePropertySources propertySources = copy.getPropertySources();
    if (true && propertySourceName != null && !propertySources.contains(propertySourceName)) {
        clonePropertySources(old);
        return copy.getPropertySources();
    }
    return propertySources;
  }
}
