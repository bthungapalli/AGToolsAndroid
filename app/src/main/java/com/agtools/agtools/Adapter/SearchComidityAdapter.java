package com.agtools.agtools.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.agtools.agtools.Constants.SPHelper;
import com.agtools.agtools.DashboardActivity;
import com.agtools.agtools.Model.SearchCommidity;
import com.agtools.agtools.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by soulsticesoftware on 03-02-2018.
 */

public class SearchComidityAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    private List<SearchCommidity> worldpopulationlist = null;
    private ArrayList<SearchCommidity> arraylist;

    public SearchComidityAdapter(Context context, List<SearchCommidity> worldpopulationlist) {
        mContext = context;
        this.worldpopulationlist = worldpopulationlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<SearchCommidity>();
        this.arraylist.addAll(worldpopulationlist);
    }

    public class ViewHolder {
        TextView textCommidity;
    }

    @Override
    public int getCount() {
        return worldpopulationlist.size();
    }

    @Override
    public SearchCommidity getItem(int position) {
        return worldpopulationlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.searchcommiditylist, null);
            // Locate the TextViews in listview_item.xml
            holder.textCommidity = (TextView) view.findViewById(R.id.textCommidity);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.textCommidity.setText(worldpopulationlist.get(position).getCommidityName());

        // Listen for ListView Item Click
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Send single item click data to SingleItemView Class
                Intent intent = new Intent(mContext, DashboardActivity.class);
                // Pass all data rank
               // intent.putExtra("commidityName",(worldpopulationlist.get(position).getCommidityName()));
                SPHelper.setValueToPrefs(mContext, SPHelper.MyPREFERENCES, SPHelper.KEY_COMMUDITYNAME, (worldpopulationlist.get(position).getCommidityName()));

                // Pass all data country
                mContext.startActivity(intent);
                ((DashboardActivity) mContext).finish();
            }
        });

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        worldpopulationlist.clear();
        if (charText.length() == 0) {
            worldpopulationlist.addAll(arraylist);
        }
        else
        {
            for (SearchCommidity wp : arraylist)
            {
                if (wp.getCommidityName().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    worldpopulationlist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }







}
