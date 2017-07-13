package com.fragibe.cajaestanco.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
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
    /*private String codigo, descripcion, lote_min, um, precio1, precio2, categoria;*/


    ArticulosSQLiteHelper aslh;

    /* Controles de la UI */
    private EditText etCodigo, etDescripcion, etLoteMin, etUm, etPrecio1, etPrecio2;
    private TextInputLayout tilCodigo, tilDescripcion, tilLoteMin, tilUm, tilPrecio1, tilPrecio2;
    private Spinner spnCategoria;

    private TextWatcher textListener;

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
        codigoIni = descripcionIni = lote_minIni = umIni = precio1Ini = precio2Ini = "";
        textListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };


        etCodigo = v.findViewById(R.id.etAddEditCodigo);
        etDescripcion = v.findViewById(R.id.etAddEditDescripcion);
        etLoteMin = v.findViewById(R.id.etAddEditLoteMin);
        etUm = v.findViewById(R.id.etAddEditUm);
        etPrecio1 = v.findViewById(R.id.etAddEditPrecio1);
        etPrecio2 = v.findViewById(R.id.etAddEditPrecio2);

        etCodigo.addTextChangedListener(textListener);
        etDescripcion.addTextChangedListener(textListener);
        etLoteMin.addTextChangedListener(textListener);
        etUm.addTextChangedListener(textListener);
        etPrecio1.addTextChangedListener(textListener);
        etPrecio2.addTextChangedListener(textListener);

        tilCodigo = v.findViewById(R.id.tilAddEditCodigo);
        tilDescripcion = v.findViewById(R.id.tilAddEditDescripcion);
        tilLoteMin = v.findViewById(R.id.tilAddEditLoteMin);
        tilUm = v.findViewById(R.id.tilAddEditUm);
        tilPrecio1 = v.findViewById(R.id.tilAddEditPrecio1);
        tilPrecio2 = v.findViewById(R.id.tilAddEditPrecio2);

        spnCategoria = v.findViewById(R.id.spnCategoria);

        ((MainActivity) getActivity()).selectNavigationDrawer(this);
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
    public void onPrepareOptionsMenu(Menu menu) {
        menu.getItem(1).setVisible(isChanged());

        super.onPrepareOptionsMenu(menu);
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


    private boolean isChanged() {
        if (!etCodigo.getText().toString().equals(codigoIni))
            return true;
        if (!etDescripcion.getText().toString().equals(descripcionIni))
            return true;
        if (!etLoteMin.getText().toString().equals(lote_minIni))
            return true;
        if (!etUm.getText().toString().equals(umIni))
            return true;
        if (!etPrecio1.getText().toString().equals(precio1Ini))
            return true;
        if (!etPrecio2.getText().toString().equals(precio2Ini))
            return true;

        // No hay cambios
        return false;


    }

    private boolean isValid() {
        boolean ret = true;

        if (etCodigo.getText().toString().trim().equals("")) {
            tilCodigo.setError("Debe rellenar este campo");
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
            tilDescripcion.setError("Debe rellenar este campo");
            ret = false;
        }

        if (etLoteMin.getText().toString().trim().equals("")) {
            tilLoteMin.setError("Debe rellenar este campo");
            ret = false;
        } else {
            try {
                Integer.parseInt(etLoteMin.getText().toString());
            } catch (NumberFormatException e) {
                tilLoteMin.setError("Formato incorrecto");
                ret = false;
            }
        }

        if (etUm.getText().toString().trim().equals("")) {
            tilUm.setError("Debe rellenar este campo");
            ret = false;
        }

        if (etPrecio1.getText().toString().trim().equals("")) {
            tilPrecio1.setError("Debe rellenar este campo");
            ret = false;
        } else {
            try {
                Double.parseDouble(etPrecio1.getText().toString());
            } catch (NumberFormatException e) {
                tilPrecio1.setError("Formato incorrecto");
                ret = false;
            }
        }

        if (etPrecio2.getText().toString().trim().equals("")) {
            tilPrecio2.setError("Debe rellenar este campo");
            ret = false;
        } else {
            try {
                Double.parseDouble(etPrecio2.getText().toString());
            } catch (NumberFormatException e) {
                tilPrecio2.setError("Formato incorrecto");
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

