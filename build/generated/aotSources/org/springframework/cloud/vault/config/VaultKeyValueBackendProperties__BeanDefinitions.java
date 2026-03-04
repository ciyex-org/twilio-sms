package org.springframework.cloud.vault.config;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

/**
 * Bean definitions for {@link VaultKeyValueBackendProperties}.
 */
@Generated
public class VaultKeyValueBackendProperties__BeanDefinitions {
  /**
   * Get the bean definition for 'vaultKeyValueBackendProperties'.
   */
  public static BeanDefinition getVaultKeyValueBackendPropertiesBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(VaultKeyValueBackendProperties.class);
    beanDefinition.setInstanceSupplier(VaultKeyValueBackendProperties::new);
    return beanDefinition;
  }
}
