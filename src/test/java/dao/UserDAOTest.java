package dao;
import database.dao.UserDAO;
import database.entity.User;
import exception.DAOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class UserDAOTest {
    @Mock
    UserDAO userDAO;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUserDao() throws DAOException {
        Mockito.when(userDAO.get(1)).thenReturn(new User("Alex", "Alex", User.Role.USER, "password"));
        User user = userDAO.get(1);
        System.out.println(user);

        assert (user.getName().equals("Alex"));
        assert (user.getRole() == User.Role.USER);
    }
}
