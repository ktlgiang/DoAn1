package org.example.doan.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.io.IOException;

public class User_Register {
    @FXML
    private TextField emailTexField;
    @FXML
    private  TextField usernameTexField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField passwordcofirm;
    @FXML
    Button buttonSignin;
    @FXML
    Button buttonSignup;
    public void register(ActionEvent event){
        String email= emailTexField.getText();
        String username= usernameTexField.getText();
        String password = passwordField.getText();
        String passwordcf= passwordcofirm.getText();
        if(!password.equals(passwordcf)){
            showAlert("Lỗi:","mật khẩu xác nhận không trùng khớp");
            return;
        }
        User newuser= new User(password,email,username);
        newuser.setUser_role("user");
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(newuser);
            transaction.commit();
            showAlert("Thành công:", "Đăng kí thành công");
        } catch (Exception e) {
            showAlert("Lỗi:", "Đăng kí thất bại.Vui lòng thử lại");
            e.printStackTrace();
        }
    }
    public void Sign_In(ActionEvent event){
        goToUserPage(event);
    }
    private void goToUserPage(ActionEvent event) {
        try {
            // Load file FXML của trang admin
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/doan/login.fxml"));
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
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
