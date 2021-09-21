package com.example.bookmark.category_management.Model;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.bookmark.R;
import com.example.bookmark.category_management.ViewHolder.MenuViewHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class Home extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference category;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //init firebase
        database = FirebaseDatabase.getInstance();
        category = database.getReference("Category");



        //load menu
        recycler_menu = (RecyclerView)findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);

        loadMenu();


    }

    private void loadMenu() {
    }
//    FirebaseRecyclerOptions<Category> options =
//            new FirebaseRecyclerOptions.Builder<Category>()
//                    .setQuery(query, Category.class)
//                    .build();
//
//    private void loadMenu() { FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter = new }
//    }
}