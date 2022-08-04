package aplication.controller;

import aplication.view.loader.LoaderController;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskController  extends Task<Void> {
    private final XSSFSheet customerSheet;

    public TaskController(XSSFSheet customerSheet) {
        this.customerSheet = customerSheet;
    }


    @Override
    protected Void call() throws Exception {

//        LoaderController loader = new LoaderController();
//        loader.index();
        Row row;
        for (int i = 5; i < customerSheet.getLastRowNum(); i++) {

            row = this.customerSheet .getRow(i);

            java.sql.Date birthday = null;
            java.sql.Date date = null;
            String sex = "";

            if(row.getCell(3) != null) {
                try{
                    String sexInput = String.valueOf(row.getCell(3));
                    // capitalize first letter
                    String sexOutput = sexInput.substring(0, 1).toUpperCase() + sexInput.substring(1);
                    if (sexOutput.equals("Hombre") || sexOutput.equals("Mujer")) {
                        sex = sexOutput;
                    }else{
                        sex = "";
                    }
                }catch (Exception ex) {
                    Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(row.getCell(4) != null) {
                String dateString = row.getCell(4).toString();
                if(dateString != ""){
                    try{
                        DateController dateController = new DateController();
                        birthday = dateController.tryParse(dateString);
                    }catch (Exception ex) {
                        birthday = null;
                        Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            if(row.getCell(8) != null) {
                String dateString = row.getCell(8).toString();
                if(dateString != ""){
                    try{
                        DateController dateController = new DateController();
                        date = dateController.tryParse(dateString);
                    }catch (Exception ex) {
                        date = null;
                        Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            String name = String.valueOf(row.getCell(1));
            String lastName = String.valueOf(row.getCell(2));
            String phone = String.valueOf(row.getCell(5));
            String email = String.valueOf(row.getCell(6));
            String note = String.valueOf(row.getCell(7));

            CustomerController customerCL = new CustomerController();
            try{
                customerCL.addClient( name,  lastName,  sex,  birthday,  phone,  email,  note, date);
            }catch(Exception ex){
                Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
            }
//            updateValue();
            updateProgress(i, customerSheet.getLastRowNum());
            updateMessage("Done upto: " + i);

        }
//        loader.finish();
        return null;
    }

}
