package ru.haval;

import org.springframework.context.annotation.Configuration;

@Configuration
public class HmmrDataSourceConfiguration {

  // @Bean(name = "work-recording")
  // // @ConfigurationProperties(prefix = "spring.datasource.work-recording")
  // public DataSource mySqlDataSource() {
  // DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
  // dataSourceBuilder.url("jdbc:mysql://10.168.150.74:3306/hmmr_mu?serverTimezone=UTC");
  // dataSourceBuilder.username("hmmr");
  // dataSourceBuilder.password("Ro145437");
  // return dataSourceBuilder.build();
  // }

  // @Bean
  // // @ConfigurationProperties(prefix = "spring.datasource")
  // @Primary
  // public DataSource tSqlDataSource() {
  // DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
  // dataSourceBuilder.url("jdbc:mysql://10.168.150.74:3306/trainings?serverTimezone=UTC");
  // dataSourceBuilder.username("hmmr");
  // dataSourceBuilder.password("Ro145437");
  // return dataSourceBuilder.build();
  // }
}
