package com.ztf.core.jdbc.utils;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * <p>User: ztf
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public class JdbcTemplateUtils {

    private static JdbcTemplate jdbcTemplate;

    public static JdbcTemplate jdbcTemplate() {
        if(jdbcTemplate == null) {
            jdbcTemplate = createJdbcTemplate();
        }
        return jdbcTemplate;
    }

    private static JdbcTemplate createJdbcTemplate() {
        PropertiesLoader propertiesLoader = new PropertiesLoader("spring-jdbc.properties");
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName(propertiesLoader.getProperty("jdbc.driver"));
        ds.setUrl(propertiesLoader.getProperty("jdbc.url"));
        ds.setUsername(propertiesLoader.getProperty("jdbc.username"));
        ds.setPassword(propertiesLoader.getProperty("jdbc.password"));
        return new JdbcTemplate(ds);
    }

}
