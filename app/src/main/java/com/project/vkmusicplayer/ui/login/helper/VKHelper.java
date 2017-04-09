package com.project.vkmusicplayer.ui.login.helper;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

/**
 * Created by gleb on 22.02.17.
 */

public class VKHelper {

    private VKResponse vkResponse;
    private String[] scope;

    public VKHelper(VKResponse vkResponse, String[] scope) {
        if (vkResponse == null)
            throw new IllegalArgumentException("VKResponse listener cannot be null.");
        this.vkResponse = vkResponse;
        this.scope = scope;
    }

    public void performSignIn(Activity activity) {
        VKSdk.login(activity, scope);
    }

    /**
     * Perform vk login. This method should be called when you are signing in from
     * fragment.<p>
     * This method should generally call when user clicks on "Sign in with vk" button.
     *
     * @param fragment caller fragment.
     */
    public void performSignIn(Fragment fragment) {
        VKSdk.login(fragment.getActivity(), new String[] { VKScope.FRIENDS, VKScope.PHOTOS, VKScope.STATUS });
    }

    private VKCallback<VKAccessToken> callback = new VKCallback<VKAccessToken>() {
        @Override
        public void onResult(VKAccessToken res) {
            vkResponse.onVKResult(res);
        }

        @Override
        public void onError(VKError error) {
            vkResponse.onVKError(error);
        }
    };

    /**
     * This method handles onActivityResult callbacks from fragment or activity.
     *
     * @param requestCode request code received.
     * @param resultCode  result code received.
     * @param data        Data intent.
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, callback)) {
            VKSdk.onActivityResult(requestCode, resultCode, data, callback);
        }
    }
}
