package semestrovka.module.entities;

import java.util.Objects;

public class UserModel {
    private int id;
    private String name;
    private String secondName;
    private String email;
    private String pass;
    private String phoneNumber;
    private String token;
    private boolean isAdmin;

    public UserModel(String name, String secondName, String email, String pass, String phoneNumber) {
        this.name = name;
        this.secondName = secondName;
        this.email = email;
        this.pass = pass;
        this.phoneNumber = phoneNumber;
        this.isAdmin = false;
    }

    public UserModel(int id, String name, String secondName, String email, String pass, String phoneNumber) {
        this(name, secondName, email, pass, phoneNumber);
        this.id = id;
    }

    public UserModel() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return id == userModel.id && Objects.equals(name, userModel.name) && Objects.equals(secondName, userModel.secondName) && Objects.equals(email, userModel.email) && Objects.equals(pass, userModel.pass) && Objects.equals(phoneNumber, userModel.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, secondName, email, pass, phoneNumber);
    }
}
