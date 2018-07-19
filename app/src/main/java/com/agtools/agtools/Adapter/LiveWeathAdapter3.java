package com.agtools.agtools.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.agtools.agtools.Constants.SPHelper;
import com.agtools.agtools.DashboardActivity;
import com.agtools.agtools.Model.PlacesModal;
import com.agtools.agtools.R;

import java.util.List;

import static com.agtools.agtools.Constants.SPHelper.KEY_LIVEWEATHERCITY1;
import static com.agtools.agtools.Constants.SPHelper.KEY_LIVEWEATHERCITY3;

/**
 * Created by soulsticesoftware on 11-03-2018.
 */

public class LiveWeathAdapter3 extends BaseAdapter {
    Context context;
    List<PlacesModal> placesModalList;
    TextView textplaceName;
    public LiveWeathAdapter3(Context context, List<PlacesModal> placesModalList) {
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
                Intent placesIntent = new Intent(context,DashboardActivity.class);
               // placesIntent.putExtra("placeName3",placesModal.getPlaceName());
                SPHelper.setValueToPrefs(context,SPHelper.MyPREFERENCES,KEY_LIVEWEATHERCITY3,placesModal.getPlaceName());
                context.startActivity(placesIntent);
                ((DashboardActivity) context).finish();
            }
        });
        return convertView;
    }
}

