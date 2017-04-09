package com.project.vkmusicplayer.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.project.vkmusicplayer.R;
import com.project.vkmusicplayer.etc.SharedPrefs;
import com.project.vkmusicplayer.ui.BaseFragment;
import com.project.vkmusicplayer.ui.downloaded.DownloadedActivity;
import com.project.vkmusicplayer.ui.login.helper.VKHelper;
import com.project.vkmusicplayer.ui.login.helper.VKResponse;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKScope;
import com.vk.sdk.api.VKError;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by gleb on 22.02.17.
 */

public class LoginFragment extends BaseFragment implements VKResponse {

    private VKHelper vkHelper;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vkHelper = new VKHelper(this, new String[] { VKScope.FRIENDS, VKScope.PHOTOS, VKScope.STATUS });
        if(!TextUtils.isEmpty(SharedPrefs.getToken(context))) {
            startActivity(new Intent(context, DownloadedActivity.class));
            context.finish();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.loginButton)
    public void onVkontakteClick() {
        vkHelper.performSignIn(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        vkHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onVKResult(VKAccessToken res) {
        SharedPrefs.setToken(context, String.valueOf(res.userId));
        startActivity(new Intent(context, DownloadedActivity.class));
        context.finish();
    }

    @Override
    public void onVKError(VKError error) {
        Toast.makeText(context, error.errorReason, Toast.LENGTH_LONG).show();
    }
}
