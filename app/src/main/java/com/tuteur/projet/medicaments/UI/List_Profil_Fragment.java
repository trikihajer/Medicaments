package com.tuteur.projet.medicaments.UI;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tuteur.projet.medicaments.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class List_Profil_Fragment extends Fragment {


    FloatingActionButton add;

    public List_Profil_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_list_profil, container, false);
        add= (FloatingActionButton) v.findViewById(R.id.addProfil);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),AddProfilActivity.class);
                startActivity(i);
            }
        });

        return v;


    }

}
