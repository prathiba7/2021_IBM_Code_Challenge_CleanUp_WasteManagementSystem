package com.example.cleanup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private Button buser,badmin;
    final private int REQ_PERMISSION=1;
    private Button bworker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buser=(Button)findViewById(R.id.user);
        badmin=(Button)findViewById(R.id.admin);


        buser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,UserLoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        }
        );
        badmin.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
                                         Intent intent=new Intent(MainActivity.this,ListActivity.class);
                                         startActivity(intent);
                                         finish();
                                         return;
                                     }
                                 }
        );


        bworker=(Button)findViewById(R.id.worker);
        bworker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,WorkerActivity.class);
                startActivity(intent);
                finish();
                return;

            }
        });




    }


}