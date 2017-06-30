package com.fragibe.cajaestanco.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.fragibe.cajaestanco.data.ArticuloContract.ArticuloEntry;

/**
 * Created by Javier on 25/06/2017.
 */

public class ArticulosSQLiteHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Articulos.db";

    public ArticulosSQLiteHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
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

    private void mockData(SQLiteDatabase sqLiteDatabase) {
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

    public long saveArticulo(Articulo articulo) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                ArticuloEntry.TABLE_NAME,
                null,
                articulo.toContentValues());

    }
}
