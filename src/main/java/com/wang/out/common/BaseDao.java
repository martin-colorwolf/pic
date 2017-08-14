package com.wang.out.common;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.SQLException;


/*
* 链接数据库
* */
public class BaseDao {


    public static ApplicationContext ctx = null;
    public static JdbcTemplate jdbcTemplate;
    public static java.sql.PreparedStatement preparedStatement = null;
    public static ComboPooledDataSource dataSource = null;
    public static Connection conn = null;


    {
        ctx = new ClassPathXmlApplicationContext("classpath:spring-config.xml");
        jdbcTemplate = (JdbcTemplate) ctx.getBean("jdbcTemplate");

    }

    public static Connection getConnection() throws SQLException {

        return jdbcTemplate.getDataSource().getConnection();
    }


}
