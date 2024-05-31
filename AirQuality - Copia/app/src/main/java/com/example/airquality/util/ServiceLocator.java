package com.example.airquality.util;

import static com.example.airquality.util.Constants.BASE_URL;

import android.app.Application;

//import com.example.airquality.data.repository.user.IUserRepository;
//import com.example.airquality.data.repository.user.UserRepository;
import com.example.airquality.data.service.AqiApiService;
//import com.example.airquality.data.source.user.BaseUserAuthenticationRemoteDataSource;
//import com.example.airquality.data.source.user.BaseUserDataRemoteDataSource;
//import com.example.airquality.data.source.user.UserAuthenticationRemoteDataSource;
//import com.example.airquality.data.source.user.UserDataRemoteDataSource;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

    public class ServiceLocator {

        private static volatile ServiceLocator INSTANCE = null;

        private ServiceLocator() {}

        /**
         * Returns an instance of ServiceLocator class.
         * @return An instance of ServiceLocator.
         */


        public static ServiceLocator getInstance() {
            if (INSTANCE == null) {
                synchronized(ServiceLocator.class) {
                    if (INSTANCE == null) {
                        INSTANCE = new ServiceLocator();
                    }
                }
            }
            return INSTANCE;
        }


        public static AqiApiService getAqiApiService() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            return retrofit.create(AqiApiService.class);
        }
/*
        public IUserRepository getUserRepository(Application application) {
            SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(application);

            BaseUserAuthenticationRemoteDataSource userRemoteAuthenticationDataSource =
                    new UserAuthenticationRemoteDataSource();

            BaseUserDataRemoteDataSource userDataRemoteDataSource =
                    new UserDataRemoteDataSource(sharedPreferencesUtil);

           // DataEncryptionUtil dataEncryptionUtil = new DataEncryptionUtil(application);

           BaseNewsLocalDataSource newsLocalDataSource =
                    new NewsLocalDataSource(getNewsDao(application), sharedPreferencesUtil,
                            dataEncryptionUtil);



            return new UserRepository(userRemoteAuthenticationDataSource,
                    userDataRemoteDataSource, newsLocalDataSource);
        }
        */
}
