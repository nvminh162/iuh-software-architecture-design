package com.nvminh162.lab02.functional.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
    entityManagerFactoryRef = "inventoryEntityManagerFactory",
    transactionManagerRef = "inventoryTransactionManager",
    basePackages = { 
        "com.nvminh162.lab02.functional.inventory",
        "com.nvminh162.lab02.horizontal.repository",
        "com.nvminh162.lab02.vertical.repository"
    }
)
public class InventoryDbConfig {

    @Primary
    @Bean(name = "inventoryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.inventory")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "inventoryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder, 
            @Qualifier("inventoryDataSource") DataSource dataSource) {
            
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect");
        
        return builder
                .dataSource(dataSource)
                .properties(properties)
                .packages(
                    "com.nvminh162.lab02.functional.inventory",
                    "com.nvminh162.lab02.horizontal.entity",
                    "com.nvminh162.lab02.vertical.entity"
                )
                .persistenceUnit("inventory")
                .build();
    }

    @Primary
    @Bean(name = "inventoryTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("inventoryEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
