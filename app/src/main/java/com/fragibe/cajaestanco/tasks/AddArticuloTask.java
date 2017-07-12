package com.fragibe.cajaestanco.tasks;

import android.os.AsyncTask;

import com.fragibe.cajaestanco.data.Articulo;
import com.fragibe.cajaestanco.data.ArticulosSQLiteHelper;

/**
 * Created by Javier on 10/07/2017.
 */

public class AddArticuloTask extends AsyncTask<Articulo, Void, Boolean> {

    private ArticulosSQLiteHelper mArticulosDbHelper;

    public void setArticulosSQLiteHelper(ArticulosSQLiteHelper dbHelper) {
        mArticulosDbHelper = dbHelper;
    }

    @Override
    protected Boolean doInBackground(Articulo... articulos) {
        boolean ret = true;

        for (Articulo art : articulos)
            ret = ret && mArticulosDbHelper.saveArticulo(art);

        return ret;
    }
}
