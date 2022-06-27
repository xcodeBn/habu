package com.example.habuonlineshop.Classes;

public class Sales {

    Integer id;
    String price;

    public Sales(Integer id, String price) {
        this.id = id;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String
    toString() {
        return "Sales{" +
                "id=" + id +
                ", price='" + price + '\'' +
                '}';
    }
}
