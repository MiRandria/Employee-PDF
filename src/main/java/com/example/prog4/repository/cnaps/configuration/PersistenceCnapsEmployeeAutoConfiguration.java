package com.example.prog4.repository.cnaps.configuration;

import java.util.HashMap;
import javax.sql.DataSource;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySource({"classpath:application.properties"})
@EnableJpaRepositories(basePackages = "com.example.prog4.repository.cnaps", entityManagerFactoryRef = "cnapsEmployeeEntityManager", transactionManagerRef = "cnapsEmployeeTransactionManager")
@AllArgsConstructor
public class PersistenceCnapsEmployeeAutoConfiguration {
  private Environment env;

  @Bean
  public LocalContainerEntityManagerFactoryBean cnapsEmployeeEntityManager() {
    final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(cnapsEmployeeDataSource());
    em.setPackagesToScan("com.example.prog4.repository.cnaps");

    final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);
    final HashMap<String, Object> properties = new HashMap<>();
    properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
    properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
    em.setJpaPropertyMap(properties);

    return em;
  }

  @Bean(name = "cnapsEmployeeDataSource")
  @ConfigurationProperties(prefix = "spring.second-datasource")
  public DataSource cnapsEmployeeDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean
  public PlatformTransactionManager cnapsEmployeeTransactionManager() {
    final JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(cnapsEmployeeEntityManager().getObject());
    return transactionManager;
  }

}