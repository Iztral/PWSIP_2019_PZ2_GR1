package com.grupa1.teleman;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.grupa1.teleman.fragments.LoginFragment;
import com.grupa1.teleman.fragments.MainListFragment;
import com.grupa1.teleman.fragments.SettingsFragment;

import androidx.navigation.Navigation;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener, SettingsFragment.OnFragmentInteractionListener, MainListFragment.OnFragmentInteractionListener {
    public Context startContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startContext = this.getApplicationContext();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public MainActivity getActivity(){
        return MainActivity.this;
    }
}