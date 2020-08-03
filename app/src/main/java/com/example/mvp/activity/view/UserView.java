package com.example.mvp.activity.view;

import com.example.mvp.model.User;
import com.example.mvp.model.Users;

import java.util.List;

public interface UserView {
   public void showProgressBar();
   public void hideProgressBar();
   public void setData(Users usersList);
   public void loaMoredData(Users usersList);

}
