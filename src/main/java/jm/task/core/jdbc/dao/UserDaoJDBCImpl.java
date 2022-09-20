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

    public UserDaoJDBCImpl() throws SQLException {
    }

    public void createUsersTable() throws SQLException {
        String SQL_CREATE = "CREATE TABLE IF NOT EXISTS Users" +
                "(id int NOT NULL AUTO_INCREMENT, " +
                "name varchar(30), " +
                "lastName varchar(50), " +
                "age int, " +
                "primary key (id))";
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate(SQL_CREATE);
            connection.commit();
        } catch (SQLException e) {
            e.getStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }


    }

    public void dropUsersTable() throws SQLException {
        String SQL_DROP = "DROP TABLE IF EXISTS Users";
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate(SQL_DROP);
            connection.commit();
        } catch (SQLException e) {
            e.getStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("INSERT INTO Users(name, lastName, age) VALUES(?, ?, ?)")){
            connection.setAutoCommit(false);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            System.out.printf("User с именем – %s добавлен в базу данных \n", name);
            connection.commit();
        } catch (SQLException e) {
            e.getStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void removeUserById(long id) throws SQLException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("DELETE FROM Users WHERE id = ?")) {
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.getStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public List<User> getAllUsers() throws SQLException {

        List<User> users = new ArrayList<>();
        String SQL = "SELECT * FROM Users";

        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            ResultSet resultSet = statement.executeQuery(SQL);

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
        } finally {
            connection.setAutoCommit(true);
        }

        return users;
    }

    public void cleanUsersTable() throws SQLException {
        String SQL_CLEAR = "TRUNCATE TABLE Users";
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate(SQL_CLEAR);
            connection.commit();
        } catch (SQLException e) {
            e.getStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
