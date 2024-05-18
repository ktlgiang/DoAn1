package org.example.doan.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.User;

import java.util.List;
import java.util.Optional;

public class AdminController{
    @FXML
    private AnchorPane DocumentPane;

    @FXML
    private Button deleteUserButton;

    @FXML
    public void initialize() {
        deleteUserButton.setOnAction(event -> displayAllUsersWithRoleUser());
    }


    private void displayAllUsersWithRoleUser() {
        List<User> users = User.getAllUsersWithRoleUser();
        if (users != null) {
            DocumentPane.getChildren().clear();
            int yPosition = 10;
            for (User user : users) {
                AnchorPane userPane = new AnchorPane();
                userPane.setLayoutY(yPosition);
                userPane.setPrefHeight(30);

                Text userInfo = new Text(user.getUserName() + " - " + user.getEmail());
                userInfo.setLayoutY(20);

                Button deleteButton = new Button("Delete");
                deleteButton.setLayoutX(300);
                deleteButton.setLayoutY(0);
                deleteButton.setOnAction(event -> confirmAndDeleteUser(user.getUser_id()));

                userPane.getChildren().addAll(userInfo, deleteButton);
                DocumentPane.getChildren().add(userPane);

                yPosition += 40;
            }
        }
    }

    private void confirmAndDeleteUser(int userId) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this user?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean success = User.deleteUserById(userId);
            if (success) {
                displayAllUsersWithRoleUser();
            } else {
                showAlert("Error", "Failed to delete user.");
            }
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
