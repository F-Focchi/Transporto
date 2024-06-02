package com.example.airquality.ui.account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.airquality.R;
import com.example.airquality.data.repository.user.IUserRepository;
import com.example.airquality.ui.welcome.UserViewModel;
import com.example.airquality.ui.welcome.UserViewModelFactory;
import com.example.airquality.util.ServiceLocator;
import com.google.android.material.snackbar.Snackbar;


public class AccountFragment extends Fragment {

    private static final String TAG = AccountFragment.class.getSimpleName();
    //private FragmentSettingsBinding fragmentSettingsBinding;
    private UserViewModel userViewModel;
    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment AccountFragment.
     */

    public static AccountFragment newInstance() {

        return new AccountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IUserRepository userRepository = ServiceLocator.getInstance().
                getUserRepository(requireActivity().getApplication());
        userViewModel = new ViewModelProvider(
                requireActivity(),
                new UserViewModelFactory(userRepository)).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button logoutButton = view.findViewById(R.id.logout_button);
        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.clear();
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        });

        logoutButton.setOnClickListener(v -> {
            userViewModel.logout().observe(getViewLifecycleOwner(), result -> {
                if (result.isSuccess()) {
                    Navigation.findNavController(view).navigate(
                            R.id.action_accountFragment_to_welcomeActivity);
                } else {
                    Snackbar.make(view,
                            requireActivity().getString(R.string.unexpected_error),
                            Snackbar.LENGTH_SHORT).show();
                }
            });
        });


    }
    /*
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentSettingsBinding = null;
    }

     */
}