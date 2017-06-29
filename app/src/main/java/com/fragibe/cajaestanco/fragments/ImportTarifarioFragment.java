package com.fragibe.cajaestanco.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fragibe.cajaestanco.R;
import com.fragibe.cajaestanco.adapters.ArticuloLogistaAdapter;
import com.fragibe.cajaestanco.data.Articulo;
import com.fragibe.cajaestanco.tasks.AsyncTaskChanged;
import com.fragibe.cajaestanco.tasks.DownloadFileFromURL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ImportTarifarioFragment extends Fragment {

    //private OnFragmentInteractionListener mListener;

    public ImportTarifarioFragment() {
        // Required empty public constructor
    }

    private Context context;
    private View view;

    private LinearLayout downloadLayout, downloadingLayout;
    private ProgressBar pbDownloadProgress;
    private TextView tvDownloadProgress, tvDescargando;
    private CardView cvDownload;
    private ImageView ivDownload;

    private EditText etFilterDescripcion;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    private ArrayList<Articulo> myDataset;
    private DownloadFileFromURL task;
    private String path;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myDataset = new ArrayList<>();

        task = new DownloadFileFromURL(new AsyncTaskChanged<String>() {
            @Override
            public void onTaskProgressUpdate(String progress) {
                pbDownloadProgress.setProgress(Integer.parseInt(progress));
                tvDownloadProgress.setText(progress + " %");
            }

            @Override
            public void onTaskComplete(String result) {
                ivDownload.setImageResource(R.drawable.ic_cloud_done);
                tvDescargando.setText("Leyendo tarifario...");
                pbDownloadProgress.setIndeterminate(true);
                tvDownloadProgress.setVisibility(View.INVISIBLE);
                try {
                    File file = new File(path);
                    FileInputStream fin = new FileInputStream(file);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(fin, "UTF8"));

                    String line;
                    line = reader.readLine(); // Descartar primera linea
                    while ((line = reader.readLine()) != null) {
                        line = line.replaceAll("[^A-Za-z0-9 ;',]", "");
                        if (!line.equals("")) {
                            //System.out.println(line);
                            String[] rowData = line.split(";");
                            int cod = Integer.parseInt(rowData[0]);
                            String descr = rowData[1];
                            int lote_min = Integer.parseInt(rowData[2]);
                            String um = rowData[3];
                            Double precio1, precio2;
                            try {
                                precio1 = Double.parseDouble(rowData[4]);
                            } catch (Exception e) {
                                precio1 = -1.0;
                            }
                            try {
                                precio2 = Double.parseDouble(rowData[5]);
                            } catch (Exception e) {
                                precio2 = -1.0;
                            }
                            myDataset.add(new Articulo(cod,
                                    descr,
                                    lote_min,
                                    um,
                                    precio1,
                                    precio2));
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                    reader.close();
                    cvDownload.setVisibility(View.GONE);
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
        tvDescargando = view.findViewById(R.id.tvDescargando);
        ivDownload = view.findViewById(R.id.ivDownload);
        cvDownload = view.findViewById(R.id.cvDownload);
        cvDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadLayout.setVisibility(View.GONE);
                downloadingLayout.setVisibility(View.VISIBLE);
                task.execute("http://www.logista.es/es/Paginas/CatalogoCompleto.aspx", path);
            }
        });

        etFilterDescripcion = view.findViewById(R.id.etFilterDescripcion);
        etFilterDescripcion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ((ArticuloLogistaAdapter) mAdapter).getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mRecyclerView = view.findViewById(R.id.rvArticulosLogista);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // myDataset.add(new Articulo(8956, "Marlboro Gold", 10, "Cajetilla", 4.95, 5.1));
        // specify an adapter (see also next example)
        mAdapter = new ArticuloLogistaAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onAttach(Activity a) {
        context = a;
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
