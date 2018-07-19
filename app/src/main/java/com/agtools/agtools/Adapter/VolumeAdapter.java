package com.agtools.agtools.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.agtools.agtools.Model.VolumeResponse;
import com.agtools.agtools.R;

import java.util.List;

/**
 * Created by soulsticesoftware on 29-01-2018.
 */

public class VolumeAdapter extends BaseAdapter {

    Context context;
    List<VolumeResponse> volumeResponseList;
    TextView textView_Sno,textView_location,textView_total;

    public VolumeAdapter(Context context, List<VolumeResponse> volumeResponseList) {
        this.context = context;
        this.volumeResponseList = volumeResponseList;
    }

    @Override
    public int getCount() {
        return volumeResponseList.size();
    }

    @Override
    public Object getItem(int position) {
        return volumeResponseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return volumeResponseList.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.volumeadapter, null);
        final VolumeResponse volumeResponseModal = volumeResponseList.get(position);
        textView_Sno = (TextView)convertView.findViewById(R.id.textSno);
        textView_location = (TextView)convertView.findViewById(R.id.textlocation);
       textView_total = (TextView)convertView.findViewById(R.id.texttotal);
        textView_Sno.setText(volumeResponseModal.getIndex());
       textView_location.setText(volumeResponseModal.getLocation());
        textView_total.setText(volumeResponseModal.getCount());
        return convertView;
    }
}
