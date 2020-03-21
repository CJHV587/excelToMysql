package com.aliyun.sqlsample.service;

import com.aliyun.sqlsample.entity.Category;
import com.aliyun.sqlsample.entity.Function;
import com.aliyun.sqlsample.entity.Model;
import com.aliyun.sqlsample.entity.Reference;
import com.aliyun.sqlsample.util.DBHelper;
import com.aliyun.sqlsample.util.JdbcUtils;
import com.aliyun.sqlsample.util.MultipartFileToFile;
import jxl.Sheet;
import jxl.Workbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TableService {
    private static ResultSet rs = null;
    private static Connection con = null;

    public static List<Category> getCategoriesFromDB() {
        con = JdbcUtils.getConnection();
        List<Category> list = new ArrayList<Category>();
        try {
            DBHelper db = new DBHelper();
            String sql = "select * from sql_category";
            rs = db.Search(con, sql, null);
            while (rs.next()) {

                String id = rs.getString("id");
                String name = rs.getString("name");
                String detail = rs.getString("detail");
                String picture = rs.getString("picture");
                list.add(new Category(id, name, detail, picture));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(null, null, rs);
        }
        return list;
    }

    public static List<Model> getModelsFromDB() {
        con = JdbcUtils.getConnection();
        List<Model> list = new ArrayList<Model>();
        try {
            DBHelper db = new DBHelper();
            String sql = "select * from sql_model";
            rs = db.Search(con, sql, null);
            while (rs.next()) {
                String id = rs.getString("id");
                String sql_t = rs.getString("sql");
                String title = rs.getString("title");
                String detail = rs.getString("detail");
                String author = rs.getString("author");
                String picture = rs.getString("picture");
                String logSample = rs.getString("log_sample");
                String indexConfig = rs.getString("index_config");
                String category = rs.getString("category");
                String function = rs.getString("function");
                String version = rs.getString("version");
                String deleteFlag = rs.getString("delete_flag");
                String createTime = rs.getString("c_time");
                list.add(new Model(id, sql_t, title, detail, author, picture, logSample, indexConfig, category, function, version, deleteFlag, createTime));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(null, null, rs);
        }
        return list;
    }

    public static List<Reference> getReferencesFromDB() {
        con = JdbcUtils.getConnection();
        List<Reference> list = new ArrayList<Reference>();
        try {
            DBHelper db = new DBHelper();
            String sql = "select * from sql_reference";
            rs = db.Search(con, sql, null);
            while (rs.next()) {
                String id = rs.getString("sql_id");
                String count = rs.getString("count");
                String recommend = rs.getString("recommend");
                list.add(new Reference(id, count, recommend));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(null, null, rs);
        }
        return list;
    }

    public static List<Function> getFunctionsFromDB() {
        con = JdbcUtils.getConnection();
        List<Function> list = new ArrayList<Function>();
        try {
            DBHelper db = new DBHelper();
            String sql = "select * from sql_function";
            rs = db.Search(con, sql, null);
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String funcCategory = rs.getString("func_category");
                String detail = rs.getString("detail");
                String link = rs.getString("link");
                String count = rs.getString("count");
                list.add(new Function(id, name, funcCategory, detail, link, count));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(null, null, rs);
        }
        return list;
    }

    /**
     * 查询指定目录中excel中所有的数据
     * @param file 文件完整路径
     * @return
     */
    public List<Category> getAllCategoryByExcel(File file) {
        List<Category> list = new ArrayList<Category>();
        try {
            Workbook rwb = Workbook.getWorkbook(file);
            MultipartFileToFile.delteTempFile(file);
            Sheet rs = rwb.getSheet(0);
            int clos = rs.getColumns();
            int rows = rs.getRows();
            for (int i = 1; i < rows; i++) {
                for (int j = 0; j < clos; j++) {
                    // 第一个是列数，第二个是行数
                    String id = rs.getCell(j++, i).getContents();// 默认最左边编号也算一列
                    if (id == null || "".equals(id)) id = "0";
                    // 所以这里得j++
                    String name = rs.getCell(j++, i).getContents();
                    String detail = rs.getCell(j++, i).getContents();
                    String picture = rs.getCell(j++, i).getContents();
                    list.add(new Category(id, name,
                            detail, picture));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Reference> getAllReferenceByExcel(File file) {
        List<Reference> list = new ArrayList<Reference>();
        try {
            Workbook rwb = Workbook.getWorkbook(file);
            MultipartFileToFile.delteTempFile(file);
            Sheet rs = rwb.getSheet(0);
            int clos = rs.getColumns();
            int rows = rs.getRows();
            for (int i = 1; i < rows; i++) {
                for (int j = 0; j < clos; j++) {
                    String id = rs.getCell(j++, i).getContents();
                    if (id == null || "".equals(id)) id = "0";
                    String count = rs.getCell(j++, i).getContents();
                    String recommond = rs.getCell(j++, i).getContents();
                    list.add(new Reference(id, count,
                            recommond));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Function> getAllFunctionByExcel(File file) {
        List<Function> list = new ArrayList<Function>();
        try {
            Workbook rwb = Workbook.getWorkbook(file);
            MultipartFileToFile.delteTempFile(file);
            Sheet rs = rwb.getSheet(0);
            int clos = rs.getColumns();
            int rows = rs.getRows();
            for (int i = 1; i < rows; i++) {
                for (int j = 0; j < clos; j++) {
                    String id = rs.getCell(j++, i).getContents();
                    if (id == null || "".equals(id)) id = "0";
                    String name = rs.getCell(j++, i).getContents();
                    String functionCategory = rs.getCell(j++, i).getContents();
                    String detail = rs.getCell(j++, i).getContents();
                    String link = rs.getCell(j++, i).getContents();
                    String count = rs.getCell(j++, i).getContents();
                    list.add(new Function(id, name, functionCategory, detail, link, count));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Model> getAllModelByExcel(File file) {
        List<Model> list = new ArrayList<Model>();
        try {
            Workbook rwb = Workbook.getWorkbook(file);
            MultipartFileToFile.delteTempFile(file);
            Sheet rs = rwb.getSheet(0);
            int clos = rs.getColumns();
            int rows = rs.getRows();
            for (int i = 1; i < rows; i++) {
                for (int j = 0; j < clos; j++) {
                    String id = rs.getCell(j++, i).getContents();
                    if (id == null || "".equals(id)) id = "0";
                    String sql = rs.getCell(j++, i).getContents();
                    String title = rs.getCell(j++, i).getContents();
                    String detail = rs.getCell(j++, i).getContents();
                    String author = rs.getCell(j++, i).getContents();
                    String picture = rs.getCell(j++, i).getContents();
                    String logSample = rs.getCell(j++, i).getContents();
                    String indexConfig = rs.getCell(j++, i).getContents();
                    String category = rs.getCell(j++, i).getContents();
                    String function = rs.getCell(j++, i).getContents();
                    String version = rs.getCell(j++, i).getContents();
                    String deleteFlag = rs.getCell(j++, i).getContents();
                    String createTime = rs.getCell(j++, i).getContents();
                    list.add(new Model(id, sql, title, detail, author, picture, logSample, indexConfig, category, function, version, deleteFlag, createTime));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 通过Id判断是否存在
     * @param id
     * @return
     */
    public static boolean isExist(String table, String id) {
        con = JdbcUtils.getConnection();
        String sql = null;
        if (table.contains("reference")) {
            sql = "select * from " + table + " where sql_id=?";
        } else {
            sql = "select * from " + table + " where id=?";
        }
        ResultSet rs = null;
        try {
            DBHelper db = new DBHelper();
            rs = db.Search(con, sql, new String[]{id});
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(con, null, null);
        }
        return false;
    }
}
