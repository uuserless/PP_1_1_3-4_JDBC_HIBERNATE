package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String HOST_NAME = "localhost";
    private static final String DB_NAME = "Kata";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "rootroot";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://" + HOST_NAME + ":3306/" + DB_NAME +
            "?autoReconnect=true&useUnicode=true&serverTimezone=UTC&" +
            "useSSL=true&verifyServerCertificate=false";
    
    private static Util util;
    private static Connection connection;
    private static SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration()
                    .setProperty("hibernate.connection.driver_class", DRIVER)
                    .setProperty("hibernate.connection.url", URL)
                    .setProperty("hibernate.connection.username", USERNAME)
                    .setProperty("hibernate.connection.password", PASSWORD)
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect")
                    .setProperty("hibernate.current_session_context_class", "thread")
                    .setProperty("hibernate.hbm2ddl.auto", "none")
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
            Configuration configuration = new Configuration().addAnnotatedClass(User.class);
            sessionFactory = configuration.buildSessionFactory();
        }
        return sessionFactory;
    }

    private Util() {

        try {
            Class.forName(DRIVER).getConstructor().newInstance();
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);

        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static Util getInstance() throws SQLException {
        if (util == null) {
            util = new Util();
        } else if (util.getConnection().isClosed()) {
            util = new Util();
        }
        connection.setAutoCommit(false);
        return util;
    }
}
