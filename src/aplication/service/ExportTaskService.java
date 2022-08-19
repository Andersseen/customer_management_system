package aplication.service;

import aplication.controller.FileController;
import aplication.module.VO.CustomerVO;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.sql.Date;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;


public class ExportTaskService extends Service<XSSFSheet> {
    private XSSFWorkbook workbook;
    private ArrayList<CustomerVO> list;

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

                FileController fileController = new FileController();

                fileController.printTitle(workbook,sheet,titleRow);
                fileController.makeHeaderRow(workbook,headerRow);

                AtomicInteger index = new AtomicInteger(5);
                list.forEach(client -> {
                    // parse to string
                    String id = "";
                    String name = "";
                    String lastName = "";
                    String phone = "";
                    String email = "";
                    String sex = "";
                    String note = "";
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
                    row.createCell(0).setCellValue(id.valueOf(client.getId()));
                    row.createCell(1).setCellValue(name.valueOf(client.getName()));
                    row.createCell(2).setCellValue(lastName.valueOf(client.getLastName()));
                    row.createCell(3).setCellValue(sex.valueOf(client.getSex()));
                    row.createCell(4).setCellValue(birthday);
                    row.createCell(5).setCellValue(phone.valueOf(client.getPhone()));
                    row.createCell(6).setCellValue(email.valueOf(client.getEmail()));
                    row.createCell(7).setCellValue(note.valueOf(client.getNote()));
                    row.createCell(8).setCellValue(date);

                    index.getAndIncrement();
                });
                for(int i = 0; i < 9; i++) {
                    sheet.autoSizeColumn(i);
                }
               return sheet;
            }
        };
    }

}