package com.example.alex.movieinfoapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.alex.movieinfoapp.MovieDatabaseHelper.MOVIE_TABLE_NAME;

public class MovieDisplay extends AppCompatActivity {
    final Context ctxt = this;
    private ProgressBar spinner;

    Toolbar toolbar;
    ListView moviesListView;
    String snackbarMessage = "All movies cleared";
    MovieQuery query;
    ArrayList<MovieInfo> movieList;
    MovieAdapter movieAdapter;
    FrameLayout frameLayout;
    boolean isTablet;
    String message = "test";
    TextView tv;

    Button addMoviesBtn, deleteAllBtn;
    Drawable posterImage;
    //String poster = "", title= "", actors= "", length= "", description= "", genre= "", rating= "";

    protected static final String ACTIVITY_NAME = "MovieDisplay";

    final static String CLEAR_ALL_MOVIES_QUERY = "DELETE FROM " + MOVIE_TABLE_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_display);
        //set toolbar
        toolbar = findViewById(R.id.toolbar);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);


        //setSupportActionBar(toolbar);
        spinner = (ProgressBar) findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);

        frameLayout = findViewById(R.id.movieFrameLayout);

        isTablet = frameLayout != null;
        Log.i(ACTIVITY_NAME, isTablet + "");

        //initialize widgets
        moviesListView = findViewById(R.id.moviesLV);


        addMoviesBtn = findViewById(R.id.addMovieBtn);
        deleteAllBtn = findViewById(R.id.deleteAllMoviesBtn);

        movieAdapter = new MovieAdapter(this, ctxt);
        moviesListView.setAdapter(movieAdapter);

        addMoviesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                //bundle.putInt("id", v.getId() );//id is the database ID of selected item
                bundle.putBoolean("isTablet", isTablet);

                bundle.putInt("poster", 0);
                bundle.putString("title", "");
                bundle.putString("actors", "");
                bundle.putString("length", "");
                bundle.putString("description", "");
                bundle.putFloat("rating", 0);
                bundle.putString("genre", "");
                bundle.putBoolean("created", false);

                if (isTablet) {
                    MovieFragment fragment = new MovieFragment();
                    fragment.setArguments(bundle);
                    //getFragmentManager().putFragment(bundle,"fragment",fragment);
                    getFragmentManager().beginTransaction().replace(R.id.movieFrameLayout, fragment, "my fragment").addToBackStack(null).commit();
                } else {
                    Intent intent = new Intent(getBaseContext(), MovieDetails.class);
                    intent.putExtras(bundle); //pass the clicked item ID and message to next activity

                    startActivityForResult(intent, 1);
                }
            }
        });

        deleteAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(findViewById(R.id.toolbar), snackbarMessage, Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                /*final int count = cursor .getCount();
                database.execSQL(CLEAR_ALL_MOVIES_QUERY);*/
                movieAdapter.clearDatabase();
            }
        });


        moviesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                movieList = movieAdapter.getMovieList();


                bundle.putInt("id", (int) (movieList.get(position).getId()));//id is the database ID of selected item
                bundle.putString("poster", movieList.get(position).getPosterUrl());
                bundle.putString("title", movieList.get(position).getTitle());
                bundle.putString("actors", movieList.get(position).getActors());
                bundle.putString("length", movieList.get(position).getLength());
                bundle.putString("description", movieList.get(position).getDescription());
                bundle.putFloat("rating", (int) movieList.get(position).getRating());
                bundle.putString("genre", movieList.get(position).getGenre());
                //if on tablet:
                if (isTablet) {
                    MovieFragment fragment = new MovieFragment();
                    fragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.movieFrameLayout, fragment).addToBackStack(null).commit();
                } else {
                    Intent intent = new Intent(MovieDisplay.this, MovieDetails.class);
                    intent.putExtras(bundle); //pass the clicked item ID and message to next activity
                    startActivityForResult(intent, 2);
                }
            }
        });


        Button downloadMovies = (Button) findViewById(R.id.downloadMoviesBtn);
        downloadMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked download");
                query = new MovieQuery(ctxt, movieAdapter, true, spinner);
                query.execute();
            }
        });

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(ACTIVITY_NAME, "in on Activity Result");
        Bundle extras = data.getExtras();
        int id = extras.getInt("id");
        String poster = extras.getString("poster");
        String title = extras.getString("title");
        String actors = extras.getString("actors");
        String length = extras.getString("length");
        String description = extras.getString("description");
        float rating = extras.getFloat("rating");
        String genre = extras.getString("genre");

        if (resultCode == 1) {
            Log.i(ACTIVITY_NAME, "in on result 1");
          /*  Bundle extras = data.getExtras();
            // int id = extras.getInt("id");
            String poster = extras.getString("poster");
            String title = extras.getString("title");
            String actors = extras.getString("actors");
            String length = extras.getString("length");
            String description = extras.getString("description");
            float rating = extras.getFloat("rating");
            String genre = extras.getString("genre");*/

            MovieInfo movie = new MovieInfo(poster, title, actors, length, description, rating, genre);
            movieAdapter.addToMyList(movie);


        } else if (resultCode == 2) {
            /*Log.i(ACTIVITY_NAME, "in on result 2");
            Bundle extras = data.getExtras();
            int id = extras.getInt("id");
            String title = extras.getString("title");*/
            MovieInfo movie = new MovieInfo(id, poster, title, actors, length, description, rating, genre);

            movieAdapter.deleteMovie(movie);

        } else if (resultCode == 3) {


            MovieInfo movie = new MovieInfo(id, poster, title, actors, length, description, rating, genre);

            movieAdapter.updateMovie(movie);
        }
    }

    public boolean onCreateOptionsMenu (Menu m){
        getMenuInflater().inflate(R.menu.movie_toolbar_menu, m );
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi){

        int id = mi.getItemId();

        switch( id ){
            case R.id.menuItemMC:
                //multiple choice app
                break;
            case R.id.menuItemPat:
                // patient form app
                break;
            case R.id.menuItemOC:
                //oc transpo app
                break;
            case R.id.menuStats:

                String max = "";
                String min = "";
                String avg = "";
                float most = 0;
                float least = 4;
                float mean = 0;
                movieList = movieAdapter.getMovieList();
                for(MovieInfo movie : movieList){
                   if(movie.getRating()>most)
                       most = movie.getRating();
                    if(movie.getRating()<least)
                        least = movie.getRating();
                   mean += movie.getRating();
                }
                mean = mean/movieList.size();

                max = Float.toString(most);
                min = Float.toString(least);
                avg = Float.toString(mean);

                AlertDialog.Builder builder2 = new AlertDialog.Builder(ctxt);
                builder2.setMessage("Max rating: "+max+"\nMin rating: "
                        +min+"\nAverage rating: "+avg);
                // Get the layout inflater
                // LayoutInflater inflater = MovieToolbar.this.getLayoutInflater();
                LayoutInflater inflater = (LayoutInflater) ctxt.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                //setContentView(R.layout.movie_custom_layout);
                builder2.setView(inflater.inflate(R.layout.movie_custom_layout, null))
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
        /*        tv.setText(R.string.cancel);*/
                AlertDialog dialog2 = builder2.create();

                dialog2.show();
                break;
            case R.id.menuItemAbout:
                Toast.makeText(getApplicationContext(), "Version 1.0 by Alex Ross",
                        Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }


  /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder2 = new AlertDialog.Builder(ctxt);
            // Get the layout inflater
            LayoutInflater inflater = (LayoutInflater) ctxt.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder2.setView(inflater.inflate(R.layout.movie_custom_layout, null))
                    // Add action buttons
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            AlertDialog alertDialog = (AlertDialog)dialog;
                            TextView messageText = alertDialog.findViewById(R.id.new_message);
                            alertDialog.findViewById(R.id.new_message);
                            String message = "yep";
                            message = messageText.getText().toString();
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            AlertDialog dialog2 = builder2.create();
            dialog2.show();
        }
    });*/

}


