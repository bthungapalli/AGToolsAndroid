package com.agtools.agtools.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.agtools.agtools.DashboardActivity;
import com.agtools.agtools.Model.JuiceModel;
import com.agtools.agtools.Model.WeatherResponse;
import com.agtools.agtools.R;

import java.util.List;

/**
 * Created by soulsticesoftware on 27-02-2018.
 */

public class JuiceAdapter extends BaseAdapter{
    Context context;
    List<JuiceModel> juiceModelList;
    TextView textjuiceweekend,textjuiceweek,textjuiceaccum;
    public JuiceAdapter(Context context, List<JuiceModel> juiceModelList) {
        this.context = context;
        this.juiceModelList = juiceModelList;
    }

    @Override
    public int getCount() {
        return juiceModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return juiceModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return juiceModelList.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.juiceadapterlayout, null);
        final JuiceModel juiceModelModal = juiceModelList.get(position);
        textjuiceweekend = (TextView)convertView.findViewById(R.id.textjuiceweekend);
        textjuiceweek = (TextView)convertView.findViewById(R.id.textjuiceweek);
        textjuiceaccum = (TextView)convertView.findViewById(R.id.textjuiceaccum);
        textjuiceweekend.setText(juiceModelModal.getWeekDay());
        textjuiceweek.setText(juiceModelModal.getWeek());
        textjuiceaccum.setText(juiceModelModal.getAccum());
        return convertView;
    }
}
