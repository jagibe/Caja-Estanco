package com.fragibe.cajaestanco.tasks;

/**
 * Created by Javier on 26/06/2017.
 */

public interface AsyncTaskChanged<T> {
    public void onTaskProgressUpdate(T progress);

    public void onTaskComplete(T result);

    public void onTaskFailed(T result);
}
