package vn.lvc.quanlychitieu.dao;

public class UserNameAttribute {
    public int id;
    public String userName;
    public String email;
    public String phoneNumber;
    public String password;

    public UserNameAttribute() {
    }

    public UserNameAttribute(int id, String userName, String email,
                         String phoneNumber, String password) {
        id = id;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
    public String toString() {
        return  userName + "\n" + email + "\n" + phoneNumber + "\n" + password;

    }
}
