package com.br.bnsantos.login.example.http.communicator;

import android.util.Log;
import android.widget.Toast;
import com.br.bnsantos.login.example.RequestActivity;
import com.br.bnsantos.login.example.http.response.BasicResponse;
import com.br.bnsantos.login.example.http.task.ITaskCommunicator;

/**
 * Created with IntelliJ IDEA.
 * User: bruno
 * Date: 1/9/14
 * Time: 11:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class StringResponseCommunicator implements ITaskCommunicator<BasicResponse>{
    private static String TAG = StringResponseCommunicator.class.getName();

    private RequestActivity currentActivity;

    public StringResponseCommunicator(RequestActivity activity) {
        super();
        this.currentActivity = activity;
    }

    @Override
    public void onPostExecute(BasicResponse item) {
        if (item != null) {
            Toast.makeText(currentActivity.getApplicationContext(), item.getStatus() + " - " + item.getResponse(), Toast.LENGTH_LONG).show();
        } else {
            Log.e(TAG, "Response is null");
            Toast.makeText(currentActivity.getApplicationContext(), "Response null", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCancelled() {
        Log.e(TAG, "Task cancelled");
    }

}
