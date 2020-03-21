package com.aliyun.sqlsample.service;

import com.aliyun.sqlsample.entity.Category;
import com.aliyun.sqlsample.entity.Function;
import com.aliyun.sqlsample.entity.Model;
import com.aliyun.sqlsample.entity.Reference;
import com.aliyun.sqlsample.util.DBHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class ImportMysql {
    @Autowired
    TableService tableService;

    public void importToMysql(File file) {
        String name = file.getName();
        if (name.contains("category")) {
            importToCategory(file);
        } else if (name.contains("reference")) {
            importToReference(file);
        } else if (name.contains("function")) {
            importToFunction(file);
        } else if (name.contains("model")) {
            importToModel(file);
        } else {
            throw new RuntimeException("file name is illegal");
        }
    }

    private void importToCategory(File file) {
        //得到表格中所有的数据
        List<Category> listExcel = tableService.getAllCategoryByExcel(file);
        DBHelper db = new DBHelper();
        for (Category category : listExcel) {
            String id = category.getId();
            if (id == null || !tableService.isExist("sql_category", id)) {
                //不存在就添加
                String sql = "insert into sql_category (id,name,detail,picture) values(?,?,?,?)";
                String[] str = new String[]{id, category.getName(), category.getDetail(), category.getPicture()};
                db.AddU(sql, str);
            } else {
                //存在就更新
                String sql = "update sql_category set name=?,detail=?,picture=? where id=?";
                String[] str = new String[]{category.getName(), category.getDetail(), category.getPicture(), id};
                db.AddU(sql, str);
            }
        }
    }

    private void importToReference(File file) {
        List<Reference> listExcel = tableService.getAllReferenceByExcel(file);
        DBHelper db = new DBHelper();
        for (Reference reference : listExcel) {
            String id = reference.getId();
            if (id == null || !tableService.isExist("sql_reference", id)) {
                String sql = "insert into sql_reference (sql_id,count,recommend) values(?,?,?)";
                String[] str = new String[]{id, reference.getCount(), reference.getRecommend()};
                db.AddU(sql, str);
            } else {
                String sql = "update sql_reference set count=?,recommend=? where sql_id=?";
                String[] str = new String[]{reference.getCount(), reference.getRecommend(), id};
                db.AddU(sql, str);
            }
        }
    }

    private void importToFunction(File file) {
        List<Function> listExcel = tableService.getAllFunctionByExcel(file);
        DBHelper db = new DBHelper();
        for (Function function : listExcel) {
            String id = function.getId();
            if (id == null || !tableService.isExist("sql_function", id)) {
                String sql = "insert into sql_function (id,name,func_category,detail,link,count) values(?,?,?,?,?,?)";
                String[] str = new String[]{id, function.getName(), function.getFunctionCategory(), function.getDetail(), function.getLink(), function.getCount()};
                db.AddU(sql, str);
            } else {
                String sql = "update sql_function set name=?,func_category=?,detail=?,link=?,count=? where id=?";
                String[] str = new String[]{function.getName(), function.getFunctionCategory(), function.getDetail(), function.getLink(), function.getCount(), id};
                db.AddU(sql, str);
            }
        }
    }

    private void importToModel(File file) {
        List<Model> listExcel = tableService.getAllModelByExcel(file);
        DBHelper db = new DBHelper();
        for (Model model : listExcel) {
            String id = model.getId();
            if (id == null || !tableService.isExist("sql_model", id)) {
                String sql = "insert into sql_model (id,`sql`,title,detail,author,picture,log_sample,index_config,category,`function`,version,delete_flag,c_time) " +
                        "values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
                String[] str = new String[]{id, model.getSql(), model.getTitle(), model.getDetail(), model.getAuthor(),
                        model.getPicture(), model.getLogSample(), model.getIndexConfig(), model.getCategory(),
                        model.getFunction(), model.getVersion(), model.getDeleteFlag(), model.getCreateTime()};
                db.AddU(sql, str);
            } else {
                String sql = "update sql_model set `sql`=?,title=?,detail=?,author=?," +
                        "picture=?,log_sample=?,index_config=?,category=?," +
                        "`function`=?,version=?,delete_flag=?,c_time=? where id=?";
                String[] str = new String[]{model.getSql(), model.getTitle(), model.getDetail(), model.getAuthor(),
                        model.getPicture(), model.getLogSample(), model.getIndexConfig(), model.getCategory(),
                        model.getFunction(), model.getVersion(), model.getDeleteFlag(), model.getCreateTime(), id};
                db.AddU(sql, str);
            }
        }
    }
}