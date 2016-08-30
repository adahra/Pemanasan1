package com.sebangsa.pemanasan1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sebangsa on 8/30/16.
 */
public class User {
    @Expose
    @SerializedName("username")
    private String username;
    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("action")
    private Action action;

    @Expose
    @SerializedName("avatar")
    private Avatar avatar;

    public static class Action {
        @Expose
        @SerializedName("is_muted")
        private boolean muted;

        @Expose
        @SerializedName("is_follow")
        private boolean follow;

        public boolean isMuted() {
            return muted;
        }

        public void setMuted(boolean muted) {
            this.muted = muted;
        }

        public boolean isFollow() {
            return follow;
        }

        public void setFollow(boolean follow) {
            this.follow = follow;
        }
    }

    public static class Avatar {
        @Expose
        @SerializedName("small")
        private String small;

        @Expose
        @SerializedName("medium")
        private String medium;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }
}
