package org.springframework.cloud.vault.config;

import java.lang.SuppressWarnings;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.core.lease.SecretLeaseContainer;

/**
 * Bean definitions for {@link VaultBootstrapPropertySourceConfiguration}.
 */
@Generated
public class VaultBootstrapPropertySourceConfiguration__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'org.springframework.cloud.vault.config.VaultBootstrapPropertySourceConfiguration'.
   */
  @SuppressWarnings("deprecation")
  private static BeanInstanceSupplier<VaultBootstrapPropertySourceConfiguration> getVaultBootstrapPropertySourceConfigurationInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<VaultBootstrapPropertySourceConfiguration>forConstructor(VaultProperties.class, ConfigurableApplicationContext.class)
            .withGenerator((registeredBean, args) -> new VaultBootstrapPropertySourceConfiguration(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'vaultBootstrapPropertySourceConfiguration'.
   */
  @SuppressWarnings("deprecation")
  public static BeanDefinition getVaultBootstrapPropertySourceConfigurationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(VaultBootstrapPropertySourceConfiguration.class);
    beanDefinition.setInstanceSupplier(getVaultBootstrapPropertySourceConfigurationInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'vaultPropertySourceLocator'.
   */
  @SuppressWarnings("deprecation")
  private static BeanInstanceSupplier<PropertySourceLocator> getVaultPropertySourceLocatorInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<PropertySourceLocator>forFactoryMethod(VaultBootstrapPropertySourceConfiguration.class, "vaultPropertySourceLocator", VaultOperations.class, VaultProperties.class, VaultKeyValueBackendProperties.class, ObjectFactory.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.cloud.vault.config.VaultBootstrapPropertySourceConfiguration", VaultBootstrapPropertySourceConfiguration.class).vaultPropertySourceLocator(args.get(0), args.get(1), args.get(2), args.get(3)));
  }

  /**
   * Get the bean definition for 'vaultPropertySourceLocator'.
   */
  public static BeanDefinition getVaultPropertySourceLocatorBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(PropertySourceLocator.class);
    beanDefinition.setFactoryBeanName("org.springframework.cloud.vault.config.VaultBootstrapPropertySourceConfiguration");
    beanDefinition.setInstanceSupplier(getVaultPropertySourceLocatorInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'secretLeaseContainer'.
   */
  @SuppressWarnings("deprecation")
  private static BeanInstanceSupplier<SecretLeaseContainer> getSecretLeaseContainerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<SecretLeaseContainer>forFactoryMethod(VaultBootstrapPropertySourceConfiguration.class, "secretLeaseContainer", VaultOperations.class, VaultAutoConfiguration.TaskSchedulerWrapper.class, ObjectProvider.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.cloud.vault.config.VaultBootstrapPropertySourceConfiguration", VaultBootstrapPropertySourceConfiguration.class).secretLeaseContainer(args.get(0), args.get(1), args.get(2)));
  }

  /**
   * Get the bean definition for 'secretLeaseContainer'.
   */
  public static BeanDefinition getSecretLeaseContainerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(SecretLeaseContainer.class);
    beanDefinition.setLazyInit(true);
    beanDefinition.setFactoryBeanName("org.springframework.cloud.vault.config.VaultBootstrapPropertySourceConfiguration");
    beanDefinition.setInstanceSupplier(getSecretLeaseContainerInstanceSupplier());
    return beanDefinition;
  }
}
