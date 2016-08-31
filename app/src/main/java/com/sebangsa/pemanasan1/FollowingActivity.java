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
import com.sebangsa.pemanasan1.model.User;
import com.sebangsa.pemanasan1.retrofit.RetrofitService;
import com.sebangsa.pemanasan1.ui.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class FollowingActivity extends AppCompatActivity implements View.OnKeyListener {
    private final String LOG_TAG = "FOLLOWING ACTIVITY";
    private RecyclerView recView;
    private SebangsaRecyclerViewAdapter adapter;
    private EditText editTextSearch;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);
        recView = (RecyclerView) findViewById(R.id.rec_list);
        recView.setLayoutManager(new LinearLayoutManager(this));
        editTextSearch = (EditText) findViewById(R.id.editText_search);
        editTextSearch.setOnKeyListener(this);
        recView.addItemDecoration(new SimpleDividerItemDecoration(this));
        actionBarSetup();
        RetrofitService retrofitService = new RetrofitService(this);
        retrofitService.retrieveUser();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void actionBarSetup() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            android.support.v7.app.ActionBar ab = getSupportActionBar();
            ab.setTitle("dennywidyatmokoasli");
            ab.setSubtitle("Following");
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
                searchUser(newText.toLowerCase().trim());
                return false;
            }

        });
        return true;
    }

    public void setUserListData(List<User> userList) {
        this.userList = userList;
        setAdapter(userList);
    }

    public void setFailureMessage() {
        Toast.makeText(getApplicationContext(), "Fail Retrieve", Toast.LENGTH_SHORT).show();
    }

    private void setAdapter(List<User> userList) {
        adapter = new SebangsaRecyclerViewAdapter(userList, this, "User");
        recView.setAdapter(adapter);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        searchUser(editTextSearch.getText().toString().toLowerCase().trim());
        return false;
    }

    private void searchUser(String query) {
        List<User> userListTemp = new ArrayList<User>();
        for (User u : userList) {
            if (u.getUsername().toLowerCase().contains(query)) {
                userListTemp.add(u);
            }
        }
        setAdapter(userListTemp);
    }
}
