package service;

import database.entity.User;
import dto.UserDTO;
import exception.ServiceException;
import exception.ValidateException;

/**
 * @author Oleksandr Pavelko
 */
public interface UserService {
    void addUser(UserDTO userDTO) throws ServiceException, ValidateException, Exception;
    User getByEmail(String email) throws ServiceException, ValidateException;
    UserDTO authorize(String login, String password) throws ServiceException, ValidateException;
}
