package com.agtools.agtools;

/**
 * Created by Ranjith on 3/30/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.agtools.agtools.Constants.SPHelper;
import com.agtools.agtools.DashboardActivity;
import com.agtools.agtools.Model.PlacesModal;
import com.agtools.agtools.R;


import java.util.List;

import static com.agtools.agtools.Constants.SPHelper.KEY_LIVEWEATHERCITY1;
import static com.agtools.agtools.Constants.SPHelper.KEY_LIVEWEATHERCITY2;

/**
 * Created by soulsticesoftware on 03-03-2018.
 */

public class PlaceAdapterWeather extends BaseAdapter{
    Context context;
    List<PlacesModal> placesModalList;
    TextView textplaceName;
    public PlaceAdapterWeather(Context context, List<PlacesModal> placesModalList) {
        this.context = context;
        this.placesModalList = placesModalList;
    }

    @Override
    public int getCount() {
        return placesModalList.size();
    }

    @Override
    public Object getItem(int position) {
        return placesModalList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return placesModalList.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.placeadaptertext, null);
        textplaceName = (TextView)convertView.findViewById(R.id.textplaceName);
        final PlacesModal placesModal = placesModalList.get(position);
        textplaceName.setText(placesModal.getPlaceName());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent placesIntent = new Intent(context,WeatherHistoricActivity.class);
                placesIntent.putExtra("placeName",placesModal.getPlaceName());
                context.startActivity(placesIntent);
                ((WeatherHistoricActivity) context).finish();
            }
        });
        return convertView;
    }
}