package com.example.hebunilhanli.clonegram.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.hebunilhanli.clonegram.R;
import com.example.hebunilhanli.clonegram.adapters.ArrayAdapter;
import com.example.hebunilhanli.clonegram.adapters.ArrayAdapterOtherUser;
import com.example.hebunilhanli.clonegram.adapters.ArrayAdapterProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentOtherUser extends Fragment {

    DatabaseReference newReference, myRef, myRef2;
    DatabaseReference newReference2;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    ArrayAdapterOtherUser adapter;
    ArrayList<String> otherFullnameFromFB;
    ArrayList<String> otherPPFromFB;
    TextView textView;
    ImageView imageView;
    FragmentPrivateProfile privateProfile;
    String s;
    ArrayAdapterProfile adapterProfile;
    ArrayList<String> images;
    TextView photoCount;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.profile_information, container,false);


        images = new ArrayList<>();


        firebaseDatabase = FirebaseDatabase.getInstance();
        newReference = firebaseDatabase.getReference();

        textView = view.findViewById(R.id.otherFullName);
        imageView = view.findViewById(R.id.otherPP);

        otherFullnameFromFB = new ArrayList<>();
        otherPPFromFB = new ArrayList<>();

        privateProfile = new FragmentPrivateProfile();

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        Bundle b = getArguments();

        s = b.getString("Username");
        String a = b.getString("UserImage");

        Picasso.get().load(a).into(imageView);
        textView.setText(s);

        setFragment(privateProfile);

        photoCount = view.findViewById(R.id.photoCountSize);




        getDataBaseSize();


        return view;


    }
    public void setFragment(Fragment fragment){

        Bundle bundle = new Bundle();
        bundle.putString("keykey",s);

        privateProfile.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.profile_frame, privateProfile );
        fragmentTransaction.commit();


    }
    public void getDataBaseSize(){
        myRef = firebaseDatabase.getReference().child("Users");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                    String ac = String.valueOf(dsp.getKey());

                    myRef2 = myRef.child(ac);
                    Query query = myRef2.orderByChild("Fullname").equalTo(s);
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                String bc = String.valueOf(dsp.getKey());
                                images.add(dataSnapshot.child(bc).child("downloadurl").getValue(String.class));

                                String photosize = String.valueOf(images.size());
                                photoCount.setText(photosize);

                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }




    }

