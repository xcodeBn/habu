package com.example.habuonlineshop.Classes;

public class Review {

    Integer id;
    Integer productid;
    String email;
    String title;
    String text;
    Double rate;

    public Review(Integer id, Integer productid, String email, String title, String text, Double rate) {
        this.id = id;
        this.productid = productid;
        this.email = email;
        this.title = title;
        this.text = text;
        this.rate = rate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", productid=" + productid +
                ", email='" + email + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", rate=" + rate +
                '}';
    }
}
