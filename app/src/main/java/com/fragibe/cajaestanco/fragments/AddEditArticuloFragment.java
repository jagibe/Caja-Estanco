package com.fragibe.cajaestanco.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.fragibe.cajaestanco.R;
import com.fragibe.cajaestanco.activities.MainActivity;
import com.fragibe.cajaestanco.data.Articulo;
import com.fragibe.cajaestanco.data.ArticuloContract.ArticuloEntry;
import com.fragibe.cajaestanco.data.ArticulosSQLiteHelper;


public class AddEditArticuloFragment extends Fragment {

    private String codigoIni, descripcionIni, lote_minIni, umIni, precio1Ini, precio2Ini, categoriaIni;
    private String codigo, descripcion, lote_min, um, precio1, precio2, categoria;


    ArticulosSQLiteHelper aslh;

    /* Controles de la UI */
    private EditText etCodigo, etDescripcion, etLoteMin, etUm, etPrecio1, etPrecio2;
    private Spinner spnCategoria;

    public AddEditArticuloFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param articulo Objeto de la clase Articulo, si se especifica es que se quiere editar el articulo.
     *                 Si no se especifica se quiere crear un nuevo artículo.
     * @return A new instance of fragment AddEditArticuloFragment.
     */
    public static AddEditArticuloFragment newInstance(Articulo articulo) {
        AddEditArticuloFragment fragment = new AddEditArticuloFragment();

        if (articulo != null) {
            Bundle args = new Bundle();
            args.putString(ArticuloEntry.CODIGO, Integer.toString(articulo.getCodigo()));
            args.putString(ArticuloEntry.DESCRIPCION, articulo.getDescripcion());
            args.putString(ArticuloEntry.LOTE_MIN, Integer.toString(articulo.getLote_min()));
            args.putString(ArticuloEntry.UM, articulo.getUm());
            args.putString(ArticuloEntry.PRECIO1, Double.toString(articulo.getPrecio1()));
            args.putString(ArticuloEntry.PRECIO2, Double.toString(articulo.getPrecio2()));
            args.putString(ArticuloEntry.CATEGORIA, articulo.getCategoria());
            fragment.setArguments(args);
        }

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            codigoIni = getArguments().getString(ArticuloEntry.CODIGO);
            descripcionIni = getArguments().getString(ArticuloEntry.DESCRIPCION);
            lote_minIni = getArguments().getString(ArticuloEntry.LOTE_MIN);
            umIni = getArguments().getString(ArticuloEntry.UM);
            precio1Ini = getArguments().getString(ArticuloEntry.PRECIO1);
            precio2Ini = getArguments().getString(ArticuloEntry.PRECIO2);
            categoriaIni = getArguments().getString(ArticuloEntry.CATEGORIA);
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_edit_articulo, container, false);
        etCodigo = v.findViewById(R.id.etAddEditCodigo);
        etDescripcion = v.findViewById(R.id.etAddEditDescripcion);
        etLoteMin = v.findViewById(R.id.etAddEditLoteMin);
        etUm = v.findViewById(R.id.etAddEditUm);
        etPrecio1 = v.findViewById(R.id.etAddEditPrecio1);
        etPrecio2 = v.findViewById(R.id.etAddEditPrecio2);
        spnCategoria = v.findViewById(R.id.spnCategoria);

        return v;
    }

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        aslh = new ArticulosSQLiteHelper(getActivity(), null);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_edit_articulo, menu);  // Use filter.xml from step 1
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            ((MainActivity) getActivity()).closeKeyboard();
            saveArticulo();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isValid() {
        boolean ret = true;

        if (etCodigo.getText().toString().trim().equals("")) {
            etCodigo.setError("Debe rellenar este campo");
            ret = false;
        } else {
            try {
                Integer.parseInt(etCodigo.getText().toString());
            } catch (NumberFormatException e) {
                etCodigo.setError("Formato incorrecto");
                ret = false;
            }
        }

        if (etDescripcion.getText().toString().trim().equals("")) {
            etDescripcion.setError("Debe rellenar este campo");
            ret = false;
        }

        if (etLoteMin.getText().toString().trim().equals("")) {
            etLoteMin.setError("Debe rellenar este campo");
            ret = false;
        } else {
            try {
                Integer.parseInt(etLoteMin.getText().toString());
            } catch (NumberFormatException e) {
                etLoteMin.setError("Formato incorrecto");
                ret = false;
            }
        }

        if (etUm.getText().toString().trim().equals("")) {
            etUm.setError("Debe rellenar este campo");
            ret = false;
        }

        if (etPrecio1.getText().toString().trim().equals("")) {
            etPrecio1.setError("Debe rellenar este campo");
            ret = false;
        } else {
            try {
                Double.parseDouble(etPrecio1.getText().toString());
            } catch (NumberFormatException e) {
                etPrecio1.setError("Formato incorrecto");
                ret = false;
            }
        }

        if (etPrecio2.getText().toString().trim().equals("")) {
            etPrecio2.setError("Debe rellenar este campo");
            ret = false;
        } else {
            try {
                Double.parseDouble(etPrecio2.getText().toString());
            } catch (NumberFormatException e) {
                etPrecio2.setError("Formato incorrecto");
                ret = false;
            }
        }


        return ret;
    }

    private void saveArticulo() {
        if (isValid())
            if (aslh.saveArticulo(new Articulo(Integer.parseInt(etCodigo.getText().toString()),
                    etDescripcion.getText().toString(),
                    Integer.parseInt(etLoteMin.getText().toString()),
                    etUm.getText().toString(),
                    Double.parseDouble(etPrecio1.getText().toString()),
                    Double.parseDouble(etPrecio2.getText().toString()), ""))) {
                getFragmentManager().popBackStack();
            }
    }

}

