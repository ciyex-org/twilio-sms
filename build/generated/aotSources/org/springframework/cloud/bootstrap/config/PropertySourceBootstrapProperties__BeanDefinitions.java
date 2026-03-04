package org.springframework.cloud.bootstrap.config;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link PropertySourceBootstrapProperties}.
 */
@Generated
public class PropertySourceBootstrapProperties__BeanDefinitions {
  /**
   * Get the bean definition for 'propertySourceBootstrapProperties'.
   */
  public static BeanDefinition getPropertySourceBootstrapPropertiesBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(PropertySourceBootstrapProperties.class);
    beanDefinition.setInstanceSupplier(PropertySourceBootstrapProperties::new);
    return beanDefinition;
  }
}
