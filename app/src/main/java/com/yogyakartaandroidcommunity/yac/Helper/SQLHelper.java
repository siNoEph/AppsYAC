package com.yogyakartaandroidcommunity.yac.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yogyakartaandroidcommunity.yac.Config.ConfigDB;
import com.yogyakartaandroidcommunity.yac.Serialize.AnggotaSerialize;
import com.yogyakartaandroidcommunity.yac.Serialize.DaftarHadirSerialize;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Septian on 20-Aug-16.
 */
public class SQLHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ANGGOTA =
            "CREATE TABLE anggota (" +
                    "id Integer PRIMARY KEY AUTOINCREMENT, " +
                    "member_id varchar(255), " +
                    "nama varchar(255), " +
                    "email varchar(255), " +
                    "hp varchar(255)" +
                    ");";

    private static final String SQL_DELETE_ANGGOTA = "DROP TABLE IF EXISTS anggota";

    private static final String SQL_CREATE_DAFTAR_HADIR =
            "CREATE TABLE daftar_hadir (" +
                    "id Integer PRIMARY KEY AUTOINCREMENT, " +
                    "member_id varchar(255), " +
                    "hadir DATETIME DEFAULT CURRENT_TIMESTAMP" +
                    ");";

    private static final String SQL_DELETE_DAFTAR_HADIR = "DROP TABLE IF EXISTS daftar_hadir";

    public SQLHelper(Context context) {
        super(context, ConfigDB.getDatabaseName(), null, ConfigDB.getDatabaseVersion());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ANGGOTA);
        db.execSQL(SQL_CREATE_DAFTAR_HADIR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ANGGOTA);
        db.execSQL(SQL_DELETE_DAFTAR_HADIR);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void addAnggota(AnggotaSerialize anggotaSerialize) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("member_id", anggotaSerialize.getMemberId());
        values.put("nama", anggotaSerialize.getNama());
        values.put("email", anggotaSerialize.getEmail());
        values.put("hp", anggotaSerialize.getHp());

        db.insert("anggota", null, values); // Inserting Row
        db.close(); // Closing database connection
    }

    public void updateAnggota(AnggotaSerialize anggotaSerialize, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("member_id", anggotaSerialize.getMemberId());
        values.put("nama", anggotaSerialize.getNama());
        values.put("email", anggotaSerialize.getEmail());
        values.put("hp", anggotaSerialize.getHp());

        db.update("anggota", values, "id = ?", new String[]{String.valueOf(id)}); // Updating row
        db.close(); // Closing database connection
    }

    public AnggotaSerialize getAnggota(String member_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM anggota WHERE member_id = '" + member_id + "'", null);

        AnggotaSerialize anggota = new AnggotaSerialize();
        if (cursor.moveToFirst()) {
            anggota.setId(Integer.parseInt(cursor.getString(0)));
            anggota.setMemberId(cursor.getString(1));
            anggota.setNama(cursor.getString(2));
            anggota.setEmail(cursor.getString(3));
            anggota.setHp(cursor.getString(4));
        }
        db.close(); // Closing database connection

        return anggota;
    }

    public List<AnggotaSerialize> getAllAnggota() {
        List<AnggotaSerialize> anggotaList = new ArrayList<AnggotaSerialize>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM anggota", null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AnggotaSerialize anggota = new AnggotaSerialize();
                anggota.setId(Integer.parseInt(cursor.getString(0)));
                anggota.setMemberId(cursor.getString(1));
                anggota.setNama(cursor.getString(2));
                anggota.setEmail(cursor.getString(3));
                anggota.setHp(cursor.getString(4));

                // Adding contact to list
                anggotaList.add(anggota);
            } while (cursor.moveToNext());
        }
        db.close(); // Closing database connection

        return anggotaList;
    }

    public void deleteAnggota(AnggotaSerialize anggotaSerialize) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("anggota", "id = ?", new String[]{String.valueOf(anggotaSerialize.getId())});
        db.close();
    }

    public void addDaftarHadir(DaftarHadirSerialize daftarHadirSerialize) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("member_id", daftarHadirSerialize.getMemberId());
        values.put("hadir", OtherMethod.dateNow("yyyy-MM-dd HH:mm:ss"));

        db.insert("daftar_hadir", null, values); // Inserting Row
        db.close(); // Closing database connection
    }

    public DaftarHadirSerialize getSudahAbsen(String member_id, String dateHadir) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM daftar_hadir WHERE member_id = '" + member_id + "' AND hadir LIKE '" + dateHadir + "%'", null);

        DaftarHadirSerialize sudahHadir = new DaftarHadirSerialize();
        if (cursor.moveToFirst()) {
            sudahHadir.setMemberId(cursor.getString(1));
            sudahHadir.setHadir(cursor.getString(2));
        }
        db.close(); // Closing database connection

        return sudahHadir;
    }

    public List<DaftarHadirSerialize> getAllDaftarHadir() {
        List<DaftarHadirSerialize> daftarHadirList = new ArrayList<DaftarHadirSerialize>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM daftar_hadir", null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DaftarHadirSerialize daftarHadir = new DaftarHadirSerialize();
                daftarHadir.setMemberId(cursor.getString(1));
                daftarHadir.setHadir(cursor.getString(2));

                // Adding contact to list
                daftarHadirList.add(daftarHadir);
            } while (cursor.moveToNext());
        }
        db.close(); // Closing database connection

        return daftarHadirList;
    }

    public void deleteAllMemberId(AnggotaSerialize anggotaSerialize) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("daftar_hadir", "member_id = ?", new String[]{anggotaSerialize.getMemberId()});
        db.close();
    }
}
