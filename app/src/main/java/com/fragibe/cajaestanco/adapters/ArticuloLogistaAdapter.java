package com.fragibe.cajaestanco.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fragibe.cajaestanco.R;
import com.fragibe.cajaestanco.data.Articulo;

import java.util.ArrayList;

/**
 * Created by Javier on 28/06/2017.
 */

public class ArticuloLogistaAdapter extends RecyclerView.Adapter<ArticuloLogistaAdapter.ViewHolder> {
    private ArrayList<Articulo> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvDescripcion;
        public TextView tvCodigo;

        public ViewHolder(View v) {
            super(v);
            tvDescripcion = v.findViewById(R.id.tvRowDescripcion);
            tvCodigo = v.findViewById(R.id.tvRowCodigo);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ArticuloLogistaAdapter(ArrayList<Articulo> myDataset) {
        mDataset = myDataset;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ArticuloLogistaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_articulo_logista, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        Articulo e = mDataset.get(position);

        // - replace the contents of the view with that element
        holder.tvDescripcion.setText(e.getDescripcion());
        String cod = e.getCodigo() + "";
        holder.tvCodigo.setText(cod);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}
