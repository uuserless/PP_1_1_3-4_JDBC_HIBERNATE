package jm.task.core.jdbc;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args)  throws SQLException {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Egor","Mashkov",(byte) 26);
        userService.saveUser("Ivan","Ivanov",(byte) 30);
        userService.saveUser("Daniil","Petrov",(byte) 19);
        userService.saveUser("Anastasia","Krutova",(byte) 25);
        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
