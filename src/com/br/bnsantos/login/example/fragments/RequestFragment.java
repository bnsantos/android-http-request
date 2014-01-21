package com.br.bnsantos.login.example.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.br.bnsantos.login.example.R;
import com.br.bnsantos.login.example.RequestActivity;
import com.br.bnsantos.login.example.components.ProgressSpinner;
import com.br.bnsantos.login.example.dialog.PortPickerDialog;
import com.br.bnsantos.login.example.http.communicator.StringResponseCommunicator;
import com.br.bnsantos.login.example.http.rest.HttpMethodType;
import com.br.bnsantos.login.example.http.task.HttpTask;
import com.br.bnsantos.login.example.tasks.ServerConnectivityTask;
import com.br.bnsantos.login.example.utils.JsonUtils;
import com.br.bnsantos.login.example.utils.Validator;

/**
 * Created with IntelliJ IDEA.
 * User: bruno
 * Date: 1/7/14
 * Time: 10:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class RequestFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    private static final String PICK_PORT_DIALOG = "PICK_PORT_DIALOG";

    private EditText editTextServer;
    private String selectedServer;
    private Button connectivityBtn;

    private Button portBtn;

    private EditText editTextPort;
    private int serverPort = -1;
    private HttpMethodType httpMethod = HttpMethodType.GET;
    private EditText editTextTargetPath;

    private EditText editTextPath;
    private RequestFragment(){}

    private static RequestFragment instance;

    private  String jsonBodyRequest;
    private EditText jsonBodyRequestEditText;

    private ProgressSpinner progressSpinner;

    public static RequestFragment getInstance(){
        if(instance ==null){
            instance = new RequestFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request, container, false);
        editTextServer = (EditText)view.findViewById(R.id.fragmentRequestServerEditText);

        view.findViewById(R.id.fragmentRequestEditServerBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RequestActivity)getActivity()).showConfigServerFragment();
            }
        });

        view.findViewById(R.id.fragmentRequestEditRequestBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RequestActivity)getActivity()).showConfigRequestFragment();
            }
        });

        portBtn = (Button)view.findViewById(R.id.fragmentRequestServerPortBtn);
        portBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPort();
            }
        });
        editTextPort = (EditText)view.findViewById(R.id.fragmentRequestServerPortEditText);
        editTextTargetPath = (EditText)view.findViewById(R.id.fragmentRequestTargetURI);
        editTextPath = (EditText)view.findViewById(R.id.fragmentRequestPathEditText);

        jsonBodyRequestEditText = (EditText)view.findViewById(R.id.fragmentRequestEditText);

        connectivityBtn = (Button)view.findViewById(R.id.fragmentRequestTestConnectivityBtn);
        connectivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testConnectivity();
            }
        });

        editTextPath.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                buildTargetURI();
            }
        });

        Spinner requestMethodSpinner = (Spinner) view.findViewById(R.id.fragmentLoginRequestMethodSpinner);
        requestMethodSpinner.setOnItemSelectedListener(this);

        view.findViewById(R.id.fragmentRequestDoRequestBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRequest();
            }
        });

        progressSpinner = new ProgressSpinner((LinearLayout) view.findViewById(R.id.fragmentRequestProgressBar),
                (LinearLayout) view.findViewById(R.id.fragmentRequestResponseLayout), getResources().getInteger(android.R.integer.config_shortAnimTime));
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();

        editTextServer.setText(selectedServer);
        if(serverPort!=-1){
            editTextPort.setText(Integer.toString(serverPort));
        }

        updateBody();
        jsonBodyRequestEditText.setText(jsonBodyRequest);


        buildTargetURI();
    }

    public void setSelectedServer(String server){
        selectedServer=server;
        editTextServer.setText(server);
        buildTargetURI();
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
        portPickerDialog.show(getActivity().getSupportFragmentManager(), PICK_PORT_DIALOG);
    }

    public void addPort(int port){
        serverPort = port;
        editTextPort.setText(Integer.toString(port));
        portBtn.setBackgroundResource(R.drawable.checked);
        buildTargetURI();
    }

    private void doRequest(){
        String pathToAction = editTextTargetPath.getText().toString();
        if(pathToAction!=null&&pathToAction.length()>0){
            showProgressSpinner(true);
            switch (this.httpMethod){
                case GET:
                    HttpTask taskGet = new HttpTask<Void, Void>(new StringResponseCommunicator((RequestActivity)getActivity()),
                            null, pathToAction, this.httpMethod);
                    taskGet.execute();
                case POST:
                    String json = jsonBodyRequestEditText.getText().toString();
                    if(JsonUtils.isJSONValid(json)){
                        HttpTask taskPost = new HttpTask<Void, Void>(new StringResponseCommunicator((RequestActivity)getActivity()),
                                null, pathToAction, this.httpMethod);
                        taskPost.execute(json);
                    }else{
                        Toast.makeText(getActivity().getApplicationContext(), getString(R.string.invalid_json), Toast.LENGTH_SHORT).show();
                    }
            }
        }else{
            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.invalid_path), Toast.LENGTH_SHORT).show();
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
        this.httpMethod = HttpMethodType.valueOf(parent.getItemAtPosition(pos).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    public void buildTargetURI(){
        StringBuffer targetURI = new StringBuffer();
        if(selectedServer!=null){
            targetURI.append(getString(R.string.server_prefix));
            targetURI.append(selectedServer);
            if(serverPort!=-1){
                targetURI.append(":");
                targetURI.append(serverPort);
                targetURI.append("/");
            }
            String path = editTextPath.getText().toString();
            if(path!=null){
                targetURI.append(path);
            }
            editTextTargetPath.setText(targetURI.toString());
        }
    }

    public void updateBody(){
        jsonBodyRequest = JsonUtils.formatJsonRequest(((RequestActivity) getActivity()).getRequestBody());
        jsonBodyRequestEditText.setText(jsonBodyRequest);
    }

    public void showProgressSpinner(boolean show) {
        progressSpinner.show(show);
    }
}
