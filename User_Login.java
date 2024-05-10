package org.example.doan.Controller;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;

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
            // Load file FXML của trang admin
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/doan/admin.fxml"));
            Parent root = loader.load();

            // Kiểm tra null trước khi sử dụng stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            if (stage != null) {
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                System.err.println("Stage is null!");
            }
        } catch (IOException e) {
            // Xử lý ngoại lệ cụ thể
            e.printStackTrace();
        }
    }

    private void goToUserPage(ActionEvent event) {
        try {
            // Load file FXML của trang admin
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/doan/user.fxml"));
            Parent root = loader.load();

            // Kiểm tra null trước khi sử dụng stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            if (stage != null) {
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                System.err.println("Stage is null!");
            }
        } catch (IOException e) {
            // Xử lý ngoại lệ cụ thể
            e.printStackTrace();
        }
    }


    @FXML
    void initialize() {
        //observable: Đối tượng ObservableValue của thuộc tính selectedProperty(), cho phép bạn truy cập thông tin về giá trị trước và sau khi thay đổi.
        //oldValue: Giá trị trước khi thay đổi của thuộc tính. Đối với thuộc tính selectedProperty(), oldValue là giá trị trạng thái của checkbox trước khi thay đổi.
        //newValue: Giá trị sau khi thay đổi của thuộc tính. Đối với thuộc tính selectedProperty(), newValue là giá trị trạng thái của checkbox sau khi thay đổi.
        showPasswordCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                passwordField.setPromptText(passwordField.getText());
                passwordField.setText("");
            } else {
                //Giả sử passwordField.getText() trả về giá trị "123456", khi dòng lệnh này được thực thi,
                // giá trị "123456" sẽ được gán vào PromptText của PasswordField.
                // Khi không có dữ liệu nhập vào PasswordField, "123456" sẽ hiển thị như một gợi ý hoặc nhãn trong ô nhập liệu.
                passwordField.setText(passwordField.getPromptText());
            }
        });
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
