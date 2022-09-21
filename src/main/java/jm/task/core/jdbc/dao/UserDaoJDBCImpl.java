package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static Connection connection;

    static {
        try {
            connection = Util.getInstance().getConnection();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() throws SQLException {
        String SQL_CREATE = "CREATE TABLE IF NOT EXISTS Users" +
                "(id int NOT NULL AUTO_INCREMENT, " +
                "name varchar(30), " +
                "lastName varchar(50), " +
                "age int, " +
                "primary key (id))";
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(SQL_CREATE)) {
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.getStackTrace();
            connection.rollback();
        }
    }

    public void dropUsersTable() throws SQLException {
        String SQL_DROP = "DROP TABLE IF EXISTS Users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(SQL_DROP);
            connection.commit();
        } catch (SQLException e) {
            e.getStackTrace();
            connection.rollback();
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("INSERT INTO Users(name, lastName, age) VALUES(?, ?, ?)")){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            System.out.printf("User с именем – %s добавлен в базу данных \n", name);
            connection.commit();
        } catch (SQLException e) {
            e.getStackTrace();
            connection.rollback();
        }
    }

    public void removeUserById(long id) throws SQLException {
        String SQL = "DELETE FROM Users WHERE id = ?";
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(SQL)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.getStackTrace();
            connection.rollback();
        }
    }

    public List<User> getAllUsers() throws SQLException {

        List<User> users = new ArrayList<>();
        String SQL = "SELECT * FROM Users";

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge((byte) resultSet.getInt("age"));
                users.add(user);
            }
            connection.commit();
        } catch (SQLException e) {
            e.getStackTrace();
            connection.rollback();
        }
        return users;
    }

    public void cleanUsersTable() throws SQLException {
        String SQL_CLEAR = "TRUNCATE TABLE Users";
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(SQL_CLEAR)) {
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.getStackTrace();
            connection.rollback();
        }
    }
}
