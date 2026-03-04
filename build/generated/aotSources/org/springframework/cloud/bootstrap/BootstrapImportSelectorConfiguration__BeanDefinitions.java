package org.springframework.cloud.bootstrap;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link BootstrapImportSelectorConfiguration}.
 */
@Generated
public class BootstrapImportSelectorConfiguration__BeanDefinitions {
  /**
   * Get the bean definition for 'bootstrapImportSelectorConfiguration'.
   */
  public static BeanDefinition getBootstrapImportSelectorConfigurationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(BootstrapImportSelectorConfiguration.class);
    beanDefinition.setInstanceSupplier(BootstrapImportSelectorConfiguration::new);
    return beanDefinition;
  }
}
