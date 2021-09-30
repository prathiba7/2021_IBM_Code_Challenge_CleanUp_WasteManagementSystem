package com.example.cleanup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class UserLoginActivity extends AppCompatActivity {

    private EditText motp,mphno;
    private Button msendotp,mverifyotp;
    private static final String TAG = "UserLoginActivity";
    private boolean mVerificationInProgress = false;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        mphno=findViewById(R.id.editTextPhone);
        motp=findViewById(R.id.otpuser);
        msendotp=findViewById(R.id.usersendotp);
        mverifyotp=findViewById(R.id.userverifyotp);
        mAuth = FirebaseAuth.getInstance();


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                mVerificationInProgress=false;
                Toast.makeText(UserLoginActivity.this,"Verification In Progress",Toast.LENGTH_SHORT).show();
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }


            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(UserLoginActivity.this,"Verification Failed",Toast.LENGTH_SHORT).show();
                if(e instanceof FirebaseAuthInvalidCredentialsException){
                    Toast.makeText(UserLoginActivity.this,"Invalid Phone Number",Toast.LENGTH_SHORT).show();
                }
                else if(e instanceof FirebaseTooManyRequestsException){}
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Toast.makeText(UserLoginActivity.this,"Verification code has been send on your number",Toast.LENGTH_SHORT).show();
                mVerificationId=s;
                mResendToken=forceResendingToken;
                mphno.setVisibility(View.GONE);
                msendotp.setVisibility(View.GONE);
                motp.setVisibility(View.VISIBLE);
                mverifyotp.setVisibility(View.VISIBLE);

            }
        };
        msendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance()
                        .verifyPhoneNumber(mphno.getText()
                                .toString(),60, TimeUnit.SECONDS,UserLoginActivity.this,mCallbacks);
            }
        });
        mverifyotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthCredential credential=PhoneAuthProvider.getCredential(mVerificationId,motp.getText().toString());
                signInWithPhoneAuthCredential(credential);
            }
        });



    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential)
    {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            startActivity(new Intent(UserLoginActivity.this,UserRegitserActivity.class));
                            Toast.makeText(UserLoginActivity.this,"Verification Done",Toast.LENGTH_SHORT).show();
                            String user_id = mAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
                            current_user_db.setValue(true);
                            DatabaseReference current_user_ph = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id).child("Phoneno");
                            current_user_ph.setValue(mphno.getText().toString());
                        }
                        else {
                            if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(UserLoginActivity.this,"Invalid Verification",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

}