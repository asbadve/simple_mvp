package com.ajinkya.simplemvp;

import com.ajinkya.simplemvp.view.fragment.UserFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { AppModule.class })
public interface AppComponent {
    void inject(UserFragment target);
}
