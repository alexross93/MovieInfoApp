package com.example.alex.movieinfoapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


public class MovieDetails extends AppCompatActivity {
    String title;
    protected static final String ACTIVITY_NAME="MovieDetails";
    //Immediately create the fragment and insert it in the framelayout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        Bundle bundle = this.getIntent().getExtras();

        title =bundle.getString("title");

        if (title.equals("")) {
            Log.i(ACTIVITY_NAME,"title is an empty string and id= " + bundle.getInt("id"));
        } else {
            Log.i(ACTIVITY_NAME,"title is not empty, it is :" + title);

        }

        MovieFragment movieFragment = new MovieFragment();
        movieFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().add(R.id.emptyframelayout,movieFragment).commit();

        /*movieFragment.setArguments( bundle );
        manager.beginTransaction().add(R.id.emptyframelayout, movieFragment).commit();*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
