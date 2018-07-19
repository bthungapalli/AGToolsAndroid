package com.agtools.agtools.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.agtools.agtools.Model.HolidayModal;
import com.agtools.agtools.R;

import java.util.List;

/**
 * Created by soulsticesoftware on 03-02-2018.
 */

public class HolidayAdapter extends ArrayAdapter<HolidayModal> {
    Context context;
    List<HolidayModal> holidayModalList;

    public HolidayAdapter(Context context, int resourceId,List<HolidayModal> holidayModalList) {
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
        /*LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.holidayadapter,null);
        HolidayModal holidayModal = holidayModalList.get(position);
        holidaydate = (TextView)convertView.findViewById(R.id.holidaydate);
        holidaydesc = (TextView)convertView.findViewById(R.id.holidaydesc);
        holidaydate.setText(holidayModal.getHolidayDate());
        holidaydesc.setText(holidayModal.getDescription());
        return convertView;*/
        ViewHolder holder = null;
        HolidayModal holidayModal = holidayModalList.get(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.holidayadapter, null);
            holder = new ViewHolder();
            holder.holidaydate = (TextView)convertView.findViewById(R.id.holidaydate);
            holder.holidaydesc = (TextView)convertView.findViewById(R.id.holidaydesc);

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.holidaydate.setText(holidayModal.getHolidayDate());
        holder.holidaydesc.setText(holidayModal.getDescription());
        return convertView;

    }
}
