package com.example.hebunilhanli.clonegram.adapters;

import android.app.Activity;

import com.example.hebunilhanli.clonegram.Fragments.FragmentOtherUserProfile;
import com.example.hebunilhanli.clonegram.R;
import com.example.hebunilhanli.clonegram.SquareImageView;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ArrayAdapterProfile extends android.widget.ArrayAdapter<String>{

    private final ArrayList<String> profileimage;
    private final Activity context;
    FragmentOtherUserProfile fragmentOtherUserProfile;



    public ArrayAdapterProfile(ArrayList<String> profileimage, Activity context) {
        super(context, R.layout.custom_profile_pic_view, profileimage);

        this.profileimage = profileimage;
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        convertView = layoutInflater.inflate(R.layout.custom_profile_pic_view,null,true);

        SquareImageView imageView = convertView.findViewById(R.id.imageView10);



        Picasso.get().load(profileimage.get(position)).into(imageView);



        getDataFromFirebase();


        return convertView;
    }
    public void getDataFromFirebase(){


    }
}
