package com.example.cleanup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.ImageView;

public class WorkerActivity extends AppCompatActivity {
    Button bImageClick;
    ImageView imageSucces;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker);
        bImageClick = (Button) findViewById(R.id.clickImageButton);
        imageSucces=findViewById(R.id.imageSucces);
        if(ContextCompat.checkSelfPermission(MainActivity.this,
                ))
        /*bImageClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
    }
}