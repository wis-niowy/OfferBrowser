package com.example.wisienka.mobileapplication.Models;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wisienka.mobileapplication.R;
import com.example.wisienka.mobileapplication.Adapters.RecyclerViewAdapter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Wisienka on 2018-04-22.
 */

public class Offer {

    private String title;
    private double area; // surface of a flat
    private int rooms; // number of rooms
    private String address; // district
    private double price;
    private LatLng location;
    private String pic_url;
    private String offer_url;

    public Offer(Context mainContext, LatLng loc){
        title = "TITLE";
        area = 34.5;
        rooms = 3;
        address = "Bemowo";
        price = 2400;
        location = loc;
    }

    public Offer(String title, double area, int rooms, String address, double price, String pic_url, String offer_url) {
        this.title = title;
        this.area = area;
        this.rooms = rooms;
        this.address = address;
        this.price = price;
        this.pic_url = pic_url;
        this.offer_url = offer_url;
    }

    public void BindData(RecyclerViewAdapter.ViewHolder holder, Context mainActivityContext) {
        final TextView titleView = (TextView)holder.getMyElementView().findViewById(R.id.offer_title);
        titleView.setText(this.title);
        final TextView areaView = (TextView)holder.getMyElementView().findViewById(R.id.flat_area);
        areaView.setText(String.valueOf(this.area));
        final TextView roomView = (TextView)holder.getMyElementView().findViewById(R.id.rooms_number);
        roomView.setText(String.valueOf(this.rooms));
        final TextView addressView = (TextView)holder.getMyElementView().findViewById(R.id.offer_address);
        addressView.setText(this.address);
        final ImageView imageView = (ImageView)holder.getMyElementView().findViewById(R.id.offer_picture);
        imageView.setImageDrawable(ContextCompat.getDrawable(mainActivityContext, R.drawable.no_offer_image_icon));
        final TextView priceView = (TextView)holder.getMyElementView().findViewById(R.id.offer_price);
        priceView.setText(String.valueOf(this.price));
    }

    public void SetMarker(GoogleMap map){
        if (location != null) {
            map.addMarker(new MarkerOptions().position(this.getLocation()).title(this.getTitle()));
            Log.v("Offer: ", "Added " + this.getTitle());
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

}
