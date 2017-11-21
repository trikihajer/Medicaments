package com.tuteur.projet.medicaments.UI;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tuteur.projet.medicaments.Controler.MyAdapter;
import com.tuteur.projet.medicaments.Model.metier.Conseils;
import com.tuteur.projet.medicaments.data.MyDataConseil;
import com.tuteur.projet.medicaments.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListConseilFragment extends Fragment {

    private static final String TAG = "MyActivity";

    private static RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static RecyclerView mRecyclerView;
    private  ArrayList<Conseils> conseilsArrayList;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.fragment_list_conseil, container, false);
        setHasOptionsMenu(true);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.conseil_recycler_view);

        mRecyclerView.setHasFixedSize(true);
    mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);




        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        conseilsArrayList = new ArrayList<Conseils>();
        for (int i = 0; i < 3; i++) {
            Log.v(TAG, "indexhhhhhhh=" + i);
            conseilsArrayList.add(new Conseils(
                    MyDataConseil.nameArray[i],
                    MyDataConseil.versionArray[i],
                    MyDataConseil.id_[i],
                    MyDataConseil.drawableArray[i],
                    MyDataConseil.url[i]
            ));
        }

        removedItems = new ArrayList<Integer>();

        mAdapter = new MyAdapter(conseilsArrayList);
        mRecyclerView.setAdapter(mAdapter);
        return view ;

    }


}
