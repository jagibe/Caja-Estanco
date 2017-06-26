package com.fragibe.cajaestanco.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fragibe.cajaestanco.R;
import com.fragibe.cajaestanco.tasks.AsyncTaskChanged;
import com.fragibe.cajaestanco.tasks.DownloadFileFromURL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ImportTarifarioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ImportTarifarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImportTarifarioFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ImportTarifarioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ImportTarifarioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ImportTarifarioFragment newInstance(String param1, String param2) {
        ImportTarifarioFragment fragment = new ImportTarifarioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private View view;

    private ConstraintLayout downloadLayout;
    private ProgressBar pbDownloadProgress;
    private TextView tvDownloadProgress;

    private LinearLayout resultLayout;
    private EditText etFile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        final String path = getActivity().getFilesDir().getAbsolutePath() + File.separator + "tarifario.csv";
        DownloadFileFromURL task = new DownloadFileFromURL(new AsyncTaskChanged<String>() {
            @Override
            public void onTaskProgressUpdate(String progress) {
                pbDownloadProgress.setProgress(Integer.parseInt(progress));
                tvDownloadProgress.setText(progress + " %");
            }

            @Override
            public void onTaskComplete(String result) {
                Toast.makeText(getActivity(), "Descargado!", Toast.LENGTH_SHORT).show();
                try {
                    File fl = new File(path);
                    FileInputStream fin = new FileInputStream(fl);

                    BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    reader.close();
                    etFile.setText(sb.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "File not found!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "IOException!", Toast.LENGTH_SHORT).show();
                }

                downloadLayout.setVisibility(View.GONE);
                resultLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTaskFailed(String result) {
                Toast.makeText(getActivity(), "La descarga falló!", Toast.LENGTH_SHORT).show();
            }
        });


        //www.logista.es/es/Paginas/CatalogoCompleto.aspx
        task.execute("www.logista.es/es/Paginas/CatalogoCompleto.aspx", path);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_import_tarifario, container, false);

        downloadLayout = view.findViewById(R.id.downloadLayout);
        pbDownloadProgress = view.findViewById(R.id.pbTarifarioProgress);
        tvDownloadProgress = view.findViewById(R.id.tvTarifarioProgress);

        resultLayout = view.findViewById(R.id.resultLayout);
        etFile = view.findViewById(R.id.etFile);

        return view;
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
