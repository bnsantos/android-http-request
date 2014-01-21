package com.br.bnsantos.login.example.tasks;

import android.os.AsyncTask;
import com.br.bnsantos.login.example.R;
import com.br.bnsantos.login.example.fragments.RequestFragment;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: bruno
 * Date: 1/8/14
 * Time: 10:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class ServerConnectivityTask extends AsyncTask<String, Void, String> {

    private boolean serverAvailable = false;
    private RequestFragment requestFragment;

    public ServerConnectivityTask(RequestFragment requestFragment){
        this.requestFragment = requestFragment;
    }

    public String doInBackground(String... urls){
        try{
            InetAddress address = InetAddress.getByName(urls[0]);
            if(address.isReachable(1000)){
                serverAvailable = true;
                return requestFragment.getString(R.string.server_available);
            }else{
                serverAvailable = false;
                return requestFragment.getString(R.string.server_not_available);
            }
        }catch (UnknownHostException e){
            return requestFragment.getString(R.string.server_not_available) + " " + e.getMessage();
        }catch (IOException e){
            return requestFragment.getString(R.string.server_not_available) + " " + e.getMessage();
        }
    }

    protected void onPostExecute(String errorMessage) {
        requestFragment.setServerConnectivity(serverAvailable, errorMessage);
    }
}
