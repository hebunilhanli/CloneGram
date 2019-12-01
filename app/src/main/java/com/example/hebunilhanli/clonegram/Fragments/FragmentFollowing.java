package com.example.hebunilhanli.clonegram.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.hebunilhanli.clonegram.R;
import com.example.hebunilhanli.clonegram.adapters.ArrayAdapterFollowing;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentFollowing extends Fragment {


    FirebaseUser user;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    private ArrayList<String> fullNameFromFB;
    private ArrayList<String> ppFromFB;
    ArrayAdapterFollowing adapterFollowing;
    ListView listView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_fragment_following, container, false);


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        fullNameFromFB = new ArrayList<>();
        ppFromFB = new ArrayList<>();


        listView = view.findViewById(R.id.listViewFollowing);

        adapterFollowing = new ArrayAdapterFollowing(fullNameFromFB,ppFromFB,getActivity());

        listView.setAdapter(adapterFollowing);


      getDataFromFB();


        return view;
    }
    public void getDataFromFB(){

        reference = database.getReference().child("Users Informations").child("user_" + user.getUid()).child("Following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    fullNameFromFB.add(String.valueOf(dsp.getKey()));
                    ppFromFB.add(String.valueOf(dsp.getValue()));
                    adapterFollowing.notifyDataSetChanged();

                }



               // ppFromFB.add(dataSnapshot.getValue(String.class));

                //adapterFollowing.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}