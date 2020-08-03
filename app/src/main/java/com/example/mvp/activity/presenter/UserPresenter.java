package com.example.mvp.activity.presenter;

import android.util.Log;

import com.example.mvp.activity.model.UserRepository;
import com.example.mvp.activity.view.UserView;
import com.example.mvp.model.Users;

public class UserPresenter implements UserPresenterInterface {
    UserView userView;
    UserRepository userRepository;

    public UserPresenter(UserView userView) {
        this.userView = userView;
        userRepository = new UserRepository(this);
    }

    public void callGetUserApi(int page, int perPage) {
        userRepository.getUser(page, perPage);

    }

    public void callNextUser(int page, int perPage) {
        userRepository.getUserNext(page, perPage);

    }

    @Override
    public void showProgressbar() {
        userView.showProgressBar();
    }

    @Override
    public void hideProgressBar() {
        userView.hideProgressBar();
    }

    @Override
    public void passDataToView(Users usersList) {
        userView.setData(usersList);
    }

    @Override
    public void loadMoreToView(Users usersList) {
        userView.loaMoredData(usersList);
    }
}
