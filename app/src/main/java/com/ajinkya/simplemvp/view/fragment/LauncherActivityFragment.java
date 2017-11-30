package com.ajinkya.simplemvp.view.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ajinkya.simplemvp.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class LauncherActivityFragment extends Fragment {
    protected EditText userFirstName, userLastName;
    protected Button saveButton;
    public static final String USER_ID = "user_id";

    public LauncherActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_launcher, container, false);
        userFirstName = view.findViewById(R.id.userFirstName);
        userLastName = view.findViewById(R.id.userLastName);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
