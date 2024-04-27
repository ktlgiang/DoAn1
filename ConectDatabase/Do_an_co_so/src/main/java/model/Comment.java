package model;

import java.util.Date;

public class Comment {

    private int cmt_Id;
    private int docId;
    private int user_id;
    private String content;
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
