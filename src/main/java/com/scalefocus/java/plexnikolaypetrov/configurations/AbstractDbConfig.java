package com.scalefocus.java.plexnikolaypetrov.configurations;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

public abstract class AbstractDbConfig {

  public abstract DataSource dataSource();

  public LocalContainerEntityManagerFactoryBean entityManager(final EntityManagerFactoryBuilder builder,
      final DataSource dataSource, final String builderPackage, final String persistenceUnit) {
    return builder.dataSource(dataSource)
        .packages(builderPackage)
        .persistenceUnit(persistenceUnit)
        .build();
  }

  public PlatformTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }
}
