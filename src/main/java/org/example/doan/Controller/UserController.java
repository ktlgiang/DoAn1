package org.example.doan.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Document;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import java.util.List;

public class UserController {

    @FXML
    private AnchorPane DocumentPane;

    @FXML
    private TextField searchTextField;

    @FXML
    private AnchorPane ComentPane;
    @FXML
    private Label Load_username;

    public void handleLogin(String username) {
        Load_username.setText(username);
    }


    @FXML
    private void initialize() {
        // Thêm sự kiện xử lý khi người dùng nhấp vào một tài liệu trong DocumentPane
        for (Node node : DocumentPane.getChildren()) {
            if (node instanceof TextArea) {
                node.setOnMouseClicked(event -> {
                    // Lấy tài liệu tương ứng
                    Document selectedDocument = getDocumentFromDatabase(((TextArea) node).getText());
                    if (selectedDocument != null) {
                        // Hiển thị thông tin chi tiết của tài liệu trong ComentPane
                        displayDocumentDetails(selectedDocument);
                    }
                });
            }
        }
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        String keyword = searchTextField.getText().trim().toUpperCase();
        if (!keyword.isEmpty()) {
            clearDocumentPane();
            searchDocuments(keyword);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Empty Search Field");
            alert.setContentText("Please enter a keyword to search for documents.");
            alert.showAndWait();
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

    // Phương thức để xóa tất cả các nội dung trong DocumentPane
    private void clearDocumentPane() {
        DocumentPane.getChildren().clear();
    }

    private Document getDocumentFromDatabase(String documentText) {
        // Thực hiện logic để lấy thông tin chi tiết của tài liệu từ cơ sở dữ liệu
        // Giả sử bạn đã có mã logic để lấy thông tin chi tiết của tài liệu dựa trên nội dung của nó
        // Đây là nơi bạn cần triển khai logic đó
        return null;
    }

    private void displayDocumentDetails(Document document) {
        // Hiển thị thông tin chi tiết của tài liệu trong ComentPane
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

    @FXML
    private void handleSearchButton(ActionEvent event) {
        String keyword = searchTextField.getText().trim().toUpperCase();
        if (!keyword.isEmpty()) {
            clearDocumentPane();
            searchDocuments(keyword);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Empty Search Field");
            alert.setContentText("Please enter a keyword to search for documents.");
            alert.showAndWait();
        }
    }
}
