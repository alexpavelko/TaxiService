package database.dao.impl;

import database.connection.MyDataSource;
import database.dao.UserDAO;
import database.entity.User;
import exception.DAOException;
import exception.ValidateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.dao.impl.SqlRequestConstants.*;
import static database.dao.impl.FieldsConstants.*;

public class MySqlUserDAO implements UserDAO {
    private static final Logger logger = LoggerFactory.getLogger(MyDataSource.class);

    private final DataSource dataSource;

    public MySqlUserDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User get(long id) throws DAOException {
        User user = null;
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_GET_USER_BY_ID)) {
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next())
                    user = mapResultSet(rs);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return user;
    }

    @Override
    public User getByEmail(String email) {
        User user = null;
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_GET_USER_BY_EMAIL)) {
            pst.setString(1, email);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next())
                    user = mapResultSet(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    @Override
    public void add(User user) throws DAOException, ValidateException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_ADD_USER)) {
            pst.setString(1, user.getEmail());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getName());
            pst.setString(4, user.getRole().toString());
            pst.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            logger.error("UserDAO: attempt to add not unique field.");
            throw new ValidateException("nameOrEmailNotUnique");
        } catch (SQLException e) {
            logger.error("UserDAO: Couldn't add user." + e);
            throw new DAOException(e);
        }
    }

    @Override
    public void update(User user) throws DAOException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_UPDATE_USER)) {
            stmt.setString(setStatementFields(user, stmt), String.valueOf(user.getId()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(long id) throws DAOException {
        try (Connection con = dataSource.getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_DELETE_USER)) {
            int k = 0;
            stmt.setString(++k, String.valueOf(id));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<User> getAll(String param) throws DAOException {
        List<User> users = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_USERS)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    users.add(mapResultSet(rs));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return users;
    }

    private static User mapResultSet(ResultSet rs) {
        User user;
        try {
            user = new User();
            user.setId(rs.getInt(USER_ID));
            user.setName(rs.getString(USER_NAME));
            user.setEmail(rs.getString(USER_EMAIl));
            user.setPassword(rs.getString(USER_PASSWORD));
            user.setRole(User.Role.valueOf(rs.getString(USER_ROLE_NAME).toUpperCase()));
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return user;
    }

    private int setStatementFields(User user, PreparedStatement stmt) throws SQLException {
        int k = 0;
        stmt.setString(++k, user.getName());
        stmt.setString(++k, user.getEmail());
        stmt.setString(++k, user.getPassword());
        stmt.setInt(++k, user.getRole().getId());
        stmt.setString(++k, user.getEmail());
        stmt.setInt(++k, user.getRole().getId());
        return ++k;
    }
}
