package aplication.controller;

import aplication.module.DAO.CustomerDAO;
import aplication.module.VO.CustomerVO;
import aplication.view.dashboard.DashboardController;


import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerController {

    private CustomerDAO customerDAO;
    private CustomerVO customerVO;

    public CustomerController() {
        this.customerDAO = new CustomerDAO();

    }

    public void addClient(String name, String lastName, String sex, Date birthday, String phone, String email, String note, Date date) {
        customerVO = new CustomerVO();
        String rs = "Estoy agregando cliente";

        try {

            customerVO.setName(name);
            customerVO.setLastName(lastName);
            customerVO.setSex(sex);
            customerVO.setBirthday(birthday);
            customerVO.setPhone(phone);
            customerVO.setEmail(email);
            customerVO.setNote(note);
            customerVO.setDate(date);

         this.customerDAO.addCustomer(customerVO);

        }catch(SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            rs = "Algo ha pasado mal";
        }

    }
}
