package serviceTesting;

import service.impl.CarServiceImpl;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.Assert.assertEquals;

public class CarServiceImplTest {

    @ParameterizedTest
    @CsvSource({"cheap,1","comfort,2","business,3"})
    public void typeIdTest(String type, int expected){
        assertEquals(expected, CarServiceImpl.getTypeId(type));
    }
}
