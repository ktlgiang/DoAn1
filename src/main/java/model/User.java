package model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import util.HibernateUtil;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Người_dùng")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Mã_người_dùng")
    private int user_id;

    @Column(name="Mật_khẩu")
    private String password;

    @Column(name="Email")
    private String email;

    @Column(name="Tên_người_dùng")
    private String userName;

    @Column(name="Vai_trò")
    private String user_role;

    @OneToMany(mappedBy = "user")
    private List<Document> list_Document;

    @OneToMany(mappedBy = "user_comment")
    private List<Comment> List_Comment;

    public User() {}

    public User(String email, String userName) {
        this.email = email;
        this.userName = userName;
    }

    public User(String password, String email, String userName) {
        this.password = password;
        this.email = email;
        this.userName = userName;
    }

    public User(int user_id, String password, String email, String user_role) {
        this.user_id = user_id;
        this.password = password;
        this.email = email;
        this.user_role = user_role;
    }

    public User(int user_id, String password, String email, String userName, String user_role) {
        this.user_id = user_id;
        this.password = password;
        this.email = email;
        this.userName = userName;
        this.user_role = user_role;
    }

    // Getters and setters

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    // Method to perform login using Hibernate
    public static User login(String email, String password) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<User> query = session.createQuery("FROM User WHERE email=:email AND password=:password", User.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            User user = query.uniqueResult();
            session.getTransaction().commit();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to get all users with role "user"
    public static List<User> getAllUsersWithRoleUser() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<User> query = session.createQuery("FROM User WHERE user_role = 'user'", User.class);
            List<User> users = query.getResultList();
            session.getTransaction().commit();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to delete a user by ID
    public static boolean deleteUserById(int userId) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, userId);
            if (user != null) {
                session.delete(user);
                session.getTransaction().commit();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
