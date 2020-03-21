package com.aliyun.sqlsample;

import com.aliyun.sqlsample.service.ExportExcel;
import org.junit.Test;

public class MyTest {
    @Test
    public void testExport(){
        ExportExcel source = new ExportExcel();
        //source.exportCategories();
        //source.exportModels();
        //source.exportReferences();
        source.exportFunctions();
    }
}
