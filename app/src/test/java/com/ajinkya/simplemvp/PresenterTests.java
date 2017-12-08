package com.ajinkya.simplemvp;

import com.ajinkya.simplemvp.model.User;
import com.ajinkya.simplemvp.presenter.UserPresenter;
import com.ajinkya.simplemvp.presenter.UserPresenterImpl;
import com.ajinkya.simplemvp.presenter.ViewNotFoundException;
import com.ajinkya.simplemvp.repository.UserRepository;
import com.ajinkya.simplemvp.view.UserView;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by asbad on 02-12-2017.
 */

public class PresenterTests {
    UserRepository mockUserRepository;
    UserView mockView;
    UserPresenter presenter;
    User user;

    /***
     * runs every times before each test executes
     */
    @Before
    public void setUp() {
        mockUserRepository = mock(UserRepository.class);
        user = new User();
        user.setId(1);
        user.setFirstName("Mighty");
        user.setLastName("Mouse");

        when(mockUserRepository.getUser(anyInt())).thenReturn(user);

        mockView = mock(UserView.class);

        presenter = new UserPresenterImpl(mockUserRepository);
    }

    @Test
    public void noInteractionsWithViewShouldTakePlaceIfUserIsNull() {
        presenter.saveUser();

        //user object not initialized, let verify no interaction take place
        verifyZeroInteractions(mockView);

    }

    @Test
    public void shouldBeAbleToLoadTheUserFromTheRepositoryWhenValidUserIsPresent() {
        when(mockView.getUserId()).thenReturn(1);
        presenter.setView(mockView);

        //verify repository interaction
        verify(mockUserRepository, times(1)).getUser(anyInt());

        //verify repository interaction
        verify(mockView, times(1)).getUserId();
        verify(mockView, times(1)).displayFirstName("Mighty");
        verify(mockView, times(1)).displayLastName("Mouse");
        verify(mockView, never()).showUserNotFoundMessage();
    }

    @Test
    public void shouldShowErrorMessageOnViewWhenUserIsNotPresent() {
        when(mockView.getUserId()).thenReturn(1);

        //return null when ask the repo for user.
        when(mockUserRepository.getUser(anyInt())).thenReturn(null);

        presenter.setView(mockView);

        //verify repository interaction
        verify(mockUserRepository, times(1)).getUser(anyInt());

        //verify view interaction
        verify(mockView, times(1)).getUserId();
        verify(mockView, times(1)).showUserNotFoundMessage();
        verify(mockView, never()).displayFirstName(anyString());
        verify(mockView, never()).displayLastName(anyString());
    }

    @Test
    public void shouldShowErrorMessageDuringSaveIfFirstOrLastNameIsMissing() {
        when(mockView.getUserId()).thenReturn(1);

        //Load the user
        presenter.setView(mockView);

        verify(mockView, times(1)).getUserId();

        //setup the view mock
        when(mockView.getFirstName()).thenReturn("");//

        presenter.saveUser();

        verify(mockView, times(2)).getFirstName();
        verify(mockView, never()).getLastName();
        verify(mockView, times(1)).showUserNameIsRequired();

        //now tell mockView to return a value for first name and an empty last name
        when(mockView.getFirstName()).thenReturn("Foo");
        when(mockView.getLastName()).thenReturn("");

        presenter.saveUser();


        verify(mockView, times(4)).getFirstName();//called 2 times now, once before save user and after it
        verify(mockView, times(2)).getLastName();//called 2 times now, once before
        verify(mockView, times(2)).showUserNameIsRequired();//called 2 times now, once before save user and after it

    }

    @Test
    public void shouldBeAbleToSaveAValidUser() {
        when(mockView.getUserId()).thenReturn(1);

        //load the user
        presenter.setView(mockView);

        verify(mockView, times(1)).getUserId();
        when(mockView.getFirstName()).thenReturn("Foo");
        when(mockView.getLastName()).thenReturn("Bar");

        presenter.saveUser();

        //called two more times in the saveUser call.
        verify(mockView, times(3)).getFirstName();
        verify(mockView, times(3)).getLastName();

        assertThat(user.getFirstName(), is("Foo"));
        assertThat(user.getLastName(), is("Bar"));

        //make sure repository saved the user
        verify(mockUserRepository, times(1)).save(user);

        //make sure that view showed the user saved message
        verify(mockView, times(1)).showUserSavedMessage();

    }


    @Test
    public void shouldLoadUserDetailWhenViewIsSet() {
        presenter.setView(mockView);
        verify(mockUserRepository, times(1)).getUser(anyInt());
        verify(mockView, times(1)).displayFirstName(anyString());
        verify(mockView, times(1)).displayLastName(anyString());
    }

    @Test(expected = ViewNotFoundException.class)
    public void shouldThrowNullPointerExceptionWhenViewIsNull() {
        //null out the view
        presenter.setView(null);

        //try load the screen which will force interaction with the view
        presenter.loadUserDetails();
    }

    @Test
    public void shouldBeAbleToHandleNullFirstName() {
        when(mockView.getUserId()).thenReturn(1);

        //Load the user
        presenter.setView(mockView);

        verify(mockView, times(1)).getUserId();

        //setup the view mock
        when(mockView.getFirstName()).thenReturn(null);//

        presenter.saveUser();

        verify(mockView, times(1)).getFirstName();
        verify(mockView, never()).getLastName();
        verify(mockView, times(1)).showUserNameIsRequired();
    }

    @Test
    public void shouldBeAbleToHandleNullLastName() {
        when(mockView.getUserId()).thenReturn(1);

        //Load the user
        presenter.setView(mockView);

        verify(mockView, times(1)).getUserId();

        //setup the view mock
        when(mockView.getFirstName()).thenReturn("Foo");//
        when(mockView.getLastName()).thenReturn(null);//

        presenter.saveUser();

        verify(mockView, times(2)).getFirstName();
        verify(mockView, times(1)).getLastName();
        verify(mockView, times(1)).showUserNameIsRequired();
    }


}
