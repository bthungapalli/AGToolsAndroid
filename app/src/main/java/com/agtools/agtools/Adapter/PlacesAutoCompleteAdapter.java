package com.agtools.agtools.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.agtools.agtools.R;
import com.agtools.agtools.ServiceProvider.PlaceAPI;

import java.util.ArrayList;

/**
 * Created by HOME on 2/3/2018.
 */
public class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {

    ArrayList<String> resultList;

    Context mContext;
    int mResource;
    PlaceAPI mPlaceAPI;

    public PlacesAutoCompleteAdapter(Context context, int resource) {
        super(context, resource);

        mContext = context;
        mResource = resource;

        mPlaceAPI= new PlaceAPI();

    }

    @Override
    public int getCount() {
        // Last item will be the footer
        return resultList.size();
    }

    @Override
    public String getItem(int position) {
        return resultList.get(position);
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    resultList = mPlaceAPI.autocomplete(constraint.toString());

                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }
        };

        return filter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //if (convertView == null) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (position != (resultList.size() - 1))
            convertView = inflater.inflate(R.layout.autocomplete_list_item, null);

        if (position != (resultList.size() - 1)) {
            TextView autocompleteTextView = (TextView) convertView.findViewById(R.id.autocompleteText);
            autocompleteTextView.setText(resultList.get(position));
        }

        return convertView;
    }
}