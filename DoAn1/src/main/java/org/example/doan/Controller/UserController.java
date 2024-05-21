package org.example.doan.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Document;
import model.Document_Type;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.HibernateUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class UserController {

    @FXML
    private AnchorPane step1;

    @FXML
    private AnchorPane step2;

    @FXML
    private AnchorPane step3;

    @FXML
    private TextField textField1;

    @FXML
    private TextField textField3;

    @FXML
    private TextField textField2;

    @FXML
    private ChoiceBox<String> choiceBox1;

    @FXML
    private ChoiceBox<String> choiceBox2;
    @FXML
    private TextField searchTextField;
    @FXML
    private AnchorPane ComentPane;
    @FXML
    private Label Load_username;

    @FXML
    private AnchorPane DocumentPane;

    @FXML
    private StackPane documentContainer;

    private File selectedFile;

    @FXML
    private void showStep1() {
        step1.setVisible(true);
        step2.setVisible(false);
        step3.setVisible(false);
    }

    @FXML
    private void showStep2() {
        step1.setVisible(false);
        step2.setVisible(true);
        step3.setVisible(false);
    }

    @FXML
    private void showStep3() {
        step1.setVisible(false);
        step2.setVisible(false);
        step3.setVisible(true);
    }

    @FXML
    private void chooseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Chọn tệp");

        selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            System.out.println("Tệp đã chọn: " + selectedFile.getAbsolutePath());
        } else {
            System.out.println("Không có tệp nào được chọn.");
        }
    }

    @FXML
    private void uploadFile(ActionEvent event) {
        int userid = SessionManager.getInstance().getUserId();
        String username = SessionManager.getInstance().getUsername();
        if (selectedFile != null) {
            try {
                String uploadDir = "upload";
                File uploadDirectory = new File(uploadDir);
                if (!uploadDirectory.exists()) {
                    uploadDirectory.mkdirs();
                }

                String data1 = textField1.getText();
                String data2 = textField2.getText();
                String data3 = textField3.getText();
                String selectedOption1 = choiceBox1.getValue();
                String selectedOption2 = choiceBox2.getValue();
                String fileName = selectedFile.getName();
                String filePath = Paths.get(uploadDir, fileName).toString();
                Files.copy(selectedFile.toPath(), Paths.get(uploadDir, fileName), StandardCopyOption.REPLACE_EXISTING);

                Long documentTypeId = getDocumentTypeId(selectedOption1);

                if (documentTypeId != null) {
                    Document document = new Document();
                    document.setDocumentName(data3);
                    document.setDescription(data2);
                    document.setDocumentTypeId(documentTypeId.intValue());
                    document.setSubjectId(selectedOption2);
                    document.setFilePath(filePath);
                    document.setUserId(userid);
                    document.setAuthor(data1);
                    document.setUploadDateTime(new java.util.Date());
                    document.setFileStatus("Chờ duyệt");
                    Session session = HibernateUtil.getSessionFactory().openSession();
                    Transaction transaction = session.beginTransaction();
                    session.save(document);
                    transaction.commit();
                    session.close();
                    System.out.println("File uploaded successfully: " + fileName);
                    showStep3();
                } else {
                    System.out.println("Không tìm thấy loại tài liệu tương ứng.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Vui lòng chọn tệp trước khi tải lên.");
        }
    }

    @FXML
    private Long getDocumentTypeId(String documentTypeName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Number> query = builder.createQuery(Number.class);
            Root<Document_Type> root = query.from(Document_Type.class);
            query.select(root.get("documentTypeId")).where(builder.equal(root.get("documentTypeName"), documentTypeName));
            Number documentTypeId = session.createQuery(query).uniqueResult();
            System.out.println("Kết quả trả về của phương thức getDocumentType: " + documentTypeId);
            if (documentTypeId != null) {
                return documentTypeId.longValue();
            } else {
                return null;
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }


    private String readFileContent(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @FXML
    private void Open() {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();

        try {
            session.beginTransaction();

            List<Document> documents = session.createQuery("from Document where fileStatus = 'Hoạt động'", Document.class)
                    .getResultList();

            DocumentPane.getChildren().clear();

            int yPosition = 10;
            for (Document document : documents) {
                TextArea textArea = new TextArea();
                String text = " ";
                text += "Tên file   : " + document.getDocumentName() + "\n";
                text += "Tác giả    : " + document.getAuthor() + "\n";
                text += "Ngày Upload: " + document.getUploadDateTime() + "\n";
                text += "Môn học    : " + document.getSubjectId() + "\n";
                text += "Trạng thái : " + document.getFileStatus();

                textArea.setText(text);
                textArea.setPrefWidth(500);
                textArea.setPrefHeight(100);
                textArea.setEditable(false);

                // Tùy chỉnh màu sắc của TextArea tùy thuộc vào trạng thái của tài liệu
                if ("Hoạt động".equals(document.getFileStatus())) {
                    textArea.setStyle("-fx-text-fill: green;");
                } else {
                    textArea.setStyle("-fx-text-fill: black;");
                }

                HBox documentBox = new HBox(10, textArea);
                documentBox.setLayoutY(yPosition);
                yPosition += 120;

                DocumentPane.getChildren().add(documentBox);

                // Thêm sự kiện xử lý double-click vào TextArea để mở tài liệu
                textArea.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2) {
                        String fileContent = readFileContent(document.getFilePath());
                        if (fileContent != null) {
                            showDocumentContent(fileContent);
                        } else {
                            System.out.println("Không thể đọc nội dung của tệp.");
                        }
                    }
                });
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

    private void showDocumentContent(String content) {
        TextArea textArea = new TextArea(content);
        textArea.setPrefWidth(600);
        textArea.setPrefHeight(500);
        textArea.setEditable(false);

        DocumentPane.getChildren().clear();
        DocumentPane.getChildren().add(textArea);
    }

    // Phương thức mở tài liệu
    private void openDocument(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                Desktop.getDesktop().open(file);
            } else {
                System.out.println("Không thể mở tệp: " + filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    public void handleLogin(String username) {
//        Load_username.setText(username);
//    }
    @FXML
    private void backToHome(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/doan/user.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void uploadMore() {
        showStep1();
    }
   @FXML
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

