package com.example.cleanup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class Admin extends AppCompatActivity {
    private Button accept,reject;
    ImageView imgwaste;
    ImageButton loc;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        loc = findViewById(R.id.loc);
        accept=(Button)findViewById(R.id.accept);
        reject=(Button)findViewById(R.id.reject);

        imgwaste=(ImageView)findViewById(R.id.imgwaste);

        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri=Uri.parse("google.navigation:q=10.602821,76.596962&mode=1");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                intent.setPackage("com.google.android.apps.maps");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Admin.this,AdminSchedule.class);
                startActivity(intent);
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Admin.this,ListActivity.class);
                startActivity(intent);
            }
        });

        StorageReference reference= FirebaseStorage.getInstance().getReference()
                .child("Uploads")
                .child("img" +".jpeg");
        final long ONE_MEGABYTE=1024*1024;
        reference.getBytes(ONE_MEGABYTE)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap=BitmapFactory.decodeByteArray(bytes,0, bytes.length);
//                        imgwaste.setImageBitmap(bitmap);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getApplicationContext(),"no such file",Toast.LENGTH_LONG).show();
            }
        });

    }

}