package com.cts.onepay.utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;


public final class ExcelUtils {

    private static final String DEFAULT_FILE_PATH = System.getProperty("user.dir") + ConfigReader.get("datafile.directory") + ConfigReader.get("datafile.name");
    private static FileInputStream fi;
    private static FileOutputStream fo;
    private static XSSFWorkbook wb;
    private static XSSFSheet ws;
    private static XSSFRow row;
    private static XSSFCell cell;
    private static DataFormatter formatter = new DataFormatter();

    private ExcelUtils() {}

    static {
        try {
            fi = new FileInputStream(DEFAULT_FILE_PATH);
            wb = new XSSFWorkbook(fi);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load workbook: " + DEFAULT_FILE_PATH, e);
        }
    }

    public static Object[][] getSheetData(String sheetName){
        Object[][] data;

            ws = wb.getSheet(sheetName);

            int totalRows = ws.getLastRowNum();
            int totalCells = ws.getRow(1).getLastCellNum();

            data = new Object[totalRows-1][totalCells];

            //loop through the cells
            for (int r = 1; r < totalRows ; r++) {

                row = ws.getRow(r);

                for (int c = 0; c < totalCells; c++) {      //as column no. starts from 1 in excel
                    cell = row.getCell(c);
                    data[r-1][c] = formatter.formatCellValue(cell);
                }
            }


            return data;



    }

    public static void close() {
        try {
            wb.close();
        } catch (IOException ignored) {}
    }

//    public static void main(String[] args) {
//        System.out.println(Arrays.deepToString(getSheetData("RegisterData")));
//    }

}