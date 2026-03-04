package org.springframework.cloud.vault.config;

import java.lang.SuppressWarnings;
import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.authentication.SessionManager;
import org.springframework.vault.client.RestTemplateFactory;
import org.springframework.vault.config.AbstractVaultConfiguration;
import org.springframework.vault.core.VaultTemplate;

/**
 * Bean definitions for {@link VaultBootstrapConfiguration}.
 */
@Generated
public class VaultBootstrapConfiguration__BeanDefinitions {
  /**
   * Get the bean instance supplier for 'org.springframework.cloud.vault.config.VaultBootstrapConfiguration'.
   */
  @SuppressWarnings("deprecation")
  private static BeanInstanceSupplier<VaultBootstrapConfiguration> getVaultBootstrapConfigurationInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<VaultBootstrapConfiguration>forConstructor(ConfigurableApplicationContext.class, VaultProperties.class, ObjectProvider.class, ObjectProvider.class, ObjectProvider.class)
            .withGenerator((registeredBean, args) -> new VaultBootstrapConfiguration(args.get(0), args.get(1), args.get(2), args.get(3), args.get(4)));
  }

  /**
   * Get the bean definition for 'vaultBootstrapConfiguration'.
   */
  @SuppressWarnings("deprecation")
  public static BeanDefinition getVaultBootstrapConfigurationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(VaultBootstrapConfiguration.class);
    beanDefinition.setInstanceSupplier(getVaultBootstrapConfigurationInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'clientHttpRequestFactoryWrapper'.
   */
  @SuppressWarnings("deprecation")
  private static BeanInstanceSupplier<AbstractVaultConfiguration.ClientFactoryWrapper> getClientHttpRequestFactoryWrapperInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<AbstractVaultConfiguration.ClientFactoryWrapper>forFactoryMethod(VaultBootstrapConfiguration.class, "clientHttpRequestFactoryWrapper")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean("org.springframework.cloud.vault.config.VaultBootstrapConfiguration", VaultBootstrapConfiguration.class).clientHttpRequestFactoryWrapper());
  }

  /**
   * Get the bean definition for 'clientHttpRequestFactoryWrapper'.
   */
  public static BeanDefinition getClientHttpRequestFactoryWrapperBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(AbstractVaultConfiguration.ClientFactoryWrapper.class);
    beanDefinition.setFactoryBeanName("org.springframework.cloud.vault.config.VaultBootstrapConfiguration");
    beanDefinition.setInstanceSupplier(getClientHttpRequestFactoryWrapperInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'vaultRestTemplateFactory'.
   */
  @SuppressWarnings("deprecation")
  private static BeanInstanceSupplier<RestTemplateFactory> getVaultRestTemplateFactoryInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<RestTemplateFactory>forFactoryMethod(VaultBootstrapConfiguration.class, "vaultRestTemplateFactory", AbstractVaultConfiguration.ClientFactoryWrapper.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.cloud.vault.config.VaultBootstrapConfiguration", VaultBootstrapConfiguration.class).vaultRestTemplateFactory(args.get(0)));
  }

  /**
   * Get the bean definition for 'vaultRestTemplateFactory'.
   */
  public static BeanDefinition getVaultRestTemplateFactoryBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(RestTemplateFactory.class);
    beanDefinition.setFactoryBeanName("org.springframework.cloud.vault.config.VaultBootstrapConfiguration");
    beanDefinition.setInstanceSupplier(getVaultRestTemplateFactoryInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'vaultTemplate'.
   */
  @SuppressWarnings("deprecation")
  private static BeanInstanceSupplier<VaultTemplate> getVaultTemplateInstanceSupplier() {
    return BeanInstanceSupplier.<VaultTemplate>forFactoryMethod(VaultBootstrapConfiguration.class, "vaultTemplate", AbstractVaultConfiguration.ClientFactoryWrapper.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.cloud.vault.config.VaultBootstrapConfiguration", VaultBootstrapConfiguration.class).vaultTemplate(args.get(0)));
  }

  /**
   * Get the bean definition for 'vaultTemplate'.
   */
  public static BeanDefinition getVaultTemplateBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(VaultTemplate.class);
    beanDefinition.setFactoryBeanName("org.springframework.cloud.vault.config.VaultBootstrapConfiguration");
    beanDefinition.setInstanceSupplier(getVaultTemplateInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'vaultTaskScheduler'.
   */
  @SuppressWarnings("deprecation")
  private static BeanInstanceSupplier<VaultAutoConfiguration.TaskSchedulerWrapper> getVaultTaskSchedulerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<VaultAutoConfiguration.TaskSchedulerWrapper>forFactoryMethod(VaultBootstrapConfiguration.class, "vaultTaskScheduler")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean("org.springframework.cloud.vault.config.VaultBootstrapConfiguration", VaultBootstrapConfiguration.class).vaultTaskScheduler());
  }

  /**
   * Get the bean definition for 'vaultTaskScheduler'.
   */
  public static BeanDefinition getVaultTaskSchedulerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(VaultAutoConfiguration.TaskSchedulerWrapper.class);
    beanDefinition.setLazyInit(true);
    beanDefinition.setFactoryBeanName("org.springframework.cloud.vault.config.VaultBootstrapConfiguration");
    beanDefinition.setInstanceSupplier(getVaultTaskSchedulerInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'vaultSessionManager'.
   */
  @SuppressWarnings("deprecation")
  private static BeanInstanceSupplier<SessionManager> getVaultSessionManagerInstanceSupplier() {
    return BeanInstanceSupplier.<SessionManager>forFactoryMethod(VaultBootstrapConfiguration.class, "vaultSessionManager", ClientAuthentication.class, ObjectFactory.class, RestTemplateFactory.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.cloud.vault.config.VaultBootstrapConfiguration", VaultBootstrapConfiguration.class).vaultSessionManager(args.get(0), args.get(1), args.get(2)));
  }

  /**
   * Get the bean definition for 'vaultSessionManager'.
   */
  public static BeanDefinition getVaultSessionManagerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(SessionManager.class);
    beanDefinition.setFactoryBeanName("org.springframework.cloud.vault.config.VaultBootstrapConfiguration");
    beanDefinition.setInstanceSupplier(getVaultSessionManagerInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'clientAuthentication'.
   */
  @SuppressWarnings("deprecation")
  private static BeanInstanceSupplier<ClientAuthentication> getClientAuthenticationInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<ClientAuthentication>forFactoryMethod(VaultBootstrapConfiguration.class, "clientAuthentication", AbstractVaultConfiguration.ClientFactoryWrapper.class, RestTemplateFactory.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.cloud.vault.config.VaultBootstrapConfiguration", VaultBootstrapConfiguration.class).clientAuthentication(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'clientAuthentication'.
   */
  public static BeanDefinition getClientAuthenticationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(ClientAuthentication.class);
    beanDefinition.setFactoryBeanName("org.springframework.cloud.vault.config.VaultBootstrapConfiguration");
    beanDefinition.setInstanceSupplier(getClientAuthenticationInstanceSupplier());
    return beanDefinition;
  }
}
