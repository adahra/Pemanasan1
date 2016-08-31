package com.sebangsa.pemanasan1;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String LOG = "MAIN ACTIVITY";
    private Button buttonFollowing, buttonFollower, buttonCommunity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        buttonFollowing = (Button) findViewById(R.id.button_following_MA);
        buttonFollowing.setOnClickListener(this);
        buttonFollower = (Button) findViewById(R.id.button_follower_MA);
        buttonFollower.setOnClickListener(this);
        buttonCommunity = (Button) findViewById(R.id.button_community_MA);
        buttonCommunity.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == buttonFollowing.getId()) {
            Log.i(LOG, "Following");
            Intent i = new Intent(MainActivity.this, FollowingActivity.class);
            startActivity(i);
        } else if (v.getId() == buttonFollower.getId()) {
            Log.i(LOG, "Follower");
            Intent i = new Intent(MainActivity.this, FollowerActivity.class);
            startActivity(i);
        } else {
            Log.i(LOG, "Community");
            Intent i = new Intent(MainActivity.this, CommunityActivity.class);
            startActivity(i);
        }
    }
}
