package com.example.bookmark.category_management;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.bookmark.R;
import com.example.bookmark.cart_management.CartActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ItemDetails extends AppCompatActivity {



    private Button addToCartBtn;
    private ImageView productImage;
    private ElegantNumberButton numberButton;
    private TextView txt2, txt3, txt1;
    private ShapeableImageView shapeableImageView;
    private  String productID= "001";
    public static String TAG = "TAG";
    public String itemkey="";
    public String imgUrl ="";



//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.item_detail);
//
//        productID = getIntent().getStringExtra("pid");
//        numberButton = (ElegantNumberButton)findViewById(R.id.number_btn);
//        addToCartBtn = (Button) findViewById(R.id.add_product_to_cart_btn);
//        numberButton = (ElegantNumberButton)findViewById(R.id.number_btn);
//        txt1 = (TextView)findViewById(R.id.product_name_details);
//        txt3 = (TextView)findViewById(R.id.product_description_details);
//        txt2 = (TextView)findViewById(R.id.product_price_details);
//
//        getProductDetails(productID);
//
//        addToCartBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addingtoToCartList();
//            }
//        });
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        itemkey = getIntent().getStringExtra("itemKey");
        numberButton = (ElegantNumberButton)findViewById(R.id.number_btn);
        addToCartBtn = (Button) findViewById(R.id.add_product_to_cart_btn);
        shapeableImageView = (ShapeableImageView)findViewById(R.id.product_image_details);
        txt1 = (TextView)findViewById(R.id.product_name_details);
        txt2 = (TextView)findViewById(R.id.product_price_details);
        txt3 = (TextView)findViewById(R.id.product_description_details);
        //txt4 = (TextView)findViewById(R.id.product_description_details);

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingtoToCartList();
            }
        });





//        String itemkey = getIntent().getStringExtra("itemKey");
//
//        ref.child(itemkey).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    String itemName = snapshot.child("title").getValue().toString();
//                    String itemprice = snapshot.child("price").getValue().toString();
//                    //String itemdis = snapshot.child("discountPrice").getValue().toString();
//                    String itemdesc = snapshot.child("description").getValue().toString();
//                    String itemimage = snapshot.child("image").getValue().toString();
//
//                    Picasso.get().load(itemimage).into(shapeableImageView);
//                    txt1.setText(itemName);
//                    txt2.setText(itemprice);
//                    txt3.setText(itemdesc);
//                   // txt4.setText(itemdesc);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference().child("Books");
        ref.child(itemkey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String itemName = snapshot.child("title").getValue().toString();
                    String itemprice = snapshot.child("price").getValue().toString();
                    // String itemdis = snapshot.child("discountPrice").getValue().toString();
                    String itemdesc = snapshot.child("description").getValue().toString();
                    String itemimage = snapshot.child("image").getValue().toString();

                    Picasso.get().load(itemimage).into(shapeableImageView);
                    txt1.setText(itemName);
                    txt2.setText(itemprice);
                    txt3.setText(itemdesc);
                    imgUrl = itemimage;


//                    txt4.setText(itemdesc);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void addingtoToCartList() {
        String SaveCurrentTime,SaveCurrentDate;

        Calendar calForDate =  Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        SaveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        SaveCurrentTime = currentTime.format(calForDate.getTime());

        DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid",itemkey);
        cartMap.put("pname",txt1.getText().toString());
        cartMap.put("price",txt2.getText().toString());
        cartMap.put("date",SaveCurrentDate);
        cartMap.put("time",SaveCurrentTime);
        cartMap.put("quantity",numberButton.getNumber());
        cartMap.put("discount","");
        cartMap.put("image",imgUrl);

        cartListRef.child("User View").child("001")
                .child("Products").child(itemkey)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful()){

                            Toast.makeText(ItemDetails.this, "Added to Cart List.",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ItemDetails.this, CartActivity.class);
                            startActivity(intent);
                        }
                    }
                });

    }




}