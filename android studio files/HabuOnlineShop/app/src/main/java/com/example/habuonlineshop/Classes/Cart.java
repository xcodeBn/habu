package com.example.habuonlineshop.Classes;

public class Cart {

    private Integer id;
    private String email;
    private Integer productid;

    public Cart(Integer id, String email, Integer productid) {
        this.id = id;
        this.email = email;
        this.productid = productid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }
}
