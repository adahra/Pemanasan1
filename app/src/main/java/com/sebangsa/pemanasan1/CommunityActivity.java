package com.sebangsa.pemanasan1;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.sebangsa.pemanasan1.retrofit.RetrofitService;
import com.sebangsa.pemanasan1.ui.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class CommunityActivity extends AppCompatActivity implements View.OnKeyListener {
    private final String LOG_TAG = "COMMUNITY ACTIVITY";
    private List<Community> x = null;
    private RecyclerView recView;
    private SebangsaRecyclerViewAdapter adapter;
    private List<Community> communityList;
    private EditText editTextSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);
        actionBarSetup();
        editTextSearch = (EditText) findViewById(R.id.editText_search);
        editTextSearch.setOnKeyListener(this);
        recView = (RecyclerView) findViewById(R.id.rec_list);
        recView.setLayoutManager(new LinearLayoutManager(this));
        recView.addItemDecoration(new SimpleDividerItemDecoration(this));
        communityList = new ArrayList<Community>();
        RetrofitService rs = RetrofitService.getRetrofitServiceInstance();
        rs.retrieveCommunity(this);
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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i(LOG_TAG, query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i(LOG_TAG, newText);
                if (communityList.size() > 0) {
                    searchCommunity(newText.toLowerCase().trim());
                }
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        Log.i(LOG_TAG, editTextSearch.getText().toString());
        if (communityList.size() > 0) {
            searchCommunity(editTextSearch.getText().toString().toLowerCase().trim());
        }
        return false;
    }

    private void setAdapter(List<Community> communityList) {
        adapter = new SebangsaRecyclerViewAdapter(communityList, this, "Community");
        recView.setAdapter(adapter);
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

    public void setCommunityListData(List<Community> communityList) {
        this.communityList = communityList;
        setAdapter(communityList);
    }

    public void setFailureMessage() {
        Toast.makeText(getApplicationContext(), "Fail Retrieve", Toast.LENGTH_SHORT).show();
    }
}
