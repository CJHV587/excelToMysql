package com.aliyun.sqlsample.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
    private static DataSource ds;

    static {
        Properties p = new Properties();
        try {
            p.load(JdbcUtils.class.getClassLoader().getResourceAsStream("druid.properties"));
            ds = DruidDataSourceFactory.createDataSource(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void close(Connection connection, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}