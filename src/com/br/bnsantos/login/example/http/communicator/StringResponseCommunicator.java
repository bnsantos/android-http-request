package com.br.bnsantos.login.example.http.communicator;

import android.util.Log;
import android.widget.Toast;
import com.br.bnsantos.login.example.fragments.RequestFragment;
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

    private RequestFragment requestFragment;

    public StringResponseCommunicator(RequestFragment fragment) {
        super();
        this.requestFragment = fragment;
    }

    @Override
    public void onPostExecute(BasicResponse item) {
        requestFragment.showProgressSpinner(false);
        if (item != null) {
            requestFragment.setHttpResponse(item.getStatus(), item.getResponse());
        } else {
            Log.e(TAG, "Response is null");
            Toast.makeText(requestFragment.getActivity().getApplicationContext(), item.getStatus() + " - " + item.getResponse(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCancelled() {
        Log.e(TAG, "Task cancelled");
    }

}
