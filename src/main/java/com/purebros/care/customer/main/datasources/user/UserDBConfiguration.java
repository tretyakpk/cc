package com.purebros.care.customer.main.datasources.user;

import com.purebros.care.customer.main.component.CustomAuthenticationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
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
        basePackages = "com.purebros.care.customer.main.datasources.user",
        entityManagerFactoryRef = "userEntityManager",
        transactionManagerRef = "userTransactionManager"
)
public class UserDBConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    private final Environment env;

    @Autowired
    public UserDBConfiguration(Environment env) {
        this.env = env;
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean userEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(userDataSource());
        em.setPackagesToScan("com.purebros.care.customer.main.datasources.user.entity");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return em;
    }

    @Bean
    @Primary
    public JpaTransactionManager userTransactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    @Primary
    public DataSource userDataSource() {

        logger.info("<<<<<< Connection to user database >>>>>> "
                + "class: " + env.getProperty("user.driver-class-name")
                + "; url: " + env.getProperty("user.url")
                + "; username: " + env.getProperty("user.username")
                + "; password: " + env.getProperty("user.password")
        );

        return DataSourceBuilder.create()
                .username(env.getProperty("user.username"))
                .password(env.getProperty("user.password"))
                .driverClassName(env.getProperty("user.driver-class-name"))
                .url(env.getProperty("user.url"))
                .build();
    }
}
