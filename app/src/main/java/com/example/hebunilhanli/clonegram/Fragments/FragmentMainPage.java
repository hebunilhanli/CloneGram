package com.example.hebunilhanli.clonegram.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;


import com.example.hebunilhanli.clonegram.R;
import com.example.hebunilhanli.clonegram.adapters.ArrayAdapter;
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

public class FragmentMainPage extends android.app.Fragment {

    ArrayList<String> fullnameFromFB;
    ArrayList<String> userimageFromFB;
    ArrayList<String> usercommentFromFB;
    ArrayList<String> userppFromFB;
    ArrayList<String> userlikeFromFB;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    ArrayAdapter adapter;
    ListView listView;
    DatabaseReference newReference;
    FirebaseAuth mAuth;
    FirebaseUser user;







    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mainpage, container, false);


        fullnameFromFB = new ArrayList<>();
        usercommentFromFB = new ArrayList<>();
        userimageFromFB = new ArrayList<>();
        userppFromFB = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        user = mAuth.getCurrentUser();


        adapter = new ArrayAdapter(fullnameFromFB, userimageFromFB, usercommentFromFB, userppFromFB,getActivity());
        listView = view.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        getDataFromFirebase();

        return view;

    }

    protected void getDataFromFirebase() {
        newReference = firebaseDatabase.getReference("All Posts");
        Query query = newReference.orderByKey();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                    fullnameFromFB.add(hashMap.get("Fullname"));
                    userimageFromFB.add(hashMap.get("downloadurl"));
                    usercommentFromFB.add(hashMap.get("comment"));
                    userppFromFB.add(hashMap.get("ProfilePP"));

                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    }



