package com.example.sqlitebestdemo.bean;

/**
 * Desc:
 * Created by huanghao123 on 7/29 0029.
 */
public class Category {
    private int id;
    private String categoryName;
    private int categoryCode;

    public Category() {
    }

    public Category(int id, String categoryName, int categoryCode) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryCode = categoryCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                ", categoryCode=" + categoryCode +
                '}';
    }
}
