package com.example.habuonlineshop.Classes;

import android.media.Image;

import java.util.Currency;

public class Product {

    private Integer id;
    private String name;
    private String weight;
    private String size;
    private Integer category;
    private String image;
    private Integer price;
    private Integer stock;



    public Product(Integer id, String name, String weight, String size, Integer category, String image, Integer price, Integer stock) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.size = size;
        this.category = category;
        this.image = image;
        this.price = price;
        this.stock = stock;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weight='" + weight + '\'' +
                ", size='" + size + '\'' +
                ", category=" + category +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}
