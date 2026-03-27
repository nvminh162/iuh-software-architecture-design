package com.nvminh162.lab02.functional.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import jakarta.persistence.EntityManagerFactory;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "orderEntityManagerFactory",
    transactionManagerRef = "orderTransactionManager",
    basePackages = { "com.nvminh162.lab02.functional.orders" }
)
public class OrderDbConfig {

    @Bean(name = "orderDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.orders")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "orderEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder, 
            @Qualifier("orderDataSource") DataSource dataSource) {
        
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect");
        
        return builder
                .dataSource(dataSource)
                .properties(properties)
                .packages("com.nvminh162.lab02.functional.orders")
                .persistenceUnit("orders")
                .build();
    }

    @Bean(name = "orderTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("orderEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
