package com.fragibe.cajaestanco.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fragibe.cajaestanco.R;
import com.fragibe.cajaestanco.activities.MainActivity;

public class CajaFragment extends Fragment {
    //private OnFragmentInteractionListener mListener;

    public CajaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MainActivity) getActivity()).selectNavigationDrawer(this);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_caja, container, false);
    }
}
