package com.project.vkmusicplayer.ui.downloaded.controllers.model;

import io.realm.RealmObject;

/**
 * Created by gleb on 26.02.17.
 */

public class RealmModel extends RealmObject {

    private int id;
    private String filePathUrl;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilePathUrl() {
        return filePathUrl;
    }

    public void setFilePathUrl(String filePathUrl) {
        this.filePathUrl = filePathUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
