package com.example.hebunilhanli.clonegram.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.hebunilhanli.clonegram.Fragments.FragmentOtherUser;
import com.example.hebunilhanli.clonegram.Fragments.FragmentOtherUserProfile;
import com.example.hebunilhanli.clonegram.Fragments.FragmentPrivateProfile;
import com.example.hebunilhanli.clonegram.Fragments.FragmentProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import com.example.hebunilhanli.clonegram.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class ArrayAdapter extends android.widget.ArrayAdapter<String> {

    ImageButton like;

    private final ArrayList<String> fullname;
    private final ArrayList<String> userImage;
    private final ArrayList<String> usercomment;
    private ArrayList<String> asd;
    private final Activity context;
    private final ArrayList<String> userpp;
    TextView textView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    ImageView minipp;
    String username;
    ImageView imageViews;
    ImageView mGrayHeart;


    public ArrayAdapter(ArrayList<String> fullname, ArrayList<String> userImage, ArrayList<String> usercomment, ArrayList<String> userpp, Activity context) {
        super(context, R.layout.custom_view, fullname);
        this.fullname = fullname;
        this.userImage = userImage;
        this.usercomment = usercomment;
        this.userpp = userpp;
        this.context = context;

    }


    @SuppressLint("ClickableViewAccessibility")
    @NonNull
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        final LayoutInflater layoutInflater = context.getLayoutInflater();
        convertView = layoutInflater.inflate(R.layout.custom_view, null, true);

        final TextView useremailText = convertView.findViewById(R.id.userCustomEmail);
        final TextView commentText = convertView.findViewById(R.id.userCustomComment);
        final ImageView imageView = convertView.findViewById(R.id.imageView2);

        minipp = convertView.findViewById(R.id.miniProfilePic);
        imageViews = convertView.findViewById(R.id.mHeartGray);
        textView = convertView.findViewById(R.id.textView3);
        mGrayHeart = convertView.findViewById(R.id.mHeartGray);



        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();



        useremailText.setText(fullname.get(position));
        commentText.setText(usercomment.get(position));
        Picasso.get().load(userImage.get(position)).into(imageView);
        Picasso.get().load(userpp.get(position)).into(minipp);






        minipp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = fullname.get(position);


                Bundle bundle = new Bundle();
                FragmentOtherUser fragmentOtherUser = new FragmentOtherUser();


                bundle.putString("Username", fullname.get(position));
                bundle.putString("UserImage", userpp.get(position));


                fragmentOtherUser.setArguments(bundle);

                FragmentTransaction fragmentTransaction = context.getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame, fragmentOtherUser).addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        return convertView;

    }

    }









