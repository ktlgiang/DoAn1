package org.example.doan.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.User;

import java.io.*;
import java.net.Socket;

public class User_Login {

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label messageLabel;

    @FXML
    private CheckBox showPasswordCheckBox;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    @FXML
    public void initialize() {
        try {
            socket = new Socket("127.0.0.1", 12345); // Đảm bảo rằng server đang chạy trên cổng này
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Kết nối tới server thành công!");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không thể kết nối tới server: " + e.getMessage());
        }

        showPasswordCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                passwordField.setPromptText(passwordField.getText());
                passwordField.setText("");
            } else {
                passwordField.setText(passwordField.getPromptText());
            }
        });
    }

    @FXML
    void login(ActionEvent event) {
        String email = emailTextField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Lỗi", "Email và mật khẩu là trường bắt buộc");
            return;
        }

        User user = User.login(email, password);
        if (user != null) {
            String userRole = user.getUser_role();
            SessionManager sessionManager = SessionManager.getInstance();
            sessionManager.saveLoggedInUser(user); // Lưu người dùng vào SessionManager
            sessionManager.setUserId(user.getUser_id());
            if ("IOT".equals(userRole) || "HTML".equals(userRole) || "OOP".equals(userRole) || "DSA".equals(userRole) || "JS".equals(userRole)) {
                showAlert("Thành công", "Đăng nhập thành công với quyền quản trị");
                goToAdminPage(event);
            } else {
                showAlert("Thành công", "Đăng nhập thành công với quyền người dùng");
                goToUserPage(event);
            }
        } else {
            showAlert("Lỗi", "Email hoặc mật khẩu không hợp lệ");
        }
    }

    private void goToAdminPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/doan/admin.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không thể chuyển tới trang quản trị: " + e.getMessage());
        }
    }

    private void goToUserPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/doan/user.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Lỗi", "Không thể chuyển tới trang người dùng: " + e.getMessage());
        }
    }
    @FXML
    private void goToRegister(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/doan/signup.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
