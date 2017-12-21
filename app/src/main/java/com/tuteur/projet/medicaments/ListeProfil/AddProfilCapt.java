package com.tuteur.projet.medicaments.ListeProfil;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tuteur.projet.medicaments.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class AddProfilCapt extends ActionBarActivity {
    private ArrayList<MyImage> images;

    private Uri mCapturedImageURI;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private ImageAdapter imageAdapter;
    private DAOdb daOdb;
    private Uri partager;
    private String titleString;
    EditText title ;
    int galorCap;

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;

    RadioGroup rg;
    String sexe;

    TextView tvDate;
    String dateString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profil_capt);

        rg=(RadioGroup) findViewById(R.id.myRadioGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.mn) {
                    sexe = "masculin";
                }
                else if (checkedId == R.id.f) {
                    sexe = "feminin";
                }
            }
        });
        tvDate=(TextView) findViewById(R.id.neeletv);
        dateString= tvDate.getText().toString();


        dateView = (TextView) findViewById(R.id.neeletv);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        imageAdapter = new ImageAdapter(this, images);
        title=(EditText) findViewById(R.id.editTextName) ;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddPic);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(AddProfilCapt.this);
                dialog.setContentView(R.layout.custom_dialog_box);
                dialog.setTitle("Choisir une photo...");
                Button btnExit = (Button) dialog.findViewById(R.id.btnExit);
                btnExit.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.findViewById(R.id.btnChoosePath).setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        activeGallery();
                    }
                });
                dialog.findViewById(R.id.btnTakePhoto).setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        activeTakePhoto();
                    }
                });

                // show dialog on screen
                dialog.show();

            }
        });
        ImageView    imageViewProfAdd = (ImageView) findViewById(R.id.imageViewPersonne);

        // Construct the data source
        images = new ArrayList();
        // Create the adapter to convert the array to views
        imageAdapter = new ImageAdapter(this, images);
        // Attach the adapter to a ListView

        initDB();

        Button Bb =(Button) findViewById(R.id.buttonAjoutProfilBb);
        Bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(galorCap==1) {
                  titleString = title.getText().toString();
                  String[] filePathColumn = {MediaStore.Images.Media.DATA};
                  Cursor cursor = getContentResolver().query(partager, filePathColumn, null, null, null);
                  cursor.moveToFirst();
                  int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                  String picturePath = cursor.getString(columnIndex);
                  cursor.close();
                  MyImage image = new MyImage();
                  image.setTitle(titleString);
                  image.setSexe(sexe);
                  image.setDatetimeLong(dateString);
                  image.setPath(picturePath);
                  //                    images.add(image);//notifyDataSetChanged does not work well sometimes
                  Log.v("hajer",sexe);
                  Log.v("hajer",titleString);
                  Log.v("hajer",dateString);

                  imageAdapter.add(image);
                  daOdb.addImage(image);
                  startActivity(new Intent(getApplicationContext(),Main2Activity.class));
              }
                else if (galorCap==2){
                  titleString = title.getText().toString();
                  String[] projection = {MediaStore.Images.Media.DATA};
                  Cursor cursor = managedQuery(mCapturedImageURI, projection, null, null, null);
                  int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                  cursor.moveToFirst();
                  String picturePath = cursor.getString(column_index_data);
                  MyImage image = new MyImage();
                  image.setTitle(titleString);
                  image.setSexe(sexe);
                  image.setDatetimeLong(dateString);
                  image.setPath(picturePath);
                  imageAdapter.add(image);
                  //                    images.add(image);
                  daOdb.addImage(image);
                  startActivity(new Intent(getApplicationContext(),Main2Activity.class));

              }
            }
        });
    }


    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    private void addItemClickListener(final ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MyImage image = (MyImage) listView.getItemAtPosition(position);
                Intent intent = new Intent(getBaseContext(), DisplayImage.class);
                intent.putExtra("IMAGE", (new Gson()).toJson(image));
                startActivity(intent);
            }
        });
    }
    private void activeTakePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            String fileName = "temp.jpg";
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, fileName);
            mCapturedImageURI = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            galorCap =2;
        }
    }

    /**
     * to gallery
     */
    private void activeGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
        galorCap =1;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            partager=(uri);
            Log.d("photoAdd", String.valueOf(uri));

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);



            ImageView    imageViewProfAdd = (ImageView) findViewById(R.id.imageViewPersonne);

                imageViewProfAdd.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }    private void initDB() {
        daOdb = new DAOdb(this);
        //        add images from database to images ArrayList
        for (MyImage mi : daOdb.getImages()) {
            images.add(mi);
        }
    }


}

