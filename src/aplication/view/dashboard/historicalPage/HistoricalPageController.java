package aplication.view.dashboard.historicalPage;

import aplication.controller.HistoricalController;
import aplication.module.VO.CustomerVO;
import aplication.module.VO.HistoricalVO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;


public class HistoricalPageController {
    private HistoricalVO historicalVO;

    @FXML
    private Button addBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private TextArea historyInput;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label nameLabel;

    public void index(CustomerVO customer){

        nameLabel.setText(customer.getName());
        lastNameLabel.setText(customer.getLastName());

        HistoricalController historicalCL = new HistoricalController();
        historicalVO = historicalCL.controlGettingHistorical(customer.getId());
        if(historicalVO != null){
            try{
                historyInput.setText(historicalVO.getHistorical());
            }catch (Exception ex){
                historyInput.setText("");
            }
        }
    }
}
