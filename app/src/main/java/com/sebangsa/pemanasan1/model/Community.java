package com.sebangsa.pemanasan1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sebangsa on 8/30/16.
 */
public class Community {
    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("description")
    private String description;

    @Expose
    @SerializedName("action")
    private Action action;

    @Expose
    @SerializedName("avatar")
    private Avatar avatar;

    @Expose
    @SerializedName("statistic")
    private Statistic statistic;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public void setStatistic(Statistic statistic) {
        this.statistic = statistic;
    }

    public static class Action {
        @Expose
        @SerializedName("is_member")
        private boolean member;

        public boolean isMember() {
            return member;
        }

        public void setMember(boolean member) {
            this.member = member;
        }
    }

    public static class Avatar {

        @Expose
        @SerializedName("medium")
        private String medium;

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }
    }

    public static class Statistic {

        @Expose
        @SerializedName("user")
        private int user;

        public int getUser() {
            return user;
        }

        public void setUser(int user) {
            this.user = user;
        }
    }
}
