package com.project.vkmusicplayer.ui.login.helper;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.api.VKError;

/**
 * Created by gleb on 22.02.17.
 */

public interface VKResponse {
    void onVKResult(VKAccessToken res);
    void onVKError(VKError error);
}
