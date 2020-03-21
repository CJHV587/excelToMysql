package com.aliyun.sqlsample.entity;

public class Category {
    private String id;
    private String name;
    private String detail;
    private String picture;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Category() {
    }

    public Category(String id, String name, String detail, String picture) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", detail='" + detail + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
