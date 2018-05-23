package com.example.wisienka.mobileapplication.Helpers;

import android.os.AsyncTask;

import com.example.wisienka.mobileapplication.Fragments.MapTabFragment;
import com.example.wisienka.mobileapplication.Models.Offer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Wisienka on 2018-05-03.
 */

public class OfferBrowserAsyncTask extends AsyncTask<Void, Void, Void> {

    private MapTabFragment fragment;
    private List<Offer> offerList;
    private List<LatLng[]> hulls;

    public OfferBrowserAsyncTask(MapTabFragment fr){
        fragment = fr;
        offerList = new ArrayList<Offer>();
    }

    @Override
    protected void onPreExecute (){
        hulls = fragment.getDrawnPolygonsPointsCopy();
    }
    @Override
    protected Void doInBackground(Void... args) {
        // TODO: here Database related objects will be used in order to retrive offers settled in surrounding of drawn hulls(Polygon)
        //Offer[] offersFromDB = getFromDB();
        Offer[] offersFromDB = GetExampleList();
        FilterOffers(offersFromDB);
        // TODO: here result offers will be filtered by hulls


            // Escape early if cancel() is called
        if (isCancelled()) return null;
        return null;
        //return offerList.toArray(new Offer[offerList.size()]); // JAVA <3
    }
    @Override
    protected void onProgressUpdate(Void... progress) {

    }
    @Override
    protected void onPostExecute(Void results) {
        fragment.updateMapState(offerList);
    }

    private void FilterOffers(Offer[] offersFromDB){

        for (Offer offer : offersFromDB){
            for (LatLng[] hull : hulls){
                List<LatLng> points = Arrays.asList(hull);
                if (PolyUtil.containsLocation(offer.getLocation(), points, false)){
                    offerList.add(offer);
                }
            }
        }
    }

    private Offer[] GetExampleList(){
        List<Offer> tempList = new ArrayList<Offer>();
        tempList.add(new Offer(fragment.getActivity().getApplicationContext(), new LatLng(52.230, 21.000)));
        tempList.add(new Offer(fragment.getActivity().getApplicationContext(), new LatLng(52.235, 21.005)));
        tempList.add(new Offer(fragment.getActivity().getApplicationContext(), new LatLng(52.125, 20.915)));
        tempList.add(new Offer(fragment.getActivity().getApplicationContext(), new LatLng(52.240, 20.910)));
        tempList.add(new Offer(fragment.getActivity().getApplicationContext(), new LatLng(52.245, 21.010)));
        tempList.add(new Offer(fragment.getActivity().getApplicationContext(), new LatLng(52.250, 21.015)));

        return tempList.toArray(new Offer[tempList.size()]);
    }
}
