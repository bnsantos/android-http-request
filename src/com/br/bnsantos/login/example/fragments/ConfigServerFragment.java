package com.br.bnsantos.login.example.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.br.bnsantos.login.example.LoginActivity;
import com.br.bnsantos.login.example.R;
import com.br.bnsantos.login.example.adapter.ServerArrayAdapter;
import com.br.bnsantos.login.example.dialog.AddServerDialog;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: bruno
 * Date: 1/7/14
 * Time: 9:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConfigServerFragment extends Fragment {

    private ListView configServer;
    private ArrayList<String> servers;
    private ServerArrayAdapter serverArrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_config_server, container, false);
        configServer = (ListView)view.findViewById(R.id.configServerListView);
        servers = new ArrayList<String>();
        serverArrayAdapter = new ServerArrayAdapter(servers, (LoginActivity)getActivity());
        configServer.setAdapter(serverArrayAdapter);

        view.findViewById(R.id.configServerAddServerBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddServerDialog addServerDialog = new AddServerDialog();
                addServerDialog.show(getActivity().getSupportFragmentManager(), "teste");
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        return view;

    }

    public void addServer(String server){
        servers.add(server);
        serverArrayAdapter.notifyDataSetChanged();
    }
}
