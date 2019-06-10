package com.grupa1.teleman;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.grupa1.teleman.files.ConnectionConfig;
import com.grupa1.teleman.files.FileOperations;
import com.grupa1.teleman.fragments.LoginFragment;
import com.grupa1.teleman.fragments.MainListFragment;
import com.grupa1.teleman.fragments.NoConfigPrompt;
import com.grupa1.teleman.fragments.SettingsFragment;
import com.grupa1.teleman.networking.JdbcConnection;

import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.fragment.NavHostFragment;

import static com.grupa1.teleman.files.FILES.FILE_TYPE.CONFIG;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener, SettingsFragment.OnFragmentInteractionListener, MainListFragment.OnFragmentInteractionListener, NoConfigPrompt.OnFragmentInteractionListener, NoConfigPrompt.OnFragmentCreateListener{
    public static String currentDir;
    public static ConnectionConfig config;
    public static JdbcConnection connection;
    private static NavGraph graph;
    private static NavController navController;
    private static  MainActivity app;
    private static LayoutInflater infltr;

    public static Context getAppContext(){
        return app;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        app = this;
        currentDir = this.getApplicationContext().getApplicationInfo().dataDir;
        config = new ConnectionConfig(FileOperations.readFile(CONFIG));

        NavHostFragment navHost = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        navController = navHost.getNavController();

        NavInflater navInflater = navController.getNavInflater();
        graph = navInflater.inflate(R.navigation.nav_graph);

        if (!(config.isValid())) {
            graph.setStartDestination(R.id.noConfigPrompt);
        } else {
            graph.setStartDestination(R.id.loginFragment);
        }

        navController.setGraph(graph);

        infltr = this.getLayoutInflater();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentCreate() {
        if(graph.getStartDestination()==R.id.noConfigPrompt){
            graph.setStartDestination(R.id.loginFragment);
            navController.setGraph(graph);
        }
    }

    public static void replaceStartDestination(){
        if(graph.getStartDestination()==R.id.noConfigPrompt){
            graph.setStartDestination(R.id.loginFragment);
            navController.setGraph(graph);
            int x=0;
        }
    }

    public static LayoutInflater getInflater(){
        return infltr;
    }
}