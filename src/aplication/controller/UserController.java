package aplication.controller;

import aplication.module.DAO.UserDAO;
import aplication.module.VO.UserVO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserController {

    private final UserDAO userDAO;

    public UserController() {
        this.userDAO = new UserDAO();
    }

    public boolean checkLogin( String username, String pass) throws SQLException {

        try {
            UserVO userVO = this.userDAO.getUser(username);

            if(userVO != null){
                if(userVO.getUsername().equals(username) && userVO.getPassword().equals(pass)) {
                    return true;
                }
            }
        }catch(SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
