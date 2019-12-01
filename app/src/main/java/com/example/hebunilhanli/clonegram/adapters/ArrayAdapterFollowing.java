package com.example.hebunilhanli.clonegram.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hebunilhanli.clonegram.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;

public class ArrayAdapterFollowing extends android.widget.ArrayAdapter<String> {

    private final ArrayList<String> followingName;
    private final ArrayList<String> followingPP;
    private ArrayList<String> tick;
    private final Activity context;
    ImageView minipp;
    ImageView check;
    TextView fullname;


    public ArrayAdapterFollowing(ArrayList<String> followingName, ArrayList<String> followingPP, Activity context) {
        super(context, R.layout.custom_following_layout, followingName);

        this.followingName = followingName;
        this.followingPP = followingPP;
        this.context = context;
    }

    public View getView(final int position, View convertView, final ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(R.layout.custom_following_layout,null,true);

        minipp = convertView.findViewById(R.id.minipp);
        check = convertView.findViewById(R.id.check);
        fullname = convertView.findViewById(R.id.fullname);

        Picasso.get().load(followingPP.get(position)).into(minipp);
        fullname.setText(followingName.get(position));


        return convertView;
    }
}
