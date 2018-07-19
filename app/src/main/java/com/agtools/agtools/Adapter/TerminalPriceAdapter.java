package com.agtools.agtools.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.agtools.agtools.Model.TerminalPriceResponse;
import com.agtools.agtools.R;

import java.util.List;

/**
 * Created by soulsticesoftware on 03-02-2018.
 */

public class TerminalPriceAdapter extends BaseAdapter {
    Context context;
    List<TerminalPriceResponse> terminalPriceResponseList;
    TextView textlastupda, textpackage, texttype, textlowhigh,textorigin, textsize;
    public TerminalPriceAdapter(Context context, List<TerminalPriceResponse> terminalPriceResponseList) {
        this.context = context;
        this.terminalPriceResponseList=terminalPriceResponseList;
    }

    @Override
    public int getCount() {
        return terminalPriceResponseList.size();
    }

    @Override
    public Object getItem(int position) {
        return terminalPriceResponseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return terminalPriceResponseList.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.terminaladapter,null);
        final TerminalPriceResponse terminalResponse = terminalPriceResponseList.get(position);
        textlastupda = (TextView)convertView.findViewById(R.id.textlastupda);
        textpackage = (TextView)convertView.findViewById(R.id.textpackage);
        texttype = (TextView)convertView.findViewById(R.id.texttype);
        textlowhigh = (TextView)convertView.findViewById(R.id.textlowhigh);
        textorigin = (TextView)convertView.findViewById(R.id.textorigin);
        textsize = (TextView)convertView.findViewById(R.id.textsize);
        textlastupda.setText(terminalResponse.getTerLastUpdateDate());
        textpackage.setText(terminalResponse.getTerPackage());
        texttype.setText(terminalResponse.getTerType());
        textlowhigh.setText(terminalResponse.getTerLowHigh());
        textorigin.setText(terminalResponse.getTerOrigin());
        textsize.setText(terminalResponse.getTerSize());
        return convertView;
    }
}
