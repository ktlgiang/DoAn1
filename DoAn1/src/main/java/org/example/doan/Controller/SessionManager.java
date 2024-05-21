package org.example.doan.Controller;

import model.User;

public class SessionManager {
    private static SessionManager instance;
    private User currentUser;
    private int userId;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private SessionManager() {
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
        userId = user.getUser_id(); // Lưu userid khi người dùng đăng nhập
    }

    public int getUserId() {
        return userId;
    }
    // Phương thức để lưu userId
    public void setUserId(int id) {
        userId = id;
    }
    // Phương thức để lưu thông tin người dùng đã đăng nhập
    public void saveLoggedInUser(User user) {
        currentUser = user;
        userId = user.getUser_id();
        username= user.getUserName();// Lưu userid khi người dùng đăng nhập
        // Đây là nơi bạn có thể thực hiện các hành động khác nếu cần.
    }
}
