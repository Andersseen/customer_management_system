package aplication.service;

import aplication.controller.ExcelFileController;
import aplication.module.VO.CustomerVO;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.sql.Date;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;


public class ExportTaskService extends Service<XSSFSheet> {
    private final XSSFWorkbook workbook;
    private final ArrayList<CustomerVO> list;
    private final int numberOfTitles = 9;

    public ExportTaskService(ArrayList<CustomerVO> list, XSSFWorkbook workbook) {
        this.list = list;
        this.workbook = workbook;
    }

    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected XSSFSheet call() throws Exception {
                XSSFSheet sheet = workbook.createSheet();
                Row titleRow = sheet.createRow(1);
                Row headerRow = sheet.createRow(4);

                ExcelFileController excelFileController = new ExcelFileController();

                excelFileController.printTitle(workbook,sheet,titleRow);
                excelFileController.makeHeaderRow(workbook,headerRow);

                AtomicInteger index = new AtomicInteger(5);
                list.forEach(client -> {

                    String birthday = "";
                    String date = "";

                    //Converting the Date object to String format
                    if (client.getDate() != null) {
                        Date dateObj = client.getDate();
                        date = dateObj.toString();
                    }
                    if (client.getBirthday() != null) {
                        Date birthdayObj = client.getBirthday();
                        birthday = birthdayObj.toString();
                    }
                    Row row = sheet.createRow(index.get());
                    row.createCell(0).setCellValue(String.valueOf(client.getId()));
                    row.createCell(1).setCellValue(String.valueOf(client.getName()));
                    row.createCell(2).setCellValue(String.valueOf(client.getLastName()));
                    row.createCell(3).setCellValue(String.valueOf(client.getSex()));
                    row.createCell(4).setCellValue(birthday);
                    row.createCell(5).setCellValue(String.valueOf(client.getPhone()));
                    row.createCell(6).setCellValue(String.valueOf(client.getEmail()));
                    row.createCell(7).setCellValue(String.valueOf(client.getNote()));
                    row.createCell(8).setCellValue(date);

                    index.getAndIncrement();
                });
                for(int i = 0; i < numberOfTitles; i++) {
                    sheet.autoSizeColumn(i);
                }
               return sheet;
            }
        };
    }

}