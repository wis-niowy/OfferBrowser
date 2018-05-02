package com.example.wisienka.mobileapplication.Models;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wisienka.mobileapplication.R;
import com.example.wisienka.mobileapplication.Adapters.RecyclerViewAdapter;

/**
 * Created by Wisienka on 2018-04-22.
 */

public class Offer {

    private String title;
    private String typeOfOffer; // rent/buy
    private String address;
    private double price;

    public Offer(Context mainContext){
        title = "TITLE";
        typeOfOffer = "wynajem";
        address = "ul. Emilii Plater 53";
        price = 2400;
    }

    public void BindData(RecyclerViewAdapter.ViewHolder holder, Context mainActivityContext) {
        final TextView titleView = (TextView)holder.getMyElementView().findViewById(R.id.offer_title);
        titleView.setText(this.title);
        final TextView typeView = (TextView)holder.getMyElementView().findViewById(R.id.type_of_offer);
        typeView.setText(this.typeOfOffer);
        final TextView addressView = (TextView)holder.getMyElementView().findViewById(R.id.offer_address);
        addressView.setText(this.address);
        final ImageView imageView = (ImageView)holder.getMyElementView().findViewById(R.id.offer_picture);
        imageView.setImageDrawable(ContextCompat.getDrawable(mainActivityContext, R.drawable.no_offer_image_icon));
        final TextView priceView = (TextView)holder.getMyElementView().findViewById(R.id.offer_price);
        priceView.setText(String.valueOf(this.price));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTypeOfOffer() {
        return typeOfOffer;
    }

    public void setTypeOfOffer(String typeOfOffer) {
        this.typeOfOffer = typeOfOffer;
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

}
