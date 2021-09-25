package com.example.bookmark.category_management.Model;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bookmark.R;
import com.example.bookmark.category_management.Interface.ItemClickListener;
import com.example.bookmark.category_management.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;


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
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Category");
        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(query, new SnapshotParser<Category>() {

                    @NotNull
                    @Override
                    public Category parseSnapshot( @NotNull DataSnapshot snapshot) {
                        return new Category(snapshot.child("Image").getValue().toString(),
                                snapshot.child("Name").getValue().toString()
                                );
                    }
                }).build();


        FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter = new FirebaseRecyclerAdapter<Category,MenuViewHolder>(options) {
            @Override
            protected void onBindViewHolder(MenuViewHolder holder, int position, Category model) {
                holder.txtMenuName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(holder.imageView);
                final Category clickItem = model;
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View v, int position, boolean isLongClick) {
                        Toast.makeText(Home.this, ""+clickItem.getName(), Toast.LENGTH_SHORT).show();
                    }
                });
            }


            @Override
            public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return null;
            }


        };
        recycler_menu.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();

        }
    }


}