package com.hobbit.core.tool.config;

import com.hobbit.core.tool.support.xss.XssFilter;
import com.hobbit.core.tool.support.xss.XssProperties;
import com.hobbit.core.tool.support.xss.XssUrlProperties;
import javax.servlet.DispatcherType;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * Xss配置类
 *
 * @author Chill
 */
@Configuration
@AllArgsConstructor
@ConditionalOnProperty(value = "blade.xss.enabled", havingValue = "true")
@EnableConfigurationProperties({XssProperties.class, XssUrlProperties.class})
public class XssConfiguration {

  private final XssProperties xssProperties;
  private final XssUrlProperties xssUrlProperties;

  /**
   * 防XSS注入
   */
  @Bean
  public FilterRegistrationBean<XssFilter> xssFilterRegistration() {
    FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>();
    registration.setDispatcherTypes(DispatcherType.REQUEST);
    registration.setFilter(new XssFilter(xssProperties, xssUrlProperties));
    registration.addUrlPatterns("/*");
    registration.setName("xssFilter");
    registration.setOrder(Ordered.LOWEST_PRECEDENCE);
    return registration;
  }
}
