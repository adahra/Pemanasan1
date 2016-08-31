package com.sebangsa.pemanasan1;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sebangsa.pemanasan1.adapter.SebangsaRecyclerViewAdapter;
import com.sebangsa.pemanasan1.model.Community;
import com.sebangsa.pemanasan1.model.CommunityWrapper;
import com.sebangsa.pemanasan1.retrofit.SebangsaService;
import com.sebangsa.pemanasan1.ui.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommunityActivity extends AppCompatActivity implements View.OnKeyListener {
    private RecyclerView recView;
    private SebangsaRecyclerViewAdapter adapter;
    private List<Community> communityList;
    private EditText editTextSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);

        recView = (RecyclerView) findViewById(R.id.rec_list);
        recView.setLayoutManager(new LinearLayoutManager(this));
        editTextSearch = (EditText) findViewById(R.id.editText_search);
        editTextSearch.setOnKeyListener(this);

        recView.addItemDecoration(new SimpleDividerItemDecoration(this));

        retrieveCommunity();
        actionBarSetup();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void actionBarSetup() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            android.support.v7.app.ActionBar ab = getSupportActionBar();
            ab.setTitle("enda");
            ab.setSubtitle("Communities");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i("FOLLOWING", query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i("FOLLOWING", newText);
                searchCommunity(newText.toLowerCase().trim());
                return false;
            }

        });

        return true;
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
//                Toast.makeText(getApplicationContext(), "Success Retrieve: " + response.code() + "/" + response.message(), Toast.LENGTH_SHORT).show();
                communityList = new ArrayList<Community>();
                for (Community c : response.body().getCommunities()) {
                    Log.i("COMMUNITY", c.getName().trim() + " : " + c.getDescription().trim() + " : " + c.getAction().isMember() + " : " + c.getAvatar().getMedium().trim());
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

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        Log.i("COMMUNITY", editTextSearch.getText().toString());
        searchCommunity(editTextSearch.getText().toString().toLowerCase().trim());
        return false;
    }


    private void searchCommunity(String query) {
        List<Community> communityListTemp = new ArrayList<Community>();
        for (Community c : communityList) {
            if (c.getName().toLowerCase().contains(query)) {
                communityListTemp.add(c);
            }
        }
        setAdapter(communityListTemp);
    }
}
