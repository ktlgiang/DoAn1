package model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Bình_luận")
public class Comment {
    @Id
    @GeneratedValue
    @Column(name="Mã_bình_luận")
    private int cmt_Id;
    
    @Column(name="Mã_tài_liệu")
    private int docId;
    
    @ManyToOne
    @JoinColumn(name="Mã_tài_liệu", insertable = false, updatable = false) 
    private Document document_comment;
    
    // Các thuộc tính khác và constructor không thay đổi

    @Column(name="Mã_người_dùng")
    private int user_id;
    
    @ManyToOne
    @JoinColumn(name="Mã_người_dùng", insertable = false, updatable = false) 
    private User user_comment;
    
    // Các thuộc tính khác và constructor không thay đổi
    @Column(name="Nội_dung")
    private String content;
    @Column(name="Ngày_bình_luận")
    private Date uploadDateTime;
    private String userName;
    
    
    public Comment() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Comment(int docId, int user_id, String content) {
        this.docId = docId;
        this.user_id = user_id;
        this.content = content;
    }

    public Comment(int cmt_Id, int docId, int user_id, String content, Date uploadDateTime) {
        this.cmt_Id = cmt_Id;
        this.docId = docId;
        this.user_id = user_id;
        this.content = content;
        this.uploadDateTime = uploadDateTime;
    }

    public Comment(int docId, String content) {
        this.docId = docId;
        this.content = content;
    }

    public int getCmt_Id() {
        return cmt_Id;
    }

    public void setCmt_Id(int cmt_Id) {
        this.cmt_Id = cmt_Id;
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getUploadDateTime() {
        return uploadDateTime;
    }

    public void setUploadDateTime(Date uploadDateTime) {
        this.uploadDateTime = uploadDateTime;
    }
    
    
    

}
