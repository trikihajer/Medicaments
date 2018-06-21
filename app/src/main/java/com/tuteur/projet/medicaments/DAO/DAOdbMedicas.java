package com.tuteur.projet.medicaments.DAO;

/**
 * Created by Lenovo on 24/11/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tuteur.projet.medicaments.Controler.MedicasHelper;
import com.tuteur.projet.medicaments.Model.metier.Medicas;

import java.util.ArrayList;
import java.util.List;


public class DAOdbMedicas {

    private SQLiteDatabase database;
    private MedicasHelper medicasHelper;

    public DAOdbMedicas(Context context) {
        medicasHelper = new MedicasHelper(context);
        database = medicasHelper.getWritableDatabase();
    }


    public void close() {
        medicasHelper.close();
    }


    public long addMedicas(Medicas image) {
        ContentValues cv = new ContentValues();
        cv.put(MedicasHelper.COLUMN_NAME, image.getNomMed());
        cv.put(MedicasHelper.COLUMN_PATHOLOGIE, image.getPathologie());
        cv.put(MedicasHelper.COLUMN_MEDICASPATH, image.getPhotoMed());
        cv.put(MedicasHelper.COLUMN_FREQ, image.getFreq());
        cv.put(MedicasHelper.COLUMN_DATETIMEDeb, image.getDateDeb());
        cv.put(MedicasHelper.COLUMN_MOMENT, image.getMomentPrise());
        cv.put(MedicasHelper.COLUMN_nbComp, image.getNbComp());
        cv.put(MedicasHelper.COLUMN_DUREE, image.getDuree());
        cv.put(MedicasHelper.COLUMN_HORRAIRE, image.getHoraire());



        return database.insert(MedicasHelper.TABLE_NAME, null, cv);
    }


    public void deleteMedicas(Medicas image) {
        String whereClause =
                MedicasHelper.COLUMN_NAME + "=? AND " + MedicasHelper.COLUMN_DATETIMEDeb +
                        "=?";
        String[] whereArgs = new String[]{image.getNomMed(),
                String.valueOf(image.getDateDeb())};
        database.delete(MedicasHelper.TABLE_NAME, whereClause, whereArgs);
    }


    public List<Medicas> getImages() {
        List<Medicas> medicases = new ArrayList<>();
        Cursor cursor =
                database.query(MedicasHelper.TABLE_NAME, null, null, null, null,
                        null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Medicas medicas = cursorToMyImage(cursor);
            medicases.add(medicas);
            cursor.moveToNext();
        }
        cursor.close();
        return medicases;
    }

    private Medicas cursorToMyImage(Cursor cursor) {
        Medicas image = new Medicas();
        image.setNomMed(
                cursor.getString(cursor.getColumnIndex(MedicasHelper.COLUMN_NAME)));
        image.setPathologie(
                cursor.getString(cursor.getColumnIndex(MedicasHelper.COLUMN_PATHOLOGIE)));
        image.setPhotoMed(cursor.getString(
                cursor.getColumnIndex(MedicasHelper.COLUMN_MEDICASPATH)));
        image.setFreq(cursor.getString(
                cursor.getColumnIndex(MedicasHelper.COLUMN_FREQ)));
        image.setDateDeb(cursor.getString(
                cursor.getColumnIndex(MedicasHelper.COLUMN_DATETIMEDeb)));
        image.setMomentPrise(cursor.getString(
                cursor.getColumnIndex(MedicasHelper.COLUMN_MOMENT)));
        image.setNbComp(cursor.getInt(
                cursor.getColumnIndex(MedicasHelper.COLUMN_nbComp)));
        image.setDuree(cursor.getInt(
                cursor.getColumnIndex(MedicasHelper.COLUMN_DUREE)));
        image.setHoraire(cursor.getInt(
                cursor.getColumnIndex(MedicasHelper.COLUMN_HORRAIRE)));
        return image;
    }
}