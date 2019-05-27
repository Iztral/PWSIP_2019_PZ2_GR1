package com.grupa1.teleman.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.grupa1.teleman.MainActivity;
import com.grupa1.teleman.R;
import com.grupa1.teleman.files.ConnectionConfig;
import com.grupa1.teleman.files.FileOperations;
import com.grupa1.teleman.networking.JdbcConnection;
import com.grupa1.teleman.networking.RunningConfig;

import java.sql.ResultSet;
import java.sql.SQLException;

import androidx.navigation.Navigation;

import static com.grupa1.teleman.files.FILES.FILE_TYPE.CONFIG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    private View inflatedView;
    private ConnectionConfig connCfg;
    private JdbcConnection connection;
    private RunningConfig runnCfg;

    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_login, container, false);

        Button button_login = inflatedView.findViewById(R.id.button_login);
        Button button_settings = inflatedView.findViewById(R.id.button_settings);
        final EditText editText_login = inflatedView.findViewById(R.id.editText_email);
        final EditText editText_password = inflatedView.findViewById(R.id.editText_password);

        MainActivity.replaceStartDestination();

        Bundle receivedData = getArguments();
        if (receivedData != null) {
            connCfg = receivedData.getParcelable("connCfg");
        } else {
            connCfg = new ConnectionConfig(FileOperations.readFile(CONFIG));
        }

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: mechanika logowania
                connection = new JdbcConnection(
                        connCfg.getDatabaseAddress(),
                        connCfg.getDatabasePort(),
                        connCfg.getDatabaseName(),
                        connCfg.getDatabaseUsername(),
                        connCfg.getDatabasePassword());
                ResultSet response = connection.executeQuery(String.format("SELECT ID FROM Users u WHERE (u.Login=%s OR u.Email=%s) AND u.Password=%s AND u.Rank=driver",
                        editText_login.getText().toString(), editText_login.getText().toString(), editText_password.getText().toString()));
                try {
                    if (response.getFetchSize() != 0) {
                        response.next();
                        runnCfg.setClientID(response.getInt("ID"));
                        Navigation.findNavController(v).navigate(R.id.action_login);
                    }
                    else{

                    }
                } catch (Exception ex){
                    ex.printStackTrace();
                }

            }
        });

        button_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundleToSend = new Bundle();
                bundleToSend.putParcelable("connCfg", connCfg);
                Navigation.findNavController(v).navigate(R.id.action_settings, bundleToSend);
            }
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


}
