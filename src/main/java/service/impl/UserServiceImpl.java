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

    public void addUser(UserDTO userDTO) throws ServiceException, ValidateException {
        logger.info(UserService.class + "#addUser()");
        validatePassword(userDTO.getPassword());
        validateName(userDTO.getName());
        validateEmail(userDTO.getEmail());
        userDTO.setRole(User.Role.USER);
        try {
            userDTO.setPassword(Security.hashPassword(userDTO.getPassword()));
            try {
                userDao.add(Converter.convertDTOtoUser(userDTO));
            } catch (DAOException e) {
                logger.error(e.getMessage());
                throw new ServiceException(e);
            } catch (ValidateException e) {
                throw new ServiceException(e);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public User getByEmail(String email) {
        return userDao.getByEmail(email);
    }

    @Override
    public UserDTO authorize(String email, String password) throws ServiceException, ValidateException {
        UserDTO result = null;
        logger.info(UserService.class.toString() + "#authorize()");
        try {
            User user = userDao.getByEmail(email);
            if (user == null) {
                throw new ValidateException("wrongEmailOrPassword");
            }
            boolean passwordIsCorrect = Security.isPasswordCorrect(password, user.getPassword());
            if (passwordIsCorrect) {
                result = Converter.convertUserToDTO(user);
            }
        } catch (DAOException e) {
            System.out.println(e.getMessage());
            throw new ServiceException(e);
        } catch (Exception e) {
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
            throw new ValidateException("wrongEmailOrPassword");
        }
        return result;
    }
}
