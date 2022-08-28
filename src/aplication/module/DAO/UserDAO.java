package aplication.module.DAO;

import aplication.module.VO.UserVO;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import config.DatabaseConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {

    private Connection connection = null;
    private DatabaseConnection dbConnect = null;
    private PreparedStatement preStatement = null;

    private Statement dbQ;

    public UserDAO() {
        this.dbConnect = new DatabaseConnection();
        this.connection = (Connection) dbConnect.getConnection();
        try {
            this.dbQ =  (Statement) this.connection.createStatement();
        } catch(SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


//	private String conectar() {
//		conexion = new Connect();
//		String resultado=conexion.conectar();
//		if (resultado.equals("conectado")) {
//			connection = conexion.getConnection();
//			preStatement = null;
//		}else {
//			JOptionPane.showMessageDialog(null, resultado,"Error",JOptionPane.ERROR_MESSAGE);
//		}
//		return resultado;
//	}

    public UserVO getUser(String username) throws SQLException {
        UserVO user=null;

        ResultSet result=null;

        String consulta="SELECT id, name, last_name, username, password, status"
                + " FROM users where username= ? ";

        try {
            preStatement= (PreparedStatement) connection.prepareStatement(consulta);
            preStatement.setString(1, username);

            result=preStatement.executeQuery();

            if(result.next()){
                user=new UserVO();

                user.setId(result.getInt("id"));
                user.setName(result.getString("name"));
                user.setLastName(result.getString("last_name"));
                user.setUsername(result.getString("username"));
                user.setPassword(result.getString("password"));
                user.setStatus(result.getInt("status"));

            }

        } catch (SQLException e) {
            System.out.println("Error en la consulta de la persona: "+e.getMessage());
        }finally {
            result.close();
            preStatement.close();
            connection.close();
            dbConnect.disconnect();
        };
        return user;
    }

}