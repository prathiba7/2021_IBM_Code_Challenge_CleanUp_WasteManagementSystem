package com.example.cleanup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.ImageButton;

import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import static android.app.Activity.RESULT_OK;

public class UserHomeFragmentActivity extends Fragment {

    View view;
    Button rButton;
        ImageView image;
    ImageButton imgbtn;
    Bitmap bitmap;
    private static final String TAG = "UserHomeFragmentActivity";
    int TAKE_IMAGE_CODE=10001;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_user_home_fragment, container, false);
        if(ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{
                            Manifest.permission.CAMERA
                    },
                    100);
        }
        image=(ImageView)view.findViewById(R.id.imageSucces);
        imgbtn = (ImageButton) view.findViewById(R.id.uploadimg);
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,100);

            }
        });
//        imgbtn.setOnClickListener(this);
        rButton = (Button) view.findViewById(R.id.uploadbtn);
//        rButton.setOnClickListener(this);
        rButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bitmap!=null){
                    handleUpload(bitmap);
//                    if (!loc.getText().toString().equals("")) {
//                        DatabaseReference current_user_name = ref.child("Location");
//                        current_user_name.setValue(loc.getText().toString());
//                    }
                    Intent intent=new Intent(getActivity().getBaseContext(),SuccesfullUploadActivity.class);
                    startActivity(intent);
                }
                else{
               Toast.makeText(getContext(),"No Image Added",Toast.LENGTH_SHORT).show();
             }

            }
        });
        return view;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            switch (resultCode){
                case  RESULT_OK:
                    bitmap=(Bitmap) data.getExtras().get("data");
                    image.setImageBitmap(bitmap);

            }
        }
    }
    private void handleUpload(Bitmap bitmap){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
//        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        StorageReference reference= FirebaseStorage.getInstance().getReference()
                .child("Uploads")
                .child("img"+".jpeg");
        reference.putBytes(baos.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getContext(),"Image Upload Success",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG,"onFailure: ",e.getCause());
                    }
                });
    }
}