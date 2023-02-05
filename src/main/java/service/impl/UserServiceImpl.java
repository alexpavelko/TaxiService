package service.impl;

import database.connection.MyDataSource;
import database.dao.UserDAO;
import database.entity.User;
import dto.Converter;
import dto.UserDTO;
import exception.DAOException;
import exception.ServiceException;
import exception.ValidateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import utils.Security;

import static utils.validator.Validator.*;

/**
 * @author Oleksandr Pavelko
 */
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(MyDataSource.class);
    private final UserDAO userDao;

    public UserServiceImpl(UserDAO userDao) {
        this.userDao = userDao;
    }

    public void addUser(UserDTO userDTO) throws ServiceException, ValidateException, Exception {
        validatePassword(userDTO.getPassword());
        validateName(userDTO.getName());
        validateEmail(userDTO.getEmail());
        userDTO.setRole(User.Role.USER);
        userDTO.setPassword(Security.hashPassword(userDTO.getPassword()));
        userDao.add(Converter.convertDTOtoUser(userDTO));
    }

    @Override
    public User getByEmail(String email) throws ServiceException, ValidateException {
        try {
            return userDao.getByEmail(email);
        } catch (DAOException e) {
            throw new ValidateException("wrongEmailOrPassword");
        }
    }

    @Override
    public UserDTO authorize(String email, String password) throws ServiceException, ValidateException {
        UserDTO result = null;

        User user = getByEmail(email);
        if (user == null) {
            throw new ValidateException("wrongEmailOrPassword");
        }
        boolean passwordIsCorrect = Security.isPasswordCorrect(password, user.getPassword());
        if (passwordIsCorrect) {
            result = Converter.convertUserToDTO(user);
        }

        return result;
    }
}
