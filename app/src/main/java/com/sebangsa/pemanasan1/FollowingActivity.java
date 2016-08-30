package com.sebangsa.pemanasan1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.sebangsa.pemanasan1.adapter.SebangsaRecyclerViewAdapter;
import com.sebangsa.pemanasan1.model.User;
import com.sebangsa.pemanasan1.model.UserWrapper;
import com.sebangsa.pemanasan1.retrofit.SebangsaService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FollowingActivity extends AppCompatActivity {
    private RecyclerView recView;
    private SebangsaRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);

        recView = (RecyclerView) findViewById(R.id.rec_list);
        recView.setLayoutManager(new LinearLayoutManager(this));

        retrieveFollowing();

        setTitle("Following");

    }

    private void setAdapter(List<User> userList) {
        adapter = new SebangsaRecyclerViewAdapter(userList, this, "User");
        recView.setAdapter(adapter);
    }

    private void retrieveFollowing() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://hangga.web.id/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SebangsaService service = retrofit.create(SebangsaService.class);

        Call<UserWrapper> call = service.listUsers();
        call.enqueue(new Callback<UserWrapper>() {
            @Override
            public void onResponse(Call<UserWrapper> call, Response<UserWrapper> response) {
                Toast.makeText(getApplicationContext(), "Success Retrieve: " + response.code() + "/" + response.message(), Toast.LENGTH_SHORT).show();
                List<User> userList = new ArrayList<User>();
                for (User u : response.body().getUsers()) {
                    Log.i("FOLLOWING", u.getUsername() + " : " + u.getName() + " : " + u.getAction().isFollow() + " : " + u.getAvatar().getMedium());
                    User user = new User();
                    user.setUsername(u.getUsername());
                    user.setName(u.getName());

                    User.Action action = new User.Action();
                    action.setFollow(u.getAction().isFollow());
                    user.setAction(action);

                    User.Avatar avatar = new User.Avatar();
                    avatar.setMedium(u.getAvatar().getMedium());
                    user.setAvatar(avatar);
                    userList.add(user);
                }

                setAdapter(userList);
            }

            @Override
            public void onFailure(Call<UserWrapper> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fail Retrieve", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
