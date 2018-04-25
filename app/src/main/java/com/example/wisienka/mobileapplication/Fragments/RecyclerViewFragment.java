package com.example.wisienka.mobileapplication.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wisienka.mobileapplication.Models.ListElement;
import com.example.wisienka.mobileapplication.R;
import com.example.wisienka.mobileapplication.Adapters.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wisienka on 2018-04-22.
 */

public class RecyclerViewFragment extends Fragment {
    protected RecyclerViewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview_layout, container, false);

        List<ListElement> elements = new ArrayList<ListElement>();
        for (int i = 0; i < 10; ++i){
            elements.add(new ListElement(this.getActivity().getApplicationContext()));
        }

                // set up the RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.mainRecyclerViewer);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext())); // context from current view (content_main) - NOT activity_main
        adapter = new RecyclerViewAdapter(view.getContext(), elements);
        recyclerView.setAdapter(adapter);
//        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(view.getContext(), recyclerView, new RecyclerViewClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                //Toast.makeText(getApplicationContext(), adapter.getItem(position) + " is clicked!", Toast.LENGTH_SHORT).show();
//            }
//        }));

        return view;
    }
}
