package com.tuteur.projet.medicaments.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tuteur.projet.medicaments.Model.essai.MainActivity;
import com.tuteur.projet.medicaments.Model.essai.Product;
import com.tuteur.projet.medicaments.Model.essai.database.SqliteDatabase;
import com.tuteur.projet.medicaments.R;

import java.io.IOException;


public class AddProfilActivity extends AppCompatActivity {

    private SqliteDatabase mDatabase;
    private ImageView imageViewProfAdd;
    private String partager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product_layout);
         final EditText nameField = (EditText)findViewById(R.id.enter_name);
         final EditText quantityField = (EditText)findViewById(R.id.enter_quantity);
         final ImageView imageViewBB=(ImageView)findViewById(R.id.imageAdd);

        Button bb =(Button) findViewById(R.id.AjouterProfil);
        Button cc =(Button) findViewById(R.id.buttoncc);

        mDatabase = new SqliteDatabase(this);
        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = nameField.getText().toString();
                final int quantity = Integer.parseInt(quantityField.getText().toString());
           /*    final String imagee= String.valueOf(imageViewBB.getResources());
                Log.v("getImg",String.valueOf(imageViewBB.)));
                */
                if(TextUtils.isEmpty(name) || quantity <= 0){
                    Toast.makeText(getApplicationContext(), "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else{

                    Product newProduct = new Product(name, quantity,partager);
                    mDatabase.addProduct(newProduct);
                    Log.d("ifffff reussi",name);

                    //refresh the activity
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }
        });

cc.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
// Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);

    }
});


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            Log.d("photoAdd", String.valueOf(uri));

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                partager=String.valueOf(bitmap);

                imageViewProfAdd = (ImageView) findViewById(R.id.imageViewPersonne);

                imageViewProfAdd.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDatabase != null){
            mDatabase.close();
        }
    }
}
