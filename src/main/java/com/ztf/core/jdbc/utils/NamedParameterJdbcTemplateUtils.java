package com.ztf.core.jdbc.utils;


import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * <p>User: ztf
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public class NamedParameterJdbcTemplateUtils {

    private static NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public static NamedParameterJdbcTemplate jdbcTemplate() {
        if(namedParameterJdbcTemplate == null) {
            namedParameterJdbcTemplate = createJdbcTemplate();
        }
        return namedParameterJdbcTemplate;
    }

    private static NamedParameterJdbcTemplate createJdbcTemplate() {
        PropertiesLoader propertiesLoader = new PropertiesLoader("spring-jdbc.properties");
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName(propertiesLoader.getProperty("jdbc.driver"));
        ds.setUrl(propertiesLoader.getProperty("jdbc.url"));
        ds.setUsername(propertiesLoader.getProperty("jdbc.username"));
        ds.setPassword(propertiesLoader.getProperty("jdbc.password"));
        return new NamedParameterJdbcTemplate(ds);
    }

}
