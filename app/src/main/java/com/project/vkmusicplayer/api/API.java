package com.project.vkmusicplayer.api;

import com.project.vkmusicplayer.ui.downloaded.adapter.model.SearchModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by gleb on 22.02.17.
 */

public interface API {

    @GET("api.php?method=get.audio")
    Call<List<List<String>>> getAudio(@Query("ids") String id,
                                      @Query("key") String key);

    @GET("api.php?method=search")
    Call<SearchModel> searchAudio(@Query("q") String query,
                                  @Query("key") String key);

    @GET("api.php?method=by_owner")
    Call<String> getUserMusic(@Query("owner_id") String ownerId,
                                    @Query("key") String key);
}
