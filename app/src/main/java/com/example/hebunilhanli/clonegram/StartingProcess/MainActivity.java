package com.example.hebunilhanli.clonegram.StartingProcess;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.hebunilhanli.clonegram.Fragments.FragmentDm;
import com.example.hebunilhanli.clonegram.Fragments.FragmentMainPage;
import com.example.hebunilhanli.clonegram.Fragments.FragmentNotification;
import com.example.hebunilhanli.clonegram.Fragments.FragmentProfile;
import com.example.hebunilhanli.clonegram.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private static final int OPEN_MEDIA = 2;
    TextView textView;
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private FragmentMainPage home;
    private FragmentDm dm;
    private FragmentProfile profile;
    private Fragment otherProfile;
    private FragmentNotification notify;
    private DrawerLayout drawerLayout;
    static Uri selected;
    ImageView imageView;
    static Bitmap bitmap;
    static Bitmap imageBitmap;
    static Bundle image;
    ImageView navpp;
    TextView navusername;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    FirebaseUser user;
    FirebaseAuth mAuth;
    NavigationView navigationView;
    ImageView minipp;





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_post, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.choosee) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
            }
        } else if (item.getItemId() ==R.id.take)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 3);
            } else {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 4);
            }
        }else if (item.getItemId() == R.id.logout) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
                startActivity(intent);
            }




        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = findViewById(R.id.textView2);
        bottomNavigationView = findViewById(R.id.main_nav);
        frameLayout = findViewById(R.id.main_frame);

        home = new FragmentMainPage();
        notify = new FragmentNotification();
        dm = new FragmentDm();
        profile = new FragmentProfile();
        otherProfile = new Fragment();

        imageView = findViewById(R.id.imageView);





        setFragment(home);




        mAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();

        user = mAuth.getCurrentUser();

        navigationView = findViewById(R.id.nav_view);




        View view = navigationView.inflateHeaderView(R.layout.nav_header);
        Log.e("Check It", (view == null) ? "View is null " : "View not null");

       // assert view != null;
        navpp = view.findViewById(R.id.navProfilePP);
        Log.e("Check It", (navpp == null) ? "View is null " : "View not null");

        navusername = view.findViewById(R.id.navFullname);














        /*---------------------------------------------*/


        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.main_content);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getfirebasedatabase();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.homeBar:
                        textView.setBackgroundResource(R.drawable.hometext);
                        setFragment(home);
                        return true;

                    case R.id.notify:
                        textView.setBackgroundResource(R.drawable.notifytext);
                        setFragment(notify);
                        return true;


                    case R.id.profile:
                        textView.setBackgroundResource(R.drawable.profiletext);
                        setFragment(profile);
                        return true;

                    case R.id.dm:

                        textView.setBackgroundResource(R.drawable.dmtext);
                        setFragment(dm);
                        return true;

                    default:
                        return false;


                }
            }


        });


    }


    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);

        fragmentTransaction.commit();

    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);

        } else {

            super.onBackPressed();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, MainActivity.OPEN_MEDIA );
            }
        } else if (requestCode == 3) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intentcam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentcam, 4);
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {

            selected = data.getData();


        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selected);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
            byte[] bytes = stream.toByteArray();
            Intent intent = new Intent(getApplicationContext(),add_post.class);
            intent.putExtra("image" , bytes );
            startActivity(intent);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }else if (requestCode == 4 && resultCode == RESULT_OK && data != null){

            image = data.getExtras();

            try {
                imageBitmap = (Bitmap) image.get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                byte[] bytes = stream.toByteArray();
                Intent intent = new Intent(getApplicationContext(),add_post.class);
                intent.putExtra( "image"  ,bytes );
                startActivity(intent);

            }catch (Exception e){
                e.printStackTrace();
            }


        }

        super.onActivityResult(requestCode, resultCode, data);
    }

        public void getfirebasedatabase(){

        myRef = firebaseDatabase.getReference("Users Informations").child("user_" + user.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String url = dataSnapshot.child("DownloadURL").getValue(String.class);
                Log.e("URL IS = ", url);
                Picasso.get().load(url).into(navpp);

                String fullname = dataSnapshot.child("Fullname").getValue(String.class);
                navusername.setText(fullname);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}