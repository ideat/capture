package com.mindware.capture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "informixEntityManagerFactory", transactionManagerRef = "informixTransactionManager",
        basePackages = { "com.mindware.capture.repository.informix"})
public class InformixConfig {

    @Autowired
    private Environment env;

    @Bean(name = "informixDataSource")
    public DataSource adminDatasource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(env.getProperty("informix.datasource.url"));
        dataSource.setUsername(env.getProperty("informix.datasource.username"));
        dataSource.setPassword(env.getProperty("informix.datasource.password"));
        dataSource.setDriverClassName(env.getProperty("informix.datasource.driver-class-name"));

        return dataSource;
    }


    @Primary
    @Bean(name = "informixEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(adminDatasource());
        em.setPackagesToScan("com.mindware.capture.model.informix");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("informix.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.show-sql", env.getProperty("informix.jpa.show-sql"));
        properties.put("hibernate.dialect", env.getProperty("informix.jpa.database-platform"));

        em.setJpaPropertyMap(properties);

        return em;

    }

    @Primary
    @Bean(name = "informixTransactionManager")
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }
}
