package aplication.module.DAO;

import aplication.module.VO.CustomerVO;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import config.DatabaseConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerDAO {


    private Connection connection = null;
    private DatabaseConnection dbConnect = null;
    private PreparedStatement preStatement = null;

    private Statement dbQ;

//	private String conectar() {
//		conexion = new DB();
//		String resultado = conexion.getConexion();
//		if (resultado.equals("conectado")) {
//			connection = conexion.getConnection();
//			preStatement = null;
//		}else {
//			JOptionPane.showMessageDialog(null, resultado,"Error",JOptionPane.ERROR_MESSAGE);
//		}
//		return resultado;
//	}

    public CustomerDAO() {
        this.dbConnect = new DatabaseConnection();
        this.connection = (Connection) dbConnect.getConnection();
        if(this.connection == null){
            System.out.println("La base de datos esta inactiva");
            return;
        }
        try {
            this.dbQ =  (Statement) this.connection.createStatement();
        } catch(SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public String addCustomer(CustomerVO client) throws SQLException {
//        String resultado = "";
//        String consulta = "INSERT INTO customers (name, last_name, sex, birthday, phone, email, note, date)"
//                + "  VALUES (?,?,?,?,?,?, ?, ?)";
//        try {
//            preStatement = (PreparedStatement) connection.prepareStatement(consulta);
//
////			preStatement.setInt(1, client.getId());
//            preStatement.setString(1, client.getName());
//            preStatement.setString(2,client.getLastName());
//            preStatement.setString(3,client.getSex());
//            preStatement.setDate(4, client.getBirthday());
//            preStatement.setString(5,client.getPhone());
//            preStatement.setString(6,client.getEmail());
//            preStatement.setString(7,client.getNote());
//            preStatement.setDate(8,  client.getDate());
//            preStatement.execute();
//
//            resultado = "Cliente agregado";
//
//        }catch (SQLException e) {
//            System.out.println("No se pudo registrar el cliente, verifique que el documento no exista: " + e.getMessage());
//            //e.printStackTrace();
//            resultado = "error";
//        }
//        catch (Exception e) {
//            System.out.println("No se pudo registrar el cliente: " + e.getMessage());
//            //e.printStackTrace();
//            resultado = "error";
//        }
//        finally {
//            preStatement.close();
//            connection.close();
//            dbConnect.desconnect();
//        }
//        return resultado;
//    }

    public int addCustomer(CustomerVO client) throws SQLException {
//        CustomerVO resultado = new CustomerVO();

        // for insert a new candidate
        ResultSet rs = null;
        int thisId = 0;

        String consulta = "INSERT INTO customers (name, last_name, sex, birthday, phone, email, note, date)"
                + "  VALUES (?,?,?,?,?,?, ?, ?)";
        try {
            preStatement = (PreparedStatement) connection.prepareStatement(consulta,Statement.RETURN_GENERATED_KEYS);

//			preStatement.setInt(1, client.getId());
            preStatement.setString(1, client.getName());
            preStatement.setString(2,client.getLastName());
            preStatement.setString(3,client.getSex());
            preStatement.setDate(4, client.getBirthday());
            preStatement.setString(5,client.getPhone());
            preStatement.setString(6,client.getEmail());
            preStatement.setString(7,client.getNote());
            preStatement.setDate(8,  client.getDate());
//            preStatement.execute();
            int rowAffected = preStatement.executeUpdate();
            if(rowAffected == 1)
            {
                // get candidate id
                rs = preStatement.getGeneratedKeys();
                if(rs.next())
                    thisId = rs.getInt(1);
            }
//            resultado = ;

        }catch (SQLException e) {
            System.out.println("No se pudo registrar el cliente, verifique que el documento no exista: " + e.getMessage());
            //e.printStackTrace();
//            resultado = "error";
        }
        catch (Exception e) {
            System.out.println("No se pudo registrar el cliente: " + e.getMessage());
            //e.printStackTrace();
//            resultado = "error";
        }
        finally {
            preStatement.close();
            connection.close();
            dbConnect.desconnect();
        }
        return thisId;
    }


    public CustomerVO getCustomer(int id) throws SQLException {
        CustomerVO client=null;


        ResultSet result=null;

        String consulta="SELECT id, name, last_name, sex, birthday, phone, email, note, date"
                + " FROM customers where id= ? ";

        try {
            preStatement= (PreparedStatement) connection.prepareStatement(consulta);
            preStatement.setInt(1, id);

            result=preStatement.executeQuery();

            if(result.next()){
                client=new CustomerVO();

                client.setId(result.getInt("id"));
                client.setName(result.getString("name"));
                client.setLastName(result.getString("last_name"));
                client.setSex(result.getString("sex"));
                client.setBirthday(result.getDate("birthday"));
                client.setPhone(result.getString("phone"));
                client.setEmail(result.getString("email"));
                client.setNote(result.getString("note"));
                client.setDate(result.getDate("date"));
            }

        } catch (SQLException e) {
            System.out.println("Error en la consulta de la persona: "+e.getMessage());
        }finally {
            result.close();
            preStatement.close();
            connection.close();
            dbConnect.desconnect();
        }
        return client;
    }

    public ArrayList<CustomerVO> getCustomers() throws SQLException {
        ArrayList<CustomerVO> listaClients=new ArrayList<CustomerVO>();

//		if (!conectar().equals("conectado")) {
//			return listaClients;
//		}

        ResultSet result=null;
        CustomerVO client=null;

        String consulta="SELECT id, name, last_name, sex, birthday, phone, email, note, date"
                + " FROM customers";
        if(this.connection == null){
            System.out.println("La base de datos esta inactiva");
            return null;
        }

        try {
            preStatement= (PreparedStatement) connection.prepareStatement(consulta);


            result=preStatement.executeQuery();

            while(result.next()==true){
                client=new CustomerVO();

                client.setId(result.getInt("id"));
                client.setName(result.getString("name"));
                client.setLastName(result.getString("last_name"));
                client.setSex(result.getString("sex"));
                client.setBirthday(result.getDate("birthday"));
                client.setPhone(result.getString("phone"));
                client.setEmail(result.getString("email"));
                client.setNote(result.getString("note"));
                client.setDate(result.getDate("date"));

                listaClients.add(client);
            }

        } catch (SQLException e) {
            System.out.println("Error en la consulta de personas: "+e.getMessage());

        }finally {
            result.close();
            preStatement.close();
            connection.close();
            dbConnect.desconnect();
        }
        return listaClients;
    }


    public String updateCustomer(CustomerVO client) throws SQLException {
        String resultado="";


        try{
            //id, name, last_name, sex, birthday, phone, email, note, date"
            String consulta="UPDATE customers "
                    + "SET name = ? , "
                    + "last_name= ? , "
                    + "sex= ? , "
                    + "birthday= ? , "
                    + "phone= ?  ,"
                    + "email= ?  ,"
                    + "note= ?  ,"
                    + "date= ?  "
                    + "WHERE id= ?";
            preStatement = (PreparedStatement) connection.prepareStatement(consulta);

            preStatement.setInt(9, client.getId());
            preStatement.setString(1, client.getName());
            preStatement.setString(2,client.getLastName());
            preStatement.setString(3,client.getSex());
            preStatement.setDate(4, client.getBirthday());
            preStatement.setString(5,client.getPhone());
            preStatement.setString(6,client.getEmail());
            preStatement.setString(7,client.getNote());
            preStatement.setDate(8, client.getDate());

            preStatement.executeUpdate();


            resultado="Cliente esta editado";

        }catch(SQLException	 e){
            System.out.println("Ocurrió una excepcion de SQL "
                    + "al momento de actualizar: "+e);
            resultado="error";
        }finally {
            preStatement.close();
            connection.close();
            dbConnect.desconnect();
        }
        return resultado;
    }

    public String deleteCustomer(int id) throws SQLException {

        String respuesta="";

//		if (!conectar().equals("conectado")) {
//			return "error";
//		}

        try {
            String sentencia="DELETE FROM customers WHERE id= ? ";

            PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sentencia);
            statement.setInt(1, id);

            statement.executeUpdate();

            respuesta="ok";

        } catch (SQLException e) {
            System.out.println("Ocurrió una excepcion de SQL "
                    + "al momento de eliminar "+e);
            respuesta="error";
        }finally {
//			preStatement.close();
            connection.close();
            dbConnect.desconnect();
        }
        return respuesta;
    }


}
