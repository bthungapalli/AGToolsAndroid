package com.agtools.agtools.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.agtools.agtools.Gas;
import com.agtools.agtools.Model.HolidayModal;
import com.agtools.agtools.R;

import java.util.List;


/**
 * Created by Ranjith on 3/23/2018.
 */

public class GasAdapter extends ArrayAdapter<Gas> {
    Context context;
    List<Gas> holidayModalList;

    public GasAdapter(Context context, int resourceId,List<Gas> holidayModalList) {
        super(context, resourceId, holidayModalList);
        this.context = context;
        this.holidayModalList = holidayModalList;
    }

    @Override
    public int getCount() {
        return holidayModalList.size();
    }

    /*@Override
    public Object getItem(int position) {
        return holidayModalList.get(position);
    }*/

    @Override
    public long getItemId(int position) {
        return holidayModalList.indexOf(getItem(position));
    }
    /*private view holder class*/
    private class ViewHolder {

        TextView holidaydate,holidaydesc;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder holder = null;
        Gas holidayModal = holidayModalList.get(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.gas_layout, null);
            holder = new ViewHolder();
            holder.holidaydate = (TextView)convertView.findViewById(R.id.valdate);
            holder.holidaydesc = (TextView)convertView.findViewById(R.id.value);

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.holidaydate.setText(holidayModal.getValDate());
        holder.holidaydesc.setText(holidayModal.getValue());


        return convertView;

    }
}
