package com.grupa1.teleman.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.grupa1.teleman.MainActivity;
import com.grupa1.teleman.R;
import com.grupa1.teleman.files.ConfigFile;
import com.grupa1.teleman.files.FileOperations;

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
    private ConfigFile config;

    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
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

        MainActivity.replaceStartDestination();

        Bundle receivedData = getArguments();
        if (receivedData != null) {
            config = receivedData.getParcelable("config");
        } else {
            config = new ConfigFile(FileOperations.readFile(CONFIG));
        }

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: mechanika logowania




                Navigation.findNavController(v).navigate(R.id.action_login);
            }
        });

        button_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundleToSend = new Bundle();
                bundleToSend.putParcelable("config", config);
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
