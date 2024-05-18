package org.example.doan.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.Document;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;
import javafx.event.ActionEvent;

import java.util.List;
import java.util.Optional;

public class AdminController {

    @FXML
    private AnchorPane DocumentPane;

    @FXML
    private TextField searchTextField;

    @FXML
    private AnchorPane ComentPane;

    @FXML
    private TextField commentTextField;

    @FXML
    private Button deleteUserButton;

    @FXML
    private void initialize() {
        deleteUserButton.setOnAction(event -> displayAllUsersWithRoleUser());
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        String keyword = searchTextField.getText().trim().toUpperCase();
        if (!keyword.isEmpty()) {
            clearDocumentPane();
            searchDocuments(keyword);
        } else {
            showAlert("Warning", "Empty Search Field", "Please enter a keyword to search for documents.");
        }
    }

    @FXML
    private void handleShowOOPDocuments(ActionEvent event) {
        clearDocumentPane();
        displayDocumentsBySubject("OOP");
    }

    @FXML
    private void handleShowHTMLDocuments(ActionEvent event) {
        clearDocumentPane();
        displayDocumentsBySubject("HTML");
    }

    @FXML
    private void handleShowDSADocuments(ActionEvent event) {
        clearDocumentPane();
        displayDocumentsBySubject("DSA");
    }

    @FXML
    private void handleShowJSDocuments(ActionEvent event) {
        clearDocumentPane();
        displayDocumentsBySubject("JS");
    }

    @FXML
    private void handleShowIOTDocuments(ActionEvent event) {
        clearDocumentPane();
        displayDocumentsBySubject("IOT");
    }

    @FXML
    private void handleSearchButton(ActionEvent event) {
        String keyword = searchTextField.getText().trim().toUpperCase();
        if (!keyword.isEmpty()) {
            clearDocumentPane();
            searchDocuments(keyword);
        } else {
            showAlert("Warning", "Empty Search Field", "Please enter a keyword to search for documents.");
        }
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

                TextArea userInfo = new TextArea(user.getUserName() + " - " + user.getEmail());
                userInfo.setEditable(false);
                userInfo.setWrapText(true);
                userInfo.setLayoutY(5);
                userInfo.setPrefWidth(250);
                userInfo.setPrefHeight(30);

                Button deleteButton = new Button("Delete");
                deleteButton.setLayoutX(280);
                deleteButton.setLayoutY(5);
                deleteButton.setPrefWidth(80);
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
                showAlert("Error", "Failed to delete user.", "An error occurred while deleting the user.");
            }
        }
    }

    private void searchDocuments(String subjectCode) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();

        try {
            session.beginTransaction();

            // Lấy danh sách tài liệu từ cơ sở dữ liệu dựa trên mã môn học
            List<Document> documents = session.createQuery("from Document where subjectId = :subjectCode", Document.class)
                    .setParameter("subjectCode", subjectCode)
                    .getResultList();

            // Kiểm tra xem danh sách tài liệu có rỗng không
            if (documents.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Documents Found");
                alert.setHeaderText(null);
                alert.setContentText("Hiện tại chúng tôi chưa hỗ trợ môn học này.");
                alert.showAndWait();
                return;
            }

            // Hiển thị danh sách tài liệu trên DocumentPane
            clearDocumentPane();
            int yPosition = 10;
            for (Document document : documents) {
                TextArea textArea = new TextArea();
                textArea.setText(document.getDocumentName() + "\n" +
                        "Author: " + document.getAuthor() + "\n" +
                        "Description: " + document.getDescription());
                textArea.setPrefWidth(400);
                textArea.setPrefHeight(100);
                textArea.setLayoutX(10);
                textArea.setLayoutY(yPosition);
                yPosition += 120;
                DocumentPane.getChildren().add(textArea);
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error retrieving documents");
            alert.setContentText("An error occurred while retrieving documents from the database.");
            alert.showAndWait();
        } finally {
            session.close();
        }
    }


    private void clearDocumentPane() {
        DocumentPane.getChildren().clear();
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void displayDocumentsBySubject(String subject) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();

        try {
            session.beginTransaction();

            // Lấy danh sách tài liệu theo môn học từ cơ sở dữ liệu
            List<Document> documents = session.createQuery("from Document where subjectId = :subject", Document.class)
                    .setParameter("subject", subject)
                    .getResultList();

            // Hiển thị danh sách tài liệu trên DocumentPane
            int yPosition = 10;
            for (Document document : documents) {
                TextArea textArea = new TextArea();
                textArea.setText(document.getDocumentName() + "\n" +
                        "Author: " + document.getAuthor() + "\n" +
                        "Description: " + document.getDescription());
                textArea.setPrefWidth(400);
                textArea.setPrefHeight(100);
                textArea.setLayoutX(10);
                textArea.setLayoutY(yPosition);
                yPosition += 120;
                DocumentPane.getChildren().add(textArea);
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error retrieving documents");
            alert.setContentText("An error occurred while retrieving documents from the database.");
            alert.showAndWait();
        } finally {
            session.close();
        }
    }
}