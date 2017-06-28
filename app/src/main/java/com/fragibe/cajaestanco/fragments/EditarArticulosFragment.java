package com.fragibe.cajaestanco.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fragibe.cajaestanco.R;

public class EditarArticulosFragment extends Fragment {

    private OnEditarArticulosInteractionListener mListener;

    private CardView cvDownload, cvAdd, cvEdit;

    public EditarArticulosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_editar_articulos, container, false);
        cvDownload = v.findViewById(R.id.cvImportar);
        cvAdd = v.findViewById(R.id.cvAnyadir);
        cvEdit = v.findViewById(R.id.cvEditar);

        cvDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null)
                    mListener.onEditarArticulosButtonPressed(view.getId());
            }
        });

        return v;
    }

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        if (a instanceof OnEditarArticulosInteractionListener) {
            mListener = (OnEditarArticulosInteractionListener) a;

        } else {
            throw new RuntimeException(a.toString()
                    + " must implement OnEditarArticulosInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnEditarArticulosInteractionListener {
        void onEditarArticulosButtonPressed(int id);
    }
}
