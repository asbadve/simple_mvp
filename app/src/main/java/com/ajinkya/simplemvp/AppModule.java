package com.ajinkya.simplemvp;

import com.ajinkya.simplemvp.presenter.UserPresenter;
import com.ajinkya.simplemvp.presenter.UserPresenterImpl;
import com.ajinkya.simplemvp.repository.InMemoryUserRepositoryImpl;
import com.ajinkya.simplemvp.repository.UserRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    @Provides @Singleton
    public UserRepository provideUserRepository() {
        return new InMemoryUserRepositoryImpl();
    }

    @Provides
    public UserPresenter provideUserPresenter(UserRepository userRepository) {
        return new UserPresenterImpl(userRepository);
    }
}
