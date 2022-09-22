package jm.task.core.jdbc;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();

        try {
            userService.createUsersTable();
            userService.saveUser("name1","lastName1",(byte) 18);
            userService.saveUser("name2","lastName2",(byte) 28);
            userService.saveUser("name3","lastName3",(byte) 20);
            userService.saveUser("name4","lastName4",(byte) 35);
            userService.getAllUsers().forEach(System.out::println);
            userService.cleanUsersTable();
            userService.dropUsersTable();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
