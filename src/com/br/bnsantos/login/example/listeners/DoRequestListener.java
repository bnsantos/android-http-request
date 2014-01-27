package com.br.bnsantos.login.example.listeners;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;
import com.br.bnsantos.login.example.R;
import com.br.bnsantos.login.example.RequestActivity;
import com.br.bnsantos.login.example.fragments.RequestFragment;
import com.br.bnsantos.login.example.http.communicator.StringResponseCommunicator;
import com.br.bnsantos.login.example.http.task.HttpTask;
import com.br.bnsantos.login.example.utils.InternetConnection;
import com.br.bnsantos.login.example.utils.JsonUtils;
import com.google.inject.Inject;

/**
 * Created by bruno on 1/27/14.
 */
public class DoRequestListener implements View.OnClickListener {
    private Context context;
    private RequestActivity requestActivity;
    private RequestFragment requestFragment;

    @Inject
    public DoRequestListener(Activity requestActivity){
        this.requestActivity = (RequestActivity)requestActivity;
        this.context = this.requestActivity.getApplicationContext();
        this.requestFragment = this.requestActivity.getRequestFragment();
    }

    @Override
    public void onClick(View v) {
        doRequest();
    }

    private void doRequest(){
        if(InternetConnection.isConnectingToInternet(context)){
            String pathToAction = requestFragment.getTargetPath();
            if(pathToAction!=null&&pathToAction.length()>0){
                requestFragment.showProgressSpinner(true);
                switch (requestFragment.getHttpMethod()){
                    case GET:
                        HttpTask taskGet = new HttpTask<Void, Void>(new StringResponseCommunicator(requestFragment),
                                null, pathToAction, requestFragment.getHttpMethod());
                        taskGet.execute();
                    case POST:
                        String json = requestFragment.getJsonBodyRequest();
                        if(JsonUtils.isJSONValid(json)){
                            HttpTask taskPost = new HttpTask<Void, Void>(new StringResponseCommunicator(requestFragment),
                                    null, pathToAction, requestFragment.getHttpMethod());
                            taskPost.execute(json);
                        }else{
                            Toast.makeText(context, context.getString(R.string.invalid_json), Toast.LENGTH_SHORT).show();
                        }
                }
            }else{
                Toast.makeText(context, context.getString(R.string.invalid_path), Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(context, context.getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }
}

