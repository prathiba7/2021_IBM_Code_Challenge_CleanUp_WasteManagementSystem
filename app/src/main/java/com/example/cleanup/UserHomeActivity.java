package com.example.cleanup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;



import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserHomeActivity extends AppCompatActivity {
    DrawerLayout drawer;
    NavigationView navigationView;
    FrameLayout frameLayout;
    ImageView imageView;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    View header;
    TextView name,phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        toolbar=(Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView=(NavigationView)findViewById(R.id.nav_view);
        frameLayout=(FrameLayout)findViewById(R.id.frame);
        header=navigationView.getHeaderView(0);
        imageView=(ImageView)header.findViewById(R.id.nav_img) ;
        name=(TextView)header.findViewById(R.id.username);
        phone=(TextView)header.findViewById(R.id.userphone);

        if(ContextCompat.checkSelfPermission(UserHomeActivity.this,
                Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(UserHomeActivity.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    },
                    100);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        loadFragment(new UserHomeFragmentActivity());
//        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
//        String user_id=user.getUid();
//        DatabaseReference dr = FirebaseDatabase.getInstance().getReference();
//        dr.child("Users").child("Riders").child(user_id).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String uname=snapshot.child("Name").getValue().toString();
//                String phno=snapshot.child("Phoneno").getValue().toString();
//                name.setText(uname);
//                phone.setText(phno);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();

                 if(id==R.id.nav_Home) {
                     loadFragment(new UserHomeFragmentActivity());
                 }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();}
}
    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.commit();
    }
}