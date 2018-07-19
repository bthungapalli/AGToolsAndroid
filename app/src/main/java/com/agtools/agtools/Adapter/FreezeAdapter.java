package com.agtools.agtools.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.agtools.agtools.DashboardActivity;
import com.agtools.agtools.Model.FreezeModel;
import com.agtools.agtools.Model.JuiceModel;
import com.agtools.agtools.R;

import java.util.List;

/**
 * Created by soulsticesoftware on 27-02-2018.
 */

public class FreezeAdapter extends BaseAdapter{
    Context context;
    List<FreezeModel> freezeModelList;
    TextView textfreezeweekend,textfreezeweek,textfreezeaccum;
    public FreezeAdapter(Context context, List<FreezeModel> freezeModelList) {
        this.context = context;
        this.freezeModelList = freezeModelList;
    }

    @Override
    public int getCount() {
        return freezeModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return freezeModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return freezeModelList.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.freezedapterlayout, null);
        final FreezeModel freezeModel = freezeModelList.get(position);
        textfreezeweekend = (TextView)convertView.findViewById(R.id.textfreezeweekend);
        textfreezeweek = (TextView)convertView.findViewById(R.id.textfreezeweek);
        textfreezeaccum = (TextView)convertView.findViewById(R.id.textfreezeaccum);
        textfreezeweekend.setText(freezeModel.getWeekDay());
        textfreezeweek.setText(freezeModel.getWeek());
        textfreezeaccum.setText(freezeModel.getAccum());
        return convertView;
    }
}
