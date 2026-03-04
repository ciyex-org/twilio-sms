package org.springframework.cloud.bootstrap.config;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.InstanceSupplier;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link PropertySourceBootstrapConfiguration}.
 */
@Generated
public class PropertySourceBootstrapConfiguration__BeanDefinitions {
  /**
   * Get the bean definition for 'propertySourceBootstrapConfiguration'.
   */
  public static BeanDefinition getPropertySourceBootstrapConfigurationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(PropertySourceBootstrapConfiguration.class);
    InstanceSupplier<PropertySourceBootstrapConfiguration> instanceSupplier = InstanceSupplier.using(PropertySourceBootstrapConfiguration::new);
    instanceSupplier = instanceSupplier.andThen(PropertySourceBootstrapConfiguration__Autowiring::apply);
    beanDefinition.setInstanceSupplier(instanceSupplier);
    return beanDefinition;
  }
}
