package com.tuteur.projet.medicaments.Controler;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuteur.projet.medicaments.Model.metier.Conseils;
import com.tuteur.projet.medicaments.R;

import java.util.ArrayList;

/**
 * Created by Lenovo on 26/10/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<Conseils> dataSet;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public  class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextViewConseil, mTextViewDesc;
        public ImageView mImagetViewConseil;
        public WebView myWebView ;
        public MyViewHolder(View v) {
            super(v);
            mTextViewConseil = (TextView) v.findViewById(R.id.textViewTitreConseil);
            mTextViewDesc = (TextView) v.findViewById(R.id.textViewDescConseil);
            mImagetViewConseil=(ImageView) v.findViewById(R.id.imageViewConseil);

            myWebView = (WebView) v.findViewById(R.id.webvv);
            myWebView.getSettings().setJavaScriptEnabled(true);
            myWebView.setWebViewClient(new MyBrowser());
            myWebView.loadUrl("http://www.w3schools.com/js/tryit.asp?filename=tryjs_alert"); //try js alert
            myWebView.setWebChromeClient(new WebChromeClient()); // adding js alert support


          //   myWebView = (WebView) v.findViewById(R.id.webView);
          //  myWebView.getSettings().setJavaScriptEnabled(true);
           // myWebView.loadUrl("http://www.google.com");
            Log.v("abc", "ddddd");


        }


    }

    // Provide a suitable constructor (depends on the kind of dataset)

    public MyAdapter(ArrayList<Conseils> data) {
        this.dataSet = data;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_conseil, parent, false);
        // set the view's size, margins, paddings and layout parameters

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }



    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.mTextViewConseil;
        TextView textViewVersion = holder.mTextViewDesc;
        ImageView imageView = holder.mImagetViewConseil;
        WebView wx =holder.myWebView;


        textViewName.setText(dataSet.get(listPosition).getTitre());
        textViewVersion.setText(dataSet.get(listPosition).getDesc());
        imageView.setImageResource(dataSet.get(listPosition).getImage());

        wx.getSettings().setJavaScriptEnabled(true);
        wx.setWebViewClient(new MyBrowser());
        wx.loadUrl(dataSet.get(listPosition).getUrl()); //try js alert
        wx.setWebChromeClient(new WebChromeClient()); // adding js alert support

        // wx.loadUrl(dataSet.get(listPosition).getUrl());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}