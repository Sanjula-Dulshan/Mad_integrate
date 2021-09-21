package com.example.bookmark.category_management.Model;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Category {
       private String Name;
       private String Image;

    public Category() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}