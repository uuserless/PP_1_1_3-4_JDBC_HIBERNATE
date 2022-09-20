package jm.task.core.jdbc;
import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {

    public static void main(String[] args) {

        User user1 = new User("name1","lastName1",(byte) 18);
        User user2 = new User("name2","lastName2",(byte) 28);
        User user3 = new User("name3","lastName3",(byte) 20);
        User user4 = new User("name4","lastName4",(byte) 35);

    }
}
