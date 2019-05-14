package com.grupa1.teleman.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.grupa1.teleman.R;

import androidx.navigation.Navigation;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NoConfigPrompt.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NoConfigPrompt#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoConfigPrompt extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private OnFragmentCreateListener oListener;

    public NoConfigPrompt() {
        // Required empty public constructor
    }

    public static NoConfigPrompt newInstance(String param1, String param2) {
        NoConfigPrompt fragment = new NoConfigPrompt();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (oListener != null)
            oListener.onFragmentCreate();

        View inflatedView = inflater.inflate(R.layout.fragment_no_config_prompt, container, false);
        Button toSettings = inflatedView.findViewById(R.id.button_toSettings);
        toSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_noConfigPrompt_to_settingsFragment);
            }
        });

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
        } else if (context instanceof OnFragmentCreateListener) {
            oListener = (OnFragmentCreateListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement all listeners");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        oListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public interface OnFragmentCreateListener {
        void onFragmentCreate();
    }
}
