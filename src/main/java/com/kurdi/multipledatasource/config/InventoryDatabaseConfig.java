package com.kurdi.multipledatasource.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@EnableJpaRepositories(entityManagerFactoryRef = "inventoryEntityManagerFactory"
					, transactionManagerRef = "inventoryTransactionManager"
					,basePackages = {"com.kurdi.multipledatasource.repositories.inventory" })
public class InventoryDatabaseConfig {

	@Bean(name = "inventoryDataSource")
	@ConfigurationProperties(prefix = "spring.inventory.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "inventoryEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean inventoryEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("inventoryDataSource") DataSource dataSource) {
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", "create-drop");
		return builder.dataSource(dataSource).properties(properties)
				.packages("com.kurdi.multipledatasource.entities.inventory").persistenceUnit("Inventory").build();
	}

	@Bean(name = "inventoryTransactionManager")
	public PlatformTransactionManager inventoryTransactionManager(
			@Qualifier("inventoryEntityManagerFactory") EntityManagerFactory inventoryEntityManagerFactory) {
		return new JpaTransactionManager(inventoryEntityManagerFactory);
	}
}
