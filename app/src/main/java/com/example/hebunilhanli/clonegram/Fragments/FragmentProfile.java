package com.example.hebunilhanli.clonegram.Fragments;

import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

public class FragmentProfile extends android.app.Fragment {

    ArrayList<String> userImagesFromFB;
    TextView nameSurnameFromFB;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    GridView gridView;
    ArrayAdapterProfile arrayAdapter;
    DatabaseReference newReference;
    DatabaseReference newReference2;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    TextView nameSurname;
    ImageView profilePictures;
    StorageReference mStoreageReference;
    ArrayList<String> profilePicture;
    TextView photoCounts;
    private static final int NUM_GRID_COLUMNS = 3;
    FragmentOtherUserProfile otherUserProfile;
    TextView followingSize;
    TextView followerSize;







    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        userImagesFromFB = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();


        nameSurname = view.findViewById(R.id.nameSurnameText);

        otherUserProfile = new FragmentOtherUserProfile();

        profilePictures = view.findViewById(R.id.imageView3);
        mStoreageReference = FirebaseStorage.getInstance().getReference();
        photoCounts = view.findViewById(R.id.photoCount);
        followingSize = view.findViewById(R.id.followingSize);
        followerSize = view.findViewById(R.id.followerSize);
        LinearLayout followingList = view.findViewById(R.id.followingList);

        followingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentFollowing following = new FragmentFollowing();

                FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                transaction.replace(R.id.main_frame,following).addToBackStack(null);
                transaction.commit();



            }
        });








        firebaseUser = mAuth.getCurrentUser();


        gridView = view.findViewById(R.id.gridView);
        int gridWidth = getResources().getDisplayMetrics().widthPixels;
        int imageWidth = gridWidth/NUM_GRID_COLUMNS;
        gridView.setColumnWidth(imageWidth);


        arrayAdapter = new ArrayAdapterProfile(userImagesFromFB, getActivity());

        gridView.setAdapter(arrayAdapter);


        getDataFromFirebase();










        return view;
    }

    public void getDataFromFirebase() {


        //Log.e (   "CHECKKKKKKKKKKKKK" , firebaseUser..toString()  + ""  ) ;
        Log.e("CHECKKKKKKKKKKKKK", (firebaseUser == null) ? "firebaseUser is null " : "firebaseUser not null");

        newReference = firebaseDatabase.getReference("Users").child("user_" + firebaseUser.getUid());

        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    HashMap<String, String> hashMap = (HashMap<String, String>) ds.getValue();
                    userImagesFromFB.add(hashMap.get("downloadurl"));


                    arrayAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        newReference2 = firebaseDatabase.getReference("Users Informations").child("user_" + firebaseUser.getUid());

        newReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String url = dataSnapshot.child("DownloadURL").getValue(String.class);
                    Picasso.get().load(url).into(profilePictures);

                    String fullname = dataSnapshot.child("Fullname").getValue(String.class);
                    nameSurname.setText(fullname);

                    String photoCount = String.valueOf(userImagesFromFB.size());
                    photoCounts.setText(photoCount);

                    String followingCount = String.valueOf(dataSnapshot.child("Following").getChildrenCount());
                    followingSize.setText(followingCount);

                    String followerCount = String.valueOf(dataSnapshot.child("Follower").getChildrenCount());
                    followerSize.setText(followerCount);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
