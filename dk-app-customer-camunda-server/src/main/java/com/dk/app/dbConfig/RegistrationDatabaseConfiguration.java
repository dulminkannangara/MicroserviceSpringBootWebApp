package com.dk.app.dbConfig;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "registrationEntityManagerFactory",
        transactionManagerRef = "registrationTransactionManager",
        basePackages = {"com.dk.app.repository"})

public class RegistrationDatabaseConfiguration {

    @Bean(name = "registrationDataSource")
    @ConfigurationProperties(prefix = "spring.registration.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "registrationEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean registrationEntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                                   @Qualifier("registrationDataSource") DataSource dataSource) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        return builder.dataSource(dataSource).properties(properties)
                .packages("com.dk.app.model")
                .persistenceUnit("Role")
                .persistenceUnit("User")
                .build();
    }

    @Bean(name = "registrationTransactionManager")
    public PlatformTransactionManager registrationTransactionManager(
            @Qualifier("registrationEntityManagerFactory") EntityManagerFactory registrationEntityManagerFactory) {
        return new JpaTransactionManager(registrationEntityManagerFactory);
    }

}
