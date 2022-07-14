package com.kurdi.multipledatasource.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "ordersEntityManagerFactory"
		, transactionManagerRef = "ordersTransactionManager"
		, basePackages = {"com.kurdi.multipledatasource.repositories.orders" })
public class OrdersDatabaseConfig {
	@Primary
	@Bean(name = "ordersDataSource")
	@ConfigurationProperties(prefix = "spring.orders.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean(name = "ordersEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("ordersDataSource") DataSource ordersDataSource) {
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", "create-drop");
		return builder.dataSource(ordersDataSource).properties(properties)
				.packages("com.kurdi.multipledatasource.entities.orders").persistenceUnit("Order").build();
	}

	@Primary
	@Bean(name = "ordersTransactionManager")
	public PlatformTransactionManager transactionManager(
			@Qualifier("ordersEntityManagerFactory") EntityManagerFactory ordersEntityManagerFactory) {
		return new JpaTransactionManager(ordersEntityManagerFactory);
	}
}
