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
import android.widget.Toast;

import com.example.airquality.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class SignUpFragment extends Fragment {

    private FirebaseAuth auth;
    // private FirebaseDatabase database;
    //GoogleSignInClient googlesinginclient1;
    private TextInputLayout signupEmail, signupPassword;


    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance() {

        return new SignUpFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();
        // database = FirebaseDatabase.getInstance();
        signupEmail = view.findViewById(R.id.sign_up_email);
        signupPassword = view.findViewById(R.id.sign_up_password);
        Button signupButton = view.findViewById(R.id.sign_up_button);
        Button signinRedirectText = view.findViewById(R.id.sign_in_redirect_button);
        Button googlesignup = view.findViewById(R.id.google_signup_button);

     /*   GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail().build();

        googlesinginclient1 = GoogleSignIn.getClient(this,gso);

    */
        /*
        signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.default_web_client_id))
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(true)
                        .build())
                .build();
*/
        signupButton.setOnClickListener(item -> {

            String email = Objects.requireNonNull(signupEmail.getEditText()).getText().toString().trim();
            String pass = Objects.requireNonNull(signupPassword.getEditText()).getText().toString().trim();

            if (email.isEmpty()){
                signupEmail.setError("Email cannot be empty");
            }
            if (pass.isEmpty()){
                signupPassword.setError("Password cannot be empty");
            } else{
                auth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(requireActivity(), "SignUp Successful", Toast.LENGTH_SHORT).show();
                                    Navigation.findNavController(item).navigate(R.id.action_signUpFragment_to_mainActivity);
                                } else {
                                    Toast.makeText(requireActivity(), "SignUp Failed" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

        });

        signinRedirectText.setOnClickListener(item -> {

            Navigation.findNavController(item).navigate(R.id.action_signUpFragment_to_signInFragment);
        });

    }
}