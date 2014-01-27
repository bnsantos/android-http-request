package com.br.bnsantos.login.example.listeners;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;
import com.br.bnsantos.login.example.R;
import com.br.bnsantos.login.example.RequestActivity;
import com.br.bnsantos.login.example.fragments.RequestFragment;
import com.br.bnsantos.login.example.tasks.ServerConnectivityTask;
import com.br.bnsantos.login.example.utils.InternetConnection;
import com.br.bnsantos.login.example.utils.Validator;
import com.google.inject.Inject;

/**
 * Created by bruno on 1/27/14.
 */
public class TestServerConnectivityListener implements View.OnClickListener {
    private Context context;
    private RequestActivity requestActivity;
    private RequestFragment requestFragment;

    @Inject
    public TestServerConnectivityListener(Activity requestActivity){
        this.requestActivity = (RequestActivity)requestActivity;
        this.context = this.requestActivity.getApplicationContext();
        this.requestFragment = this.requestActivity.getRequestFragment();
    }

    @Override
    public void onClick(View v) {
        testServerConnectivity();
    }

    private void testServerConnectivity(){
        if(InternetConnection.isConnectingToInternet(context)){
            String serverAddress = requestFragment.getServer();
            if(Validator.validateServerAddress(serverAddress)){
                ServerConnectivityTask task = new ServerConnectivityTask(requestFragment);
                task.execute(serverAddress);
            }else{
                Toast.makeText(context, context.getString(R.string.login_request_select_server), Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(context, context.getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }
}
