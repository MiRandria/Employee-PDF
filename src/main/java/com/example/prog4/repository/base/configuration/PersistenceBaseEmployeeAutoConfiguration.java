package com.example.prog4.repository.base.configuration;

import java.util.HashMap;
import javax.sql.DataSource;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySource({"classpath:application.properties"})
@EnableJpaRepositories(basePackages = "com.example.prog4.repository.base", entityManagerFactoryRef = "baseEmployeeEntityManager", transactionManagerRef = "baseEmployeeTransactionManager")
@AllArgsConstructor
public class PersistenceBaseEmployeeAutoConfiguration {
  private Environment env;

  @Primary
  @Bean
  public LocalContainerEntityManagerFactoryBean baseEmployeeEntityManager() {
    final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(baseEmployeeDataSource());
    em.setPackagesToScan("com.example.prog4.repository.base");

    final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);
    final HashMap<String, Object> properties = new HashMap<>();
    properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
    properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
    em.setJpaPropertyMap(properties);

    return em;
  }

  @Bean
  @Primary
  @ConfigurationProperties(prefix = "spring.datasource")
  public DataSource baseEmployeeDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Primary
  @Bean
  public PlatformTransactionManager baseEmployeeTransactionManager() {
    final JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(baseEmployeeEntityManager().getObject());
    return transactionManager;
  }

}