package com.example.alex.movieinfoapp;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Scroller;

public class MovieFragment extends Fragment {
    Context parent;
    int _id;
    String _poster;
    String _title;
    String _actors;
    String _length;
    String _description;
    float _rating;
    String _genre;
    boolean _created;
    Bundle infoToPass;
    Bundle getInfo;
    boolean isTablet;

    protected static final String ACTIVITY_NAME = "MovieFragment";
    //boolean isTablet;

    //no matter how you got here, the data is in the getArguments
    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        // setRetainInstance(true);
      //  infoToPass = new Bundle();
        getInfo = getArguments();
        parent = getActivity();

        isTablet = getInfo.getBoolean("isTablet");

         _id = getInfo.getInt("id");
        isTablet = getInfo.getBoolean("isTablet");
        _poster = getInfo.getString("poster");
        _title = getInfo.getString("title");
        _actors = getInfo.getString("actors");
        _length = getInfo.getString("length");
        _description = getInfo.getString("description");
        _rating = getInfo.getFloat("rating");
        _genre = getInfo.getString("genre");
        _created = getInfo.getBoolean("created");

        if (getInfo != null) {
            Log.i(ACTIVITY_NAME, "NOT NULL " + _id);
        } else {
            Log.i(ACTIVITY_NAME, "NULL " + _id);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //isTablet = getInfo.getBoolean("isTablet");
        //_id = getInfo.getInt("id");
        _poster = getInfo.getString("poster");
        _title = getInfo.getString("title");
        _actors = getInfo.getString("actors");
        _length = getInfo.getString("length");
        _description = getInfo.getString("description");
        _rating = getInfo.getFloat("rating");
        _genre = getInfo.getString("genre");
        getInfo.getBoolean("created");

        Log.i(ACTIVITY_NAME, "On saved: " + _title);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parent = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        // View view = inflater.inflate(R.layout.activity_message_fragment, container,false);
        View view = inflater.inflate(R.layout.movie_fragment, container, false);
        final EditText title = (EditText) view.findViewById(R.id.Movie_title_Fragment);
        ImageView poster = (ImageView) view.findViewById(R.id.PosterFragemnet);
        final EditText actors = (EditText) view.findViewById(R.id.Movie_Actor_Fragment);
        final EditText genre = (EditText) view.findViewById(R.id.Movie_Gen_Fragemnet);
        final RatingBar rating = (RatingBar) view.findViewById(R.id.ratingBar);
        final EditText length = (EditText) view.findViewById(R.id.Movie_length_fragment);
        final EditText description = (EditText) view.findViewById(R.id.Movie_desc_fragment);


        actors.setScroller(new Scroller(parent));
        actors.setMaxLines(1);
        actors.setVerticalScrollBarEnabled(true);
        actors.setMovementMethod(new ScrollingMovementMethod());
        description.setScroller(new Scroller(parent));
        description.setMaxLines(1);
        description.setVerticalScrollBarEnabled(true);
        description.setMovementMethod(new ScrollingMovementMethod());

        if(_title.equals("Mad Max")){
            poster.setImageResource(R.drawable.madmax1);
        }else if(_title.equals("Mad Max: Fury Road")){
            poster.setImageResource(R.drawable.madmaxfury);
        }else{
            poster.setImageResource(R.drawable.cinema);
        }

        Button addButton = (Button) view.findViewById(R.id.addEntry);
        Button updateButton = (Button) view.findViewById(R.id.update);
        Button deleteButton = (Button) view.findViewById(R.id.delete);
        if (getInfo.getString("title").equals("")&&getInfo.getInt("rating")==0){
            updateButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    _title = title.getText().toString();
                    _actors = actors.getText().toString();
                    _length = length.getText().toString();
                    _description = description.getText().toString();
                    _genre = genre.getText().toString();
                    _rating = (float) rating.getRating();

                    Intent data = new Intent(getActivity(), MovieDisplay.class);
                    data.putExtra("poster", _poster);
                    data.putExtra("title", _title);
                    data.putExtra("actors", _actors);
                    data.putExtra("length", _length);
                    data.putExtra("description", _description);
                    data.putExtra("genre", _genre);
                    data.putExtra("rating", _rating);

                    Log.i(ACTIVITY_NAME, "Button add title: " + _title + " GENRE: " + _genre);
                    if (isTablet) {
                        getActivity().setResult(1, data);
                        getActivity().onBackPressed();
                    } else {            //this is a phone:
                        getActivity().setResult(1, data);
                        getActivity().finish();
                    }
                }
            });

        } else {

            addButton.setVisibility(View.GONE);

            title.setText(_title);
            actors.setText(_actors);
            length.setText(_length);
            description.setText(_description);
            genre.setText(_genre);
            rating.setRating(_rating);

            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    _title = title.getText().toString();
                    _actors = actors.getText().toString();
                    _length = length.getText().toString();
                    _description = description.getText().toString();
                    _genre = genre.getText().toString();
                    _rating = (float) rating.getRating();


                    Intent data = new Intent(getActivity(), MovieDisplay.class);
                   // data.putExtra("id", _id);
                    //data.putExtra("poster", _poster);
                    data.putExtra("title", title.getText().toString());
                    data.putExtra("actors", actors.getText().toString());
                    data.putExtra("length", length.getText().toString());
                    data.putExtra("description", description.getText().toString());
                    data.putExtra("genre", genre.getText().toString());
                    data.putExtra("rating", (float) rating.getRating());
                    if (!isTablet) {
                        getActivity().setResult(3, data);
                        getActivity().onBackPressed();
                    } else {            //this is a phone:
                        getActivity().setResult(3, data);
                        getActivity().finish();
                    }
                }
            });


            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    _title = title.getText().toString();
                    _actors = actors.getText().toString();
                    _length = length.getText().toString();
                    _description = description.getText().toString();
                    _genre = genre.getText().toString();
                    _rating = (float) rating.getRating();

                    Intent data = new Intent(getActivity(), MovieDisplay.class);
                   // data.putExtra("id", _id);
                   // data.putExtra("poater", _poster);
                    data.putExtra("title", title.getText().toString());
                    data.putExtra("actors", actors.getText().toString());
                    data.putExtra("length", length.getText().toString());
                    data.putExtra("description", description.getText().toString());
                    data.putExtra("genre", genre.getText().toString());
                    data.putExtra("rating", (float) rating.getRating());

                    Log.i(ACTIVITY_NAME, "In on delete: " + _title + " id: " + _id + " poster: " + _poster);
                        //this is a phone:
                        getActivity().setResult(2, data);
                        getActivity().finish();
                }
            });

        }
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
