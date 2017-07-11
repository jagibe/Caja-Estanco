package com.fragibe.cajaestanco.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.fragibe.cajaestanco.R;
import com.fragibe.cajaestanco.data.Articulo;

import java.util.ArrayList;

/**
 * Created by Javier on 28/06/2017.
 */

public class ArticuloAdapter extends RecyclerView.Adapter<ArticuloAdapter.ViewHolder> implements Filterable {
    private ArrayList<Articulo> originalDataset;
    private ArrayList<Articulo> filteredDataset;
    private ArticuloFilter mArticlesFilter;

    @Override
    public Filter getFilter() {
        if (mArticlesFilter == null)
            mArticlesFilter = new ArticuloFilter();

        return mArticlesFilter;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvDescripcion;
        public TextView tvCodigo;
        public CheckBox cbRow;

        public ViewHolder(View v) {
            super(v);
            tvDescripcion = v.findViewById(R.id.tvRowDescripcion);
            tvCodigo = v.findViewById(R.id.tvRowCodigo);
            cbRow = v.findViewById(R.id.cbRow);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ArticuloAdapter(ArrayList<Articulo> myDataset) {
        originalDataset = myDataset;
        filteredDataset = myDataset;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ArticuloAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_articulo, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        final Articulo e = filteredDataset.get(position);

        // - replace the contents of the view with that element
        holder.tvDescripcion.setText(e.getDescripcion());
        String cod = e.getCodigo() + "";
        holder.tvCodigo.setText(cod);

        /*holder.itemView.setBackgroundColor(e.isSelected() ? Color.LTGRAY : Color.WHITE);
        holder.cbRow.setChecked(e.isSelected());
        holder.cbRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                e.setSelected(!e.isSelected());
                holder.itemView.setBackgroundColor(e.isSelected() ? Color.LTGRAY : Color.WHITE);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                e.setSelected(!e.isSelected());
                holder.cbRow.setChecked(e.isSelected());
                holder.itemView.setBackgroundColor(e.isSelected() ? Color.LTGRAY : Color.WHITE);
            }
        });*/

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return filteredDataset.size();
    }

    public class ArticuloFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // Create a FilterResults object
            FilterResults results = new FilterResults();

            // If the constraint (search string/pattern) is null
            // or its length is 0, i.e., its empty then
            // we just set the `values` property to the
            // original contacts list which contains all of them
            if (constraint == null || constraint.length() == 0) {
                results.values = originalDataset;
                results.count = originalDataset.size();
            } else {
                // Some search copnstraint has been passed
                // so let's filter accordingly
                ArrayList<Articulo> filteredArticles = new ArrayList<Articulo>();

                // We'll go through all the articles and see
                // if they contain the supplied string
                for (Articulo a : originalDataset) {
                    if (a.getDescripcion().toUpperCase().contains(constraint.toString().toUpperCase())) {
                        // if `contains` == true then add it
                        // to our filtered list
                        filteredArticles.add(a);
                    }
                }

                // Finally set the filtered values and size/count
                results.values = filteredArticles;
                results.count = filteredArticles.size();
            }

            // Return our FilterResults object
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            filteredDataset = (ArrayList<Articulo>) filterResults.values;
            notifyDataSetChanged();
        }
    }

}

