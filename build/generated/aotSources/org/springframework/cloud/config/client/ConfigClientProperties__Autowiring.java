package org.springframework.cloud.config.client;

import org.springframework.aot.generate.Generated;
import org.springframework.beans.factory.aot.AutowiredFieldValueResolver;
import org.springframework.beans.factory.support.RegisteredBean;

/**
 * Autowiring for {@link ConfigClientProperties}.
 */
@Generated
public class ConfigClientProperties__Autowiring {
  /**
   * Apply the autowiring.
   */
  public static ConfigClientProperties apply(RegisteredBean registeredBean,
      ConfigClientProperties instance) {
    AutowiredFieldValueResolver.forRequiredField("name").resolveAndSet(registeredBean, instance);
    return instance;
  }
}
