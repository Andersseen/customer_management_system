package aplication.service;


import aplication.controller.CustomerController;
import aplication.controller.DateController;
import aplication.controller.FileController;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import java.sql.Date;
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
            protected Void call(){
                Row row;
                if (customerSheet != null) {
                    for (int i = 5; i < customerSheet.getLastRowNum() +1 ; i++) {
                        row = customerSheet.getRow(i);
                        if(row != null) {
                            // variables for client
                            Date birthday = null;
                            Date date = null;
                            String sex;
                            String phone;
                            String name = "";
                            String lastName = "";
                            String email;
                            String note;

                            // variables for cells
                            Cell nameCell = row.getCell(1);
                            Cell lastNameCell = row.getCell(2);
                            Cell sexCell = row.getCell(3);
                            Cell birthdayCell = row.getCell(4);
                            Cell phoneCell = row.getCell(5);
                            Cell emailCell = row.getCell(6);
                            Cell noteCell = row.getCell(7);
                            Cell dateCell = row.getCell(8);

                            if (nameCell != null) {
                                name = String.valueOf(nameCell);
                            }
                            if (lastNameCell != null) {
                                lastName = String.valueOf(lastNameCell);
                            }

                            // *** Validate cell of sex ( number 3) ***
                            if(sexCell != null) {
                                switch (sexCell.getCellType()) {
                                    case STRING:
                                        String sexInput = String.valueOf(sexCell);
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
                                        break;
                                    default:
                                        sex = "";
                                }
                            }else {
                                sex = "";
                            }

                            // *** Validate cell of birthday ( number 4) ***
                            if (birthdayCell != null) {
                                String dateString = birthdayCell.toString();
                                if (dateString != "") {
                                    try {
                                        DateController dateController = new DateController();
                                        birthday = dateController.tryParse(dateString);
                                    } catch (Exception ex) {

                                        Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }else{
                                birthday = null;
                            }

                            // *** Validate cell of phone ( number 5) ***
                            if(phoneCell != null) {
                                switch (phoneCell.getCellType()) {
                                    case STRING:
                                        phone = phoneCell.getStringCellValue();
                                        break;
                                    case NUMERIC:
                                        phone = NumberToTextConverter.toText(phoneCell.getNumericCellValue());
                                        break;
                                    case BOOLEAN:
                                        phone = String.valueOf(phoneCell.getBooleanCellValue());
                                        break;
                                    default:
                                        phone = "";
                                }
                            }else {
                                phone = "";
                            }

                            // *** Validate cell of email ( number 6) ***
                            if(emailCell != null) {
                                switch (emailCell.getCellType()) {
                                    case STRING:
                                        email = String.valueOf(emailCell);
                                        break;
                                    case NUMERIC:
                                        email = NumberToTextConverter.toText(emailCell.getNumericCellValue());
                                        break;
                                    case BOOLEAN:
                                        email = String.valueOf(emailCell.getBooleanCellValue());
                                        break;
                                    default:
                                        email = "";
                                }
                            }else {
                                email = "";
                            }

                            // *** Validate cell of note ( number 7) ***
                            if(noteCell != null) {
                                switch (noteCell.getCellType()) {
                                    case STRING:
                                        note = String.valueOf(noteCell);
                                        break;
                                    case NUMERIC:
                                        note = NumberToTextConverter.toText(noteCell.getNumericCellValue());
                                        break;
                                    case BOOLEAN:
                                        note = String.valueOf(noteCell.getBooleanCellValue());
                                        break;
                                    default:
                                        note = "";
                                }
                            }else {
                                note = "";
                            }
                            // *** Validate cell of date ( number 8) ***
                            if (dateCell != null) {
                                String dateString = dateCell.toString();
                                if (dateString != "") {
                                    try {
                                        DateController dateController = new DateController();
                                        date = dateController.tryParse(dateString);
                                    } catch (Exception ex) {
                                        date = null;
                                        Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }else{
                                date = null;
                            }
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