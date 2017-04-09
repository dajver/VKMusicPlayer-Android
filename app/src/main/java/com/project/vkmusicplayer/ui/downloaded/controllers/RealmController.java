package com.project.vkmusicplayer.ui.downloaded.controllers;

import android.content.Context;

import com.project.vkmusicplayer.ui.downloaded.controllers.model.RealmModel;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by gleb on 26.02.17.
 */

public class RealmController {

    private Realm realm;

    public RealmController(Context context) {
        RealmConfiguration config = new RealmConfiguration.Builder(context).build();
        realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();
    }

    public void addFavorite(String filePath, String musicTitle) {
        realm.beginTransaction();

        RealmModel realmModel = realm.createObject(RealmModel.class);
        int id = getNextKey();
        realmModel.setId(id);
        realmModel.setFilePathUrl(filePath);
        realmModel.setTitle(musicTitle);

        realm.commitTransaction();
    }

    private int getNextKey() {
        try {
            return realm.where(RealmModel.class).max("id").intValue() + 1;
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public RealmResults<RealmModel> getAllTracks() {
        return realm.where(RealmModel.class).findAll();
    }

    public RealmResults<RealmModel> getByTitle(String title) {
        return realm.where(RealmModel.class).beginGroup()
                .contains("title", title)
                .or()
                .beginsWith("title", title)
                .endGroup()
                .findAll();
    }

    public void removeFavorite(int musicId) {
        realm.beginTransaction();

        RealmResults<RealmModel> rows = realm.where(RealmModel.class).equalTo("id", musicId).findAll();
        rows.clear();

        realm.commitTransaction();
    }
}
