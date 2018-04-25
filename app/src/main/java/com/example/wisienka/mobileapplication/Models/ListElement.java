package com.example.wisienka.mobileapplication.Models;

import android.content.Context;
import android.widget.TextView;

import com.example.wisienka.mobileapplication.R;
import com.example.wisienka.mobileapplication.Adapters.RecyclerViewAdapter;

/**
 * Created by Wisienka on 2018-04-22.
 */

public class ListElement {

    protected String text;

    public ListElement(Context mainContext){
        this.text = "ElementELO";
    }

    public void BindData(RecyclerViewAdapter.ViewHolder holder, Context mainActivityContext) {
        final TextView textView = (TextView)holder.getMyElementView().findViewById(R.id.text_view);
        textView.setText(this.text);
    }

}
