package com.tuteur.projet.medicaments.UI.ListeMedicas;

import android.Manifest;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.tuteur.projet.medicaments.Controler.MedicasAdapter;
import com.tuteur.projet.medicaments.DAO.DAOdbMedicas;
import com.tuteur.projet.medicaments.Model.metier.Medicas;
import com.tuteur.projet.medicaments.R;
import com.tuteur.projet.medicaments.UI.NotificationPublisher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class AddMedicasActivity extends AppCompatActivity {

    private ArrayList<Medicas> images;

    private Uri mCapturedImageURI;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    int galorCap;

    Uri partager;
    private DAOdbMedicas daOdb;
    private MedicasAdapter imageAdapter;

    BarcodeDetector detector;
    CameraSource cameraSource;
    SurfaceView surfaceView;
    TextView txtEss;
    final int RequestCameraPermissionID = 1001;



    private Calendar calendar;
    private int year, month, day;

    String picturePath;
    private GoogleApiClient client;

    //getchamp
    private EditText nomMedEditText;
    private TextView dateView;
    Spinner formePharmaSpinner;
    Spinner freqSpinner;
    Spinner momentSpinner;
    Spinner dureeSpinner;
    EditText dureeEditText;
    TextView horraireTextView;
    EditText nbCompEditText;

    String nomMedString, dateViewString ,formePharmaString,freqString, momentStringr,  nbCompString,dureeString,horraireString;
    private Bitmap bitmape;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                               return;
                        }
                        cameraSource.start(surfaceView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicas);
        nomMedEditText=(EditText) findViewById(R.id.editTextNameMedicas) ;
        horraireTextView=(TextView) findViewById(R.id.time1) ;
        dureeEditText=(EditText) findViewById(R.id.duree);
        nbCompEditText=(EditText) findViewById(R.id.nbComp);

        dateView = (TextView) findViewById(R.id.datedebtv);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);

        formePharmaSpinner = (Spinner) findViewById(R.id.formePharam);
        freqSpinner = (Spinner) findViewById(R.id.freq);
        momentSpinner = (Spinner) findViewById(R.id.moment);
        dureeSpinner = (Spinner) findViewById(R.id.dureeSpin);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.formePharamValue, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        formePharmaSpinner.setAdapter(adapter);


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.freqPriseValue, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        freqSpinner.setAdapter(adapter2);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.momentValue, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        momentSpinner.setAdapter(adapter3);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this,
                R.array.dureeValue, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        dureeSpinner.setAdapter(adapter4);


        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fabAddPicMedicas);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(AddMedicasActivity.this);
                dialog.setContentView(R.layout.custom_dialog_box);
                dialog.setTitle("Choisir une photo...");
                Button btnExit = (Button) dialog.findViewById(R.id.btnExit);
                btnExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.findViewById(R.id.btnChoosePath).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activeGallery();
                    }
                });
                dialog.findViewById(R.id.btnTakePhoto).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activeTakePhoto();
                    }
                });

                // show dialog on screen
                dialog.show();

            }
        });

        images = new ArrayList();
        imageAdapter = new MedicasAdapter(this, images);

        initDB();

        surfaceView = (SurfaceView) findViewById(R.id.surfaceViewEss);
        txtEss = (TextView) findViewById(R.id.Camerasurfacetv);


        detector =
                new BarcodeDetector.Builder(getApplicationContext())
                        .setBarcodeFormats(Barcode.QR_CODE)
                        .build();

        cameraSource = new CameraSource
                .Builder(this, detector)
                .setRequestedPreviewSize(640, 480)
                .build();
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {


                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddMedicasActivity.this,
                            new String[]{Manifest.permission.CAMERA}, RequestCameraPermissionID);


                    return;
                }
                try {
                    cameraSource.start(surfaceView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }


            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        detector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcode = detections.getDetectedItems();
                if (qrcode.size() != 0) {
                    txtEss.post(new Runnable() {
                        @Override
                        public void run() {

                            Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(1000);
                            txtEss.setText(qrcode.valueAt(0).displayValue);
                        }
                    });
                }
            }
        });
        Button Bb = (Button) findViewById(R.id.buttonAjoutMedicas);
        Bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChamps();

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());

                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);
                int mHh=calendar.get(Calendar.HOUR_OF_DAY);
                int mMn=calendar.get(Calendar.MINUTE);



                String datenew[]=dateViewString.split("/");
                String dateF=datenew[0]+""+datenew[1]+""+datenew[2];

                String time1[]=horraireString.split(":");
                String timeF=time1[0]+""+time1[1];

                int diffHeur=Integer.parseInt(time1[0])-mHh;
                int diffMn=Integer.parseInt(time1[1])-mMn;


                int total=((diffHeur*3600)+(diffMn*60))*1000;

                try {
                    scheduleNotification(getNotification("le nombre de comprimé est :" + nbCompString), total);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (galorCap == 1) {

                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(partager, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                     picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    Medicas image = new Medicas();
                    image.setNomMed(nomMedString);
                    image.setPathologie(formePharmaString);
                    image.setPhotoMed(picturePath);
                    image.setFreq(freqString);
                    image.setDateDeb(dateViewString);
                    image.setMomentPrise(momentStringr);
                    image.setNbComp(Integer.parseInt(nbCompString));
                    image.setDuree(Integer.parseInt(dureeString));


                    image.setHoraire(Integer.parseInt(timeF));
                    imageAdapter.add(image);
                    daOdb.addMedicas(image);
                    startActivity(new Intent(getApplicationContext(), ListeMedicasActivity.class));

                }
                else if (galorCap==2){

                    getChamps();
                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = managedQuery(mCapturedImageURI, projection, null, null, null);
                    int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                     picturePath = cursor.getString(column_index_data);
                    Medicas image = new Medicas();
                    image.setNomMed(nomMedString);
                    image.setPathologie(formePharmaString);
                    image.setPhotoMed(picturePath);
                    image.setFreq(freqString);
                    image.setDateDeb(dateViewString);
                    image.setMomentPrise(momentStringr);
                    image.setNbComp(Integer.parseInt(nbCompString));
                    image.setDuree(Integer.parseInt(dureeString));


                    image.setHoraire(Integer.parseInt(timeF));

                    imageAdapter.add(image);
                    daOdb.addMedicas(image);
                    startActivity(new Intent(getApplicationContext(), ListeMedicasActivity.class));
                }
            }
        });
        final TextView time1;
        time1 = (TextView) findViewById(R.id.time1);


        Button horraire = (Button) findViewById(R.id.horrairebnt);
        horraire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddMedicasActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time1.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Préciser le temps...");
                mTimePicker.show();
            }
        });

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    private void getChamps()
    {
        nomMedString=nomMedEditText.getText().toString();
        dateViewString=dateView.getText().toString();
        formePharmaString=formePharmaSpinner.getSelectedItem().toString();
        freqString=freqSpinner.getSelectedItem().toString();
        momentStringr=momentSpinner.getSelectedItem().toString();
        dureeString=dureeEditText.getText().toString();
        horraireString=horraireTextView.getText().toString();
        nbCompString=nbCompEditText.getText().toString();

    }
    private void initDB() {
        daOdb = new DAOdbMedicas(this);
        //        add images from database to images ArrayList
        for (Medicas mi : daOdb.getImages()) {
            images.add(mi);
        }
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);

    }
    /****************************************calendrier*************************/

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

                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    /****************************************image de la gallery ou prise *************************/

    private void activeTakePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            String fileName = "temp.jpg";
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, fileName);
            mCapturedImageURI = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            galorCap = 2;
        }
    }

    /**
     * to gallery
     */
    private void activeGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
        galorCap = 1;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            partager = (uri);
            Log.d("photoAdd", String.valueOf(uri));

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                bitmape=bitmap;

                ImageView imageViewProfAdd = (ImageView) findViewById(R.id.imageViewMedicas);

                imageViewProfAdd.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /****************************************qrCode*************************/

    @Override
    public void onStart() {
        super.onStart();
        client.connect();
        // client.connect();
        // AppIndex.AppIndexApi.start(client, getIndexApiAction());
        AppIndex.AppIndexApi.start(client, getIndexApiAction0());
    }

    @Override
    public void onStop() {
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction0());
      // client.disconnect();
        client.disconnect();
    }
   public Action getIndexApiAction0() {
        Thing object = new Thing.Builder()
                .setName("AddMedicas Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }


    /****************************************notification*************************/

    //calculer la difference entre 2 date
    private  int calculerDiff(){

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        int mHh=calendar.get(Calendar.HOUR);
        int mMn=calendar.get(Calendar.MINUTE);
        String current=mYear+""+mMonth+""+mDay+""+mHh+""+mMn;

        int diff=0;
        String datenew[]=dateViewString.split("/");
        String dateF=datenew[0]+""+datenew[1]+""+datenew[2];

        String time1[]=horraireString.split(":");
        String timeF=dateF+""+time1[0]+""+time1[1];
        int time2= Integer.parseInt(timeF);
        int currentint= Integer.parseInt(current);

        Log.d("currentt",timeF+""+current);
        diff=time2-currentint;
        Log.d("ladifference", String.valueOf(diff));
        return diff;
    }
    private void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);

    }

    private Notification getNotification(String content) throws IOException {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Medicament :"+nomMedString);
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.medicas);
        return builder.build();
    }

}


