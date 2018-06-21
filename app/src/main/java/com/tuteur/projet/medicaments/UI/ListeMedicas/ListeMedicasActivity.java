package com.tuteur.projet.medicaments.UI.ListeMedicas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.tuteur.projet.medicaments.Controler.MedicasAdapter;
import com.tuteur.projet.medicaments.DAO.DAOdbMedicas;
import com.tuteur.projet.medicaments.Model.metier.Medicas;
import com.tuteur.projet.medicaments.R;

import java.util.ArrayList;

public class ListeMedicasActivity extends AppCompatActivity {
    private ArrayList<Medicas> images;
    private MedicasAdapter imageAdapter;
    private ListView listView;
    private Uri mCapturedImageURI;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private DAOdbMedicas daOdb;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_medicas);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_med);
        // Attaching the layout to the toolbar object
        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        // Setting toolbar as the ActionBar with setSupportActionBar() call
        setSupportActionBar(toolbar);
        // Construct the data source
        images = new ArrayList();
        // Create the adapter to convert the array to views
        imageAdapter = new MedicasAdapter(this, images);
        // Attach the adapter to a ListView
        listView = (ListView) findViewById(R.id.medicas_list_view);
        listView.setAdapter(imageAdapter);
        initDB();
    }
    private void initDB() {
        daOdb = new DAOdbMedicas(this);

        //        add images from database to images ArrayList
        for (Medicas mi : daOdb.getImages()) {
            images.add(mi);
        }
    }

    public void btnAddMedicasOnClick(View view) {

        Intent i=new Intent(getApplicationContext(),AddMedicasActivity.class);
        startActivity(i);
    }

    /**
     * take a photo
     */


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_the_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
