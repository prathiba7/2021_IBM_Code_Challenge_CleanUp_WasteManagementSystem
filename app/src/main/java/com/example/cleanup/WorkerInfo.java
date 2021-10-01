package com.example.cleanup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WorkerInfo extends AppCompatActivity {
    private Button loc;
    private Button mark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_info);
        loc=(Button)findViewById(R.id.loc1);
        loc.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       Intent intent=new Intent(WorkerInfo.this,AdminMapActivity.class);
                                       startActivity(intent);
                                       finish();
                                       return;
                                   }
                               }
        );
        mark=(Button)findViewById(R.id.mark);
        mark.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       Intent intent=new Intent(WorkerInfo.this,WorkerActivity.class);
                                       startActivity(intent);
                                       finish();
                                       return;
                                   }
                               }
        );
    }

};