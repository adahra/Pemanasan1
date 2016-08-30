package com.sebangsa.pemanasan1.retrofit;

import com.sebangsa.pemanasan1.model.CommunityWrapper;
import com.sebangsa.pemanasan1.model.UserWrapper;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by sebangsa on 8/30/16.
 */
public interface SebangsaService {
    @GET("sdp-latihan/following.php")
    Call<UserWrapper> listUsers();

    @GET("sdp-latihan/grouplist.php")
    Call<CommunityWrapper> listCommunity();
}
