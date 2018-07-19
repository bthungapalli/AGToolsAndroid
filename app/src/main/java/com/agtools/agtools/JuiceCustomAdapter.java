package com.agtools.agtools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sravan.gaini on 3/29/2018.
 */

public class JuiceCustomAdapter extends ArrayAdapter<Jsuice> {
    private ArrayList<Jsuice> dataSet;
    Context mContext;
    // View lookup cache
    private static class ViewHolder {
        TextView year;
        TextView datevalue;
    }
    public JuiceCustomAdapter(ArrayList<Jsuice> data, Context context) {
        super(context, R.layout.juice_layout_historic, data);
        this.dataSet = data;
        this.mContext=context;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Jsuice dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.juice_layout_historic, parent, false);
            viewHolder.year = (TextView) convertView.findViewById(R.id.month);
            viewHolder.datevalue = (TextView) convertView.findViewById(R.id.datevalue);
            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }


        viewHolder.year.setText(dataModel.getV1());

        viewHolder.datevalue.setText(dataModel.getV2());
        // Return the completed view to render on screen
        return convertView;
    }
}
