package org.example.doan.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
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

public class AdminController {

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
                    document.setFileStatus("Hoạt động");
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

    @FXML
    private void Browser() {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();

        try {
            session.beginTransaction();

            List<Document> documents = session.createQuery("from Document where fileStatus = 'Chờ duyệt'", Document.class)
                    .getResultList();

            DocumentPane.getChildren().clear();

            int yPosition = 10;
            for (Document document : documents) {
                TextArea textArea = new TextArea();
                String text = " ";
                text += "Tên file   : " + document.getDocumentName()
                        + "\n";
                text += "Tác giả    : " + document.getAuthor() + "\n";
                text += "Ngày Upload:" + document.getUploadDateTime() + "\n";
                text += "Môn học    :" + document.getSubjectId() + "\n";
                text += "Trạng thái :" + document.getFileStatus();

                textArea.setText(text);
                textArea.setPrefWidth(500);
                textArea.setPrefHeight(100);
                textArea.setEditable(false);

                if ("Chờ duyệt".equals(document.getFileStatus())) {
                    textArea.setStyle("-fx-text-fill: green;");
                } else {
                    textArea.setStyle("-fx-text-fill: black;");
                }

                textArea.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2) {
                        openDocument(document.getFilePath());
                    }
                });


                HBox documentBox = new HBox(10, textArea);
                if (!document.getFileStatus().equals("Hoạt động")) {
                    Button acceptButton = new Button("Accept");
                    acceptButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                    acceptButton.setOnAction(event -> handleAccept(document));
                    documentBox.getChildren().add(acceptButton);
                }

                Button dutyButton = new Button("Duty");

                dutyButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                dutyButton.setOnAction(event -> handleDuty(document));
                documentBox.getChildren().add(dutyButton);

                documentBox.setLayoutY(yPosition);
                yPosition += 120;

                DocumentPane.getChildren().add(documentBox);
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

    @FXML
    private void handleAccept(Document document) {
        updateDocumentStatus(document.getDocId(), "Hoạt động");
        System.out.println("Accepted: " + document.getDocumentName());
        Browser(); // Reload documents
    }

    private void updateDocumentStatus(int documentId, String status) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Document document = session.get(Document.class, documentId);
            if (document != null) {
                document.setFileStatus(status);
                session.update(document);
                transaction.commit();
                System.out.println("Trạng thái của tài liệu đã được cập nhật thành: " + status);
            } else {
                System.out.println("Không tìm thấy tài liệu có ID: " + documentId);
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    private void handleDuty(Document document) {
        System.out.println("Duty: " + document.getDocumentName());
    }

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
}
