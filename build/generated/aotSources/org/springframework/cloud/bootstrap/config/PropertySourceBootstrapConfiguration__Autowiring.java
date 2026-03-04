package org.springframework.cloud.bootstrap.config;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link PropertySourceBootstrapConfiguration}.
 */
@Generated
public class PropertySourceBootstrapConfiguration__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static PropertySourceBootstrapConfiguration apply(RegisteredBean registeredBean,
      PropertySourceBootstrapConfiguration instance) {
    AutowiredFieldValueResolver.forField("propertySourceLocators").resolveAndSet(registeredBean, instance);
    AutowiredFieldValueResolver.forRequiredField("bootstrapProperties").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
