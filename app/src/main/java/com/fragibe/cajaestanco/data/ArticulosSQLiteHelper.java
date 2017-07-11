package com.fragibe.cajaestanco.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.fragibe.cajaestanco.data.ArticuloContract.ArticuloEntry;

import java.util.ArrayList;

/**
 * Created by Javier on 25/06/2017.
 */

public class ArticulosSQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Articulos.db";
    private SQLiteDatabase db;

    private Context context;

    public ArticulosSQLiteHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + ArticuloEntry.TABLE_NAME +
                "(" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ArticuloEntry.CODIGO + " INTEGER," +
                ArticuloEntry.DESCRIPCION + " TEXT," +
                ArticuloEntry.LOTE_MIN + " INTEGER," +
                ArticuloEntry.UM + " TEXT," +
                ArticuloEntry.PRECIO1 + " REAL," +
                ArticuloEntry.PRECIO2 + " REAL," +
                ArticuloEntry.CATEGORIA + " TEXT," +
                "UNIQUE(codigo))";

        sqLiteDatabase.execSQL(sql);

        // Insertar artículos de prueba
        mockData(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ArticuloEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void mockData(SQLiteDatabase sqLiteDatabase) {
        mockArticulo(sqLiteDatabase, new Articulo(17492, "DUCADOS RUBIO 100'S BY JPS", 10, "Cajetilla", 4.2, 4.35));
        mockArticulo(sqLiteDatabase, new Articulo(2886, "MARLBORO GOLD DURO", 10, "Cajetilla", 4.95, 5.1));
        mockArticulo(sqLiteDatabase, new Articulo(2884, "MARLBORO RED DURO", 10, "Cajetilla", 4.95, 5.1));
        mockArticulo(sqLiteDatabase, new Articulo(2637, "MARLBORO RED 100'S", 10, "Cajetilla", 5.05, 5.2));
    }

    public long mockArticulo(SQLiteDatabase sqLiteDatabase, Articulo articulo) {
        return sqLiteDatabase.insert(
                ArticuloEntry.TABLE_NAME,
                null,
                articulo.toContentValues());
    }

    public boolean saveArticulo(Articulo... articulos) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        boolean ret = true;

        for (Articulo articulo : articulos) {
            ret = ret && (sqLiteDatabase.insert(ArticuloEntry.TABLE_NAME, null, articulo.toContentValues()) > 0);
        }

        sqLiteDatabase.close();

        return ret;
    }

    public Cursor getArticuloByCodigo(int codigo) {
        Cursor c = getReadableDatabase().query(
                ArticuloEntry.TABLE_NAME,
                null,
                ArticuloEntry.CODIGO + " = ?",
                new String[]{codigo + ""},
                null,
                null,
                null);

        return c;
    }

    public ArrayList<Articulo> getAllArticulos() {
        ArrayList<Articulo> list = new ArrayList<>();

        Cursor cursor = getReadableDatabase()
                .query(ArticuloEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String codigo = cursor.getString(cursor.getColumnIndexOrThrow(ArticuloEntry.CODIGO));
                String descripcion = cursor.getString(cursor.getColumnIndexOrThrow(ArticuloEntry.DESCRIPCION));
                String lote_min = cursor.getString(cursor.getColumnIndexOrThrow(ArticuloEntry.LOTE_MIN));
                String um = cursor.getString(cursor.getColumnIndexOrThrow(ArticuloEntry.UM));
                String precio1 = cursor.getString(cursor.getColumnIndexOrThrow(ArticuloEntry.PRECIO1));
                String precio2 = cursor.getString(cursor.getColumnIndexOrThrow(ArticuloEntry.PRECIO2));
                String categoria = cursor.getString(cursor.getColumnIndexOrThrow(ArticuloEntry.CATEGORIA));

                list.add(new Articulo(Integer.parseInt(codigo), descripcion, Integer.parseInt(lote_min), um, Double.parseDouble(precio1), Double.parseDouble(precio2), categoria));
                cursor.moveToNext();
            }
        }

        return list;
    }

    public int clearArticulos() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(ArticuloEntry.TABLE_NAME, "1", null);
    }
}
