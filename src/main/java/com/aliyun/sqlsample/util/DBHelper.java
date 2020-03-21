package com.aliyun.sqlsample.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBHelper {
    Connection con = null;
    ResultSet res = null;
    PreparedStatement pst = null;

    // 查询
    public ResultSet Search(Connection con, String sql, String str[]) {
        try {
            pst = con.prepareStatement(sql);
            if (str != null) {
                for (int i = 0; i < str.length; i++) {
                    pst.setString(i + 1, str[i]);
                }
            }
            res = pst.executeQuery();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }


    //增删修改
    public int AddU(String sql, String str[]) {
        int a = 0;
        con = JdbcUtils.getConnection();
        try {
            pst = con.prepareStatement(sql);
            if (str != null) {
                for (int i = 0; i < str.length; i++) {
                    pst.setString(i + 1, str[i]);
                }
            }
            a = pst.executeUpdate();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            JdbcUtils.close(con, pst, null);
        }
        return a;
    }
}
