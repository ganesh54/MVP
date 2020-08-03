package com.example.mvp.network;



import com.example.mvp.model.Users;
import com.example.mvp.utility.Constants;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiService {

    @GET(Constants.USERS)
    Call<Users> getUsers(@QueryMap Map<String, Object> queryMap);
}
