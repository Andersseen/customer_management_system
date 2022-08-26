package aplication.controller;

import aplication.module.DAO.CustomerDAO;
import aplication.module.VO.CustomerVO;
import aplication.view.dashboard.DashboardController;


import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerController {

    private CustomerDAO customerDAO;
    private CustomerVO customerVO;

    public CustomerController() {
        this.customerDAO = new CustomerDAO();

    }

    public ArrayList<CustomerVO> getClients() throws SQLException {
        customerDAO = new CustomerDAO();
        return customerDAO.getCustomers();
    }

    public int addClient(String name, String lastName, String sex, Date birthday, String phone, String email, String note, Date date) {
        customerVO = new CustomerVO();
//        String rs = "Estoy agregando cliente";
        int thisId = 0;
        try {

            customerVO.setName(name);
            customerVO.setLastName(lastName);
            customerVO.setSex(sex);
            customerVO.setBirthday(birthday);
            customerVO.setPhone(phone);
            customerVO.setEmail(email);
            customerVO.setNote(note);
            customerVO.setDate(date);

            thisId = this.customerDAO.addCustomer(customerVO);


        }catch(SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
//            rs = "Algo ha pasado mal";
        }
        return thisId;
    }

//    int id,String name, String lastName, String sex, Date birthday, String phone, String email, String note, Date date
    public void editClient(CustomerVO customer) {
        customerVO = new CustomerVO();
//        String rs = "Estoy editando cliente";
        try {
            customerVO.setId(customer.getId());
            customerVO.setName(customer.getName());
            customerVO.setLastName(customer.getLastName());
            customerVO.setSex(customer.getSex());
            customerVO.setBirthday(customer.getBirthday());
            customerVO.setPhone(customer.getPhone());
            customerVO.setEmail(customer.getEmail());
            customerVO.setNote(customer.getNote());
            customerVO.setDate(customer.getDate());

            this.customerDAO.updateCustomer(customerVO);

        }catch(SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
//            rs = "Algo ha pasado mal";
        }

    }

    public CustomerVO getClient(int id) {
        customerVO = new CustomerVO();
//        String rs = "Estoy editando cliente";
        try {
            customerVO=  this.customerDAO.getCustomer(id);
           return customerVO;

        }catch(SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
//            rs = "Algo ha pasado mal";
        }
        return customerVO;
    }

    public void deleteClient(int id) {
//        String rs = "Estoy eliminando cliente";
        try {
            this.customerDAO.deleteCustomer( id);

        } catch (SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
}
