package com.example.habuonlineshop.Classes;

public class MutableCart {

    private String email;
    private Integer pid;
    private String pname;
    private String image;
    private Integer price;

    public MutableCart(String email, Integer pid, String pname, String image, Integer price) {
        this.email = email;
        this.pid = pid;
        this.pname = pname;
        this.image = image;
        this.price = price;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "MutableCart{" +
                "email='" + email + '\'' +
                ", pid=" + pid +
                ", pname='" + pname + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                '}';
    }
}
