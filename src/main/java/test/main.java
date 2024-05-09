package test;

import model.User;

public class main {
    public static void main(String[] args) {
        // Test case 1: Đăng nhập thành công
        String email1 = "managerIOT@gmail.com";
        String password1 = "123";
        User user1 = User.login(email1, password1);
        if (user1 != null) {
            System.out.println("Đăng nhập thành công cho người dùng: " + user1.getUserName());
        } else {
            System.out.println("Đăng nhập thất bại. Email hoặc mật khẩu không đúng.");
        }

    }
}
