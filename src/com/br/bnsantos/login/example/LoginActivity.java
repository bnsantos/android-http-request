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
import android.widget.Toast;
import com.br.bnsantos.login.example.dialog.AddServerDialog;
import com.br.bnsantos.login.example.fragments.ConfigRequestFragment;
import com.br.bnsantos.login.example.fragments.ConfigServerFragment;
import com.br.bnsantos.login.example.fragments.LoginFragment;

public class LoginActivity extends FragmentActivity implements AddServerDialog.AddServerDialogListener {

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

        showLoginFragment();
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
                Toast.makeText(getApplicationContext(), "home", Toast.LENGTH_SHORT).show();
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

    private void showConfigServerFragment(){
        configServerFragment = new ConfigServerFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(loginFragment!=null){
            fragmentTransaction.remove(loginFragment);
            loginFragment=null;
        }
        if(configRequestFragment!=null){
            fragmentTransaction.remove(configRequestFragment);
            configRequestFragment=null;
        }
        fragmentTransaction.add(R.id.loginLayout, configServerFragment).commit();
    }

    private void showLoginFragment(){
        loginFragment = new LoginFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(configRequestFragment!=null){
            fragmentTransaction.remove(configRequestFragment);
            configRequestFragment=null;
        }
        if(configServerFragment!=null){
            fragmentTransaction.remove(configRequestFragment);
            configRequestFragment=null;
        }
        fragmentTransaction.add(R.id.loginLayout, loginFragment).commit();
    }

    private void showConfigRequestFragment(){
        configRequestFragment = new ConfigRequestFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(loginFragment!=null){
            fragmentTransaction.remove(loginFragment);
            loginFragment=null;
        }
        if(configServerFragment!=null){
            fragmentTransaction.remove(configServerFragment);
            configServerFragment=null;
        }
        fragmentTransaction.add(R.id.loginLayout, configRequestFragment).commit();
    }

    public void selectedServer(String server){

    }

    @Override
    public void onDialogPositiveClick(AddServerDialog dialog) {
        ((ConfigServerFragment)configServerFragment).addServer(dialog.getServer());
    }

}
