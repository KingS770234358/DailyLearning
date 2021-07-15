package com.wq.exporttableinfofromdb.service;

import com.wq.exporttableinfofromdb.dao.TableStructureInfoDao;
import com.wq.exporttableinfofromdb.pojo.TableStructureInfo;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Service
public class TableStructureInfoService {

    @Autowired
    private TableStructureInfoDao tableStructureInfoDao;

    private static final List<String> HEADERS
            = Arrays.asList(new String[]{"列名", "字段类型", "数据类型",  "长度", "是否可为空", "默认值", "备注"});
    private static final String FILENAME_PREFIX = "D:\\上海银行\\db2excels\\";
    private static final String FILENAME_SUFFIX = ".xls";


    public void getAllTableExcel(List<String> databases){
        databases.forEach(
                dbName -> {
                    List<String> tableNames = getAllTablesByDBName(dbName);
                    tableNames.forEach(
                        tableName -> {
                            getTableStrucureInfo(dbName, tableName);
                        }
                    );
                }
        );
    }

    public List<String> getAllTablesByDBName(String dbName){
        List<String> tableNames = tableStructureInfoDao.getAllTablesByDBName(dbName);
        for (String tableName : tableNames) {
            System.out.println(tableName);
        }
        return tableNames;
    }

    public void getAllDB(){
        List<String> dbNames = tableStructureInfoDao.getAllDB();
        for (String dbName : dbNames) {
            System.out.println(dbName);
        }
    }

    public void getTableStrucureInfo(String dbName, String tbName){

        List<TableStructureInfo> tableStructureInfoList = tableStructureInfoDao.getColumnInfoByTableName(dbName, tbName);
        for (TableStructureInfo tableStructureInfo : tableStructureInfoList) {
            System.out.println(tableStructureInfo);
        }

        // 1. 创建HSSFWorkbook对象
        HSSFWorkbook wb = new HSSFWorkbook();
        // 2. 创建HSSFSheet对象
        HSSFSheet sheet = wb.createSheet("sheet0");
        // 3. 创建HSSFRow对象
        HSSFRow headers = sheet.createRow(0);
        // 4. 设置表头
        for(int i = 0; i < HEADERS.size(); i++){
            //创建HSSFCell对象
            HSSFCell cell = headers.createCell(i);
            //设置单元格的值
            cell.setCellValue(HEADERS.get(i));
        }
        // 5. 表体
        for(int i = 0; i < tableStructureInfoList.size(); i++){
            HSSFRow row =  sheet.createRow(i+1);
            Field[] fields = TableStructureInfo.class.getDeclaredFields();
            for(int j = 0; j < fields.length; j++){
                fields[j].setAccessible(true);
                HSSFCell cell = row.createCell(j);
                try {
                    cell.setCellValue( (String) fields[j].get(tableStructureInfoList.get(i)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        output2Excel(dbName, tbName, wb);
    }

    //输出Excel文件
    public void output2Excel(String dbName, String tbName, HSSFWorkbook wb){
        File outputFile = new File( FILENAME_PREFIX + dbName + "-" + tbName + FILENAME_SUFFIX);
        if(!outputFile.exists()){
            try {
                outputFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream output= null;
        try {
            output = new FileOutputStream(outputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            wb.write(output);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
