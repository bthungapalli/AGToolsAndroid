package com.agtools.agtools.GlobalResponse;

import android.app.Application;

import com.agtools.agtools.Model.CommudityResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HOME on 1/21/2018.
 */
public class GlobalData extends Application{
    public int dashboardSelection;

    public List<CommudityResponse> commudityResponse=new ArrayList<CommudityResponse>();

    public List<CommudityResponse> getCommudityResponse() {
        return commudityResponse;
    }

    public void setCommudityResponse(List<CommudityResponse> commudityResponse) {
        this.commudityResponse = commudityResponse;
    }

    public int getDashboardSelection() {
        return dashboardSelection;
    }

    public void setDashboardSelection(int dashboardSelection) {
        this.dashboardSelection = dashboardSelection;
    }
}
