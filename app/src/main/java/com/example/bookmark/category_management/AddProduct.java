package com.example.bookmark.category_management;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.bookmark.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddProduct extends AppCompatActivity {



    private ImageButton back,allBook;
    private EditText t1,t2,t3,discount;
    private Button addBtn;
    private ImageView img1;
    private TextView category;
    private SwitchCompat switched;
    private FirebaseAuth firebaseAuth;
    private RelativeLayout allBookRl;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    //permission
    private  static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 300;

    //IMAGE PICK

    private static final int IMAGE_PICK_GALLERY_CODE = 400;
    private static final int IMAGE_PICK_CAMERA_CODE = 500;

    //ARRAY

    private String[] cameraPermissions;
    private String[] storagePermissions;

    //image url
     private Uri image_uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_add_product);

        back = findViewById(R.id.back);
        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);
        discount = findViewById(R.id.discount);
        category = findViewById(R.id.category);
        switched = findViewById(R.id.switched);
        addBtn = findViewById(R.id.addBtn);

        img1 = findViewById(R.id.img1);


        firebaseAuth = FirebaseAuth.getInstance();


//        storage = FirebaseStorage.getInstance();
//        storageReference =storage.getReference();

        //init permission

        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};


        //discount bar
        switched.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    discount.setVisibility(View.VISIBLE);
                    //discount2.setVisibility(View.VISIBLE);
                }else {
                    discount.setVisibility(View.GONE);
                    //discount2.setVisibility(View.GONE);
                }
            }
        });




        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show image
                showImagePickDialog();
            }


        });

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pick category
                categoryDialog();
            }

            private void categoryDialog() {
                //dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(AddProduct.this);
                builder.setTitle("Book Category")
                        .setItems(Constants.bookCategories, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //get picked category
                                String product  = Constants.bookCategories[which];

                                //set
                                category.setText(product);
                            }
                        })
                        .show();
            }
        });



        addBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                inputData();
            }

//            private  String  title,desc,price;
            private void inputData() {
                //input data
                String title = t1.getText().toString().trim();
                String  desc = t2.getText().toString().trim();
                String price = t3.getText().toString().trim();
                String product = category.getText().toString().trim();
                String discountPrice = discount.getText().toString().trim();
                //String discountPrice2 = discount2.getText().toString().trim();






                //url of image received, upload to db
                Book book = new Book();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Books");




               String timestamp = ""+System.currentTimeMillis();
               String filePathAndName = "product_images/" +""+ timestamp;// name and path of image to be uploaded

                StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
                storageReference.putFile(image_uri)
                        .addOnSuccessListener(taskSnapshot -> {
                            //image uploaded
                            //get url of uploaded image
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while(!uriTask.isSuccessful());
                            Uri downloadImageUri = uriTask.getResult();
                            String url = downloadImageUri.toString();

                            if (uriTask.isSuccessful()){



                                book.setTitle(title);
                                book.setDescription(desc);
                                book.setPrice(price);
                                book.setProduct(product);
                                book.setDiscountPrice(discountPrice);

                                book.setImage(url);

                                reference.push().setValue(book);
                                Toast.makeText(AddProduct.this, "Book Added", Toast.LENGTH_SHORT).show();
                            }

                        });



//                book.setTitle(title);
//                book.setDescription(desc);
//                book.setPrice(price);
//                book.setProduct(product);
//                book.setDiscountPrice(discountPrice);
//                //book.setDiscountPrice2(discountPrice2);



                // book.setImage(downloadImageUri);

                reference.push().setValue(book);
                Toast.makeText(AddProduct.this, "Book Added", Toast.LENGTH_SHORT).show();
                clearData();














/****************************************************************************************************************/

//                String timestamp = ""+System.currentTimeMillis();
//
//                if(image_uri == null){
//                    //upload without image
//                    //set data
//
//                    HashMap<String,Object> hashMap = new HashMap<>();
//                    hashMap.put("title",""+title);
//                    hashMap.put("Description", ""+desc);
//                    hashMap.put("price", ""+price);
//                      hashMap.put("production","");
//
//                    //add to db
//                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
//                    reference.push().setValue(hashMap)
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
//                                    //added to db
//                                    Toast.makeText(AddProduct.this, "book added", Toast.LENGTH_SHORT).show();
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(Exception e) {
//                                     //failed to db
//                                    Toast.makeText(AddProduct.this, "book added", Toast.LENGTH_SHORT).show();
//
//                                }
//                            });
//
//
//                }
//                else{
//
//
//              }
           }

            private void clearData() {
                //clear data
                t1.setText("");
                t2.setText("");
                t3.setText("");
                discount.setText("");
                category.setText("");
                img1.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24);
                image_uri = null;

            }


        });



    }


    private void showImagePickDialog() {


        //display
        String[] options = {"Camera", "Gallery"};
        //dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("pick image")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0){
                            if(checkCameraPermission()){
                                pickFromCamera();

                            }
                            else {
                                requestCameraPermission();
                            }
                        }
                        else {
                            if(checkStoragePermission()){
                                pickFromGallery();
                            }
                            else {
                                requestStoragePermission();
                            }
                        }
                    }



                })
                .show();
    }



    private void pickFromGallery(){
        //image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_GALLERY_CODE);
    }

    private void pickFromCamera(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE,"temp image title");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION,"temp image DESCRIPTION");

        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);


    }

    private  boolean checkStoragePermission(){
        boolean result  = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                (PackageManager.PERMISSION_GRANTED);

        return result;
    }

    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }


    private boolean checkCameraPermission() {
        boolean result  = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                (PackageManager.PERMISSION_GRANTED);

        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                (PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }

    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    //HANDLE PERMISSION RESULT


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if (grantResults.length>0){
                    boolean cameraAccepted = grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1]==PackageManager.PERMISSION_GRANTED;

                    if(cameraAccepted && storageAccepted){
                        pickFromCamera();
                    }
                    else{
                        Toast.makeText(this,"camera & storage permissions are required...", Toast.LENGTH_SHORT).show();
                    }

                }
            }
            case STORAGE_REQUEST_CODE:{
                if (grantResults.length>0){
                    boolean storageAccepted = grantResults[0]==PackageManager.PERMISSION_GRANTED;

                    if(storageAccepted){
                        pickFromGallery();
                    }
                    else{
                        Toast.makeText(this,"storage permissions are required...", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }





        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode==RESULT_OK){
            if (requestCode == IMAGE_PICK_GALLERY_CODE){

                image_uri = data.getData();

                //set img
                img1.setImageURI(image_uri);
            }
            else if(requestCode== IMAGE_PICK_CAMERA_CODE){
                img1.setImageURI(image_uri);
            }
        }



        super.onActivityResult(requestCode, resultCode, data);
    }
}