package com.example.airquality.ui.welcome;

import static com.example.airquality.util.Constants.MINIMUM_PASSWORD_LENGTH;
import static com.example.airquality.util.Constants.USER_COLLISION_ERROR;
import static com.example.airquality.util.Constants.WEAK_PASSWORD_ERROR;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.airquality.R;
import com.example.airquality.model.Result;
import com.example.airquality.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.Objects;


public class SignUpFragment extends Fragment {

    private TextInputLayout signupEmail, signupPassword;

    private static final String TAG = SignUpFragment.class.getSimpleName();
    //private FragmentRegistrationBinding binding;
    private UserViewModel userViewModel;



    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment SignUpFragment.
     */
    public static SignUpFragment newInstance() {

        return new SignUpFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.setAuthenticationError(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // binding = FragmentRegistrationBinding.inflate(inflater, container, false);

       // return binding.getRoot();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        signupEmail = view.findViewById(R.id.sign_up_email);
        signupPassword = view.findViewById(R.id.sign_up_password);
        Button signupButton = view.findViewById(R.id.sign_up_button);
        Button signinRedirectText = view.findViewById(R.id.sign_in_redirect_button);


        signupButton.setOnClickListener(item -> {

            String email = Objects.requireNonNull(signupEmail.getEditText()).getText().toString().trim();
            String password = Objects.requireNonNull(signupPassword.getEditText()).getText().toString().trim();

            if (isEmailOk(email) & isPasswordOk(password)) {
                //binding.progressBar.setVisibility(View.VISIBLE);
                if (!userViewModel.isAuthenticationError()) {
                    userViewModel.getUserMutableLiveData(email, password, false).observe(
                            getViewLifecycleOwner(), result -> {
                                if (result.isSuccess()) {
                                    //User user = ((Result.UserResponseSuccess) result).getData();
                                    //saveLoginData(email, password, user.getIdToken());
                                    userViewModel.setAuthenticationError(false);
                                    Navigation.findNavController(item).navigate(
                                            R.id.action_signUpFragment_to_mainActivity);
                                } else {
                                    userViewModel.setAuthenticationError(true);
                                    Snackbar.make(requireActivity().findViewById(android.R.id.content),
                                            getErrorMessage(((Result.Error) result).getMessage()),
                                            Snackbar.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    userViewModel.getUser(email, password, false);
                }
                //binding.progressBar.setVisibility(View.GONE);
            } else {
                userViewModel.setAuthenticationError(true);
                Snackbar.make(requireActivity().findViewById(android.R.id.content),
                        R.string.check_login_data_message, Snackbar.LENGTH_SHORT).show();
            }
        });

        signinRedirectText.setOnClickListener(item -> {

            Navigation.findNavController(item).navigate(R.id.action_signUpFragment_to_signInFragment);
        });

    }
    private String getErrorMessage(String message) {
        switch(message) {
            case WEAK_PASSWORD_ERROR:
                return requireActivity().getString(R.string.error_password);
            case USER_COLLISION_ERROR:
                return requireActivity().getString(R.string.error_user_collision_message);
            default:
                return requireActivity().getString(R.string.unexpected_error);
        }
    }
    private boolean isEmailOk(String email) {
        if (!EmailValidator.getInstance().isValid((email))) {
            signupEmail.setError(getString(R.string.error_email));
            return false;
        } else {
            signupEmail.setError(null);
            return true;
        }
    }
    private boolean isPasswordOk(String password) {
        if (password.isEmpty() || password.length() < MINIMUM_PASSWORD_LENGTH) {
            signupPassword.setError(getString(R.string.error_password));
            return false;
        } else {
            signupPassword.setError(null);
            return true;
        }
    }
/*
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

 */

}