//package serviceTesting.databaseTesting;
//
//import database.connection.MyDataSource;
//import database.dao.impl.MySqlCarDAO;
//import database.dao.impl.MySqlOrderDAO;
//import database.dao.impl.MySqlUserDAO;
//import org.junit.jupiter.api.Test;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import static org.junit.Assert.assertEquals;
//
//public class DAOTest {
//
//    @Test
//    public void CarDaoRowsTest(){
//        MySqlCarDAO carDAO = CarDAO.getInstance();
//
//        int carCount = 0;
//
//        try (Connection con = MyDataSource.getConnection();
//             PreparedStatement pst = con.prepareStatement("SELECT COUNT(car_id) FROM cars")) {
//            try (ResultSet rs = pst.executeQuery()) {
//                if (rs.next()) {
//                    carCount = rs.getInt("COUNT(car_id)");
//                }
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//
//        assertEquals(carCount, carDAO.getAllCars().size());
//    }
//
//    @Test
//    public void OrderDaoLocRowsTest(){
//        OrderDAO orderDAO = OrderDAO.getInstance();
//
//        int locCount = 0;
//
//        try (Connection con = DBManager.getInstance().getConnection();
//             PreparedStatement pst = con.prepareStatement("SELECT COUNT(location_id) FROM locations")) {
//            try (ResultSet rs = pst.executeQuery()) {
//                if (rs.next()) {
//                    locCount = rs.getInt("COUNT(location_id)");
//                }
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//
//        assertEquals(locCount, orderDAO.getAllLocations().size());
//    }
//
//    @Test
//    public void UserDaoRoleRowsTest(){
//        UserDAO userDAO = UserDAO.getInstance();
//
//        int locCount = 0;
//
//        try (Connection con = DBManager.getInstance().getConnection();
//             PreparedStatement pst = con.prepareStatement("SELECT COUNT(role_id) FROM roles")) {
//            try (ResultSet rs = pst.executeQuery()) {
//                if (rs.next()) {
//                    locCount = rs.getInt("COUNT(role_id)");
//                }
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//
//        assertEquals(locCount, userDAO.getAllRoles());
//    }
//}
