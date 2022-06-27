package com.example.habuonlineshop.Classes;

public class Category {

    private int id;
    private String name;
    private String imagename;

    public Category(int id, String name, String imagename) {
        this.id = id;
        this.name = name;
        this.imagename = imagename;
    }

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

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imagename='" + imagename + '\'' +
                '}';
    }
}
