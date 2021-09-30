package com.example.cleanup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserRegitserActivity extends AppCompatActivity {
    private EditText name,address;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_regitser);
        name=(EditText)findViewById(R.id.name);
        address=(EditText)findViewById(R.id.address);
        save=(Button)findViewById(R.id.save_button);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
                if (!name.getText().toString().equals("")&&!address.getText().toString().equals("")) {
                    DatabaseReference current_user_name = ref.child("Name");
                    current_user_name.setValue(name.getText().toString());
                    DatabaseReference current_user_address = ref.child("Address");
                    current_user_address.setValue(name.getText().toString());


                    Intent intent=new Intent(UserRegitserActivity.this,UserHomeActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(UserRegitserActivity.this, "Some fields are left blank", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

}