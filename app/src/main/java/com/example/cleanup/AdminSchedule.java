package com.example.cleanup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminSchedule extends AppCompatActivity {
    Button collector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_schedule);
        collector= (Button) findViewById(R.id.collector);
        collector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminSchedule.this,AssignSuccess.class);
                startActivity(intent);
            }
        });
    }



}