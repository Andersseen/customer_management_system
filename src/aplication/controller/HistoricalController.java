package aplication.controller;

import aplication.module.DAO.HistoricalDAO;
import aplication.module.VO.CustomerVO;
import aplication.module.VO.HistoricalVO;
import aplication.view.dashboard.DashboardController;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HistoricalController {

    private HistoricalDAO historicalDAO;
    private HistoricalVO historicalVO;

    public HistoricalController() {
        this.historicalDAO = new HistoricalDAO();

    }

    public void controlAddingHistorical(int id_customer, String name, String lastName, String historical) {
        historicalVO = new HistoricalVO();
        String rs = "Estoy agregando historico";

        try {
            historicalVO.setId_customer(id_customer);
            historicalVO.setName(name);
            historicalVO.setLastName(lastName);
            historicalVO.setHistorical(historical);

            this.historicalDAO.addHistorical(historicalVO);

        }catch(SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            rs = "Algo ha pasado mal";
        }

    }

    public HistoricalVO controlGettingHistorical(int id) {
        historicalVO = new HistoricalVO();
        String rs = "Estoy editando cliente";

        try {
            historicalVO=  this.historicalDAO.getHistorical(id);
            return historicalVO;

        }catch(SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            rs = "Algo ha pasado mal";
        }
        return historicalVO;
    }

    public void controlUpdatingHistorical(HistoricalVO historical) {
        historicalVO = new HistoricalVO();
        try {
            historicalVO.setId(historical.getId());
            System.out.println(historical.getId());
            historicalVO.setHistorical(historical.getHistorical());

            this.historicalDAO.updateHistorical(historicalVO);

        }catch(SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
