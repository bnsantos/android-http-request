package com.br.bnsantos.login.example.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import com.br.bnsantos.login.example.R;
import com.br.bnsantos.login.example.RequestActivity;
import com.br.bnsantos.login.example.adapter.FieldArrayAdapter;
import com.br.bnsantos.login.example.dialog.AddFieldDialog;
import com.br.bnsantos.login.example.entities.JsonField;
import com.google.inject.Singleton;
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
@Singleton
public class ConfigRequestFragment extends RoboFragment {
    private static final String REQUEST_BUNDLE = "REQUEST_LOGIN";
    private static final String ADD_FIELD_DIALOG = "ADD_FIELD_DIALOG";

    private @InjectView(R.id.configRequestListView)         ListView configRequest;
    private @InjectView(R.id.configRequestAddFieldBtn)      Button addFieldBtn;
    private @InjectView(R.id.configRequestClearFieldsBtn)   Button clearFieldsBtn;

    private ArrayList<JsonField> fields;
    private FieldArrayAdapter fieldsArrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_config_request, container, false);
        if (savedInstanceState != null) {
            fields = savedInstanceState.getParcelableArrayList(REQUEST_BUNDLE);
        }else if (fields==null){
            fields = new ArrayList<JsonField>();
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        fieldsArrayAdapter = new FieldArrayAdapter(fields, (RequestActivity)getActivity());
        configRequest.setAdapter(fieldsArrayAdapter);

        addFieldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFieldDialog addFieldDialog = new AddFieldDialog();
                addFieldDialog.show(getActivity().getSupportFragmentManager(), ADD_FIELD_DIALOG);
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        clearFieldsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });
    }

    private void clearFields(){
        fields.clear();
        fieldsArrayAdapter.notifyDataSetChanged();
    }

    public void addField(String field, String value){
        fields.add(new JsonField(field, value));
        fieldsArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putParcelableArrayList(REQUEST_BUNDLE, fields);
    }

    public ArrayList<JsonField> getFields() {
        return fields;
    }
}
