package com.fragibe.cajaestanco.data;

import android.provider.BaseColumns;

/**
 * Created by Javier on 25/06/2017.
 */

public class ArticuloContract {
    public static abstract class ArticuloEntry implements BaseColumns {
        public static final String TABLE_NAME = "articulos";

        public static final String CODIGO = "codigo";
        public static final String DESCRIPCION = "descripcion";
        public static final String LOTE_MIN = "lote_min";
        public static final String UM = "um";
        public static final String PRECIO1 = "precio1";
        public static final String PRECIO2 = "precio2";
        public static final String CATEGORIA = "categoria";
    }
}
