package com.example.hebunilhanli.clonegram.Fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hebunilhanli.clonegram.R;
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

public class FragmentOtherUserProfile extends Fragment {

    GridView gridView;
    private static final int NUM_GRID_COLUMNS = 3;
    ArrayAdapterProfile arrayAdapter;
    ArrayList<String> userImagesFromFB;
    private ArrayList<String> urllist;
    ArrayAdapterProfile adapterProfile;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef, myRef2, myRef3;
    LinearLayout linearLayout;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String name;
    String asd;
    TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_other_user,null,true);

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view1 = layoutInflater.inflate(R.layout.profile_information, container,false);

        textView = view1.findViewById(R.id.photoCountSize);




        Bundle c = getArguments();

        asd = c.getString("keykey");


        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();





        userImagesFromFB = new ArrayList<>();

        gridView = view.findViewById(R.id.gridView12);
        int gridWidth = getResources().getDisplayMetrics().widthPixels;
        int imageWidth = gridWidth/NUM_GRID_COLUMNS;
        gridView.setColumnWidth(imageWidth);

        adapterProfile = new ArrayAdapterProfile(userImagesFromFB,getActivity());
        gridView.setAdapter(adapterProfile);


        getDataFromFirebase();




        return view;
    }
    public void getDataFromFirebase(){
        myRef = firebaseDatabase.getReference().child("Users");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//System.currentTimeMillis() ;
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                    String ac = String.valueOf(dsp.getKey());

                        myRef2 = myRef.child(ac);
                        Query query = myRef2.orderByChild("Fullname").equalTo(asd);
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                                    String bc = String.valueOf(dsp.getKey());
                                    userImagesFromFB.add(dataSnapshot.child(bc).child("downloadurl").getValue(String.class));
                                    adapterProfile.notifyDataSetChanged();
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

