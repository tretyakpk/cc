package com.purebros.care.customer.main.datasources.h3g;

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
        basePackages = "com.purebros.care.customer.main.datasources.h3g",
        entityManagerFactoryRef = "h3gEntityManager",
        transactionManagerRef = "h3gTransactionManager"
)
public class H3gDbConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    private final Environment env;

    @Autowired
    public H3gDbConfiguration(Environment env) {
        this.env = env;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean h3gEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(h3gDataSource());
        em.setPackagesToScan("com.purebros.care.customer.main.datasources.h3g");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return em;
    }

    @Bean
    public JpaTransactionManager h3gTransactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    @ConfigurationProperties(prefix = "h3g")
    public DataSource h3gDataSource() {

        logger.info("<<<<<< Connection to h3g database >>>>>> "
                + "class: " + env.getProperty("h3g.driver-class-name")
                + "; url: " + env.getProperty("h3g.url")
                + "; username: " + env.getProperty("h3g.username")
                + "; password: " + env.getProperty("h3g.password")
        );

        return DataSourceBuilder.create()
                .username(env.getProperty("h3g.username"))
                .password(env.getProperty("h3g.password"))
                .driverClassName(env.getProperty("h3g.driver-class-name"))
                .url(env.getProperty("h3g.url"))
                .build();
    }
}
