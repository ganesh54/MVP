package com.example.mvp.activity.presenter;

import com.example.mvp.model.User;
import com.example.mvp.model.Users;

import java.util.List;

public interface UserPresenterInterface {
    void showProgressbar();
    void hideProgressBar();
    void passDataToView(Users usersList);
    void loadMoreToView(Users usersList);


}
