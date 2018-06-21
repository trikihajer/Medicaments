package com.tuteur.projet.medicaments.UI.ListeMedicas;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuteur.projet.medicaments.Model.metier.Medicas;
import com.tuteur.projet.medicaments.R;

import org.json.JSONException;
import org.json.JSONObject;

public class DisplayMedicas  extends Activity {
    private Medicas image;
    private ImageView imageView;
    private TextView description;
    private String jstring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_display_medicas);
        imageView = (ImageView) findViewById(R.id.displayMedicas_image_view);
        description = (TextView) findViewById(R.id.text_view_descriptionMedicas);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            jstring = extras.getString("medicas");
        }
        image = getMyImage(jstring);
        description.setText("XYZ");
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
         }

    private Medicas getMyImage(String image) {
        try {



                JSONObject job = new JSONObject(image);
            return (new Medicas(
                    job.getString("name"),
                    job.getString("pathologie"),
                    job.getString("path"),
                    job.getString("freq"),
                    job.getString("datetime"),
                    job.getString("momentPrise"),
                    job.getInt("nbComp"),
                    job.getInt("duree"),
                    job.getInt("horraire")
                  ));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        if (savedInstanceState.containsKey("jstring")) {
            jstring = savedInstanceState.getString("jstring");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_image, menu);
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
