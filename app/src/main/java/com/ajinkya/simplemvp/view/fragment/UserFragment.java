package com.ajinkya.simplemvp.view.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ajinkya.simplemvp.MvpApplication;
import com.ajinkya.simplemvp.R;
import com.ajinkya.simplemvp.presenter.UserPresenter;
import com.ajinkya.simplemvp.view.UserView;

import javax.inject.Inject;

/**
 * A placeholder fragment containing a simple view.
 */
public class UserFragment extends Fragment implements UserView {
    protected EditText userFirstName, userLastName;
    protected Button saveButton;
    public static final String USER_ID = "user_id";
    @Inject
    UserPresenter userPresenter;

    public UserFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MvpApplication) getActivity().getApplication()).getComponent().inject(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_launcher, container, false);
        userFirstName = view.findViewById(R.id.userFirstName);
        userLastName = view.findViewById(R.id.userLastName);
        saveButton = view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPresenter.saveUser();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userPresenter.setView(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        userPresenter.setView(this);
    }

    @Override
    public int getUserId() {
        return getArguments() == null ? 0 : getArguments().getInt(USER_ID, 0);
    }

    @Override
    public void displayFirstName(String name) {
        userFirstName.setText(name);
    }

    @Override
    public void displayLastName(String name) {
        userLastName.setText(name);
    }

    @Override
    public void showUserNotFoundMessage() {
        Toast.makeText(getActivity(), R.string.user_not_found, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showUserSavedMessage() {
        Toast.makeText(getActivity(), R.string.user_saved, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getFirstName() {
        return userFirstName.getText().toString();
    }

    @Override
    public String getLastName() {
        return userLastName.getText().toString();
    }

    @Override
    public void showUserNameIsRequired() {
        Toast.makeText(getActivity(), R.string.user_name_required_message, Toast.LENGTH_SHORT).show();
    }
}
