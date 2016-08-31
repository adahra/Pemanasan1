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
import com.sebangsa.pemanasan1.model.User;
import com.sebangsa.pemanasan1.model.UserWrapper;
import com.sebangsa.pemanasan1.retrofit.SebangsaService;
import com.sebangsa.pemanasan1.ui.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FollowingActivity extends AppCompatActivity implements View.OnKeyListener {
    private RecyclerView recView;
    private SebangsaRecyclerViewAdapter adapter;
    private EditText editTextSearch;
    private List<User> userList;
    private final String LOG = "FOLLOWING ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);

        recView = (RecyclerView) findViewById(R.id.rec_list);
        recView.setLayoutManager(new LinearLayoutManager(this));
        editTextSearch = (EditText) findViewById(R.id.editText_search);
        editTextSearch.setOnKeyListener(this);

        recView.addItemDecoration(new SimpleDividerItemDecoration(this));
        retrieveFollowing();

        actionBarSetup();
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
                Log.i(LOG, query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i(LOG, newText);
                searchUser(newText.toLowerCase().trim());
                return false;
            }

        });
        return true;
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

        Call<UserWrapper> call = service.listUsersFollowing();
        call.enqueue(new Callback<UserWrapper>() {
            @Override
            public void onResponse(Call<UserWrapper> call, Response<UserWrapper> response) {
               userList = new ArrayList<User>();
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
