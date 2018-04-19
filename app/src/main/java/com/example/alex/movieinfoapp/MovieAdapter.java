package com.example.alex.movieinfoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.example.alex.movieinfoapp.MovieDatabaseHelper.KEY_ID;
import static com.example.alex.movieinfoapp.MovieDatabaseHelper.MOVIE_COL_ACTORS;
import static com.example.alex.movieinfoapp.MovieDatabaseHelper.MOVIE_COL_DESCRIPTION;
import static com.example.alex.movieinfoapp.MovieDatabaseHelper.MOVIE_COL_GENRE;
import static com.example.alex.movieinfoapp.MovieDatabaseHelper.MOVIE_COL_LENGTH;
import static com.example.alex.movieinfoapp.MovieDatabaseHelper.MOVIE_COL_POSTER;
import static com.example.alex.movieinfoapp.MovieDatabaseHelper.MOVIE_COL_RATING;
import static com.example.alex.movieinfoapp.MovieDatabaseHelper.MOVIE_COL_TITLE;

/**
 *
 */

public class MovieAdapter extends BaseAdapter {

    private Context context;
    ArrayList<MovieInfo> movieList;

    private static int count = 0;

    MovieAdapter adapter;

    ImageView poster;
    TextView title, genre, rating;
    ListView moviesListView;

    MovieDatabaseHelper dbHelper;
    Cursor cursor;
    SQLiteDatabase database;
    Bundle info;
    MovieAdapter movieAdapter;

    MovieDisplay movieDisplay;
    protected static final String ACTIVITY_NAME="MovieAdapter";


    public MovieAdapter(Context context) {
        dbHelper = new MovieDatabaseHelper(context);
        this.context = context;
    }

    public MovieAdapter(MovieDisplay movieDisplay, Context context) {
        this.context = context;
        this.movieDisplay = movieDisplay;

        dbHelper = new MovieDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();

        //if(database==null)
        //movieAdapter = new MovieAdapter( this );
        movieList = new ArrayList<MovieInfo>();
        info = new Bundle();

        String selectQuery = "SELECT  * FROM " +  dbHelper.MOVIE_TABLE_NAME;
        cursor = dbHelper.getData(selectQuery);

        String[] allColumns = {MovieDatabaseHelper.KEY_ID, MovieDatabaseHelper.MOVIE_COL_POSTER,
                MovieDatabaseHelper.MOVIE_COL_TITLE, MovieDatabaseHelper.MOVIE_COL_ACTORS,
                MovieDatabaseHelper.MOVIE_COL_LENGTH, MovieDatabaseHelper.MOVIE_COL_DESCRIPTION,
                MovieDatabaseHelper.MOVIE_COL_RATING, MovieDatabaseHelper.MOVIE_COL_GENRE};
        cursor = database.query(dbHelper.MOVIE_TABLE_NAME, allColumns, null, null, null, null, null);
        cursor.moveToFirst();

        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
            String posterUrl = cursor.getString( cursor.getColumnIndex(MOVIE_COL_POSTER) );
            String title = cursor.getString( cursor.getColumnIndex(MOVIE_COL_TITLE) );
            String actors = cursor.getString( cursor.getColumnIndex(MOVIE_COL_ACTORS) );
            String length = cursor.getString( cursor.getColumnIndex(MOVIE_COL_LENGTH) );
            String description = cursor.getString( cursor.getColumnIndex(MOVIE_COL_DESCRIPTION) );
            float rating = cursor.getInt( cursor.getColumnIndex(MOVIE_COL_RATING) );
            String genre = cursor.getString( cursor.getColumnIndex(MOVIE_COL_GENRE) );
            movieList.add(new MovieInfo(id,posterUrl,title,actors,length,description,rating,genre));
        }
    }

    @Override
    public int getCount() {
        return  movieList.size();
    }

    @Override
    public Object getItem(int position) {
        return movieList.get(position);

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = layoutInflater.inflate(R.layout.movie_row,null);
        //set widgets for view
        poster = view.findViewById(R.id.PosterImg);
        title = view.findViewById(R.id.Movie_title);
        genre = view.findViewById(R.id.Movie_Gen);
        rating = view.findViewById(R.id.Movie_rate);

        //set info for widgets
        MovieInfo movieInfo= movieList.get(position);

        //RetrievePosterTask post = new RetrievePosterTask(context,poster,movieInfo.getPosterUrl());

        if(movieInfo!=null) {
          //  if (movieInfo.getPosterUrl() != null && movieInfo.getPosterUrl().isEmpty() == false)
                if(movieInfo.getTitle().equals("Mad Max")){
                    poster.setImageResource(R.drawable.madmax1);
                }else if(movieInfo.getTitle().equals("Mad Max: Fury Road")){
                    poster.setImageResource(R.drawable.madmaxfury);
                }else{
                    poster.setImageResource(R.drawable.cinema);
                }
                // post.execute();
                title.setText(movieInfo.getTitle());
                genre.setText("Genre: " + movieInfo.getGenre());
                rating.setText("Rating: " + String.valueOf(movieInfo.getRating()));
        }
        return view;
    }

    public void setMyList(ArrayList myListOfObjects) {
        for(Object o : myListOfObjects){
            addToMyList((MovieInfo) o);
        }
    }

    public void addToMyList(MovieInfo movie) {
        if(movieList.size()>0) {
            for (MovieInfo mv : movieList) {
                if (movie.getTitle().equals(mv.getTitle())) {
                    return;
                }
            }
        }
        movieList.add(movie);
        ContentValues cv = new ContentValues();
        //cv.put(dbHelper.KEY_ID, extras.getString("title"));
        Log.i(ACTIVITY_NAME,"movie id!: " + movie.getId());

        //cv.put(KEY_ID, count);
        cv.put(MOVIE_COL_POSTER, movie.getPosterUrl());
        cv.put(MOVIE_COL_TITLE,movie.getTitle());
        cv.put(MOVIE_COL_ACTORS,movie.getActors());
        cv.put(MOVIE_COL_DESCRIPTION,movie.getDescription());
        cv.put(MOVIE_COL_LENGTH,movie.getLength());
        cv.put(MOVIE_COL_RATING,movie.getRating());
        cv.put(MOVIE_COL_GENRE,movie.getGenre());

        database.insert(dbHelper.TABLE_NAME, null, cv);
        count++;
        //setMyList(movieList);
        this.notifyDataSetChanged();

        //Log.i(ACTIVITY_NAME,"mv id: " + database.rawQuery("SELECT * FROM " + dbHelper.TABLE_NAME),null);

        String toastString = "Movie database updated";
        Toast toast = Toast.makeText(context, toastString, Toast.LENGTH_LONG);
        toast.show();
    }

    public void clearDatabase(){
        database.delete(dbHelper.TABLE_NAME,null,null);
        //database.execSQL("delete * from " + dbHelper.TABLE_NAME);
        movieList.clear();
        this.notifyDataSetChanged();


    }

    public void deleteMovie(MovieInfo movie){

        for(MovieInfo m : movieList)
            if(m.getTitle().toString().equals(movie.getTitle().toString())) {
                database.delete(dbHelper.TABLE_NAME, "title=?", new String[]{movie.getTitle()});
                movieList.remove(m);
            }
        setMyList(movieList);
        this.notifyDataSetChanged();
    }

    public void updateMovie(MovieInfo movie){
        Log.i(ACTIVITY_NAME,"movie id: " + movie.getId());
        int index = 0;
        if(movieList.size()>0) {
            for (MovieInfo mv : movieList) {
                Log.i(ACTIVITY_NAME,"mv id: " + mv.getId());

                if (movie.getId()==(mv.getId())) {

                    Log.i(ACTIVITY_NAME,"movie: " + movie.getTitle()
                            + " mv: " + mv.getTitle());

                    movieList.set(index,movie);
                    break;
                }
                index++;
            }
        }

        ContentValues cv = new ContentValues();
        cv.put(MOVIE_COL_POSTER, movie.getPosterUrl());
        cv.put(MOVIE_COL_TITLE,movie.getTitle());
        cv.put(MOVIE_COL_ACTORS,movie.getActors());
        cv.put(MOVIE_COL_DESCRIPTION,movie.getDescription());
        cv.put(MOVIE_COL_LENGTH,movie.getLength());
        cv.put(MOVIE_COL_RATING,movie.getRating());
        cv.put(MOVIE_COL_GENRE,movie.getGenre());

        database.update(dbHelper.TABLE_NAME,  cv,"id="+movie.getId(),null);

        setMyList(movieList);
        this.notifyDataSetChanged();
    }

    public ArrayList<MovieInfo> getMovieList(){
        return this.movieList;
    }


    public static Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(x);
    }

}

   /* class RetrievePosterTask extends AsyncTask<String, Void, Drawable> {
        ImageView _poster;
        String _url;
        public RetrievePosterTask(Context ctxt, ImageView poster, String url) {

            _poster = poster;
            _url = url;
        }

        Bitmap x;
        private Exception exception;
        @Override
        protected Drawable doInBackground(String... urls) {
            HttpURLConnection connection;

            try {
                connection = (HttpURLConnection) new URL(_url).openConnection();
                connection.connect();
                InputStream input = connection.getInputStream();
                connection.disconnect();
                x = BitmapFactory.decodeStream(input);
                return  new BitmapDrawable(ThumbnailUtils.extractThumbnail(x, 150, 150));

            } catch (Exception e) {
                this.exception = e;

                return null;
            } finally {
            }
        }

        protected void onPostExecute(Drawable poster) {
            _poster.setImageDrawable(poster);
        }
    }
*/
