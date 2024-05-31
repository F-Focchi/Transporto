package com.example.airquality.ui.welcome;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.airquality.R;


public class WelcomeFragment extends Fragment {



    public WelcomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WelcomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WelcomeFragment newInstance() {

        return new WelcomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button signUpButton = view.findViewById(R.id.button);
        //final Button signInButton = view.findViewById(R.id.button2);

        signUpButton.setOnClickListener(item -> {

            Navigation.findNavController(item).navigate(R.id.action_welcomeFragment_to_signInFragment);
        });
/*
        signInButton.setOnClickListener(item -> {

            startActivity(new Intent(WelcomeActivity.this, SignUpActivity.class));
            finish();
        });

 */
    }
}