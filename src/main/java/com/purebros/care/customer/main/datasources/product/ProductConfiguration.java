package com.purebros.care.customer.main.datasources.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.purebros.care.customer.main.datasources.product",
        entityManagerFactoryRef = "productEntityManager",
        transactionManagerRef = "productTransactionManager"
)
public class ProductConfiguration {

    @Autowired
    private Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean productEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(productDataSource());
        em.setPackagesToScan("com.purebros.care.customer.main.datasources.product.entity");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return em;
    }

    @Bean
    public JpaTransactionManager productTransactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    public DataSource productDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        System.out.println("<<<<<< Connection to product database >>>>>>");
        System.out.println(env.getProperty("product.datasource.driver-class-name"));
        System.out.println(env.getProperty("product.datasource.url"));
        System.out.println(env.getProperty("product.datasource.username"));
        System.out.println(env.getProperty("product.datasource.password"));

        dataSource.setDriverClassName(env.getProperty("product.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("product.datasource.url"));
        dataSource.setUsername(env.getProperty("product.datasource.username"));
        dataSource.setPassword(env.getProperty("product.datasource.password"));

        return dataSource;
    }
}
