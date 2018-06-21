package com.tuteur.projet.medicaments.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.tuteur.projet.medicaments.Controler.MyAdapter;
import com.tuteur.projet.medicaments.Model.metier.Conseils;
import com.tuteur.projet.medicaments.R;
import com.tuteur.projet.medicaments.data.MyDataConseil;

import java.util.ArrayList;

public class ConseilActivity extends AppCompatActivity {
    ArrayList<Conseils> conseilsArrayList = new ArrayList();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conseil);
        ajouterConseil();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final RecyclerView rv = (RecyclerView) findViewById(R.id.list);
		rv.setLayoutManager(new LinearLayoutManager(this));
		rv.setAdapter(new MyAdapter(conseilsArrayList));
    }
    public void ajouterConseil(){
        for (int i=0 ; i<MyDataConseil.drawableArray.length; i++)
        conseilsArrayList.add(new Conseils(MyDataConseil.nameArray[i], MyDataConseil.versionArray[i], MyDataConseil.id_[i],MyDataConseil.drawableArray[i],MyDataConseil.url[i] ));
    }

}
