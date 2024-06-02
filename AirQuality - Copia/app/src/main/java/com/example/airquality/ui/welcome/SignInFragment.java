package com.example.airquality.ui.welcome;

import static com.example.airquality.util.Constants.INVALID_CREDENTIALS_ERROR;
import static com.example.airquality.util.Constants.INVALID_USER_ERROR;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;


import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.airquality.R;
import com.example.airquality.data.repository.user.IUserRepository;
import com.example.airquality.model.Result;
import com.example.airquality.ui.main.MainActivity;
import com.example.airquality.util.ServiceLocator;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;


import org.apache.commons.validator.routines.EmailValidator;

import java.util.Objects;


public class SignInFragment extends Fragment {

    private FirebaseAuth auth;
    private TextInputLayout signInEmail, signInPassword;

    private static final String TAG = SignInFragment.class.getSimpleName();
    private static final boolean USE_NAVIGATION_COMPONENT = true;

    private ActivityResultLauncher<IntentSenderRequest> activityResultLauncher;
    private ActivityResultContracts.StartIntentSenderForResult startIntentSenderForResult;

    private UserViewModel userViewModel;
    //private LinearProgressIndicator progressIndicator;

    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;



    public SignInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment SignInFragment.
     */
    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //auth = FirebaseAuth.getInstance();
        Log.d(TAG, "onCreate called");

        IUserRepository userRepository = ServiceLocator.getInstance().
                getUserRepository(requireActivity().getApplication());
        userViewModel = new ViewModelProvider(
                requireActivity(),
                new UserViewModelFactory(userRepository)).get(UserViewModel.class);
        Log.d(TAG, "UserViewModel initialized");
        //dataEncryptionUtil = new DataEncryptionUtil(requireActivity().getApplication());

        oneTapClient = Identity.getSignInClient(requireActivity());
        signInRequest = BeginSignInRequest.builder()
                .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
                        .setSupported(true)
                        .build())
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.default_web_client_id))
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                // Automatically sign in when exactly one credential is retrieved.
                .setAutoSelectEnabled(true)
                .build();

        startIntentSenderForResult = new ActivityResultContracts.StartIntentSenderForResult();
        Log.d(TAG, "SignInClient and SignInRequest initialized");

        activityResultLauncher = registerForActivityResult(startIntentSenderForResult, activityResult -> {
            if (activityResult.getResultCode() == Activity.RESULT_OK) {
                Log.d(TAG, "Activity result is OK");
                try {
                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(activityResult.getData());
                    String idToken = credential.getGoogleIdToken();
                    if (idToken !=  null) {
                        Log.d(TAG, "Google ID token received");
                        // Got an ID token from Google. Use it to authenticate with Firebase.
                        userViewModel.getGoogleUserMutableLiveData(idToken).observe(getViewLifecycleOwner(), authenticationResult -> {
                            if (authenticationResult.isSuccess()) {
                                Log.d(TAG, "Authentication success");
                                //User user = ((Result.UserResponseSuccess) authenticationResult).getData();
                                //saveLoginData(user.getEmail(), null, user.getIdToken());
                                userViewModel.setAuthenticationError(false);
                                //retrieveUserInformationAndStartActivity(user, R.id.navigate_to_newsPreferencesActivity);
                                startActivityBasedOnCondition(
                                        R.id.action_signInFragment_to_mainActivity);
                            } else {
                                Log.e(TAG, "Authentication failed: " + ((Result.Error) authenticationResult).getMessage());
                                userViewModel.setAuthenticationError(true);
                                //progressIndicator.setVisibility(View.GONE);
                                Snackbar.make(requireActivity().findViewById(android.R.id.content),
                                        getErrorMessage(((Result.Error) authenticationResult).getMessage()),
                                        Snackbar.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Log.e(TAG, "ID token is null");
                    }

                } catch (ApiException e) {
                    Log.e(TAG, "ApiException: " + e.getMessage(), e);
                    Snackbar.make(requireActivity().findViewById(android.R.id.content),
                            requireActivity().getString(R.string.unexpected_error),
                            Snackbar.LENGTH_SHORT).show();
                }
            } else {
                Log.e(TAG, "Activity result not OK, code: " + activityResult.getResultCode());
            }

        });

    }

    /*
    private void retrieveUserInformationAndStartActivity(User user, int destination) {
        //progressIndicator.setVisibility(View.VISIBLE);

        userViewModel.getUserFavoriteNewsMutableLiveData(user.getIdToken()).observe(
                getViewLifecycleOwner(), userFavoriteNewsRetrievalResult -> {
                    progressIndicator.setVisibility(View.GONE);
                    startActivityBasedOnCondition(NewsPreferencesActivity.class, destination);
                }
        );
    }

     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (userViewModel.getLoggedUser() != null) {
            Log.d(TAG, "User is already logged in");
            /*
            SharedPreferencesUtil sharedPreferencesUtil =
                    new SharedPreferencesUtil(requireActivity().getApplication());

            if (sharedPreferencesUtil.readStringData(SHARED_PREFERENCES_FILE_NAME,
                    SHARED_PREFERENCES_COUNTRY_OF_INTEREST) != null &&
                    sharedPreferencesUtil.readStringSetData(SHARED_PREFERENCES_FILE_NAME,
                            SHARED_PREFERENCES_TOPICS_OF_INTEREST) != null) {

                startActivityBasedOnCondition(MainActivityWithBottomNavigationView.class,
                        R.id.navigate_to_mainActivityWithBottomNavigationView);
            } else {
                startActivityBasedOnCondition(NewsPreferencesActivity.class,
                        R.id.navigate_to_newsPreferencesActivity);
            }
            */
            startActivityBasedOnCondition(
                    R.id.action_signInFragment_to_mainActivity);
        }

        signInEmail = view.findViewById(R.id.sign_in_email);
        signInPassword = view.findViewById(R.id.sign_in_password);
        Button signinButton = view.findViewById(R.id.sign_in_button);
        Button signupRedirectText = view.findViewById(R.id.sign_up_button);
        Button forgotPassword = view.findViewById(R.id.forgot_password_button);
        Button googleSignIn = view.findViewById(R.id.google_signin_button);



        signinButton.setOnClickListener(v -> {
            String email = Objects.requireNonNull(signInEmail.getEditText()).getText().toString().trim();
            String password = Objects.requireNonNull(signInPassword.getEditText()).getText().toString().trim();
            Log.d(TAG, "Sign-in button clicked, email: " + email);


            // Start login if email and password are ok
            if (isEmailOk(email) & isPasswordOk(password)) {
                Log.d(TAG, "Email and password are OK");
                if (!userViewModel.isAuthenticationError()) {
                    //progressIndicator.setVisibility(View.VISIBLE);
                    userViewModel.getUserMutableLiveData(email, password, true).observe(
                            getViewLifecycleOwner(), result -> {
                                if (result.isSuccess()) {
                                    Log.d(TAG, "User login success");
                                    //User user = ((Result.UserResponseSuccess) result).getData();
                                    //saveLoginData(email, password, user.getIdToken());
                                    userViewModel.setAuthenticationError(false);
                                    //retrieveUserInformationAndStartActivity(user, R.id.navigate_to_newsPreferencesActivity);
                                    Navigation.findNavController(v).navigate(R.id.action_signInFragment_to_mainActivity);
                                } else {
                                    Log.e(TAG, "User login failed: " + ((Result.Error) result).getMessage());
                                    userViewModel.setAuthenticationError(true);
                                    //progressIndicator.setVisibility(View.GONE);
                                    Snackbar.make(requireActivity().findViewById(android.R.id.content),
                                            getErrorMessage(((Result.Error) result).getMessage()),
                                            Snackbar.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Log.e(TAG, "Authentication error flag is set");
                    userViewModel.getUser(email, password, true);
                }
            } else {
                Log.e(TAG, "Email or password not OK");
                Snackbar.make(requireActivity().findViewById(android.R.id.content),
                        R.string.check_login_data_message, Snackbar.LENGTH_SHORT).show();
            }
        });

        googleSignIn.setOnClickListener(v -> oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(requireActivity(), new OnSuccessListener<BeginSignInResult>() {
                    @Override
                    public void onSuccess(BeginSignInResult result) {
                        Log.d(TAG, "Google Sign-in success");
                        IntentSenderRequest intentSenderRequest =
                                new IntentSenderRequest.Builder(result.getPendingIntent()).build();
                        activityResultLauncher.launch(intentSenderRequest);
                    }
                })
                .addOnFailureListener(requireActivity(), new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // No saved credentials found. Launch the One Tap sign-up flow, or
                        // do nothing and continue presenting the signed-out UI.
                        Log.e(TAG, "Google Sign-in failure: " + e.getLocalizedMessage(), e);

                        Snackbar.make(requireActivity().findViewById(android.R.id.content),
                                requireActivity().getString(R.string.error_no_google_account_found_message),
                                Snackbar.LENGTH_SHORT).show();
                    }
                }));

        signupRedirectText.setOnClickListener(v -> {
            Log.d(TAG, "Redirecting to sign-up");
            Navigation.findNavController(v).navigate(R.id.action_signInFragment_to_signUpFragment);
        });

/*
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_forgot_password, null);
                TextInputLayout emailBox = dialogView.findViewById(R.id.forgot_password_email);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialogView.findViewById(R.id.reset_password_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String userEmail = Objects.requireNonNull(emailBox.getEditText()).getText().toString().trim();
                        if (TextUtils.isEmpty(userEmail) && !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
                            Toast.makeText(requireActivity(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        auth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(requireActivity(), "Check your email", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(requireActivity(), "Unable to send, failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                dialogView.findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                if (dialog.getWindow() != null){
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                dialog.show();
            }
        });

 */


    }
    @Override
    public void onResume() {
        super.onResume();
        userViewModel.setAuthenticationError(false);
    }

    /**
     * Returns the text to be shown to the user based on the type of error.
     * @param errorType The type of error.
     * @return The message to be shown to the user.
     */
    private String getErrorMessage(String errorType) {
        switch (errorType) {
            case INVALID_CREDENTIALS_ERROR:
                return requireActivity().getString(R.string.error_login_password_message);
            case INVALID_USER_ERROR:
                return requireActivity().getString(R.string.error_login_user_message);
            default:
                return requireActivity().getString(R.string.unexpected_error);
        }
    }

    /**
     * Starts another Activity using Intent or NavigationComponent.
     *
     * @param destination The ID associated with the action defined in welcome_nav_graph.xml.
     */
    private void startActivityBasedOnCondition(int destination) {
        if (USE_NAVIGATION_COMPONENT) {
            Navigation.findNavController(requireView()).navigate(destination);
        } else {
            Intent intent = new Intent(requireContext(), MainActivity.class);
            startActivity(intent);
        }
        requireActivity().finish();
    }

    /**
     * Checks if the email address has a correct format.
     * @param email The email address to be validated
     * @return true if the email address is valid, false otherwise
     */
    private boolean isEmailOk(String email) {
        if (!EmailValidator.getInstance().isValid((email))) {
            signInEmail.setError(getString(R.string.error_email));
            return false;
        } else {
            signInEmail.setError(null);
            return true;
        }
    }

    /**
     * Checks if the password is not empty.
     * @param password The password to be checked
     * @return True if the password is not empty, false otherwise
     */
    private boolean isPasswordOk(String password) {
        // Check if the password length is correct
        if (password.isEmpty()) {
            signInPassword.setError(getString(R.string.error_password));
            return false;
        } else {
            signInPassword.setError(null);
            return true;
        }
    }


}