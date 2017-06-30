package com.fragibe.cajaestanco.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fragibe.cajaestanco.R;
import com.fragibe.cajaestanco.data.Articulo;
import com.fragibe.cajaestanco.data.ArticuloContract.ArticuloEntry;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddEditArticuloFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddEditArticuloFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditArticuloFragment extends Fragment {

    // TODO: Rename and change types of parameters
    private String codigo, descripcion, lote_min, um, precio1, precio2, categoria;

    private OnFragmentInteractionListener mListener;

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
            codigo = getArguments().getString(ArticuloEntry.CODIGO);
            descripcion = getArguments().getString(ArticuloEntry.DESCRIPCION);
            lote_min = getArguments().getString(ArticuloEntry.LOTE_MIN);
            um = getArguments().getString(ArticuloEntry.UM);
            precio1 = getArguments().getString(ArticuloEntry.PRECIO1);
            precio2 = getArguments().getString(ArticuloEntry.PRECIO2);
            categoria = getArguments().getString(ArticuloEntry.CATEGORIA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_edit_articulo, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
