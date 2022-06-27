package com.example.habuonlineshop.Classes;

public class Order {

    private Integer id;
    private String pname;
    private String email;
    private String province;
    private String city;
    private String street;
    private String progress;
    private Integer price;
    private String date;
    private String recipient;

    public Order(Integer id, String pname, String email, String province, String city, String street, String progress, Integer price, String date, String recipient) {
        this.id = id;
        this.pname = pname;
        this.email = email;
        this.province = province;
        this.city = city;
        this.street = street;
        this.progress = progress;
        this.price = price;
        this.date = date;
        this.recipient = recipient;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", pname='" + pname + '\'' +
                ", email='" + email + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", progress='" + progress + '\'' +
                ", price=" + price +
                ", date='" + date + '\'' +
                ", recipient='" + recipient + '\'' +
                '}';
    }
}
