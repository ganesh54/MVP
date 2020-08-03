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
    String TAG = "UserRepository";

    public UserRepository(UserPresenterInterface userPresenterInterface) {
        this.userPresenterInterface = userPresenterInterface;
    }

    public void getUser(int page, int perPage) {
        userPresenterInterface.showProgressbar();
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("per_page", perPage);
        ApiManager.getInstance().getApiService().getUsers(map).enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    userPresenterInterface.hideProgressBar();
                    Users users = response.body();
                    if (users.getUserList() != null)
                    userPresenterInterface.passDataToView(users);
                } else {
                    Log.e(TAG, "User list null");
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                userPresenterInterface.hideProgressBar();
                Log.e(TAG, "User list null");
            }
        });
    }

    public void getUserNext(int page, int perPage) {
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("per_page", perPage);
        ApiManager.getInstance().getApiService().getUsers(map).enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.isSuccessful() && response.code() == 200 && response.body() != null) {
                    Users users = response.body();
                    if (users.getUserList() != null)
                        userPresenterInterface.loadMoreToView(users);
                } else {
                    Log.e(TAG, "User list null");
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Log.e(TAG, "onFailure");
            }
        });
    }
}
