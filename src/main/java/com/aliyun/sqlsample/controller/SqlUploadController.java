package com.aliyun.sqlsample.controller;

import com.aliyun.sqlsample.service.ImportMysql;
import com.aliyun.sqlsample.util.MultipartFileToFile;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Controller
public class SqlUploadController {
    @Autowired
    ImportMysql importMysql;

    @RequestMapping("/upload")
    public String upload(@RequestParam("fileName") MultipartFile file) {
        File tempFile = null;
        if (file.isEmpty()) {
            System.err.println("File is not allow Empty");
            return "error";
        }
        try {
            tempFile = MultipartFileToFile.multipartFileToFile(file);
            importMysql.importToMysql(tempFile);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "error";
        }
        return "success";
    }
}
