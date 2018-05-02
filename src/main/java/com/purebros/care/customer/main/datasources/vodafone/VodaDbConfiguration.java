package com.purebros.care.customer.main.datasources.vodafone;

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
        basePackages = "com.purebros.care.customer.main.datasources.vodafone",
        entityManagerFactoryRef = "vodafoneEntityManager",
        transactionManagerRef = "vodafoneTransactionManager"
)
public class VodaDbConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    private final Environment env;

    @Autowired
    public VodaDbConfiguration(Environment env) {
        this.env = env;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean vodafoneEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(vodafoneDataSource());
        em.setPackagesToScan("com.purebros.care.customer.main.datasources.vodafone");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return em;
    }

    @Bean
    public JpaTransactionManager vodafoneTransactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    @ConfigurationProperties(prefix = "vodafone")
    public DataSource vodafoneDataSource() {

        logger.info("<<<<<< Connection to vodafone database >>>>>> "
                + "class: " + env.getProperty("vodafone.driver-class-name")
                + "; url: " + env.getProperty("vodafone.url")
                + "; username: " + env.getProperty("vodafone.username")
                + "; password: " + env.getProperty("vodafone.password")
        );

        return DataSourceBuilder.create()
                .username(env.getProperty("vodafone.username"))
                .password(env.getProperty("vodafone.password"))
                .driverClassName(env.getProperty("vodafone.driver-class-name"))
                .url(env.getProperty("vodafone.url"))
                .build();
    }
}
