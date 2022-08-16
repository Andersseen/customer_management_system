package aplication.service;

import aplication.controller.FileController;
import aplication.module.VO.CustomerVO;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.sql.Date;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;


public class ExportTaskService extends Service<XSSFSheet> {
    private XSSFWorkbook workbook;
    private ArrayList<CustomerVO> list;
    final private String[] excelColumns = {"ID",  "Nombre", "Apellido", "Sexo", "Cumpleaños", "Email", "Telefono", "Nota", "Fecha"};

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
                Row headerRow = sheet.createRow(0);

                FileController fileController = new FileController();
                Font headerFont = fileController.setFontToFile(workbook);

                CellStyle headerCellStyle = workbook.createCellStyle();
                headerCellStyle.setFont(headerFont);

                for(int i =0; i < excelColumns.length; i++){
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(excelColumns[i]);
                    cell.setCellStyle(headerCellStyle);
                }

                AtomicInteger index = new AtomicInteger(1);
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
                    row.createCell(0).setCellValue(client.getId());
                    row.createCell(1).setCellValue(client.getName());
                    row.createCell(2).setCellValue(client.getLastName());
                    row.createCell(3).setCellValue(client.getSex());
                    row.createCell(4).setCellValue(birthday);
                    row.createCell(5).setCellValue(client.getEmail());
                    row.createCell(6).setCellValue(client.getPhone());
                    row.createCell(7).setCellValue(client.getNote());
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