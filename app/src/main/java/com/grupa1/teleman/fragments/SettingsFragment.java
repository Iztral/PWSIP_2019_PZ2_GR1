package com.grupa1.teleman.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.grupa1.teleman.R;
import com.grupa1.teleman.files.ConnectionConfig;
import com.grupa1.teleman.files.FILES;
import com.grupa1.teleman.files.FileOperations;

import androidx.navigation.Navigation;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {
    private View inflatedView;
    private OnFragmentInteractionListener mListener;
    private ConnectionConfig config;

    public SettingsFragment() {
    }

    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflatedView = inflater.inflate(R.layout.fragment_settings, container, false);
        final Button button_save_exit = inflatedView.findViewById(R.id.button_save_exit);

        final EditText[] fieldsBundle = new EditText[]
                {
                        inflatedView.findViewById(R.id.editText_Address),
                        inflatedView.findViewById(R.id.editText_Port),
                        inflatedView.findViewById(R.id.editText_Password),
                        inflatedView.findViewById(R.id.editText_Name),
                        inflatedView.findViewById(R.id.editText_Username)
                };

        button_save_exit.setEnabled(false);
        Button button_exit = inflatedView.findViewById(R.id.button_exit);
        Bundle args = getArguments();

        if (args != null) {
            config = args.getParcelable("config");
            for (int i = 0; i < fieldsBundle.length; i++) {
                fieldsBundle[i].setText(config.getDatabase()[i]);
            }
        } else {
            button_exit.setVisibility(View.INVISIBLE);
        }


        button_save_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateConfig();
                FileOperations.saveToFile(FILES.FILE_TYPE.CONFIG, config.getDatabase());

                Bundle bundleToSend = new Bundle();
                bundleToSend.putParcelable("config", config);

                Navigation.findNavController(v).navigate(R.id.action_returnToLogin, bundleToSend);
            }
        });

        button_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });


        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean toEnable = true;
                for (EditText ed : fieldsBundle) {
                    if (ed.getText().toString().equals("")) {
                        toEnable = false;
                        break;
                    }
                }
                button_save_exit.setEnabled(toEnable);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        for (EditText ed : fieldsBundle) {
            ed.addTextChangedListener(watcher);
        }

        return inflatedView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    private void updateConfig() {
        final EditText[] fieldsBundle = new EditText[]
                {
                        inflatedView.findViewById(R.id.editText_Address),
                        inflatedView.findViewById(R.id.editText_Port),
                        inflatedView.findViewById(R.id.editText_Password),
                        inflatedView.findViewById(R.id.editText_Name),
                        inflatedView.findViewById(R.id.editText_Username)
                };
        String[] data = new String[fieldsBundle.length];
        for (int i = 0; i < fieldsBundle.length; i++) {
            data[i] = fieldsBundle[i].getText().toString();
        }
        config = new ConnectionConfig(data);
    }
}
