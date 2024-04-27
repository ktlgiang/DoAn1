package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Người_dùng")
public class User {
	@Id
	@GeneratedValue
	@Column(name="Mã_người_dùng")
    private int user_id;
	@Column(name="Mật_khẩu")
    private String password;
	@Column(name="Email")
    private String email;
	@Column(name="Tên_người_dùng")
    private String userName;
	@Column(name="Vai_trò")
    private String user_role;
    
    public User() {
    }

    public User(String email, String userName) {
        this.email = email;
        this.userName = userName;
    }

    
    public User(String password, String email, String userName) {
        this.password = password;
        this.email = email;
        this.userName = userName;
    }

    
    public User(int user_id, String password, String email, String user_role) {
        this.user_id = user_id;
        this.password = password;
        this.email = email;
        this.user_role = user_role;
    }
    
    public User(int user_id, String password, String email, String userName, String user_role) {
        this.user_id = user_id;
        this.password = password;
        this.email = email;
        this.userName = userName;
        this.user_role = user_role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

  


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

}
