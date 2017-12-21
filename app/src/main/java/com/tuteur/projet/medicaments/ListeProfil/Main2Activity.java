package com.tuteur.projet.medicaments.ListeProfil;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.gson.Gson;
import com.tuteur.projet.medicaments.R;

import java.util.ArrayList;

public class Main2Activity extends ActionBarActivity {

    private ArrayList<MyImage> images;
    private ImageAdapter imageAdapter;
    private ListView listView;
    private Uri mCapturedImageURI;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private DAOdb daOdb;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Construct the data source
        images = new ArrayList();
        // Create the adapter to convert the array to views
        imageAdapter = new ImageAdapter(this, images);
        // Attach the adapter to a ListView
        listView = (ListView) findViewById(R.id.main_list_view);
        listView.setAdapter(imageAdapter);
        addItemClickListener(listView);
        initDB();
    }

    /**
     * initialize database
     */
    private void initDB() {
        daOdb = new DAOdb(this);
        //        add images from database to images ArrayList
        for (MyImage mi : daOdb.getImages()) {
            images.add(mi);
        }
    }

    public void btnAddOnClick(View view) {

        Intent i=new Intent(getApplicationContext(),AddProfilCapt.class);
        startActivity(i);
    }

    /**
     * take a photo
     */
    private void addItemClickListener(final ListView listView) {
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MyImage image = (MyImage) listView.getItemAtPosition(position);
                Intent intent = new Intent(getBaseContext(), DisplayImage.class);
                intent.putExtra("IMAGE", (new Gson()).toJson(image));
                startActivity(intent);
            }
        });
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        // Save the user's current game state
        if (mCapturedImageURI != null) {
            outState.putString("mCapturedImageURI", mCapturedImageURI.toString());
        }
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(outState);
    }

    @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        if (savedInstanceState.containsKey("mCapturedImageURI")) {
            mCapturedImageURI = Uri.parse(savedInstanceState.getString("mCapturedImageURI"));
        }
    }
}