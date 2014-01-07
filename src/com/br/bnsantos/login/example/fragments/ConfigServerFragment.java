package com.br.bnsantos.login.example.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.br.bnsantos.login.example.R;

/**
 * Created with IntelliJ IDEA.
 * User: bruno
 * Date: 1/7/14
 * Time: 9:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConfigServerFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_config_server, container, false);
    }
}
