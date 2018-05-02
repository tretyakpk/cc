package com.purebros.care.customer.main.datasources.wind;

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
        basePackages = "com.purebros.care.customer.main.datasources.wind",
        entityManagerFactoryRef = "windEntityManager",
        transactionManagerRef = "windTransactionManager"
)
public class WindDBConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    private final Environment env;

    @Autowired
    public WindDBConfiguration(Environment env) {
        this.env = env;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean windEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(windDataSource());
        em.setPackagesToScan("com.purebros.care.customer.main.datasources.wind");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return em;
    }

    @Bean
    public JpaTransactionManager windTransactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    @ConfigurationProperties(prefix = "wind")
    public DataSource windDataSource() {

        logger.info("<<<<<< Connection to wind database >>>>>> "
                + "class: " + env.getProperty("wind.driver-class-name")
                + "; url: " + env.getProperty("wind.url")
                + "; username: " + env.getProperty("wind.username")
                + "; password: " + env.getProperty("wind.password")
        );

        return DataSourceBuilder.create()
                .username(env.getProperty("wind.username"))
                .password(env.getProperty("wind.password"))
                .driverClassName(env.getProperty("wind.driver-class-name"))
                .url(env.getProperty("wind.url"))
                .build();
    }
}
