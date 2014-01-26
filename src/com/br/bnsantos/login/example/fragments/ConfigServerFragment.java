package com.br.bnsantos.login.example.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.br.bnsantos.login.example.R;
import com.br.bnsantos.login.example.RequestActivity;
import com.br.bnsantos.login.example.adapter.ServerArrayAdapter;
import com.br.bnsantos.login.example.dialog.AddServerDialog;
import com.br.bnsantos.login.example.utils.Validator;
import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: bruno
 * Date: 1/7/14
 * Time: 9:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConfigServerFragment extends RoboFragment {
    private static final String SERVERS_BUNDLE = "SERVERS_LOGIN";
    private static final String ADD_SERVER_DIALOG = "ADD_SERVER_DIALOG";

    private @InjectView(R.id.configServerListView) ListView configServer;
    private @InjectView(R.id.configServerAddServerBtn) Button addServerBtn;
    private @InjectView(R.id.configServerClearServersBtn) Button clearServersBtn;

    private ArrayList<String> servers;
    private ServerArrayAdapter serverArrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_config_server, container, false);

        if (savedInstanceState != null) {
            servers = savedInstanceState.getStringArrayList(SERVERS_BUNDLE);
        }else if (servers==null){
            servers = new ArrayList<String>();
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        serverArrayAdapter = new ServerArrayAdapter(servers, (RequestActivity)getActivity());
        configServer.setAdapter(serverArrayAdapter);

        addServerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddServerDialog addServerDialog = new AddServerDialog();
                addServerDialog.show(getActivity().getSupportFragmentManager(), ADD_SERVER_DIALOG);
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        clearServersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearServers();
            }
        });
    }

    public void addServer(String server){
        if(Validator.validateServerAddress(server)){
            servers.add(server);
            serverArrayAdapter.notifyDataSetChanged();
        }else{
            Toast.makeText(getActivity().getApplicationContext(), "Not AbstractHttpTask valid server address", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearServers(){
        servers.clear();
        serverArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume(){
        super.onResume();
        serverArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putStringArrayList(SERVERS_BUNDLE, servers);
    }
}
