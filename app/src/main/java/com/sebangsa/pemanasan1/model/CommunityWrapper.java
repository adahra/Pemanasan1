package com.sebangsa.pemanasan1.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sebangsa on 8/30/16.
 */
public class CommunityWrapper {
    @SerializedName(value = "groups")
    public List<Community> communities;

    public List<Community> getCommunities() {
        return communities;
    }

    public void setCommunities(List<Community> communities) {
        this.communities = communities;
    }
}
