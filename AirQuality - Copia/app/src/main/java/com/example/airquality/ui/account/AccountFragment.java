package com.example.airquality.ui.account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.airquality.R;
import com.example.airquality.data.repository.user.IUserRepository;
import com.example.airquality.model.Result;
import com.example.airquality.ui.welcome.UserViewModel;
import com.example.airquality.ui.welcome.UserViewModelFactory;
import com.example.airquality.util.ServiceLocator;
import com.google.android.material.snackbar.Snackbar;

public class AccountFragment extends Fragment {

    private static final String TAG = AccountFragment.class.getSimpleName();
    private UserViewModel userViewModel;

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IUserRepository userRepository = ServiceLocator.getInstance()
                .getUserRepository(requireActivity().getApplication());
        userViewModel = new ViewModelProvider(
                requireActivity(),
                new UserViewModelFactory(userRepository)).get(UserViewModel.class);

        Log.d(TAG, "Fragment created");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button logoutButton = view.findViewById(R.id.logout_button);
        Log.d(TAG, "Logout button initialized: " + (logoutButton != null));

        assert logoutButton != null;
        logoutButton.setOnClickListener(v -> {
            Log.d(TAG, "Logout button clicked");
            userViewModel.logout().observe(getViewLifecycleOwner(), result -> {
                Log.d(TAG, "Observer is activated");
                if (result.isSuccess()) {
                    Log.d(TAG, "Logout success");
                    Navigation.findNavController(view)
                            .navigate(R.id.action_accountFragment_to_welcomeActivity);
                } else {
                    Log.e(TAG, "Logout failed: " + ((Result.Error) result).getMessage());
                    Snackbar.make(view,
                            requireActivity().getString(R.string.unexpected_error),
                            Snackbar.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "Fragment started");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
