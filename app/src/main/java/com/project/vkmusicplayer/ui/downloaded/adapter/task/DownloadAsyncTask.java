package com.project.vkmusicplayer.ui.downloaded.adapter.task;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import com.project.vkmusicplayer.etc.ProgresDialogHelper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import static android.os.Environment.DIRECTORY_MUSIC;

/**
 * Created by gleb on 03.03.17.
 */

public class DownloadAsyncTask extends AsyncTask<String, Void, String> {

    private OnAudioFileDownloaded onAudioFileDownloaded;
    private Context context;

    public DownloadAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ProgresDialogHelper.showProgress(context);
    }

    private String downloadFile(String url) {
        URL u = null;
        int count;
        try {
            u = new URL(url);
            URLConnection conection = u.openConnection();
            conection.connect();
            InputStream input = new BufferedInputStream(u.openStream(), 8192);
            OutputStream output = new FileOutputStream(Environment.getExternalStoragePublicDirectory(DIRECTORY_MUSIC) + "/" + getFilenameFromURL(u).getName());

            byte data[] = new byte[1024];
            while ((count = input.read(data)) != -1) {
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Environment.getExternalStoragePublicDirectory(DIRECTORY_MUSIC) + "/" + getFilenameFromURL(u).getName();
    }

    public File getFilenameFromURL(URL url) {
        return new File(url.getPath().toString());
    }

    @Override
    protected String doInBackground(String... strings) {
        return downloadFile(strings[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        ProgresDialogHelper.dismissProgress();
        onAudioFileDownloaded.downloadedFilePath(result);
    }

    public DownloadAsyncTask setOnAudioFileDownloaded(OnAudioFileDownloaded onAudioFileDownloaded) {
        this.onAudioFileDownloaded = onAudioFileDownloaded;
        return this;
    }

    public interface OnAudioFileDownloaded {
        void downloadedFilePath(String path);
    }
}
