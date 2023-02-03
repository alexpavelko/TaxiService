//package serviceTesting;
//
//import database.dao.OrderDAO;
//import database.entity.Car;
//import service.impl.OrderServiceImpl;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvSource;
//import org.mockito.Mockito;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//
//public class OrderServiceImplTest {
//
//    @ParameterizedTest
//    @CsvSource({"оболонський,оболонський,100", "оболонський,деснянський,2800", "дніпровський,оболонський,2000"})
//    public void costTest(String loc_from, String loc_to, BigDecimal testCost){
//        OrderServiceImpl orderServiceImpl = new OrderServiceImpl(Mockito.spy(OrderDAO.class));
//
//        assertEquals(testCost, orderServiceImpl.cost(BigDecimal.valueOf(100), loc_from, loc_to));
//    }
//
//    @ParameterizedTest
//    @CsvSource({"оболонський,оболонський,140", "оболонський,деснянський,5540", "дніпровський,оболонський,3940"})
//    public void costWithDiscountTest(String loc_from, String loc_to, double testCost){
//        OrderServiceImpl orderServiceImpl = new OrderServiceImpl(Mockito.spy(OrderDAO.class));
//
//        assertEquals(BigDecimal.valueOf(testCost), orderServiceImpl.costWithDiscount(BigDecimal.valueOf(100), BigDecimal.valueOf(200), loc_from, loc_to));
//    }
//
//    @ParameterizedTest
//    @CsvSource({"оболонський,оболонський,100", "оболонський,деснянський,2800", "дніпровський,оболонський,2000"})
//    public void costTwoCarsTest(String loc_from, String loc_to, double testCost){
//        OrderServiceImpl orderServiceImpl = new OrderServiceImpl(Mockito.spy(OrderDAO.class));
//        List<Car> cars = new ArrayList<>();
//
//        for(int i = 0; i < 2; i++){
//            Car car = new Car();
//            double cost = 100 * (i + 1);
//            car.setCost(BigDecimal.valueOf(cost));
//            cars.add(car);
//        }
//
//        assertEquals(BigDecimal.valueOf(testCost), orderServiceImpl.costForTwoCars(cars, loc_from, loc_to));
//    }
//
//    @ParameterizedTest
//    @CsvSource({"оболонський,оболонський,70", "оболонський,деснянський,2770", "дніпровський,оболонський,1970"})
//    public void costTwoCarsWithDiscountTest(String loc_from, String loc_to, double testCost){
//        OrderServiceImpl orderServiceImpl = new OrderServiceImpl(Mockito.spy(OrderDAO.class));
//        List<Car> cars = new ArrayList<>();
//
//        for(int i = 0; i < 2; i++){
//            Car car = new Car();
//            double cost = 100 * (i + 1);
//            car.setCost(BigDecimal.valueOf(cost));
//            cars.add(car);
//        }
//
//        assertEquals(BigDecimal.valueOf(testCost), orderServiceImpl.costWithDiscountForTwoCars(BigDecimal.valueOf(100), cars, loc_from, loc_to));
//    }
//
//}
