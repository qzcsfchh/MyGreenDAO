package com.hao.sqlite.bean;

/**
 * Desc:
 * Created by huanghao123 on 7/29 0029.
 */
public class Book {
    private int id;
    private String name;
    private String author;
    private float price;
    private int pages;

    public Book() {
    }

    public Book(int id, String name, String author, float price, int pages) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.price = price;
        this.pages = pages;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", pages=" + pages +
                '}';
    }
}
