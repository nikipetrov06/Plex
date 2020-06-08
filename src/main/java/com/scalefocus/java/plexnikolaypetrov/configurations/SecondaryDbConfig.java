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
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "secondaryDbEntityManager",
    transactionManagerRef = "secondDbTransactionManager",
    basePackages = {"com.scalefocus.java.plexnikolaypetrov.repositories.secondary"}
)
public class SecondaryDbConfig extends AbstractDbConfig {

  @Bean(name = "secondaryDbDataSource")
  @ConfigurationProperties(prefix = "spring.secondary-datasource")
  @Override
  public DataSource dataSource() {
    return DataSourceBuilder
        .create()
        .build();
  }

  @Bean(name = "secondaryDbEntityManager")
  @Override
  public LocalContainerEntityManagerFactoryBean entityManager(EntityManagerFactoryBuilder builder,
      @Qualifier("secondaryDbDataSource") DataSource dataSource,
      @Value("${spring.secondary-datasource.builder-package}") String builderPackage,
      @Value("${spring.secondary-datasource.builder-persistence-unit}") String persistenceUnit) {
    return super.entityManager(builder, dataSource, builderPackage, persistenceUnit);
  }

  @Bean(name = "secondDbTransactionManager")
  @Override
  public PlatformTransactionManager transactionManager(@Qualifier("secondaryDbEntityManager") EntityManagerFactory entityManagerFactory) {
    return super.transactionManager(entityManagerFactory);
  }
}
