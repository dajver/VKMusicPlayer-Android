package com.project.vkmusicplayer.etc;

import android.app.ProgressDialog;
import android.content.Context;

import com.project.vkmusicplayer.R;

/**
 * Created by gleb on 04.03.17.
 */

public class ProgresDialogHelper {
    private static ProgressDialog pd;

    public static void showProgress(Context context) {
        if(context != null) {
            pd = new ProgressDialog(context, R.style.TransparentProgressBarStyle);
            pd.setCancelable(false);
            pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            if(pd != null)
                pd.show();
        }
    }

    public static void dismissProgress() {
        if(pd != null)
            pd.dismiss();
    }
}
