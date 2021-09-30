package com.example.cleanup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

public class WorkerActivity extends AppCompatActivity {
    ImageButton bImageClick;
    ImageView imageSucces;
    Button bSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker);
        bImageClick =  findViewById(R.id.clickImageButton);
        imageSucces=findViewById(R.id.imageSucces);
        if(ContextCompat.checkSelfPermission(WorkerActivity.this,
                Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(WorkerActivity.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    },
                    100);
        }
        bImageClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,100);

            }
        });

        bSubmit=(Button)findViewById(R.id.submitButton);
        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(WorkerActivity.this,WorkerSuccess.class);
                startActivity(intent);
                finish();
                return;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            imageSucces.setImageBitmap(captureImage);
        }

    }
}