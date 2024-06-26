package com.example.airquality.ui.welcome;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Set;

import com.example.airquality.model.Result;
import com.example.airquality.model.User;
import com.example.airquality.data.repository.user.IUserRepository;

public class UserViewModel extends ViewModel {
    private static final String TAG = UserViewModel.class.getSimpleName();

    private final IUserRepository userRepository;
    private MutableLiveData<Result> userMutableLiveData;
    //private MutableLiveData<Result> userFavoriteNewsMutableLiveData;
    //private MutableLiveData<Result> userPreferencesMutableLiveData;
    private boolean authenticationError;

    public UserViewModel(IUserRepository userRepository) {
        this.userRepository = userRepository;
        authenticationError = false;
    }

    public MutableLiveData<Result> getUserMutableLiveData(String email, String password, boolean isUserRegistered) {
        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
            getUserData(email, password, isUserRegistered);
        }
        return userMutableLiveData;
    }

    public MutableLiveData<Result> getGoogleUserMutableLiveData(String token) {
        if (userMutableLiveData == null) {
            getUserData(token);
        }
        return userMutableLiveData;
    }
/*
    public MutableLiveData<Result> getUserFavoriteNewsMutableLiveData(String idToken) {
        if (userFavoriteNewsMutableLiveData == null) {
            getUserFavoriteNews(idToken);
        }
        return userFavoriteNewsMutableLiveData;
    }

    public void saveUserPreferences(String favoriteCountry, Set<String> favoriteTopics, String idToken) {
        if (idToken != null) {
            userRepository.saveUserPreferences(favoriteCountry, favoriteTopics, idToken);
        }
    }

    public MutableLiveData<Result> getUserPreferences(String idToken) {
        if (idToken != null) {
            userPreferencesMutableLiveData = userRepository.getUserPreferences(idToken);
        }
        return userPreferencesMutableLiveData;
    }

 */
public MutableLiveData<Result> logout() {
    if (userMutableLiveData == null) {
        Log.d(TAG, "logout:1 ");
        userMutableLiveData = userRepository.logout();
    } else {
        Log.d(TAG, "logout:2 ");
        userMutableLiveData.setValue(userRepository.logout().getValue());
    }
    return userMutableLiveData;
}

    public User getLoggedUser() {
        return userRepository.getLoggedUser();
    }


/*
    private void getUserFavoriteNews(String idToken) {
        userFavoriteNewsMutableLiveData = userRepository.getUserFavoriteNews(idToken);
    }

 */

    public void getUser(String email, String password, boolean isUserRegistered) {
        userRepository.getUser(email, password, isUserRegistered);
    }

    public boolean isAuthenticationError() {
        return authenticationError;
    }

    public void setAuthenticationError(boolean authenticationError) {
        this.authenticationError = authenticationError;
    }

    private void getUserData(String email, String password, boolean isUserRegistered) {
        userMutableLiveData = userRepository.getUser(email, password, isUserRegistered);
    }

    private void getUserData(String token) {
        userMutableLiveData = userRepository.getGoogleUser(token);
    }
}


