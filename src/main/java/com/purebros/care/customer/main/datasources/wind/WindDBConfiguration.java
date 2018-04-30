package com.purebros.care.customer.main.datasources.wind;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
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
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.purebros.care.customer.main.datasources.wind",
        entityManagerFactoryRef = "windEntityManager",
        transactionManagerRef = "windTransactionManager"
)
public class WindDBConfiguration {

    @Autowired
    private Environment env;

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

        System.out.println("<<<<<< Connection to wind database >>>>>> "
                + "class: " + env.getProperty("wind.driver-class-name")
                + "; url: " + env.getProperty("wind.url")
                + "; username: " + env.getProperty("wind.username")
                + "; password: " + env.getProperty("wind.password")
        );

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("wind.driver-class-name"));
        dataSource.setUrl(     env.getProperty("wind.url"));
        dataSource.setUsername(env.getProperty("wind.username"));
        dataSource.setPassword(env.getProperty("wind.password"));
        Properties props = new Properties();
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");

        dataSource.setConnectionProperties(props);

        return dataSource;
    }
}
