package com.fragibe.cajaestanco.data;

import android.content.ContentValues;

import com.fragibe.cajaestanco.data.ArticuloContract.ArticuloEntry;

/**
 * Created by Javier on 25/06/2017.
 */

public class Articulo {

    private int codigo;
    private String descripcion;
    private int lote_min;
    private String um;
    private double precio1;
    private double precio2;
    private boolean selected;

    public Articulo(int codigo, String descripcion, int lote_min, String um, double precio1, double precio2) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.lote_min = lote_min;
        this.um = um;
        this.precio1 = precio1;
        this.precio2 = precio2;
        this.selected = false;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getLote_min() {
        return lote_min;
    }

    public void setLote_min(int lote_min) {
        this.lote_min = lote_min;
    }

    public String getUm() {
        return um;
    }

    public void setUm(String um) {
        this.um = um;
    }

    public double getPrecio1() {
        return precio1;
    }

    public void setPrecio1(double precio1) {
        this.precio1 = precio1;
    }

    public double getPrecio2() {
        return precio2;
    }

    public void setPrecio2(double precio2) {
        this.precio2 = precio2;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put(ArticuloEntry.CODIGO, this.codigo);
        values.put(ArticuloEntry.DESCRIPCION, this.descripcion);
        values.put(ArticuloEntry.LOTE_MIN, this.lote_min);
        values.put(ArticuloEntry.UM, this.um);
        values.put(ArticuloEntry.PRECIO1, this.precio1);
        values.put(ArticuloEntry.PRECIO2, this.precio2);

        return values;
    }
}
