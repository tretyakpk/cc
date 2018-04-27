package com.purebros.care.customer.main.datasources.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
        basePackages = "com.purebros.care.customer.main.datasources.test",
        entityManagerFactoryRef = "testEntityManager",
        transactionManagerRef = "testTransactionManager"
)
public class TestConfiguration {

    @Autowired
    private Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean testEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(testDataSource());
        em.setPackagesToScan("com.purebros.care.customer.main.datasources.user.entity");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return em;
    }

    @Bean
    public JpaTransactionManager testTransactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    public DataSource testDataSource() {

        System.out.println("<<<<<< Connection to test database >>>>>> "
            + "class: " + env.getProperty("test.datasource.driver-class-name")
            + "; url: " + env.getProperty("test.datasource.url")
            + "; username: " + env.getProperty("test.datasource.username")
            + "; password: " + env.getProperty("test.datasource.password")
        );

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("test.datasource.driver-class-name"));
        dataSource.setUrl(     env.getProperty("test.datasource.url"));
        dataSource.setUsername(env.getProperty("test.datasource.username"));
        dataSource.setPassword(env.getProperty("test.datasource.password"));

        return dataSource;
    }
}
