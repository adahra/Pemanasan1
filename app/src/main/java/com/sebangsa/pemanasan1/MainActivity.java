package com.sebangsa.pemanasan1;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonFollowing, buttonFollower, buttonCommunity;
    private final String LOG = "MAIN ACTIVITY";

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
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
