package com.br.bnsantos.login.example;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import com.br.bnsantos.login.example.dialog.AddFieldDialog;
import com.br.bnsantos.login.example.dialog.AddServerDialog;
import com.br.bnsantos.login.example.dialog.PortPickerDialog;
import com.br.bnsantos.login.example.fragments.ConfigRequestFragment;
import com.br.bnsantos.login.example.fragments.ConfigServerFragment;
import com.br.bnsantos.login.example.fragments.LoginFragment;

public class LoginActivity extends FragmentActivity implements AddServerDialog.AddServerDialogListener, AddFieldDialog.AddFieldDialogListener,
        PortPickerDialog.PortPickerDialogListener{

    // action bar
    private ActionBar actionBar;

    private FrameLayout frameLayout;

    private Fragment loginFragment;
    private Fragment configServerFragment;
    private Fragment configRequestFragment;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        actionBar = getActionBar();
        // Hide the action bar title
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(getString(R.string.action_bar_title));
        actionBar.setIcon(R.drawable.red_balloons_logo);
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Enabling Spinner dropdown navigation
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        frameLayout = (FrameLayout)findViewById(R.id.loginLayout);

        initFragments();

        //showLoginFragment();
    }

    private void initFragments(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        loginFragment = LoginFragment.getInstance();
        configServerFragment = ConfigServerFragment.getInstance();
        configRequestFragment = ConfigRequestFragment.getInstance();
        fragmentTransaction.add(R.id.loginLayout, loginFragment);
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
        fragmentTransaction.hide(configRequestFragment).hide(loginFragment).show(configServerFragment).commit();
    }

    public void showConfigRequestFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(configServerFragment).hide(loginFragment).show(configRequestFragment).commit();
    }

    private void showLoginFragment(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(configServerFragment).hide(configRequestFragment).show(loginFragment).commit();
    }

    public void selectedServer(String server){
        ((LoginFragment)loginFragment).setSelectedServer(server);
        showLoginFragment();
    }

    @Override
    public void onAddServerDialogPositiveClick(AddServerDialog dialog) {
        ((ConfigServerFragment)configServerFragment).addServer(dialog.getServer());
    }

    @Override
    public void onAddFieldDialogPositiveClick(AddFieldDialog dialog){
        ((ConfigRequestFragment)configRequestFragment).addField(dialog.getField());
    }

    @Override
    public void onPortPickerDialogPositiveClick(PortPickerDialog dialog){
        ((LoginFragment)loginFragment).addPort(dialog.getPort());
    }

}
