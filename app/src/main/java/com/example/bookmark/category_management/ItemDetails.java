package com.example.bookmark.category_management;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookmark.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ItemDetails extends AppCompatActivity {

    ShapeableImageView shapeableImageView;
    TextView txt1,txt2,txt3,txt4;


    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        shapeableImageView = (ShapeableImageView)findViewById(R.id.img3);
        txt1 = (TextView)findViewById(R.id.nameText3);
        txt2 = (TextView)findViewById(R.id.price3);
        txt3 = (TextView)findViewById(R.id.discountPrice3);
        txt4 = (TextView)findViewById(R.id.desc3);
        ref = FirebaseDatabase.getInstance().getReference().child("Books");



        String itemkey = getIntent().getStringExtra("itemKey");

        ref.child(itemkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String itemName = snapshot.child("title").getValue().toString();
                    String itemprice = snapshot.child("price").getValue().toString();
                    String itemdis = snapshot.child("discountPrice").getValue().toString();
                    String itemdesc = snapshot.child("description").getValue().toString();
                    String itemimage = snapshot.child("image").getValue().toString();

                    Picasso.get().load(itemimage).into(shapeableImageView);
                    txt1.setText(itemName);
                    txt2.setText(itemprice);
                    txt3.setText(itemdis);
                    txt4.setText(itemdesc);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}