package model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name= "Loại_tài_liệu")

public class Document_Type  {
    @Id
    @Column(name="Mã_loại_tài_liệu")
    private int documentTypeId;
    @Column(name="Tên_loại")
    private String documentTypeName;
    @OneToMany(mappedBy = "document_Type")
    private List<Document>list_Document;

    public Document_Type() {
    }

    public Document_Type(String documentTypeName, String documentName, String description, Date uploadDateTime, String filePath, String author, String fileStatus, int documentTypeId, String subjectId) {
        super();
        this.documentTypeName = documentTypeName;
    }

    public String getDocumentTypeName() {
        return documentTypeName;
    }

    public void setDocumentTypeName(String documentTypeName) {
        this.documentTypeName = documentTypeName;
    }

}