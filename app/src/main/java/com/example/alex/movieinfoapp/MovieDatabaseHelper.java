package com.example.alex.movieinfoapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Alex on 2018-03-03.
 */

public class MovieDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movie_database";
    public static final int VERSION_NUM = 4;
    public static final String MOVIE_TABLE_NAME = "MovieTable";
    public static final String KEY_ID = "id";
    public static final String MOVIE_COL_POSTER = "poster";
    public static final String MOVIE_COL_TITLE = "title";
    public static final String MOVIE_COL_ACTORS = "actors";
    public static final String MOVIE_COL_LENGTH = "length";
    public static final String MOVIE_COL_DESCRIPTION = "description";
    public static final String MOVIE_COL_RATING = "rating";
    public static final String MOVIE_COL_GENRE = "genre";

    public String TABLE_NAME = "MovieTable";

    public final String CLEAR_ALL_MOVIES_QUERY = "DELETE FROM " + MOVIE_TABLE_NAME;
  //  public static final String MOVIE_COL_URL = "url";

    public static final String CREATE_MOVIE_TABLE=
            "CREATE TABLE "+MOVIE_TABLE_NAME + " ( "+
            KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MOVIE_COL_POSTER +" Varchar(120), "
            + MOVIE_COL_TITLE +" Varchar(120), "
            +MOVIE_COL_ACTORS +" Varchar(120),"
            + MOVIE_COL_LENGTH +" Varchar(120),"
            +MOVIE_COL_DESCRIPTION+" Text,"
            + MOVIE_COL_RATING +" Decimal(1,1),"
            +MOVIE_COL_GENRE+" Varchar(50)"
            + ")";

    public MovieDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("MovieDatabaseHelper", "Testing: " + CREATE_MOVIE_TABLE);
        db.execSQL(CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+MOVIE_TABLE_NAME);
        onCreate(db);
        Log.i("MovieDatabaseHelper", "Calling onUpgrade, oldVersion " + oldVersion +" newVersion=" +  newVersion );
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + MOVIE_TABLE_NAME);
        onCreate(db);
        Log.i("MovieDatabaseHelper", "Calling onDowngrade, oldVersion " + oldVersion +" newVersion=" +  newVersion );
    }

    //get database by select statement, return the cusor
    public Cursor getData(String query){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(query,null);
        }
}