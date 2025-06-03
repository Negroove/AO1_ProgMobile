package com.montoya181.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.montoya181.domain.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactRepository {

    private final ContactDbHelper dbHelper;

    public ContactRepository(Context context) {
        dbHelper = new ContactDbHelper(context);
    }

    public long insert(Contact c) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ContactDbHelper.COL_FIRST,    c.getFirstName());
        cv.put(ContactDbHelper.COL_LAST,  c.getLastName());
        cv.put(ContactDbHelper.COL_PHONE,  c.getPhone());
        cv.put(ContactDbHelper.COL_ADDRESS, c.getAddress());
        cv.put(ContactDbHelper.COL_GENDER,    c.getGender());
        long id = db.insert(ContactDbHelper.TABLE, null, cv);
        db.close();
        return id;
    }

    public int update(long id, Contact c) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ContactDbHelper.COL_FIRST,    c.getFirstName());
        cv.put(ContactDbHelper.COL_LAST,  c.getLastName());
        cv.put(ContactDbHelper.COL_PHONE,  c.getPhone());
        cv.put(ContactDbHelper.COL_ADDRESS, c.getAddress());
        cv.put(ContactDbHelper.COL_GENDER,    c.getGender());
        int rows = db.update(
                ContactDbHelper.TABLE,
                cv,
                ContactDbHelper.COL_ID + "=?",
                new String[]{ String.valueOf(id) }
        );
        db.close();
        return rows;
    }

    public int delete(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows = db.delete(
                ContactDbHelper.TABLE,
                ContactDbHelper.COL_ID + "=?",
                new String[]{ String.valueOf(id) }
        );
        db.close();
        return rows;
    }


    public List<Contact> getAll() {
        List<Contact> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query(
                ContactDbHelper.TABLE,
                null,   // todas las columnas
                null,   // sin WHERE
                null,
                null, null, null
        );
        while (c.moveToNext()) {
            long   id      = c.getLong(c.getColumnIndexOrThrow(ContactDbHelper.COL_ID));
            String nombre  = c.getString(c.getColumnIndexOrThrow(ContactDbHelper.COL_FIRST));
            String apellido= c.getString(c.getColumnIndexOrThrow(ContactDbHelper.COL_LAST));
            String telefono= c.getString(c.getColumnIndexOrThrow(ContactDbHelper.COL_PHONE));
            String direc   = c.getString(c.getColumnIndexOrThrow(ContactDbHelper.COL_ADDRESS));
            String genero  = c.getString(c.getColumnIndexOrThrow(ContactDbHelper.COL_GENDER));
            // Asume que tu Contact.java tiene constructor (id, first, last, phone, address, gender)
            list.add(new Contact(id, nombre, apellido, telefono, direc, genero));
        }
        c.close();
        db.close();
        return list;
    }

    public Contact getById(long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query(
                ContactDbHelper.TABLE,
                null,
                ContactDbHelper.COL_ID + "=?",
                new String[]{ String.valueOf(id) },
                null, null, null
        );
        Contact result = null;
        if (c.moveToFirst()) {
            String first   = c.getString(c.getColumnIndexOrThrow(ContactDbHelper.COL_FIRST));
            String last    = c.getString(c.getColumnIndexOrThrow(ContactDbHelper.COL_LAST));
            String phone   = c.getString(c.getColumnIndexOrThrow(ContactDbHelper.COL_PHONE));
            String address = c.getString(c.getColumnIndexOrThrow(ContactDbHelper.COL_ADDRESS));
            String gender  = c.getString(c.getColumnIndexOrThrow(ContactDbHelper.COL_GENDER));
            result = new Contact(id, first, last, phone, address, gender);
        }
        c.close();
        db.close();
        return result;
    }
}
