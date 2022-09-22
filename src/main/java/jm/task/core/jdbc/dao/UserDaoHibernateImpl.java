package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = Util.getInstance().getSessionFactory();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String SQL_CREATE = "CREATE TABLE IF NOT EXISTS Users " +
                "(id BIGINT AUTO_INCREMENT NOT NULL, " +
                "name VARCHAR (255) NULL," +
                "lastName VARCHAR(255) NULL," +
                "age TINYINT NULL, PRIMARY KEY (id))";
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery(SQL_CREATE).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        }

    }

    @Override
    public void dropUsersTable() {
        String SQL_DROP = "DROP TABLE IF EXISTS Users";
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery(SQL_DROP).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
        System.out.printf("User с именем – %s добавлен в базу данных \n", name);
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
        }

    }

    @Override
    public List<User> getAllUsers() {
        String SQL = "FROM User";
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(SQL).getResultList();
        }
    }

    @Override
    public void cleanUsersTable() {
        String SQL = "TRUNCATE TABLE Users";
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery(SQL).addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        }
    }
}
