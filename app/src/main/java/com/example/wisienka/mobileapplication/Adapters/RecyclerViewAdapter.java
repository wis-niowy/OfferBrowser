package com.example.wisienka.mobileapplication.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import com.example.wisienka.mobileapplication.Models.Offer;
import com.example.wisienka.mobileapplication.R;

import java.util.List;

/**
 * Created by Wisienka on 2018-04-22.
 */

public class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private List<Offer> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor -- context is a main activity's context
    public RecyclerViewAdapter(Context context, List<Offer> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Offer task = mData.get(position);

        // bind all data to view here
        task.BindData(holder, mInflater.getContext());

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/  {
        protected LinearLayout myElementView;

        public ViewHolder(View itemView) {
            super(itemView);
            myElementView = itemView.findViewById(R.id.recyclerview_element);
        }

        public LinearLayout getMyElementView() {
            return myElementView;
        }
    }

    // convenience method for getting DATA at click position
    Offer getItem(int id) {
        return mData.get(id);
    }

    public void ClearDataList(List<Offer> offersList){
        mData.clear();
        notifyDataSetChanged();
        if (offersList != null){
            mData.addAll(offersList);
            notifyDataSetChanged();
        }
    }
}
