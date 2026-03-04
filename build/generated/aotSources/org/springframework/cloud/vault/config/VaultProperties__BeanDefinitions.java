package org.springframework.cloud.vault.config;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link VaultProperties}.
 */
@Generated
public class VaultProperties__BeanDefinitions {
  /**
   * Get the bean definition for 'vaultProperties'.
   */
  public static BeanDefinition getVaultPropertiesBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(VaultProperties.class);
    beanDefinition.setInstanceSupplier(VaultProperties::new);
    return beanDefinition;
  }
}
