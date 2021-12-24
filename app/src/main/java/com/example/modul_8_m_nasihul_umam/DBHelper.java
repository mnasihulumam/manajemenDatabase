package com.example.modul_8_m_nasihul_umam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context){
        super(context, "DatabaseKontak.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tabel_kontak (nim INTEGER PRIMARY KEY, nama VARCHAR, telp VARCHAR, alamat TEXT, pos VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS tabel_kontak");
        onCreate(db);
    }

    public boolean insertContact(String nim, String nama, String telp, String alamat, String pos){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nim", nim);
        contentValues.put("nama", nama);
        contentValues.put("telp", telp);
        contentValues.put("alamat", alamat);
        contentValues.put("pos", pos);
        db.insert("tabel_kontak", null, contentValues);
        return true;
    }

    public Cursor getData(String nim){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from tabel_kontak where nim = "+nim +"", null);
        return  res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, "tabel_kontak");
        return numRows;
    }

    public boolean updateContact(String nim, String nama, String telp, String alamat, String pos){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nim", nim);
        contentValues.put("nama", nama);
        contentValues.put("telp", telp);
        contentValues.put("alamat", alamat);
        contentValues.put("pos", pos);
        db.update("tabel_kontak", contentValues, "nim = ?", new String[] {nim});
        return true;
    }

    public boolean deleteContact(String nim) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("tabel_kontak", "nim = ?", new String[]{nim});
        return  true;
    }

    public ArrayList<String> getAllContacts(){
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from tabel_kontak", null);
        res.moveToFirst();

        while (res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex("nim")));
            res.moveToNext();
        }
        return array_list;
    }
}
