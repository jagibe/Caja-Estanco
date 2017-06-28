package com.fragibe.cajaestanco.tasks;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Javier on 26/06/2017.
 */

public class DownloadFileAspx extends AsyncTask<String, String, String> {
    private AsyncTaskChanged<String> callback;

    public DownloadFileAspx(AsyncTaskChanged<String> callback) {
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... params) {
        String result = null;
        try {
            // Creates a new URL from the URI
            URL url = new URL(params[0]);

            // Get a connection to the web service
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Process the response if it was successful (HTTP_OK = 200)
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Open an input channel to receive the response from the web service
                InputStreamReader isr = new InputStreamReader(connection.getInputStream());
                BufferedReader in = new BufferedReader(isr);
                String inputLine;
                while ((inputLine = in.readLine()) != null)
                    System.out.println(inputLine);

                // Close the input channel
                isr.close();
            }

            // Close the connection
            connection.disconnect();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result == null) {
            callback.onTaskFailed(result);
        } else {
            callback.onTaskComplete(result);
        }

    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        callback.onTaskProgressUpdate(values[0]);
    }
}
