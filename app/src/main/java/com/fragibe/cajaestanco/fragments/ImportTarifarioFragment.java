package com.fragibe.cajaestanco.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ImportTarifarioFragment extends Fragment {

    //private OnFragmentInteractionListener mListener;

    public ImportTarifarioFragment() {
        // Required empty public constructor
    }

    private View view;

    private LinearLayout downloadLayout, downloadingLayout;
    private ProgressBar pbDownloadProgress;
    private TextView tvDownloadProgress;
    private CardView cvDownload;

    private DownloadFileFromURL task;
    String path;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        task = new DownloadFileFromURL(new AsyncTaskChanged<String>() {
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
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    reader.close();
                    //tvFile.setText(sb.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "¡No se encontró el archivo descargado!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "IOException!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onTaskFailed(String result) {
                Toast.makeText(getActivity(), "La descarga falló!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_import_tarifario, container, false);

        downloadLayout = view.findViewById(R.id.layoutDownload);
        downloadingLayout = view.findViewById(R.id.layoutDownloading);
        pbDownloadProgress = view.findViewById(R.id.pbTarifarioProgress);
        tvDownloadProgress = view.findViewById(R.id.tvTarifarioProgress);

        cvDownload = view.findViewById(R.id.cvDownload);
        cvDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadLayout.setVisibility(View.GONE);
                downloadingLayout.setVisibility(View.VISIBLE);
                task.execute("http://www.logista.es/es/Paginas/CatalogoCompleto.aspx", path);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        path = a.getCacheDir().getAbsolutePath() + File.separator + "CatalogoLogista.csv";
        /*if (a instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) a;
        } else {
            throw new RuntimeException(a.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

}
