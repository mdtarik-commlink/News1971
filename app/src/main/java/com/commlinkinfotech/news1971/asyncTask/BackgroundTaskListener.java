package com.commlinkinfotech.news1971.asyncTask;

/**
 * Created by tarik on 4/14/16.
 */
public interface BackgroundTaskListener {

    public void onPreExecuteAsynctask(int identifier);
    public void onPostExecuteAsynctask(int identifier, Object data);
}
