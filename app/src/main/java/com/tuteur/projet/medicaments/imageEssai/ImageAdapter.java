package com.tuteur.projet.medicaments.imageEssai;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuteur.projet.medicaments.R;

import java.util.ArrayList;

/**
 * Created by Lenovo on 20/11/2017.
 */

/**
 * A ListView is used to show a vertical list of scrollable items. ArrayAdapter
 * can be used to converts an ArrayList of objects into view items loaded into
 * the ListView container.
 * <p/>
 * This file is part of SqliteImage
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on March 22, 2015.
 */
public class ImageAdapter extends ArrayAdapter<MyImage> {
    private final int THUMBSIZE = 96;

    /**
     * applying ViewHolder pattern to speed up ListView, smoother and faster
     * item loading by caching view in A ViewHolder object
     */
    private static class ViewHolder {
        ImageView imgIcon;
        TextView description;
    }

    public ImageAdapter(Context context, ArrayList<MyImage> images) {
        super(context, 0, images);
    }

    @Override public View getView(int position, View convertView,
                                  ViewGroup parent) {
        // view lookup cache stored in tag
        ViewHolder viewHolder;
        // Check if an existing view is being reused, otherwise inflate the
        // item view
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_image, parent, false);
            viewHolder.description = (TextView) convertView.findViewById(R.id.item_img_infor);
            viewHolder.imgIcon = (ImageView) convertView.findViewById(R.id.item_img_icon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Get the data item for this position
        MyImage image = getItem(position);
        // set description text
        viewHolder.description.setText(image.getDescription());
        // set image icon
        viewHolder.imgIcon.setImageBitmap(ThumbnailUtils
                .extractThumbnail(BitmapFactory.decodeFile(image.getPath()),
                        THUMBSIZE, THUMBSIZE));

        // Return the completed view to render on screen
        return convertView;
    }
}