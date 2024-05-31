package com.example.airquality.data.repository.user;

import java.util.List;

//import com.example.airquality.model.News;
import com.example.airquality.model.User;

public interface UserResponseCallback {
    void onSuccessFromAuthentication(User user);
    void onFailureFromAuthentication(String message);
    void onSuccessFromRemoteDatabase(User user);
    //void onSuccessFromRemoteDatabase(List<News> newsList);
    void onSuccessFromGettingUserPreferences();
    void onFailureFromRemoteDatabase(String message);
    void onSuccessLogout();
}


