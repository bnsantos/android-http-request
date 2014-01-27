package com.br.bnsantos.login.example;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import com.br.bnsantos.login.example.dialog.AddFieldDialog;
import com.br.bnsantos.login.example.dialog.AddServerDialog;
import com.br.bnsantos.login.example.dialog.PortPickerDialog;
import com.br.bnsantos.login.example.entities.JsonField;
import com.br.bnsantos.login.example.fragments.ConfigRequestFragment;
import com.br.bnsantos.login.example.fragments.ConfigServerFragment;
import com.br.bnsantos.login.example.fragments.RequestFragment;
import com.google.inject.Inject;
import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

import java.util.ArrayList;

@ContentView(R.layout.activity_request)
public class RequestActivity extends RoboFragmentActivity implements AddServerDialog.AddServerDialogListener, AddFieldDialog.AddFieldDialogListener,
        PortPickerDialog.PortPickerDialogListener{

    // action bar
    private ActionBar actionBar;

    private @InjectView(R.id.loginLayout) FrameLayout frameLayout;

    private @Inject     RequestFragment requestFragment;
    private @Inject     ConfigServerFragment configServerFragment;
    private @Inject     ConfigRequestFragment configRequestFragment;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBar = getActionBar();
        // Hide the action bar title
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(getString(R.string.action_bar_title));
        actionBar.setIcon(R.drawable.red_balloons_logo);
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Enabling Spinner dropdown navigation
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        initFragments();
    }


    private void initFragments(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.loginLayout, requestFragment);
        fragmentTransaction.add(R.id.loginLayout, configRequestFragment);
        fragmentTransaction.add(R.id.loginLayout, configServerFragment);
        fragmentTransaction.hide(configRequestFragment);
        fragmentTransaction.hide(configServerFragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_login_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                showLoginFragment();
                return true;
            case R.id.action_config_request:
                showConfigRequestFragment();
                return true;
            case R.id.action_config_server:
                showConfigServerFragment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showConfigServerFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(configRequestFragment).hide(requestFragment).show(configServerFragment).commit();
    }

    public void showConfigRequestFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(configServerFragment).hide(requestFragment).show(configRequestFragment).commit();
    }

    private void showLoginFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(configServerFragment).hide(configRequestFragment).show(requestFragment).commit();
    }

    public void selectedServer(String server){
        requestFragment.setSelectedServer(server);
        showLoginFragment();
    }

    @Override
    public void onAddServerDialogPositiveClick(AddServerDialog dialog) {
        configServerFragment.addServer(dialog.getServer());
    }

    @Override
    public void onAddFieldDialogPositiveClick(AddFieldDialog dialog){
        configRequestFragment.addField(dialog.getField(), dialog.getFieldValue());
        requestFragment.updateBody();

    }

    @Override
    public void onPortPickerDialogPositiveClick(PortPickerDialog dialog){
        requestFragment.addPort(dialog.getPort());
    }

    public ArrayList<JsonField> getRequestBody(){
        return configRequestFragment.getFields();
    }
}
