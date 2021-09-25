package com.example.bookmark.category_management;

public class Book {

    private String title, description, price, product, discountPrice, image;
    //private Uri image;

    public Book() {
    }

    public Book(String title, String description, String price, String product, String discountPrice, String image) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
        this.product = product;
        this.discountPrice = discountPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) { this.price = price; }

    public String getDiscountPrice() { return discountPrice; }

    public void setDiscountPrice(String discountPrice) { this.discountPrice = discountPrice; }


}


