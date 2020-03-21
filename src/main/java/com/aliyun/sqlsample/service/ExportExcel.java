package com.aliyun.sqlsample.service;

import com.aliyun.sqlsample.entity.Category;
import com.aliyun.sqlsample.entity.Function;
import com.aliyun.sqlsample.entity.Model;
import com.aliyun.sqlsample.entity.Reference;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.util.List;

public class ExportExcel {
    public void exportCategories() {
        try {
            WritableWorkbook wwb = null;
            // 创建可写入的Excel工作簿
            String fileName = "src/main/resources/download/temp_category.xls";
            File file=new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            //以fileName为文件名来创建一个Workbook
            wwb = Workbook.createWorkbook(file);
            // 创建工作表
            WritableSheet ws = wwb.createSheet("SQL类别信息", 0);
            //查询数据库中所有的数据
            List<Category> list= TableService.getCategoriesFromDB();
            //要插入到的Excel表格的行号，默认从0开始
            Label id= new Label(0, 0, "id");//表示第
            Label name= new Label(1, 0, "name");
            Label detail= new Label(2, 0, "detail");
            Label picture= new Label(3, 0, "picture");
            ws.addCell(id);
            ws.addCell(name);
            ws.addCell(detail);
            ws.addCell(picture);
            for (int i = 0; i < list.size(); i++) {
                Label id_i= new Label(0, i+1, list.get(i).getId());
                Label name_i= new Label(1, i+1, list.get(i).getName());
                Label detail_i= new Label(2, i+1, list.get(i).getDetail());
                Label picture_i= new Label(3, i+1, list.get(i).getPicture());
                ws.addCell(id_i);
                ws.addCell(name_i);
                ws.addCell(detail_i);
                ws.addCell(picture_i);
            }
            //写进文档
            wwb.write();
            // 关闭Excel工作簿对象
            wwb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void exportModels() {
        try {
            WritableWorkbook wwb = null;
            // 创建可写入的Excel工作簿
            String fileName = "src/main/resources/download/temp_model.xls";
            File file=new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            wwb = Workbook.createWorkbook(file);
            WritableSheet ws = wwb.createSheet("sql_model", 0);
            List<Model> list= TableService.getModelsFromDB();
            //要插入到的Excel表格的行号，默认从0开始
            Label id= new Label(0, 0, "id");//表示第
            Label sql= new Label(1, 0, "sql");
            Label title= new Label(2, 0, "title");
            Label detail= new Label(3, 0, "detail");
            Label author= new Label(4, 0, "author");
            Label picture= new Label(5, 0, "picture");
            Label logSample= new Label(6, 0, "log_sample");
            Label indexConfig= new Label(7, 0, "index_config");
            Label category= new Label(8, 0, "category");
            Label function= new Label(9, 0, "function");
            Label version= new Label(10, 0, "version");
            Label deleteFlag= new Label(11, 0, "delete_flag");
            Label createTime= new Label(12, 0, "c_time");
            ws.addCell(id);
            ws.addCell(sql);
            ws.addCell(title);
            ws.addCell(detail);
            ws.addCell(author);
            ws.addCell(picture);
            ws.addCell(logSample);
            ws.addCell(indexConfig);
            ws.addCell(category);
            ws.addCell(function);
            ws.addCell(version);
            ws.addCell(deleteFlag);
            ws.addCell(createTime);
            for (int i = 0; i < list.size(); i++) {
                Label id_i = new Label(0, i + 1, list.get(i).getId());
                Label sql_i = new Label(1, i + 1, list.get(i).getSql());
                Label title_i = new Label(2, i + 1, list.get(i).getTitle());
                Label detail_i = new Label(3, i + 1, list.get(i).getDetail());
                Label author_i = new Label(4, i + 1, list.get(i).getAuthor());
                Label picture_i = new Label(5, i + 1, list.get(i).getPicture());
                Label logSample_i = new Label(6, i + 1, list.get(i).getLogSample());
                Label indexConfig_i = new Label(7, i + 1, list.get(i).getIndexConfig());
                Label category_i = new Label(8, i + 1, list.get(i).getCategory());
                Label function_i = new Label(9, i + 1, list.get(i).getFunction());
                Label version_i = new Label(10, i + 1, list.get(i).getVersion());
                Label deleteFlag_i = new Label(11, i + 1, list.get(i).getDeleteFlag());
                Label createTime_i = new Label(12, i + 1, list.get(i).getCreateTime());
                ws.addCell(id_i);
                ws.addCell(sql_i);
                ws.addCell(title_i);
                ws.addCell(author_i);
                ws.addCell(detail_i);
                ws.addCell(picture_i);
                ws.addCell(logSample_i);
                ws.addCell(indexConfig_i);
                ws.addCell(category_i);
                ws.addCell(function_i);
                ws.addCell(version_i);
                ws.addCell(deleteFlag_i);
                ws.addCell(createTime_i);
            }
            //写进文档
            wwb.write();
            // 关闭Excel工作簿对象
            wwb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void exportReferences() {
        try {
            WritableWorkbook wwb = null;
            // 创建可写入的Excel工作簿
            String fileName = "src/main/resources/download/temp_reference.xls";
            File file=new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            wwb = Workbook.createWorkbook(file);
            WritableSheet ws = wwb.createSheet("sql_reference", 0);
            List<Reference> list= TableService.getReferencesFromDB();
            Label id= new Label(0, 0, "sql_id");
            Label count= new Label(1, 0, "count");
            Label recommend= new Label(2, 0, "recommend");
            ws.addCell(id);
            ws.addCell(count);
            ws.addCell(recommend);
            for (int i = 0; i < list.size(); i++) {
                Label id_i = new Label(0, i + 1, list.get(i).getId());
                Label count_i = new Label(1, i + 1, list.get(i).getCount());
                Label recommend_i = new Label(2, i + 1, list.get(i).getRecommend());
                ws.addCell(id_i);
                ws.addCell(count_i);
                ws.addCell(recommend_i);
            }
            //写进文档
            wwb.write();
            // 关闭Excel工作簿对象
            wwb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void exportFunctions() {
        try {
            WritableWorkbook wwb = null;
            String fileName = "src/main/resources/download/temp_function.xls";
            File file=new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            wwb = Workbook.createWorkbook(file);
            WritableSheet ws = wwb.createSheet("sql_function", 0);
            List<Function> list= TableService.getFunctionsFromDB();
            Label id= new Label(0, 0, "id");//表示第
            Label name= new Label(1, 0, "name");
            Label funcCategory= new Label(2, 0, "func_category");
            Label detail= new Label(3, 0, "detail");
            Label link= new Label(4, 0, "link");
            Label count= new Label(5, 0, "count");
            ws.addCell(id);
            ws.addCell(name);
            ws.addCell(funcCategory);
            ws.addCell(detail);
            ws.addCell(link);
            ws.addCell(count);
            for (int i = 0; i < list.size(); i++) {
                Label id_i = new Label(0, i + 1, list.get(i).getId());
                Label name_i = new Label(1, i + 1, list.get(i).getName());
                Label funcCategory_i = new Label(2, i + 1, list.get(i).getFunctionCategory());
                Label detail_i = new Label(3, i + 1, list.get(i).getDetail());
                Label link_i = new Label(4, i + 1, list.get(i).getLink());
                Label count_i = new Label(5, i + 1, list.get(i).getCount());
                ws.addCell(id_i);
                ws.addCell(name_i);
                ws.addCell(funcCategory_i);
                ws.addCell(detail_i);
                ws.addCell(link_i);
                ws.addCell(count_i);
            }
            wwb.write();
            wwb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
