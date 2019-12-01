package com.example.hebunilhanli.clonegram.adapters;

import android.app.Activity;
import android.media.Image;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hebunilhanli.clonegram.R;
import com.example.hebunilhanli.clonegram.SquareImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArrayAdapterOtherUser extends android.widget.ArrayAdapter<String> {

   private final ArrayList<String> profileimage;
   private final ArrayList<String> profileUserName;
   Activity activity;



    public ArrayAdapterOtherUser(ArrayList<String> profileimage, ArrayList<String> profileUserName, Activity activity) {
        super(activity, R.layout.profile_information,profileUserName);

        this.profileimage = profileimage;
        this.profileUserName = profileUserName;
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        convertView = layoutInflater.inflate(R.layout.profile_information,null,true);

        ImageView otherpp = convertView.findViewById(R.id.otherPP);
        TextView textView = convertView.findViewById(R.id.otherFullName);

        Picasso.get().load(profileimage.get(position)).into(otherpp);
        textView.setText(profileUserName.get(position));




        return convertView;
    }
}
