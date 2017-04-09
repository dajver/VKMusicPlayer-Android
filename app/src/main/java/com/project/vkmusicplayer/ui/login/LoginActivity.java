package com.project.vkmusicplayer.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.project.vkmusicplayer.R;
import com.project.vkmusicplayer.ui.BaseActivity;

/**
 * Created by gleb on 22.02.17.
 */

public class LoginActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle(getString(R.string.toolbar_signin));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}