package com.br.bnsantos.login.example.tasks;

import android.os.AsyncTask;
import com.br.bnsantos.login.example.R;
import com.br.bnsantos.login.example.fragments.LoginFragment;

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
    private LoginFragment loginFragment;

    public ServerConnectivityTask(LoginFragment loginFragment){
        this.loginFragment = loginFragment;
    }

    public String doInBackground(String... urls){
        try{
            InetAddress address = InetAddress.getByName(urls[0]);
            if(address.isReachable(1000)){
                serverAvailable = true;
                return loginFragment.getString(R.string.server_available);
            }else{
                serverAvailable = false;
                return loginFragment.getString(R.string.server_not_available);
            }
        }catch (UnknownHostException e){
            return loginFragment.getString(R.string.server_not_available) + " " + e.getMessage();
        }catch (IOException e){
            return loginFragment.getString(R.string.server_not_available) + " " + e.getMessage();
        }
    }

    protected void onPostExecute(String errorMessage) {
        loginFragment.setServerConnectivity(serverAvailable, errorMessage);
    }
}
