package services;
// Generated Feb 25, 2016 9:15:53 PM by Hibernate Tools 4.3.1

/**
 * Users generated by hbm2java
 */
public class Users implements java.io.Serializable {

    private Integer id;
    private String username;
    private String password;
    private Roles roles;

    public Users() {
    }

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public Roles getRoles() {
        return roles;
    }
    
    public void setRoles(Roles roles) {
        this.roles = roles;
    }

}
