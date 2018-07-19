package com.agtools.agtools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ranjithkumar.g on 3/22/2018.
 */

public class CustomAdapterPresent extends ArrayAdapter<ModelList> {

    private List<ModelList> dataSet;
    Context mContext;
    public BtnClickListener mClickListener = null;
    // View lookup cache


    public CustomAdapterPresent(List<ModelList> heroList, Context context, BtnClickListener listener) {
        super(context, R.layout.row_item, heroList);
        this.dataSet = heroList;
        this.mContext=context;
        mClickListener = listener;
    }

    private int lastPosition = -1;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
           TextView txtName = (TextView) convertView.findViewById(R.id.name);
           TextView txtType = (TextView) convertView.findViewById(R.id.type);
           Button button= (Button) convertView.findViewById(R.id.selectplan);
            ModelList dataModel = getItem(position);
            txtName.setText(dataModel.getPrice());
            txtType.setText(dataModel.getCommodityCount());
            button.setTag(position); //For passing the list item index
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    if (mClickListener != null)
                        mClickListener.onBtnClick(v);
                }
            });
        }

        return convertView;
    }
}