package com.project.vkmusicplayer.ui.downloaded.adapter.task;

import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import io.reactivex.Observable;

import static android.os.Environment.DIRECTORY_MUSIC;

/**
 * Created by gleb on 11/13/17.
 */

public class DownloadRepository {

    public Observable<String> downloadAudioFile(String pathUrl) {
        return Observable.create(observableEmitter -> {
            URL u = null;
            int count;
            try {
                u = new URL(pathUrl);
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

                observableEmitter.onNext(Environment.getExternalStoragePublicDirectory(DIRECTORY_MUSIC) + "/" + getFilenameFromURL(u).getName());
            } catch(FileNotFoundException e) {
                observableEmitter.onError(e);
            } catch (IOException e) {
                observableEmitter.onError(e);
            } finally {
                observableEmitter.onComplete();
            }
        });
    }

    public File getFilenameFromURL(URL url) {
        return new File(url.getPath().toString());
    }
}
