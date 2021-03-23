package ru.haval.muTrainings;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class HmmrDataSourceConfiguration {

  // @Bean(name = "workrecording")
  // @ConfigurationProperties(prefix = "spring.datasource.workrecording")
  // public DataSource mySqlDataSource() {
  // DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
  // dataSourceBuilder.url("jdbc:mysql://10.168.150.74:3306/hmmr_mu?serverTimezone=UTC");
  // dataSourceBuilder.username("hmmr");
  // dataSourceBuilder.password("Ro145437");
  // return dataSourceBuilder.build();
  // }

  // @Bean(name = "trainings")
  // @ConfigurationProperties(prefix = "spring.datasource")
  // @Primary
  // public DataSource tSqlDataSource() {
  // DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
  // dataSourceBuilder.url("jdbc:mysql://10.168.150.74:3306/trainings?serverTimezone=UTC");
  // dataSourceBuilder.username("hmmr");
  // dataSourceBuilder.password("Ro145437");
  // return dataSourceBuilder.build();
  // }
}
