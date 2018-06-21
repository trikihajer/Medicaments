package com.tuteur.projet.medicaments.Controler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lenovo on 18/12/2017.
 */

public class MedicasHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "sqliteimage.medicas";
    public static final int DB_VERSION = 1;

    public static final String COMMA_SEP = ",";
    public static final String TEXT_TYPE = " TEXT";
    public static final String NUMERIC_TYPE = " NUMERIC";

    public static final String TABLE_NAME = "medicas";

    public static final String COLUMN_MEDICASPATH = "path";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_FREQ = "freq";
    public static final String COLUMN_MOMENT = "momentPrise";
    public static final String COLUMN_DATETIMEDeb = "datetime";
    public static final String COLUMN_PATHOLOGIE = "pathologie";
    public static final String COLUMN_nbComp = "nbComp";
    public static final String COLUMN_DUREE= "duree";
    public static final String COLUMN_HORRAIRE= "horraire";

    public static final String PRIMARY_KEY = "PRIMARY KEY ("+ COLUMN_NAME + ")";

            // + COLUMN_NAME + "," + COLUMN_DATETIMEDeb + ")";

    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_MEDICASPATH + TEXT_TYPE + COMMA_SEP +
            COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
            COLUMN_PATHOLOGIE + TEXT_TYPE + COMMA_SEP +
            COLUMN_MOMENT + TEXT_TYPE + COMMA_SEP +
            COLUMN_DATETIMEDeb + TEXT_TYPE + COMMA_SEP +
            COLUMN_FREQ + TEXT_TYPE + COMMA_SEP +
            COLUMN_nbComp + NUMERIC_TYPE + COMMA_SEP +
            COLUMN_DUREE + NUMERIC_TYPE + COMMA_SEP +
            COLUMN_HORRAIRE + NUMERIC_TYPE + COMMA_SEP +

            PRIMARY_KEY +
            " )";

    public MedicasHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE);
        onCreate(db);
    }

}
