package org.springframework.cloud.config.client;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link ConfigServiceBootstrapConfiguration}.
 */
@Generated
public class ConfigServiceBootstrapConfiguration__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static ConfigServiceBootstrapConfiguration apply(RegisteredBean registeredBean,
      ConfigServiceBootstrapConfiguration instance) {
    AutowiredFieldValueResolver.forRequiredField("environment").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
