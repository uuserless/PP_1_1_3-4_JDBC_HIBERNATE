package jm.task.core.jdbc.util;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    
    private static Util util;
    private static Connection connection;



    private Util() {
        String hostName = "localhost";
        String dbName = "Kata";
        String username = "root";
        String password = "rootroot";
        String url = "jdbc:mysql://" + hostName + ":3306/" + dbName + "?autoReconnect=true&useUnicode=true&serverTimezone=UTC&useSSL=true&verifyServerCertificate=false";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
            connection = DriverManager.getConnection(url,username,password);
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
        return util;
    }


}
