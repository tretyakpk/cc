package com.purebros.care.customer.main.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
public class DbConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(DbConfiguration.class);

    private final Environment env;

    @Autowired
    public DbConfiguration(Environment env) {
        this.env = env;
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.purebros.care.customer.main.datasources.user");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return em;
    }

    @Bean
    @Primary
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    @Primary
    public DataSource dataSource() {

        logger.info("<<<<<< Connection to carrier database >>>>>> "
                + "class: " + env.getProperty("carrier.driver-class-name")
                + "; url: " + env.getProperty("carrier.url")
                + "; username: " + env.getProperty("carrier.username")
                + "; password: " + env.getProperty("carrier.password")
        );

        return DataSourceBuilder.create()
                .username(env.getProperty("carrier.username"))
                .password(env.getProperty("carrier.password"))
                .driverClassName(env.getProperty("carrier.driver-class-name"))
                .url(env.getProperty("carrier.url"))
                .build();
    }
}
