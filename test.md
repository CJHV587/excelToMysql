```java
package com.aliyun.csc.sls.service.sqlsample;

import com.alibaba.common.lang.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.aliyun.csc.sls.context.sqlresult.OneSqlResult;
import com.aliyun.csc.sls.context.sqlresult.SqlQueryMetaData;
import com.aliyun.csc.sls.context.sqlresult.SqlQueryRequest;
import com.aliyun.csc.sls.context.sqlresult.SqlQueryResult;
import com.aliyun.csc.sls.service.ISqlService;
import com.aliyun.csc.sls.utils.DBUtils;
import com.aliyun.phoenix.api.common.response.PlainResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlServiceImpl implements ISqlService {
    private static final Logger logger = LoggerFactory.getLogger(SqlServiceImpl.class);
    @Autowired
    private DataSource druidPool;

    public PlainResult<SqlQueryResult> fuzzyList(SqlQueryRequest param) {
        PlainResult<SqlQueryResult> result = new PlainResult<SqlQueryResult>();
        SqlQueryResult sqRes = new SqlQueryResult();
        Connection conn = null;
        PreparedStatement ps;
        ResultSet rs;
        List<SqlQueryMetaData> data = new ArrayList<SqlQueryMetaData>();
        String sql = "SELECT id,title,author,category,`function` from sql_model\n" +
                "where\n" +
                "\ttitle LIKE concat( '%',?, '%' ) \n" +
                "\tor author LIKE concat( '%',?, '%' ) \n" +
                "\tor category LIKE concat( '%',?, '%' ) \n" +
                "\tor function LIKE concat( '%',?, '%' ) \n" +
                "\tlimit ?,?";
        String key = param.getQueryStr().replaceAll(" ", "");
        Integer pageNum = param.getPageNum();
        Integer pageSize = param.getPageSize();
        try {
            conn = druidPool.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, key);
            ps.setString(2, key);
            ps.setString(3, key);
            ps.setString(4, key);
            ps.setInt(5, (pageNum - 1) * pageSize);
            ps.setInt(6, pageNum * pageSize);
            rs = ps.executeQuery();
            while (rs.next()) {
                SqlQueryMetaData metaData = new SqlQueryMetaData();
                metaData.setId(rs.getInt(1));
                metaData.setTitle(rs.getString(2));
                metaData.setAuthor(rs.getString(3));
                metaData.setCategory(StringUtils.removeEnd(rs.getString(4), ","));
                metaData.setAssociationFunc(StringUtils.removeEnd(rs.getString(5), ","));
                data.add(metaData);
            }
            sqRes.setSuccess(true);
            sqRes.setData(JSONArray.parseArray(JSON.toJSONString(data)));
            sqRes.setTotalCount(fuzzyListTotalCount(param));
        } catch (SQLException e) {
            logger.error("Failed to get results from database；");
            sqRes.setSuccess(false);
            sqRes.setErrorMessage("databaseQueryError", e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    logger.error("Failed to close database connection；");
                }
            }
        }
        result.setData(sqRes);
        return result;
    }

    @Override
    public PlainResult<SqlQueryResult> listCategories() {
        SqlQueryResult categories = new SqlQueryResult();
        Connection conn = null;
        PreparedStatement ps;
        ResultSet rs;
        List<SqlQueryMetaData> data = new ArrayList<SqlQueryMetaData>();
        String sql = "SELECT `name`,picture\n" +
                "FROM\n" +
                "sql_category";
        try {
            conn = druidPool.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                SqlQueryMetaData metaData = new SqlQueryMetaData();
                metaData.setCategory(rs.getString(1));
                metaData.setPicture(rs.getString(2));
                data.add(metaData);
            }
            categories.setSuccess(true);
            categories.setData(JSONArray.parseArray(JSON.toJSONString(data)));
        } catch (SQLException e) {
            logger.error("Failed to get categories from database；");
            categories.setSuccess(false);
            categories.setErrorMessage("getCategoriesError", e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    logger.error("Failed to close database connection；");
                }
            }
        }
        return categories;
    }

    private Integer fuzzyListTotalCount(SqlQueryRequest param) {
        if (StringUtil.isBlank(param.getQueryStr())) {
            param.setQueryStr("");
        }
        Connection conn = null;
        PreparedStatement ps;
        ResultSet rs;
        String sql = "select count(t.id) from(" +
                "SELECT id,title,author,category,`function` from sql_model\n" +
                "where\n" +
                "\ttitle LIKE concat( '%',?, '%' ) \n" +
                "\tor author LIKE concat( '%',?, '%' ) \n" +
                "\tor category LIKE concat( '%',?, '%' ) \n" +
                "\tor function LIKE concat( '%',?, '%' )) as t";
        String key = param.getQueryStr().replaceAll(" ", "");
        int count = 0;
        try {
            conn = druidPool.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, key);
            ps.setString(2, key);
            ps.setString(3, key);
            ps.setString(4, key);
            rs = ps.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Failed to get total count from database；" + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    logger.error("Failed to close database connection；" + e.getMessage());
                }
            }
        }
        return count;
    }

    @Override
    public OneSqlResult listOneSqlById(SqlQueryRequest param) {
        Connection conn = null;
        OneSqlResult result = new OneSqlResult();
        PreparedStatement ps;
        ResultSet rs;
        String sql = "SELECT title,`sql`,author,category,detail,recommend,`function`\n" +
                "FROM \n" +
                "sql_model m LEFT JOIN sql_reference r\n" +
                "ON m.id=r.sql_id\n" +
                "WHERE id = ?";
        try {
            conn = druidPool.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, param.getId());
            rs = ps.executeQuery();
            while (rs.next()) {
                result.setTitle(rs.getString(1));
                result.setSql(rs.getString(2));
                result.setAuthor(rs.getString(3));
                result.setCategory(rs.getString(4));
                result.setDetail(rs.getString(5));
                result.setRecommend(rs.getFloat(6));
                result.setFuncLink(DBUtils.getLinkFunc(rs.getString(7)));
                result.setSuccess(true);
            }
        } catch (SQLException e) {
            logger.error("Failed to get result from database；" + e.getMessage());
            result.setSuccess(false);
            result.setErrorMessage("databaseQueryError", e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    logger.error("Failed to close database connection；" + e.getMessage());
                }
            }
        }
        return result;
    }

    public String listFuncByString(String str) {
        Connection conn = null;
        PreparedStatement ps;
        String key = str.trim();
        ResultSet rs;
        String resultStr = "";
        String sql = "SELECT CONCAT(`name`,':',CONCAT('\"',link,'\"'))\n" +
                "FROM sql_function\n" +
                "WHERE `name` LIKE CONCAT(?,'%')" +
                "ORDER BY id";
        try {
            conn = druidPool.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, key);
            rs = ps.executeQuery();
            while (rs.next()) {
                resultStr = rs.getString(1);
                break;
            }
        } catch (SQLException e) {
            logger.error("Failed to get correlation function from database；" + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    logger.error("Failed to close database connection；" + e.getMessage());
                }
            }
        }
        return resultStr;
    }

    @Override
    public SqlQueryResult listAllFuncCategories() {
        Connection conn = null;
        List<String> funcCategories = new ArrayList<String>();
        String sql = "SELECT DISTINCT(func_category)\n" +
                "FROM\n" +
                "sql_function";
        PreparedStatement ps;
        ResultSet rs;
        SqlQueryResult result = new SqlQueryResult();
        try {
            conn = druidPool.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                funcCategories.add(rs.getString(1));
            }
            result.setFuncCategories(JSONArray.parseArray(JSON.toJSONString(funcCategories)));
            result.setSuccess(true);
        } catch (SQLException e) {
            logger.error("Failed to get function categories from database；");
            result.setSuccess(false);
            result.setErrorMessage("error", e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    logger.error("Failed to close database connection；");
                }
            }
        }
        return result;
    }

    @Override
    public SqlQueryResult listAllFuncNamesByCategory(SqlQueryRequest param) {
        Connection conn = null;
        String key = param.getQueryStr();
        List<String> funcNames = new ArrayList<String>();
        String sql = "SELECT `name`\n" +
                "FROM\n" +
                "sql_function\n" +
                "WHERE func_category = ?";
        PreparedStatement ps;
        ResultSet rs;
        SqlQueryResult result = new SqlQueryResult();
        try {
            conn = druidPool.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, key);
            rs = ps.executeQuery();
            while (rs.next()) {
                funcNames.add(rs.getString(1));
            }
            result.setFuncNames(JSONArray.parseArray(JSON.toJSONString(funcNames)));
            result.setSuccess(true);
        } catch (SQLException e) {
            logger.error("Failed to get function names from database；");
            result.setSuccess(false);
            result.setErrorMessage("databaseQueryError", e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    logger.error("Failed to close database connection；");
                }
            }
        }
        return result;
    }

    @Override
    public SqlQueryResult listSqlByFuncName(SqlQueryRequest param) {
        SqlQueryResult sqRes = new SqlQueryResult();
        Connection conn = null;
        PreparedStatement ps;
        ResultSet rs;
        List<SqlQueryMetaData> data = new ArrayList<SqlQueryMetaData>();
        String sql = "SELECT id,title,author,category,`function` from sql_model\n" +
                "where function LIKE concat( '%',?,',','%' )\n" +
                "limit ?,?";
        String key = DBUtils.getFuncName(param.getQueryStr().replaceAll(" ", ""));
        Integer pageNum = param.getPageNum();
        Integer pageSize = param.getPageSize();
        try {
            conn = druidPool.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, key);
            ps.setInt(2, (pageNum - 1) * pageSize);
            ps.setInt(3, pageNum * pageSize);
            rs = ps.executeQuery();
            while (rs.next()) {
                SqlQueryMetaData metaData = new SqlQueryMetaData();
                metaData.setId(rs.getInt(1));
                metaData.setTitle(rs.getString(2));
                metaData.setAuthor(rs.getString(3));
                metaData.setCategory(StringUtils.removeEnd(rs.getString(4), ","));
                metaData.setAssociationFunc(StringUtils.removeEnd(rs.getString(5), ","));
                data.add(metaData);
            }
            sqRes.setSuccess(true);
            sqRes.setData(JSONArray.parseArray(JSON.toJSONString(data)));
            sqRes.setPageSize(pageSize);
            sqRes.setPageNumber(pageNum);
            sqRes.setTotalCount(listSqlByFuncNameTotalCount(key));
        } catch (SQLException e) {
            logger.error("Failed to get results from database；");
            sqRes.setSuccess(false);
            sqRes.setErrorMessage("error", e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    logger.error("Failed to close database connection；");
                }
            }
        }
        return sqRes;
    }

    private Integer listSqlByFuncNameTotalCount(String funcName) {
        Connection conn = null;
        PreparedStatement ps;
        ResultSet rs;
        int i = 0;
        List<SqlQueryMetaData> data = new ArrayList<SqlQueryMetaData>();
        String sql = "select count(t.id) from (" +
                "SELECT id,title,author,category,`function` from sql_model\n" +
                "where function LIKE concat( '%',?,',','%' )) as t";
        try {
            conn = druidPool.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, funcName);
            rs = ps.executeQuery();
            while (rs.next()) {
                i = rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("Failed to get result num from database；");
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    logger.error("Failed to close database connection；");
                }
            }
        }
        return i;
    }
}
```