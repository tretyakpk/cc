package com.purebros.care.customer.main.datasources.wind;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@PropertySource({ "classpath:db_wind.yml" })
@EnableJpaRepositories(
        basePackages = "com.purebros.care.customer.main.datasources.wind",
        entityManagerFactoryRef = "windEntityManager",
        transactionManagerRef = "windTransactionManager"
)
public class WindDbConfiguration {

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
    public DataSource windDataSource() {

        System.out.println("<<<<<< Connection to wind database >>>>>> "
            + "class: " + env.getProperty("driver-class-name")
            + "; url: " + env.getProperty("url")
            + "; username: " + env.getProperty("username")
            + "; password: " + env.getProperty("password")
        );

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("driver-class-name"));
        dataSource.setUrl(     env.getProperty("url"));
        dataSource.setUsername(env.getProperty("username"));
        dataSource.setPassword(env.getProperty("password"));

        return dataSource;
    }
}
