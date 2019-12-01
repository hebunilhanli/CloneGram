package com.example.hebunilhanli.clonegram.Fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hebunilhanli.clonegram.R;
import com.example.hebunilhanli.clonegram.adapters.ArrayAdapter;
import com.example.hebunilhanli.clonegram.adapters.ArrayAdapterProfile;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentPrivateProfile extends Fragment {

    Button button;
    Handler handler;
    Runnable runnable;
    int time;
    FragmentOtherUserProfile otherUserProfile;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference myRef, myRef2;
    FirebaseDatabase firebaseDatabase;
    String s;
    String pp;
    String userUID;
    String followerName;
    String followerPP;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_private_layout,null,true);


        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        myRef2 = firebaseDatabase.getReference();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        Bundle b = getArguments();

        s = b.getString("keykey");



        button = view.findViewById(R.id.followUser);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                button.setBackgroundResource(R.drawable.followed);

                getDataFromFirebase();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        otherUserProfile = new FragmentOtherUserProfile();

                        Bundle b = new Bundle();
                        b.putString("keykey",s);


                        otherUserProfile.setArguments(b);
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.profile_frame, otherUserProfile );
                        fragmentTransaction.commit();

                    }
                },1000);

           }
        });






        return view;
    }

    public void getDataFromFirebase(){
        myRef = firebaseDatabase.getReference().child("Users Informations");
        Query query = myRef.orderByChild("Fullname").equalTo(s);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                    userUID = String.valueOf(dsp.getKey());
                    //Log.e("NAME", followerName);
                    //Log.e("PHOTO", followerPP);

                }

                pp = dataSnapshot.child(userUID).child("DownloadURL").getValue(String.class);
                myRef.child("user_" + user.getUid()).child("Following").child(s).setValue(pp);

                myRef2 = firebaseDatabase.getReference("Users Informations");
                myRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        followerName = dataSnapshot.child("user_" + user.getUid()).child("Fullname").getValue(String.class);
                        followerPP = dataSnapshot.child("user_" + user.getUid()).child("DownloadURL").getValue(String.class);
                        myRef2.child(userUID).child("Follower").child(followerName).setValue(followerPP);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });




    }



}
