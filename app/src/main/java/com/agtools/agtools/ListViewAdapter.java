package com.agtools.agtools;

/**
 * Created by ranjithkumar.g on 3/22/2018.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Belal on 9/5/2017.
 */

public class ListViewAdapter extends ArrayAdapter<ModelPackage> {
    private List<ModelPackage> heroList;
    private Context mCtx;
    public ListViewAdapter(List<ModelPackage> heroList, Context mCtx) {
        super(mCtx, R.layout.list_items, heroList);
        this.heroList = heroList;
        this.mCtx = mCtx;
    }
    //this method will return the list item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View listViewItem = inflater.inflate(R.layout.list_items, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        //Getting the hero for the specified position
        ModelPackage hero = heroList.get(position);
        //setting hero values to textviews
        textViewName.setText(hero.getPackagename());
        //returning the listitem
        return listViewItem;
    }
}