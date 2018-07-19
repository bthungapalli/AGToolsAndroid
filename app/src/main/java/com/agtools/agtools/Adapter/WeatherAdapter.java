package com.agtools.agtools.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.agtools.agtools.DashboardActivity;
import com.agtools.agtools.Model.WeatherResponse;
import com.agtools.agtools.R;

import java.util.List;

/**
 * Created by soulsticesoftware on 26-01-2018.
 */

public class WeatherAdapter extends BaseAdapter {
    Context context;
    List<WeatherResponse> weatherResponseList;
    TextView temparature, high, low, precipitation, windspeed, humidity, uvindex,day;

    public WeatherAdapter(Context context, List<WeatherResponse> weatherResponseList) {
        this.context = context;
        this.weatherResponseList = weatherResponseList;
    }

    @Override
    public int getCount() {
        return weatherResponseList.size();
    }

    @Override
    public Object getItem(int position) {
        return weatherResponseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return weatherResponseList.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.weather_adapter, null);
        final WeatherResponse weatherResponseModal = weatherResponseList.get(position);
        temparature = (TextView) convertView.findViewById(R.id.temparature);
        high = (TextView) convertView.findViewById(R.id.high);
        low = (TextView) convertView.findViewById(R.id.low);
        precipitation = (TextView) convertView.findViewById(R.id.precipitation);
        windspeed = (TextView) convertView.findViewById(R.id.windspeed);
        humidity = (TextView) convertView.findViewById(R.id.humidity);
        uvindex = (TextView) convertView.findViewById(R.id.uvindex);
        day = (TextView)convertView.findViewById(R.id.day);
        temparature.setText(weatherResponseModal.getTemparature());
        temparature.setBackgroundColor(Color.parseColor(weatherResponseModal.getColors()));
        if (weatherResponseModal.getHighTemp() == null) {
            high.setText("NAF");
        } else {
            high.setText(weatherResponseModal.getHighTemp());
        }

        low.setText(weatherResponseModal.getLowTemp());
        precipitation.setText(weatherResponseModal.getPrecipitation());
        windspeed.setText(weatherResponseModal.getWindSpeed());
        humidity.setText(weatherResponseModal.getHumidity());
        uvindex.setText(weatherResponseModal.getUvIndex());
        day.setText(weatherResponseModal.getDay());
        return convertView;
    }
}
