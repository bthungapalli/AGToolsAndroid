package com.agtools.agtools.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.agtools.agtools.Model.ShippingResponse;
import com.agtools.agtools.R;

import java.util.List;

/**
 * Created by soulsticesoftware on 30-01-2018.
 */

public class ShippingAdapter extends BaseAdapter {

    Context context;
    List<ShippingResponse> shippingResponseList;
    TextView textlastupda, textpackage, texttype, textlowhigh, textsize;

    public ShippingAdapter(Context context, List<ShippingResponse> shippingResponseList) {
        this.context = context;
        this.shippingResponseList = shippingResponseList;
    }

    @Override
    public int getCount() {
        return shippingResponseList.size();
    }

    @Override
    public Object getItem(int position) {
        return shippingResponseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return shippingResponseList.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.shippingadapter, null);
        textlastupda = (TextView) convertView.findViewById(R.id.textlastupda);
        textpackage = (TextView) convertView.findViewById(R.id.textpackage);
        texttype = (TextView) convertView.findViewById(R.id.texttype);
        textlowhigh = (TextView) convertView.findViewById(R.id.textlowhigh);
        textsize = (TextView) convertView.findViewById(R.id.textsize);
       final ShippingResponse shippingResponse = shippingResponseList.get(position);
        textlastupda.setText(shippingResponse.getLastUpdated_date());
        textpackage.setText(shippingResponse.getPackagetext());
        texttype.setText(shippingResponse.getType());
        textlowhigh.setText(shippingResponse.getHigh());
        textsize.setText(shippingResponse.getSize());
        return convertView;
    }
}
