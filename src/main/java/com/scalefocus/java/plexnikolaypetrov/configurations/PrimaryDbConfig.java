package com.scalefocus.java.plexnikolaypetrov.configurations;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "primaryDbEntityManager",
    transactionManagerRef = "primaryDbTransactionManager",
    basePackages = {"com.scalefocus.java.plexnikolaypetrov.repositories.primary"}
)
public class PrimaryDbConfig extends AbstractDbConfig {

  @Primary
  @Bean(name = "dataSource")
  @ConfigurationProperties(prefix = "spring.primary-datasource")
  @Override
  public DataSource dataSource() {
    return DataSourceBuilder
        .create()
        .build();
  }

  @Primary
  @Bean(name = "primaryDbEntityManager")
  @Override
  public LocalContainerEntityManagerFactoryBean entityManager(
      EntityManagerFactoryBuilder builder,
      @Qualifier("dataSource") DataSource dataSource,
      @Value("${spring.primary-datasource.builder-package}") String builderPackage,
      @Value("${spring.primary-datasource.builder-persistence-unit}") String persistenceUnit) {
    return super.entityManager(builder, dataSource, builderPackage, persistenceUnit);
  }

  @Primary
  @Bean(name = "primaryDbTransactionManager")
  @Override
  public PlatformTransactionManager transactionManager(@Qualifier("primaryDbEntityManager") EntityManagerFactory entityManagerFactory) {
    return super.transactionManager(entityManagerFactory);
  }
}
