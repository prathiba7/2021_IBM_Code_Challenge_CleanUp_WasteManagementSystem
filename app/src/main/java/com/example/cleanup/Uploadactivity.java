package com.example.cleanup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class Uploadactivity extends AppCompatActivity {
    View view;
    Button uploadbtn;
    ImageButton imgbtn;
    Bitmap bitmap;
    ImageView imageSucces;
    EditText loc;
    private static final String TAG = "UserHomeActivity";
    int TAKE_IMAGE_CODE=10001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadactivity);
        imgbtn = findViewById(R.id.uploadimg);
        imageSucces=findViewById(R.id.imageSucces);
        uploadbtn=findViewById(R.id.uploadbtn);
        loc=findViewById(R.id.loc);
        String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
        if(ContextCompat.checkSelfPermission(Uploadactivity.this,
                Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Uploadactivity.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    },
                    100);
        }
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,TAKE_IMAGE_CODE);

            }
        });
        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(bitmap!=null){
                   handleUpload(bitmap);
                   if (!loc.getText().toString().equals("")) {
                       DatabaseReference current_user_name = ref.child("Location");
                       current_user_name.setValue(loc.getText().toString());
                   }
                   Intent intent=new Intent(Uploadactivity.this,SuccesfullUploadActivity.class);
                   startActivity(intent);
               }

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_IMAGE_CODE) {
            bitmap = (Bitmap) data.getExtras().get("data");
            imageSucces.setImageBitmap(bitmap);
        }

    }
    private void handleUpload(Bitmap bitmap){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        StorageReference reference= FirebaseStorage.getInstance().getReference()
                .child("Uploads")
                .child(uid+".jpeg");
        reference.putBytes(baos.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(Uploadactivity.this,"Image Upload Success",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG,"onFailure: ",e.getCause());
                    }
                });
    }





}