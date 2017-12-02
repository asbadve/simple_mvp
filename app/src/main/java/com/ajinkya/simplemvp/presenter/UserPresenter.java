package com.ajinkya.simplemvp.presenter;


import com.ajinkya.simplemvp.view.UserView;

public interface UserPresenter extends LifeCyclePresenter {
    void loadUserDetails();

    void setView(UserView view);

    void saveUser();
}
