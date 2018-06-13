package com.example.wisienka.mobileapplication.Helpers;

import android.content.SharedPreferences;
import android.location.Address;
import android.os.AsyncTask;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import com.example.wisienka.mobileapplication.Activities.MainActivity;
import com.example.wisienka.mobileapplication.Fragments.MainPageFragment;
import com.example.wisienka.mobileapplication.Fragments.MapTabFragment;
import com.example.wisienka.mobileapplication.Models.Offer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.maps.android.PolyUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Wisienka on 2018-05-03.
 */

public class OfferBrowserAsyncTask extends AsyncTask<Void, Void, Void> {

    private MainPageFragment fragment;
    //private MainActivity activity;
    private List<Offer> offerList;
    private List<LatLng[]> hulls;

    private double price_from;
    private double price_to;
    private double area_from;
    private double area_to;
    private int rooms_from;
    private int rooms_to;
    private String district;


    public OfferBrowserAsyncTask(MainPageFragment fr){
        fragment = fr;
        //activity = (MainActivity)fr.getActivity();
        offerList = new ArrayList<Offer>();
    }

    @Override
    protected void onPreExecute (){
        hulls = fragment.getMapTabFragment().getDrawnPolygonsPointsCopy();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(fragment.getActivity());
        district = sp.getString("district", "");
        price_from = Double.valueOf(sp.getString("price_from", "0"));
        price_to = Double.valueOf(sp.getString("price_to", "0"));
        area_from = Double.valueOf(sp.getString("area_from", "0"));
        area_to = Double.valueOf(sp.getString("area_to", "0"));
        rooms_from = Integer.valueOf(sp.getString("rooms_from", "0"));
        rooms_to = Integer.valueOf(sp.getString("rooms_to", "0"));
    }
    @Override
    protected Void doInBackground(Void... args) {
        // TODO: here Database related objects will be used in order to retrive offers settled in surrounding of drawn hulls(Polygon)
        //Offer[] offersFromDB = getFromDB();
        //Offer[] offersFromDB = GetExampleList2();

        // TODO: here result offers will be filtered by hulls
        Offer[] offersFromDB = dbConnect("worstserverever.database.windows.net", "1433", "AgathaChristie", "Hakerman3.5XD");

        FilterOffers(offersFromDB);

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
        //((MainActivity)fragment.getActivity()).UpdateOffersContainers(offerList);
        fragment.UpdateOffersContainers(offerList);
    }

    private void FilterOffers(Offer[] offersFromDB){

        for (Offer offer : offersFromDB){
            for (LatLng[] hull : hulls){
                List<LatLng> points = Arrays.asList(hull);
                if (ValidateOffer(offer) || offer.getLocation() != null && PolyUtil.containsLocation(offer.getLocation(), points, false)){
                    if (!offerList.contains(offer))
                        offerList.add(offer);
                }
            }
        }
    }

    private Offer[] GetExampleList(){
        List<Offer> tempList = new ArrayList<Offer>();
        tempList.add(new Offer(null, new LatLng(52.230, 21.000)));
        tempList.add(new Offer(null, new LatLng(52.235, 21.005)));
        tempList.add(new Offer(null, new LatLng(52.125, 20.915)));
        tempList.add(new Offer(null, new LatLng(52.240, 20.910)));
        tempList.add(new Offer(null, new LatLng(52.245, 21.010)));
        tempList.add(new Offer(null, new LatLng(52.250, 21.015)));

        return tempList.toArray(new Offer[tempList.size()]);
    }

    private Offer[] GetExampleList2(){
        List<Offer> tempList = new ArrayList<Offer>();

        for (int i = 0; i < 6; ++i){
            Offer offer1 = new Offer("Flat " + (2 * i + 1), i + 40, (i + 1) % 4, "Mokotów", 1500, null, null);
            offer1.setLocation(new LatLng(52.230 + 0.003 * i, 21.000 - 0.003 * i));
            tempList.add(offer1);

            Offer offer2 = new Offer("Flat " + (2 * i + 2), i + 42, (i + 3) % 4, "Bemowo", 1500, null, null);
            offer2.setLocation(new LatLng(52.230 - 0.003 * i, 21.000 + 0.003 * i));
            tempList.add(offer2);
        }

        return tempList.toArray(new Offer[tempList.size()]);
    }

    private boolean ValidateOffer(Offer offer){
        if (offer.getArea() > area_to || offer.getArea() < area_from ||
                offer.getPrice() > price_to || offer.getPrice() < price_from ||
                offer.getRooms() > rooms_to || offer.getRooms() < rooms_from ||
                !offer.getAddress().equals(district))
            return false;
        else return true;
    }

    public Offer[] dbConnect(String Host, String Port, String db_userid, String db_password) {
        List<Offer> Db_list = new ArrayList<Offer>();
        try {
            String ConnectionString = "jdbc:jtds:sqlserver://" + Host + ":" + Port + "/OfferBrowser";
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            Connection conn = DriverManager.getConnection(ConnectionString, db_userid, db_password);
            Log.v("DATABASE: ", "connected!");
            Statement statement = conn.createStatement();
            String queryString = "SELECT Title, Area, Rooms, District, Price, pt.URL, mt.URL ";
            queryString += "FROM dbo.MainTable mt left join ( " +
                                "select f.[Key], f.URL, f.offerKey " +
                                "from ( " +
                                    "select min([Key]) 'Key', offerKey " +
                                    "from dbo.PicturesTable " +
                                    "group by offerKey" +
                                ") as x inner join dbo.PicturesTable f on f.[Key] = x.[Key] and f.offerKey = x.offerKey) pt on mt.[Key] = pt.offerKey ";
            queryString += "WHERE Price BETWEEN " + price_from + " AND " + price_to + " AND " +
                    "Area BETWEEN " + area_from + " AND " + area_to + " AND " +
                    "Rooms BETWEEN " + rooms_from + " AND " + rooms_to + " AND " +
                    "District LIKE \'" + district + "\'";// + " COLLATE SQL_Polish_Cp1250_CI_AS_KI_WI";
            ResultSet rs = statement.executeQuery(queryString);
            while (rs.next()) {
                //Db_list.add(rs.getString(0));
                String title = rs.getString(1);
                double area = Double.valueOf(rs.getString(2));
                int rooms = Integer.valueOf(rs.getString(3));
                String address = rs.getString(4); // district!!!
                double price = Double.valueOf(rs.getString(5));
                String pic_url = rs.getString(6);
                String offer_url = rs.getString(7);
                Db_list.add(new Offer(title, area, rooms, address, price, pic_url, offer_url));
                //Db_list.add(new Offer(title, 0, 0, null, 0.0, "", ""));
            }
        } catch (Exception e) {
            //Db_list.add("Error");
            Log.v("DATABASE: ", "connection failed!");
            e.printStackTrace();
        }
        return Db_list.toArray(new Offer[Db_list.size()]);
    }
}
