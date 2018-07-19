package com.agtools.agtools;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.agtools.agtools.Model.HolidayModal;

import java.util.List;

/**
 * Created by Ranjith on 3/25/2018.
 */

public class ExchangeHistoricAdapter extends ArrayAdapter<ExchangeHistoric> {
    Context context;
    List<ExchangeHistoric> placesModalList;
    TextView textDate1;
    public ExchangeHistoricAdapter(Context context,int resourceId, List<ExchangeHistoric> placesModalList) {
        super(context, resourceId, placesModalList);
        this.context = context;
        this.placesModalList = placesModalList;
    }
    @Override
    public int getCount() {
        return placesModalList.size();
    }

   /* @Override
    public Object getItem(int position) {
        return placesModalList.get(position);
    }*/

    @Override
    public long getItemId(int position) {
        return placesModalList.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.exchange_layout, null);
        final ExchangeHistoric placesModal = placesModalList.get(position);
        textDate1= (TextView) convertView.findViewById(R.id.exchangehistoricdate);
        textDate1.setText(placesModal.getValue());
        return convertView;
    }
}
