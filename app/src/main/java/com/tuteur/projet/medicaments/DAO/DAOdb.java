package com.tuteur.projet.medicaments.DAO;

/**
 * Created by Lenovo on 20/11/2017.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tuteur.projet.medicaments.Controler.DBhelper;
import com.tuteur.projet.medicaments.Model.metier.MyImage;

import java.util.ArrayList;
import java.util.List;


public class DAOdb {

    private SQLiteDatabase database;
    private DBhelper dbHelper;

    public DAOdb(Context context) {
        dbHelper = new DBhelper(context);
        database = dbHelper.getWritableDatabase();
    }

    /**
     * close any database object
     */
    public void close() {
        dbHelper.close();
    }

    /**
     * insert a text report item to the location database table
     *
     * @param image
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long addImage(MyImage image) {
        ContentValues cv = new ContentValues();
        cv.put(DBhelper.COLUMN_PATH, image.getPath());
        cv.put(DBhelper.COLUMN_TITLE, image.getTitle());
        cv.put(DBhelper.COLUMN_DESCRIPTION, image.getSexe());
        cv.put(DBhelper.COLUMN_DATETIME, image.getDatetimeLong());
        return database.insert(DBhelper.TABLE_NAME, null, cv);
    }

    /**
     * delete the given image from database
     *
     * @param image
     */
    public void deleteImage(MyImage image) {
        String whereClause =
                DBhelper.COLUMN_TITLE + "=? AND " + DBhelper.COLUMN_DATETIME +
                        "=?";
        String[] whereArgs = new String[]{image.getTitle(),
                String.valueOf(image.getDatetimeLong())};
        database.delete(DBhelper.TABLE_NAME, whereClause, whereArgs);
    }

    /**
     * @return all image as a List
     */
    public List<MyImage> getImages() {
        List<MyImage> MyImages = new ArrayList<>();
        Cursor cursor =
                database.query(DBhelper.TABLE_NAME, null, null, null, null,
                        null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MyImage MyImage = cursorToMyImage(cursor);
            MyImages.add(MyImage);
            cursor.moveToNext();
        }
        cursor.close();
        return MyImages;
    }

    /**
     * read the cursor row and convert the row to a MyImage object
     *
     * @param cursor
     * @return MyImage object
     */
    private MyImage cursorToMyImage(Cursor cursor) {
        MyImage image = new MyImage();
        image.setPath(
                cursor.getString(cursor.getColumnIndex(DBhelper.COLUMN_PATH)));
        image.setTitle(
                cursor.getString(cursor.getColumnIndex(DBhelper.COLUMN_TITLE)));
        image.setDatetimeLong(cursor.getString(
                cursor.getColumnIndex(DBhelper.COLUMN_DATETIME)));
        image.setSexe(cursor.getString(
                cursor.getColumnIndex(DBhelper.COLUMN_DESCRIPTION)));
        return image;
    }
}