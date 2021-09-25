package com.example.bookmark.category_management;

public class MainModel {

    String description,discountPrice,image,price,product,title;

    MainModel(){

    }

    public MainModel(String description, String discountPrice, String image, String price, String product, String title) {
        this.description = description;
        this.discountPrice = discountPrice;
        this.image = image;
        this.price = price;
        this.product = product;
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
