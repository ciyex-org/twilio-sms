package org.springframework.cloud.bootstrap.encrypt;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.BeanInstanceSupplier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Bean definitions for {@link EncryptionBootstrapConfiguration}.
 */
@Generated
public class EncryptionBootstrapConfiguration__BeanDefinitions {
  /**
   * Get the bean definition for 'encryptionBootstrapConfiguration'.
   */
  public static BeanDefinition getEncryptionBootstrapConfigurationBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(EncryptionBootstrapConfiguration.class);
    beanDefinition.setInstanceSupplier(EncryptionBootstrapConfiguration::new);
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'keyProperties'.
   */
  private static BeanInstanceSupplier<KeyProperties> getKeyPropertiesInstanceSupplier() {
    return BeanInstanceSupplier.<KeyProperties>forFactoryMethod(EncryptionBootstrapConfiguration.class, "keyProperties")
            .withGenerator((registeredBean) -> registeredBean.getBeanFactory().getBean("org.springframework.cloud.bootstrap.encrypt.EncryptionBootstrapConfiguration", EncryptionBootstrapConfiguration.class).keyProperties());
  }

  /**
   * Get the bean definition for 'keyProperties'.
   */
  public static BeanDefinition getKeyPropertiesBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(KeyProperties.class);
    beanDefinition.setFactoryBeanName("org.springframework.cloud.bootstrap.encrypt.EncryptionBootstrapConfiguration");
    beanDefinition.setInstanceSupplier(getKeyPropertiesInstanceSupplier());
    return beanDefinition;
  }

  /**
   * Get the bean instance supplier for 'environmentDecryptApplicationListener'.
   */
  private static BeanInstanceSupplier<EnvironmentDecryptApplicationInitializer> getEnvironmentDecryptApplicationListenerInstanceSupplier(
      ) {
    return BeanInstanceSupplier.<EnvironmentDecryptApplicationInitializer>forFactoryMethod(EncryptionBootstrapConfiguration.class, "environmentDecryptApplicationListener", ConfigurableApplicationContext.class, KeyProperties.class)
            .withGenerator((registeredBean, args) -> registeredBean.getBeanFactory().getBean("org.springframework.cloud.bootstrap.encrypt.EncryptionBootstrapConfiguration", EncryptionBootstrapConfiguration.class).environmentDecryptApplicationListener(args.get(0), args.get(1)));
  }

  /**
   * Get the bean definition for 'environmentDecryptApplicationListener'.
   */
  public static BeanDefinition getEnvironmentDecryptApplicationListenerBeanDefinition() {
    RootBeanDefinition beanDefinition = new RootBeanDefinition(EnvironmentDecryptApplicationInitializer.class);
    beanDefinition.setFactoryBeanName("org.springframework.cloud.bootstrap.encrypt.EncryptionBootstrapConfiguration");
    beanDefinition.setInstanceSupplier(getEnvironmentDecryptApplicationListenerInstanceSupplier());
    return beanDefinition;
  }
}
