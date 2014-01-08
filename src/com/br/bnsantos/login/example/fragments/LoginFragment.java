package com.br.bnsantos.login.example.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.br.bnsantos.login.example.LoginActivity;
import com.br.bnsantos.login.example.R;
import com.br.bnsantos.login.example.dialog.PortPickerDialog;
import com.br.bnsantos.login.example.tasks.ServerConnectivityTask;
import com.br.bnsantos.login.example.utils.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: bruno
 * Date: 1/7/14
 * Time: 10:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class LoginFragment extends Fragment {
    private EditText editTextServer;
    private String selectedServer;

    private Button connectivityBtn;

    private Button portBtn;
    private EditText editTextPort;
    private int serverPort = -1;

    private LoginFragment(){}

    private static LoginFragment instance;

    public static LoginFragment getInstance(){
        if(instance ==null){
            instance = new LoginFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        editTextServer = (EditText)view.findViewById(R.id.fragmentLoginServerEditText);

        view.findViewById(R.id.fragmentLoginEditServerBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity)getActivity()).showConfigServerFragment();
            }
        });

        view.findViewById(R.id.fragmentLoginEditRequestBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginActivity)getActivity()).showConfigRequestFragment();
            }
        });

        portBtn = (Button)view.findViewById(R.id.fragmentLoginServerPortBtn);
        portBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPort();
            }
        });
        editTextPort = (EditText)view.findViewById(R.id.fragmentLoginServerPortEditText);

        connectivityBtn = (Button)view.findViewById(R.id.fragmentLoginTestConnectivityBtn);
        connectivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testConnectivity();
            }
        });
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();

        editTextServer.setText(selectedServer);
        if(serverPort!=-1){
            editTextPort.setText(Integer.toString(serverPort));
        }
    }

    public void setSelectedServer(String server){
        selectedServer=server;
        editTextServer.setText(server);
    }

    private void testConnectivity(){
        String serverAddress = editTextServer.getText().toString();
        if(Validator.validateServerAddress(serverAddress)){
            ServerConnectivityTask task = new ServerConnectivityTask(this);
            task.execute(serverAddress);
        }else{
            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.login_request_select_server), Toast.LENGTH_SHORT).show();
        }
    }

    public void setServerConnectivity(boolean available, String msg){
        if(available){
            connectivityBtn.setBackgroundResource(R.drawable.connectivity_green);
        }else{
            connectivityBtn.setBackgroundResource(R.drawable.connectivity_black);
        }
        Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void selectPort(){
        PortPickerDialog portPickerDialog = new PortPickerDialog();
        portPickerDialog.show(getActivity().getSupportFragmentManager(), "TESTE");
    }

    public void addPort(int port){
        serverPort = port;
        editTextPort.setText(Integer.toString(port));
        portBtn.setBackgroundResource(R.drawable.checked);
    }
}
