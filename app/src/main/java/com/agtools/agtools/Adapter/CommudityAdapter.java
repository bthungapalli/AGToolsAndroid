package com.agtools.agtools.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.agtools.agtools.R;

import java.util.List;

/**
 * Created by HOME on 1/21/2018.
 */
public class CommudityAdapter extends ArrayAdapter<String> {

    LayoutInflater flater;
    List<String> commudityResponseList;

    public CommudityAdapter(Activity context,int resouceId, int textviewId, List<String> list){
        super(context,resouceId,textviewId, list);
        flater = context.getLayoutInflater();
        this.commudityResponseList=list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowview = flater.inflate(R.layout.commudity_dropdown,null);
        TextView txtTitle = (TextView) rowview.findViewById(R.id.commudity_textView);
        txtTitle.setText(commudityResponseList.get(position));

        return rowview;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = flater.inflate(R.layout.commudity_dropdown,null);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.commudity_textView);
        txtTitle.setText(commudityResponseList.get(position));

        return convertView;
    }
}