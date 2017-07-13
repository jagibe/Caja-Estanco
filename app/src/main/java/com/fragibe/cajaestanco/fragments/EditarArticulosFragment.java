package com.fragibe.cajaestanco.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fragibe.cajaestanco.R;
import com.fragibe.cajaestanco.activities.MainActivity;
import com.fragibe.cajaestanco.adapters.ArticuloAdapter;
import com.fragibe.cajaestanco.data.Articulo;
import com.fragibe.cajaestanco.data.ArticulosSQLiteHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class EditarArticulosFragment extends Fragment {
    private FloatingActionButton fabAddArticle;

    private MainActivity main;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArticuloAdapter mAdapter;
    private ArrayList<Articulo> myDataset;
    ArticulosSQLiteHelper aslh;

    private boolean mAlreadyLoaded;
    public EditarArticulosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_editar_articulos, container, false);
        // Toast.makeText(getActivity(), aslh.clearArticulos()+" articulos borrados", Toast.LENGTH_SHORT).show();

        fabAddArticle = v.findViewById(R.id.fabAddArticle);
        fabAddArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.replaceFragment(AddEditArticuloFragment.newInstance(null), true);
            }
        });

        mRecyclerView = v.findViewById(R.id.rvArticulos);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        myDataset = aslh.getAllArticulos();
        // sortArticulos(myDataset);
        mAdapter = new ArticuloAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

        // From AddEditArticuloFragment, reload Articulos
        if (mAlreadyLoaded)
            loadArticulos();

        main.selectNavigationDrawer(this);

        return v;
    }


    public void loadArticulos() {
        myDataset.clear();
        myDataset.addAll(aslh.getAllArticulos());
        // sortArticulos(myDataset);
        mAdapter.notifyDataSetChanged();
    }

    private void sortArticulos(ArrayList<Articulo> list) {
        Collections.sort(list, new Comparator<Articulo>() {
            @Override
            public int compare(Articulo articulo1, Articulo articulo2) {
                return articulo1.getDescripcion().compareTo(articulo2.getDescripcion());
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        main = (MainActivity) a;
        aslh = new ArticulosSQLiteHelper(a, null);
        mAlreadyLoaded = false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_articulos, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_clear_articulos) {
            aslh.clearArticulos();
            loadArticulos();
            return true;
        } else if (id == R.id.action_add_mock_data) {
            aslh.mockData(aslh.getWritableDatabase());
            loadArticulos();
            return true;
        } else if (id == R.id.action_refresh) {
            loadArticulos();
            Toast.makeText(main, "Actualizado", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
