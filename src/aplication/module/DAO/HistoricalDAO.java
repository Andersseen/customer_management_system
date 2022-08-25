package aplication.module.DAO;

import aplication.module.VO.CustomerVO;
import aplication.module.VO.HistoricalVO;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import config.DatabaseConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HistoricalDAO {

    private Connection connection = null;
    private DatabaseConnection dbConnect = null;
    private PreparedStatement preStatement = null;

    private Statement dbQ;

    public HistoricalDAO() {
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

    public String addHistorical(HistoricalVO historical) throws SQLException {

        String resultado = "";


        String consulta = "INSERT INTO historicals (id_customer, name, last_name, historical)"
                + "  VALUES (?,?,?,?)";

//        String consulta = "INSERT INTO historicals (name, last_name, historical)"
//                + "  VALUES (?,?,?)";

        try {
            preStatement = (PreparedStatement) connection.prepareStatement(consulta);

            preStatement.setInt(1, historical.getId_customer());
            preStatement.setString(2, historical.getName());
            preStatement.setString(3,historical.getLastName());
            preStatement.setString(4,historical.getHistorical());

            preStatement.execute();

            resultado = "Historico agregado";

        }catch (SQLException e) {
            System.out.println("No se pudo registrar el historico, verifique que el documento no exista: " + e.getMessage());
            //e.printStackTrace();
            resultado = "error";
        }
        catch (Exception e) {
            System.out.println("No se pudo registrar el historico: " + e.getMessage());
            //e.printStackTrace();
            resultado = "error";
        }
        finally {
            preStatement.close();
            connection.close();
            dbConnect.desconnect();
        }
        return resultado;
    }

    public HistoricalVO getHistorical(int idCustomer) throws SQLException {
        HistoricalVO historical=null;


        ResultSet result=null;

        String consulta="SELECT id, id_customer, name, last_name, historical"
                + " FROM historicals where id_customer= ? ";

        try {
            preStatement= (PreparedStatement) connection.prepareStatement(consulta);
            preStatement.setInt(1, idCustomer);

            result=preStatement.executeQuery();

            if(result.next()){
                historical=new HistoricalVO();

                historical.setId(result.getInt("id"));
                historical.setName(result.getString("name"));
                historical.setLastName(result.getString("last_name"));
                historical.setHistorical(result.getString("historical"));
            }

        } catch (SQLException e) {
            System.out.println("Error en la consulta de el historico: "+e.getMessage());
        }finally {
            result.close();
            preStatement.close();
            connection.close();
            dbConnect.desconnect();
        }
        return historical;
    }

    public String updateHistorical(HistoricalVO historicalVO) throws SQLException {
        String resultado="";
        try{
//            String consulta="UPDATE historicals"
//                    + "SET historical = ? "
//                    + "WHERE id= ?";
            String consulta = "UPDATE historicals SET historical = ? WHERE id= ?";
            preStatement = (PreparedStatement) connection.prepareStatement(consulta);

            preStatement.setInt(2, historicalVO.getId());
            preStatement.setString(1,historicalVO.getHistorical());

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


//    public HistoricalVO getHistorical(int id) throws SQLException {
//        HistoricalVO historical=null;
//
//
//        ResultSet result=null;
//
//        String consulta="SELECT id, id_customer, name, last_name, historical"
//                + " FROM historicals where id= ? ";
//
//        try {
//            preStatement= (PreparedStatement) connection.prepareStatement(consulta);
//            preStatement.setInt(1, id);
//
//            result=preStatement.executeQuery();
//
//            if(result.next()){
//                historical=new HistoricalVO();
//
//                historical.setId(result.getInt("id"));
//                historical.setName(result.getString("name"));
//                historical.setLastName(result.getString("last_name"));
//            }
//
//        } catch (SQLException e) {
//            System.out.println("Error en la consulta de el historico: "+e.getMessage());
//        }finally {
//            result.close();
//            preStatement.close();
//            connection.close();
//            dbConnect.desconnect();
//        }
//        return historical;
//    }
//
//    public ArrayList<HistoricalVO> getHistoricals() throws SQLException {
//        ArrayList<HistoricalVO> listaClients=new ArrayList<HistoricalVO>();
//
//        ResultSet result=null;
//        HistoricalVO client=null;
//
//        String consulta="SELECT id, name, last_name, historical"
//                + " FROM historicals";
//        if(this.connection == null){
//            System.out.println("La base de datos esta inactiva");
//            return null;
//        }
//
//        try {
//            preStatement= (PreparedStatement) connection.prepareStatement(consulta);
//
//            result=preStatement.executeQuery();
//
//            while(result.next()==true){
//                client=new HistoricalVO();
//
//                client.setId(result.getInt("id"));
//                client.setId(result.getInt("id_customer"));
//                client.setName(result.getString("name"));
//                client.setLastName(result.getString("last_name"));
//                client.setLastName(result.getString("historico"));
//                listaClients.add(client);
//            }
//
//        } catch (SQLException e) {
//            System.out.println("Error en la consulta del historico: "+e.getMessage());
//
//        }finally {
//            result.close();
//            preStatement.close();
//            connection.close();
//            dbConnect.desconnect();
//        }
//        return listaClients;
//    }

//    public String deleteHistorical(int id) throws SQLException {
//
//        String respuesta="";
//
//        try {
//            String sentencia="DELETE FROM historicals WHERE id= ? ";
//
//            PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sentencia);
//            statement.setInt(1, id);
//
//            statement.executeUpdate();
//
//            respuesta="ok";
//
//        } catch (SQLException e) {
//            System.out.println("Ocurrió una excepcion de SQL "
//                    + "al momento de eliminar "+e);
//            respuesta="error";
//        }finally {
//            connection.close();
//            dbConnect.desconnect();
//        }
//        return respuesta;
//    }

}
