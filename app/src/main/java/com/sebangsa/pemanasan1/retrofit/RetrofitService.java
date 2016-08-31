package com.sebangsa.pemanasan1.retrofit;

import android.content.Context;
import android.util.Log;

import com.sebangsa.pemanasan1.CommunityActivity;
import com.sebangsa.pemanasan1.FollowerActivity;
import com.sebangsa.pemanasan1.FollowingActivity;
import com.sebangsa.pemanasan1.model.Community;
import com.sebangsa.pemanasan1.model.CommunityWrapper;
import com.sebangsa.pemanasan1.model.User;
import com.sebangsa.pemanasan1.model.UserWrapper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sebangsa on 8/31/16.
 */
public class RetrofitService {
    private final String BASE_URL = "http://hangga.web.id/";
    private SebangsaService service;
    private Context context;

    public RetrofitService(Context context) {
        this.context = context;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(SebangsaService.class);
    }

    public void retrieveUser() {
        String className = context.getClass().getName();
        Call<UserWrapper> call = null;
        if (className.endsWith(".FollowingActivity")) {
            call = service.listUsersFollowing();
        } else if (className.endsWith(".FollowerActivity")) {
            call = service.listUsersFollower();
        }
        runCallBackUser(call, className);
    }

    private void runCallBackUser(Call<UserWrapper> call, final String className) {
        call.enqueue(new Callback<UserWrapper>() {
            @Override
            public void onResponse(Call<UserWrapper> call, Response<UserWrapper> response) {
                List<User> userList = new ArrayList<User>();
                User user = null;
                User.Action action = null;
                User.Avatar avatar = null;
                for (User u : response.body().getUsers()) {
                    Log.i("FOLLOW_USER", u.getUsername() + " : " + u.getName() + " : " + u.getAction().isFollow() + " : " + u.getAvatar().getMedium());
                    user = new User();
                    user.setUsername(u.getUsername());
                    user.setName(u.getName());

                    action = new User.Action();
                    action.setFollow(u.getAction().isFollow());
                    user.setAction(action);

                    avatar = new User.Avatar();
                    avatar.setMedium(u.getAvatar().getMedium());
                    user.setAvatar(avatar);
                    userList.add(user);
                }

                if (className.endsWith(".FollowingActivity")) {
                    FollowingActivity fa = (FollowingActivity) context;
                    fa.setUserListData(userList);
                } else {
                    FollowerActivity fa = (FollowerActivity) context;
                    fa.setUserListData(userList);
                }
            }

            @Override
            public void onFailure(Call<UserWrapper> call, Throwable t) {
                if (className.endsWith(".FollowingActivity")) {
                    FollowingActivity fa = (FollowingActivity) context;
                    fa.setFailureMessage();
                } else {
                    FollowerActivity fa = (FollowerActivity) context;
                    fa.setFailureMessage();
                }
            }
        });
    }

    public void retrieveCommunity() {
        final CommunityActivity ca = (CommunityActivity) context;
        Call<CommunityWrapper> call = service.listCommunity();
        call.enqueue(new Callback<CommunityWrapper>() {
            @Override
            public void onResponse(Call<CommunityWrapper> call, Response<CommunityWrapper> response) {
                List<Community> communityList = new ArrayList<Community>();
                Community community = null;
                Community.Action action = null;
                Community.Avatar avatar = null;
                for (Community c : response.body().getCommunities()) {
                    Log.i("COMMUNITY", c.getName().trim() + " : " + c.getDescription().trim() + " : " + c.getAction().isMember() + " : " + c.getAvatar().getMedium().trim());
                    community = new Community();
                    community.setName(c.getName().trim());
                    community.setDescription(c.getDescription().trim());

                    action = new Community.Action();
                    action.setMember(c.getAction().isMember());
                    community.setAction(action);

                    avatar = new Community.Avatar();
                    avatar.setMedium(c.getAvatar().getMedium().trim());
                    community.setAvatar(avatar);
                    communityList.add(community);
                }
                ca.setCommunityListData(communityList);
            }

            @Override
            public void onFailure(Call<CommunityWrapper> call, Throwable t) {
                ca.setFailureMessage();
            }
        });
    }
}
