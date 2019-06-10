package com.grupa1.teleman.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.grupa1.teleman.MainActivity;
import com.grupa1.teleman.R;
import com.grupa1.teleman.files.ConnectionConfig;
import com.grupa1.teleman.files.FileOperations;
import com.grupa1.teleman.networking.JdbcConnection;
import com.grupa1.teleman.objects.Order;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static com.grupa1.teleman.files.FILES.FILE_TYPE.CONFIG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainListFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private JdbcConnection connection;
    private ConnectionConfig connCfg;
    private LinearLayout currentView, waitingView;

    public MainListFragment() {
    }

    public static MainListFragment newInstance() {
        MainListFragment fragment = new MainListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    ArrayList<Order> orders = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflatedView = inflater.inflate(R.layout.fragment_main_list, container, false);
        currentView = inflatedView.findViewById(R.id.current_LinearLayout);
        waitingView = inflatedView.findViewById(R.id.waiting_LinearLayout);

        connection = MainActivity.connection;
        String query =
                "SELECT o.ID, o.Description, o.Location, o.Destination, o.State " +
                "FROM Orders o, Association a, Users u " +
                        "WHERE u.ID="+ LoginFragment.getRunnCfg().getClientID()+
                        " AND a.UserID=u.ID" +
                        " AND o.ID=a.OrderID" +
                        " AND (o.State='waiting' OR o.State='ongoing')";

        ResultSet resultSet = connection.executeQuery(query);


        refreshScreen(resultSet);

        Button refresh = inflatedView.findViewById(R.id.refresh_button);
        refresh.setOnClickListener(v -> {
            refreshScreen(connection.executeQuery(query));
            Toast.makeText(MainActivity.getAppContext(),"Odświeżono", Toast.LENGTH_SHORT).show();
        });

        return inflatedView;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void refreshScreen(ResultSet resultSet){
        currentView.removeAllViews();
        waitingView.removeAllViews();
        orders.clear();
        try {
            int i=0;
            while (resultSet.next()){
                Order order = new Order(resultSet, i);
                i++;
                orders.add(order);
            }

        } catch (Exception ignored){
        }

        for(Order o : orders){
            switch (o.getState()){
                case "waiting":{
                    waitingView.addView(o.getView(),o.getIndex()-1);
                    break;
                }
                case "ongoing":{
                    currentView.addView(o.getView(),o.getIndex()-1);
                    break;
                }
            }
        }

    }
}
