package com.tuteur.projet.medicaments.Controler;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuteur.projet.medicaments.Model.metier.Medicas;
import com.tuteur.projet.medicaments.R;

import java.util.ArrayList;

public class MedicasAdapter extends ArrayAdapter<Medicas> {
    private final int THUMBSIZE = 96;

    /**
     * applying ViewHolder pattern to speed up ListView, smoother and faster
     * item loading by caching view in A ViewHolder object
     */
    private static class ViewHolderMedicas {
        ImageView imgIcon;
        TextView description;
        TextView freq;
        TextView datee;
    }

    public MedicasAdapter(Context context, ArrayList<Medicas> images) {
        super(context, 0, images);
    }

    @Override public View getView(int position, View convertView,
                                  ViewGroup parent) {
        // view lookup cache stored in tag
       ViewHolderMedicas viewHolder;
        // Check if an existing view is being reused, otherwise inflate the
        // item view
        if (convertView == null) {
            viewHolder = new ViewHolderMedicas();
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_medicas, parent, false);
            viewHolder.description = (TextView) convertView.findViewById(R.id.item_medicas_name);
            viewHolder.datee = (TextView) convertView.findViewById(R.id.item_medica_datedebTrait);
            viewHolder.freq = (TextView) convertView.findViewById(R.id.item_medica_freq);
            viewHolder.imgIcon = (ImageView) convertView.findViewById(R.id.item_img_medicas);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderMedicas) convertView.getTag();
        }
         Medicas image = getItem(position);
        // set description text
        viewHolder.description.setText(image.getNomMed());
        viewHolder.datee.setText(image.getDateDeb());
        viewHolder.freq.setText(image.getFreq());
        // set image icon
        viewHolder.imgIcon.setImageBitmap(ThumbnailUtils
                .extractThumbnail(BitmapFactory.decodeFile(image.getPhotoMed()),
                        THUMBSIZE, THUMBSIZE));

        // Return the completed view to render on screen
        return convertView;
    }
}
