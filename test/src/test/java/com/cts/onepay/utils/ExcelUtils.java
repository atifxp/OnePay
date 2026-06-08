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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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

    public static Object[][] getSheetData(String sheetName) {
        ws = wb.getSheet(sheetName);
        int lastRow = ws.getLastRowNum();
        int totalCells = ws.getRow(1).getLastCellNum();

        List<Object[]> rows = new ArrayList<>();
        for (int r = 1; r <= lastRow; r++) {
            XSSFRow row = ws.getRow(r);
            if (row == null) continue;

            Object[] rowData = new Object[totalCells];
            boolean blank = true;
            for (int c = 0; c < totalCells; c++) {
                String val = formatter.formatCellValue(row.getCell(c));
                rowData[c] = val;
                if (!val.trim().isEmpty()) blank = false;
            }
            if (!blank) rows.add(rowData);   // skip fully-empty rows
        }
        return rows.toArray(new Object[0][]);
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