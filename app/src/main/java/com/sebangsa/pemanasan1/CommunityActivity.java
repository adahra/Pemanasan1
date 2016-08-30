package com.sebangsa.pemanasan1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.sebangsa.pemanasan1.adapter.SebangsaRecyclerViewAdapter;
import com.sebangsa.pemanasan1.model.Community;
import com.sebangsa.pemanasan1.model.CommunityWrapper;
import com.sebangsa.pemanasan1.model.User;
import com.sebangsa.pemanasan1.retrofit.SebangsaService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommunityActivity extends AppCompatActivity {
    private RecyclerView recView;
    private SebangsaRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);

        recView = (RecyclerView) findViewById(R.id.rec_list);
        recView.setLayoutManager(new LinearLayoutManager(this));

        setTitle("Community");
        retrieveCommunity();
    }

    private void setAdapter(List<Community> communityList) {
        adapter = new SebangsaRecyclerViewAdapter(communityList, this, "Community");
        recView.setAdapter(adapter);
    }

    private void retrieveCommunity() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://hangga.web.id/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SebangsaService service = retrofit.create(SebangsaService.class);

        Call<CommunityWrapper> call = service.listCommunity();
        call.enqueue(new Callback<CommunityWrapper>() {
            @Override
            public void onResponse(Call<CommunityWrapper> call, Response<CommunityWrapper> response) {
                Toast.makeText(getApplicationContext(), "Success Retrieve: " + response.code() + "/" + response.message(), Toast.LENGTH_SHORT).show();
                List<Community> communityList = new ArrayList<Community>();
                for (Community c : response.body().getCommunities()) {
                    Log.i("FOLLOWING", c.getName().trim() + " : " + c.getDescription().trim() + " : " + c.getAction().isMember() + " : " + c.getAvatar().getMedium().trim());
                    Community community = new Community();
                    community.setName(c.getName().trim());
                    community.setDescription(c.getDescription().trim());

                    Community.Action action = new Community.Action();
                    action.setMember(c.getAction().isMember());
                    community.setAction(action);

                    Community.Avatar avatar = new Community.Avatar();
                    avatar.setMedium(c.getAvatar().getMedium().trim());
                    community.setAvatar(avatar);
                    communityList.add(community);
                }

                setAdapter(communityList);
            }

            @Override
            public void onFailure(Call<CommunityWrapper> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fail Retrieve", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
