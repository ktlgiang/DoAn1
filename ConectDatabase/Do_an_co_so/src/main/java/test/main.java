package test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List; // Sửa import ở đây

import model.User;
import util.HibernateUtil;

public class main {

    public static void main(String[] args) {
        // Lấy SessionFactory từ HibernateUtil
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        // Mở một phiên làm việc mới
        try (Session session = sessionFactory.openSession()) {
            // Bắt đầu giao dịch
            session.beginTransaction();

            // Truy vấn để lấy danh sách người dùng
            List<User> users = session.createQuery("FROM User", User.class).list();

            // In thông tin người dùng
            System.out.println("Danh sách người dùng:");
            for (User user : users) {
                System.out.println("ID: " + user.getUser_id());
                System.out.println("Tên người dùng: " + user.getUserName());
                System.out.println("Email: " + user.getEmail());
                System.out.println("Vai trò: " + user.getUser_role());
                System.out.println("----------------------");
            }

            // Commit giao dịch
            session.getTransaction().commit();
        } catch (Exception e) {
            // Xử lý ngoại lệ nếu có
            e.printStackTrace();
        } finally {
            // Đóng sessionFactory
            HibernateUtil.shutdown();
        }
    }
}
