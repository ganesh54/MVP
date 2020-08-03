package com.example.mvp.activity.model;

import android.app.Application;
import android.util.Log;

import com.example.mvp.activity.presenter.UserPresenterInterface;
import com.example.mvp.model.Users;
import com.example.mvp.network.ApiManager;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private Application application;
    private UserPresenterInterface userPresenterInterface;

    public UserRepository(Application application) {
        this.application = application;
        Log.e("UserRepository", "UserRepository");
    }

    public UserRepository(UserPresenterInterface userPresenterInterface) {
        this.userPresenterInterface = userPresenterInterface;
    }

    public void getUser(int page, int perPage) {
        //  final MutableLiveData<Users> mutableLiveData = new MutableLiveData<>();
        userPresenterInterface.showProgressbar();
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("per_page", perPage);
        ApiManager.getInstance().getApiService().getUsers(map).enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                Log.e("test2", "test2");
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    userPresenterInterface.hideProgressBar();
                    Users users = response.body();
                    if (users.getUserList() != null)
                        Log.e("user repository", "data pass to presenter");
                    userPresenterInterface.passDataToView(users);

                } else {

                    Log.e("user repository", "User list null");
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                userPresenterInterface.hideProgressBar();
                Log.e("user repository", "User list null");
            }
        });
    }

    public void getUserNext(int page, int perPage) {
        // final MutableLiveData<Users> mutableLiveData = new MutableLiveData<>();
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("per_page", perPage);
        ApiManager.getInstance().getApiService().getUsers(map).enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    Users users = response.body();
                    if (users.getUserList() != null)
                        Log.e("user getUserNext", "data pass to presenter");
                    userPresenterInterface.loadMoreToView(users);
                } else {
                    Log.e("user getUserNext", "User list null");
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                //    mutableLiveData.setValue(null);
                Log.e("user getUserNext", "onFailure");
            }
        });

        // return mutableLiveData;
    }

}
