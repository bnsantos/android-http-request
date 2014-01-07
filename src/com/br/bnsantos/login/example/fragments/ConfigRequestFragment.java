package com.br.bnsantos.login.example.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.br.bnsantos.login.example.LoginActivity;
import com.br.bnsantos.login.example.R;
import com.br.bnsantos.login.example.adapter.FieldArrayAdapter;
import com.br.bnsantos.login.example.dialog.AddFieldDialog;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: bruno
 * Date: 1/7/14
 * Time: 9:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConfigRequestFragment extends Fragment {
    private ListView configRequest;
    private ArrayList<String> fields;
    private FieldArrayAdapter fieldsArrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_config_request, container, false);
        configRequest = (ListView)view.findViewById(R.id.configRequestListView);
        fields = new ArrayList<String>();
        fieldsArrayAdapter = new FieldArrayAdapter(fields, (LoginActivity)getActivity());
        configRequest.setAdapter(fieldsArrayAdapter);

        view.findViewById(R.id.configRequestAddFieldBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFieldDialog addFieldDialog = new AddFieldDialog();
                addFieldDialog.show(getActivity().getSupportFragmentManager(), "teste");
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        view.findViewById(R.id.configRequestClearFieldsBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });

        return view;
    }

    private void clearFields(){
        fields.clear();
        fieldsArrayAdapter.notifyDataSetChanged();
    }

    public void addField(String field){
        fields.add(field);
        fieldsArrayAdapter.notifyDataSetChanged();
    }
}
