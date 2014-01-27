package com.br.bnsantos.login.example.fragments;

import android.os.Bundle;
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
import com.br.bnsantos.login.example.http.rest.HttpMethodType;
import com.br.bnsantos.login.example.listeners.DoRequestListener;
import com.br.bnsantos.login.example.listeners.TestServerConnectivityListener;
import com.br.bnsantos.login.example.utils.JsonUtils;
import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: bruno
 * Date: 1/7/14
 * Time: 10:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class RequestFragment extends RoboFragment implements AdapterView.OnItemSelectedListener{
    private static final String PICK_PORT_DIALOG = "PICK_PORT_DIALOG";

    private @InjectView(R.id.fragmentRequestServerEditText)         EditText editTextServer;
    private @InjectView(R.id.fragmentRequestTestConnectivityBtn)    Button connectivityBtn;
    private @InjectView(R.id.fragmentRequestServerPortEditText)     EditText editTextPort;
    private @InjectView(R.id.fragmentRequestServerPortBtn)          Button portBtn;
    private @InjectView(R.id.fragmentRequestTargetURI)              EditText editTextTargetPath;
    private @InjectView(R.id.fragmentRequestEditText)               EditText jsonBodyRequestEditText;
    private @InjectView(R.id.fragmentRequestPathEditText)           EditText editTextPath;
    private @InjectView(R.id.fragmentRequestResponseStatusEditText) EditText responseStatus;
    private @InjectView(R.id.fragmentRequestResponseTextView)       EditText responseTextView;
    private @InjectView(R.id.fragmentLoginRequestMethodSpinner)     Spinner requestMethodSpinner;
    private @InjectView(R.id.fragmentRequestEditServerBtn)          Button editServerBtn;
    private @InjectView(R.id.fragmentRequestEditRequestBtn)         Button editRequestBtn;
    private @InjectView(R.id.fragmentRequestDoRequestBtn)           Button doRequestBtn;

    private  String jsonBodyRequest;
    private String selectedServer;
    private int serverPort = -1;
    private HttpMethodType httpMethod = HttpMethodType.GET;
    private ProgressSpinner progressSpinner;

    @Inject
    private DoRequestListener doRequestListener;

    @Inject
    private TestServerConnectivityListener testServerConnectivityListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request, container, false);

        progressSpinner = new ProgressSpinner((LinearLayout) view.findViewById(R.id.fragmentRequestProgressBar),
                (LinearLayout) view.findViewById(R.id.fragmentRequestResponseLayout), getResources().getInteger(android.R.integer.config_shortAnimTime));

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        portBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPort();
            }
        });

        connectivityBtn.setOnClickListener(testServerConnectivityListener);

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

        requestMethodSpinner.setOnItemSelectedListener(this);

        editServerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RequestActivity) getActivity()).showConfigServerFragment();
            }
        });

        editRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RequestActivity) getActivity()).showConfigRequestFragment();
            }
        });

        doRequestBtn.setOnClickListener(doRequestListener);
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

    public void setHttpResponse(int status, String response){
        responseStatus.setText(Integer.toString(status));
        responseTextView.setText(response);
    }

    public HttpMethodType getHttpMethod() {
        return httpMethod;
    }

    public String getTargetPath(){
        return editTextTargetPath.getText().toString();
    }

    public String getJsonBodyRequest(){
        return jsonBodyRequestEditText.getText().toString();
    }

    public String getServer(){
        return editTextServer.getText().toString();
    }
}
