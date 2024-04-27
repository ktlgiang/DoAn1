package model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name="Tài_liệu")
public class Document {
	@Id
	@GeneratedValue
	@Column(name="Mã_tài_liệu")
    private int docId;
	@Column(name="Tên_tài_liệu")
    private String documentName;
	@Column(name="Mô_tả")
    private String description;
	@Column(name="Ngày_tải")
    private Date uploadDateTime;
	@Column(name="Đường_dẫn")
    private String filePath;
	@Column(name="Tác_giả")
    private String author;
	@Column(name="Trạng_thá")
    private String fileStatus;
	@Column(name="Mã_loại_tài_liệu")
	private int documentTypeId;
	@ManyToOne
    @JoinColumn(name="Mã_loại_tài_liệu")
    private Document_Type document_Type;
	@Column(name="Mã_môn_học")
    private String subjectId;
	@Column(name="Mã_người_dùng")
	private int userId;
	@ManyToOne
	@JoinColumn(name="Mã_người_dùng")
    private User user;

    // Constructor
    public Document() {
    }
    public Document(String documentName, String description, Date uploadDateTime, String filePath, String author, String fileStatus, int documentTypeId, String subjectId, int userId) {
        this.documentName = documentName;
        this.description = description;
        this.uploadDateTime = uploadDateTime;
        this.filePath = filePath;
        this.author = author;
        this.fileStatus = fileStatus;
        this.documentTypeId = documentTypeId;
        this.subjectId = subjectId;
        this.userId = userId;
    }


    public Document(String documentName, String description, Date uploadDateTime, String filePath, String author, String fileStatus, int documentTypeId, String subjectId) {
        this.documentName = documentName;
        this.description = description;
        this.uploadDateTime = uploadDateTime;
        this.filePath = filePath;
        this.author = author;
        this.fileStatus = fileStatus;
        this.documentTypeId = documentTypeId;
        this.subjectId = subjectId;
    }

    // Getters and setters
    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getUploadDateTime() {
        return uploadDateTime;
    }

    public void setUploadDateTime(Date uploadDateTime) {
        this.uploadDateTime = uploadDateTime;
    }
  public java.sql.Date getDob() {
        return new java.sql.Date(uploadDateTime.getTime());
    }

  

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus;
    }

    public int getDocumentTypeId() {
        return documentTypeId;
    }

    public void setDocumentTypeId(int documentTypeId) {
        this.documentTypeId = documentTypeId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

  
}
