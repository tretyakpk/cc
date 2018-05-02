package com.purebros.care.customer.main.datasources.tim;

import com.purebros.care.customer.main.component.CustomAuthenticationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.purebros.care.customer.main.datasources.tim",
        entityManagerFactoryRef = "timEntityManager",
        transactionManagerRef = "timTransactionManager"
)
public class TimDbConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    private final Environment env;

    @Autowired
    public TimDbConfiguration(Environment env) {
        this.env = env;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean timEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(timDataSource());
        em.setPackagesToScan("com.purebros.care.customer.main.datasources.tim");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return em;
    }

    @Bean
    public JpaTransactionManager timTransactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    @ConfigurationProperties(prefix = "tim")
    public DataSource timDataSource() {

        logger.info("<<<<<< Connection to tim database >>>>>> "
                + "class: " + env.getProperty("tim.driver-class-name")
                + "; url: " + env.getProperty("tim.url")
                + "; username: " + env.getProperty("tim.username")
                + "; password: " + env.getProperty("tim.password")
        );

        return DataSourceBuilder.create()
                .username(env.getProperty("tim.username"))
                .password(env.getProperty("tim.password"))
                .driverClassName(env.getProperty("tim.driver-class-name"))
                .url(env.getProperty("tim.url"))
                .build();
    }
}
