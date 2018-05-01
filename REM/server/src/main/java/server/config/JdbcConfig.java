package server.config;


import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Driver;


@Configuration
@ComponentScan("server.repository")
public class JdbcConfig {
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(org.postgresql.Driver.class.getName());
        ds.setUrl("jdbc:postgresql://localhost:5432/BookStore");
        ds.setUsername("postgres");
        ds.setPassword("root");
        ds.setInitialSize(2);
        return ds;
    }
}
