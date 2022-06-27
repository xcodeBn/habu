package com.example.habuonlineshop.Classes;

public class Customer {


    private String name;
    private String email;
    private String password;
    private String location;
    private String accountcreation;
    private Boolean isverified;



    public Customer(String name, String email, String password, String location, String accountcreation,Boolean isVerified) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.location = location;
        this.accountcreation = accountcreation;
        this.isverified=isVerified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getIsverified() {
        return isverified;
    }

    public void setIsverified(Boolean isverified) {
        this.isverified = isverified;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAccountcreation() {
        return accountcreation;
    }

    public void setAccountcreation(String accountcreation) {
        this.accountcreation = accountcreation;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", location='" + location + '\'' +
                ", accountcreation='" + accountcreation + '\'' +
                ", isverified='" + isverified + '\'' +
                '}';
    }
}
