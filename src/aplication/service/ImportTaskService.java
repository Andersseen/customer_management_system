package aplication.service;


import aplication.controller.CustomerController;
import aplication.controller.DateController;
import aplication.controller.FileController;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImportTaskService extends Service<Void> {
    private XSSFSheet customerSheet;

    public ImportTaskService(XSSFSheet customerSheet) {
        this.customerSheet = customerSheet;
    }

    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected Void call() throws Exception {
                Row row;
                if (customerSheet != null) {
                    for (int i = 5; i < customerSheet.getLastRowNum(); i++) {
                        row = customerSheet.getRow(i);
                        if(row != null) {
                            java.sql.Date birthday = null;
                            java.sql.Date date = null;
                            String sex = "";
                            String phone = "";
                            if (row.getCell(3) != null) {
                                try {
                                    String sexInput = String.valueOf(row.getCell(3));
                                    if(sexInput.length() > 1 ){
                                        // capitalize first letter
                                        String sexOutput = sexInput.substring(0, 1).toUpperCase() + sexInput.substring(1);
                                        if (sexOutput.equals("Hombre") || sexOutput.equals("Mujer")) {
                                            sex = sexOutput;
                                        }else{
                                            sex = "";
                                        }
                                    } else{
                                        sex = "";
                                    }

                                } catch (Exception ex) {
                                    Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                sex = "";
                            }
                            if (row.getCell(4) != null) {
                                String dateString = row.getCell(4).toString();
                                if (dateString != "") {
                                    try {
                                        DateController dateController = new DateController();
                                        birthday = dateController.tryParse(dateString);
                                    } catch (Exception ex) {
                                        birthday = null;
                                        Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                            if (row.getCell(5) != null) {
                                if (row.getCell(5).getNumericCellValue() != 0) {
                                    try {
                                        phone = NumberToTextConverter.toText(row.getCell(5).getNumericCellValue());
                                    } catch (Exception ex) {
                                        birthday = null;
                                        Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }

                            if (row.getCell(8) != null) {
                                String dateString = row.getCell(8).toString();
                                if (dateString != "") {
                                    try {
                                        DateController dateController = new DateController();
                                        date = dateController.tryParse(dateString);
                                    } catch (Exception ex) {
                                        date = null;
                                        Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                            String name = String.valueOf(row.getCell(1));
                            String lastName = String.valueOf(row.getCell(2));
                            String email = String.valueOf(row.getCell(6));
                            String note = String.valueOf(row.getCell(7));

                            CustomerController customerCL = new CustomerController();
                            try {
                                customerCL.addClient(name, lastName, sex, birthday, phone, email, note, date);
                            } catch (Exception ex) {
                                Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            //            updateValue();
                            updateProgress(i, customerSheet.getLastRowNum());
                            updateMessage("Done upto: " + i);
                        }else{
                            break;
                        }
                    }
                }
                    return null;
            }
        };
    }

}