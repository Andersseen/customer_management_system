package aplication.view.dashboard.historicalPage;

import aplication.controller.HistoricalController;
import aplication.module.VO.CustomerVO;
import aplication.module.VO.HistoricalVO;
import aplication.view.dashboard.DashboardController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HistoricalPageController implements Initializable {
    private HistoricalVO historicalVO;
    private HistoricalController historicalCL;
    private CustomerVO customer;

    @FXML
    private Button addBtn;
    @FXML
    private Button editBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextArea historyInput;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label nameLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cancelBtn.setOnMouseClicked((MouseEvent event) -> {
            DashboardController dashboardCL = new DashboardController();
            dashboardCL.returnToRefreshDashboard(cancelBtn);
        });
    }

    public void indexEditForm(CustomerVO customer){

        this.customer = customer;

        addBtn.setVisible(false);

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

    public void indexAddForm(CustomerVO customer){

        this.customer = customer;

        editBtn.setVisible(false);

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

    @FXML
    private void onClickAddHistorical(){
        try{
            historicalCL = new HistoricalController();
            historicalCL.controlAddingHistorical(this.customer.getId(), this.customer.getName(), this.customer.getLastName(),historyInput.getText());
        }catch (Exception ex){
            Logger.getLogger(HistoricalPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        DashboardController dashboardCL = new DashboardController();
        dashboardCL.returnToRefreshDashboard(addBtn);
    }

    @FXML
    private void onClickEditHistorical(){
        historicalCL = new HistoricalController();
        historicalVO = historicalCL.controlGettingHistorical(customer.getId());
        if(historicalVO != null){
            try{
                historicalVO.setHistorical(historyInput.getText());
                historicalCL = new HistoricalController();
                historicalCL.controlUpdatingHistorical(historicalVO);
            }catch (Exception ex){
                Logger.getLogger(HistoricalPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        DashboardController dashboardCL = new DashboardController();
        dashboardCL.returnToRefreshDashboard(addBtn);
    }


}
