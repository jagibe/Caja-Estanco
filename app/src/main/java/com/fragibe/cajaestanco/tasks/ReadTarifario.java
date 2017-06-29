package com.fragibe.cajaestanco.tasks;

import android.os.AsyncTask;

/**
 * Created by Javier on 29/06/2017.
 */

public class ReadTarifario extends AsyncTask<Integer, Integer, Integer> {

    private AsyncTaskChanged<Integer> callback;

    public ReadTarifario(AsyncTaskChanged<Integer> callback) {
        this.callback = callback;
    }

    @Override
    protected Integer doInBackground(Integer... strings) {
        return callback.doInBackground();
    }

    @Override
    protected void onPostExecute(Integer result) {
        if (result != 0) {
            callback.onTaskFailed(result);
        } else {
            callback.onTaskComplete(result);
        }
    }
}
