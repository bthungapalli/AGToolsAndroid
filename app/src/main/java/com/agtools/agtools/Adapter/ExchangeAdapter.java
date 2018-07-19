package com.agtools.agtools.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.agtools.agtools.Model.ExchangeRateModal;
import com.agtools.agtools.Model.HolidayModal;
import com.agtools.agtools.R;

import java.util.List;

/**
 * Created by soulsticesoftware on 30-01-2018.
 */

public class ExchangeAdapter extends BaseAdapter {
    Context context;
    List<ExchangeRateModal> exchangeRateModalList;
    TextView txtlastUpdatedate,txtcanada,txtmexico;

    public ExchangeAdapter(Context context,List<ExchangeRateModal> exchangeRateModalList) {

        this.context = context;
        this.exchangeRateModalList = exchangeRateModalList;
    }

    @Override
    public int getCount() {
        return exchangeRateModalList.size();
    }

    @Override
    public Object getItem(int position) {
        return exchangeRateModalList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return exchangeRateModalList.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.exchangeadapter, null);
        final ExchangeRateModal exchangeResponseModal = exchangeRateModalList.get(position);
        txtlastUpdatedate = (TextView)convertView.findViewById(R.id.txtlastUpdatedate);
        txtcanada = (TextView)convertView.findViewById(R.id.txtcanada);
        txtmexico = (TextView)convertView.findViewById(R.id.txtmexico);
        txtlastUpdatedate.setText(exchangeResponseModal.getLastUpdate());
        txtcanada.setText(exchangeResponseModal.getCanadaExchange());
        txtmexico.setText(exchangeResponseModal.getMexicoExchange());
        return convertView;
    }
}
