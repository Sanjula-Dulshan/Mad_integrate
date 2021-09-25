package com.example.bookmark.category_management;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookmark.R;

public class MainActivity extends AppCompatActivity {


    private ImageButton logoutBtn,edit,addProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        addProduct.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //open edit add product
                startActivity(new Intent(MainActivity.this, AddProduct.class));
            }
        });


    }

}