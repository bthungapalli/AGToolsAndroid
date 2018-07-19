package com.agtools.agtools;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ranjith on 5/19/2018.
 */

public class SubAdapter extends BaseAdapter {
    private Activity activity;
    List<ExchangeHistoric> placesModalList;
    TextView textDate1;
    public SubAdapter(Activity activity, List<ExchangeHistoric> placesModalList) {
        // super(context, placesModalList);
        this.activity = activity;
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.exchange_layout, null);
        final ExchangeHistoric placesModal = placesModalList.get(position);
        textDate1= (TextView) convertView.findViewById(R.id.exchangehistoricdate);
        textDate1.setText(placesModal.getValue());
        return convertView;
    }
}