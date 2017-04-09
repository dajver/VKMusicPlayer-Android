package com.project.vkmusicplayer.ui.downloaded.adapter.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gleb on 22.02.17.
 */

public class SearchModel {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("ownerId")
    @Expose
    private Integer ownerId;
    @SerializedName("albumId")
    @Expose
    private String albumId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("list")
    @Expose
    private List<List<String>> list = new ArrayList<List<String>>();
    @SerializedName("hasMore")
    @Expose
    private Boolean hasMore;
    @SerializedName("nextOffset")
    @Expose
    private Integer nextOffset;
    @SerializedName("totalCount")
    @Expose
    private Integer totalCount;
    @SerializedName("totalCountHash")
    @Expose
    private String totalCountHash;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<List<String>> getList() {
        return list;
    }

    public void setList(List<List<String>> list) {
        this.list = list;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

    public Integer getNextOffset() {
        return nextOffset;
    }

    public void setNextOffset(Integer nextOffset) {
        this.nextOffset = nextOffset;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public String getTotalCountHash() {
        return totalCountHash;
    }

    public void setTotalCountHash(String totalCountHash) {
        this.totalCountHash = totalCountHash;
    }

}
