package com.example.alex.movieinfoapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MovieQuery extends AsyncTask<String, Integer, String> {

        private boolean showLoad;
        String urlString = "http://torunski.ca/CST2335/MovieInfo.xml";

       // final Context c = this;
       // private ProgressBar dialog;
        ProgressBar spinner;

        ListView moviesListView;
        Drawable posterImage;
        String poster="", title = "", actors= "", length= "", description= "", genre= "", rating= "";

        ArrayList<MovieInfo> movieList = new ArrayList<>();
  /*      dialog = (ProgressBar) findViewById(R.id.);
        dialog.setVisibility(View.VISIBLE);*/
        MovieAdapter movieAdapter;

        public MovieQuery(Context c, MovieAdapter adapter, boolean showLoad, ProgressBar spinner) {
            //dialog = new ProgressBar(c);

            this.spinner = spinner;
            this.showLoad = showLoad;
            this.movieAdapter = adapter;
        }

        @Override
        protected void onPreExecute() {

            spinner.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... strings) {
            URL url;
            HttpURLConnection conn = null;
            String tile = "";
            try {
                //String str = strings[0];
                url = new URL(urlString);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.connect();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(conn.getInputStream(), "UTF-8");

                int eventType = parser.getEventType();

                while ((eventType) != XmlPullParser.END_DOCUMENT) {

                    if (parser.getEventType() == XmlPullParser.START_TAG) {

                        String tagname = parser.getName();

                        switch(tagname){
                            case "Title":
                                title = parser.nextText();
                                publishProgress(15);
                                break;
                            case "Actors":
                                actors = parser.nextText();
                                publishProgress(25);
                                break;
                            case "Length":
                                length = parser.nextText();
                                publishProgress(35);
                                break;
                            case "Description":
                                description = parser.nextText();
                                publishProgress(45);
                                break;
                            case "Rating":
                                rating = parser.nextText();
                                publishProgress(55);
                                break;
                            case "Genre":
                                genre = parser.nextText();
                                publishProgress(65);
                                break;
                            case "URL":
                                poster = parser.getAttributeValue(0);
                                publishProgress(75);
                                break;
                        }

                       // posterImage = drawableFromUrl(poster);
                    }else
                        if (parser.getEventType() == XmlPullParser.END_TAG) {
                               String endTagName = parser.getName();
                               if(endTagName.equals("Movie")){
                                   MovieInfo newMovie = new MovieInfo(poster,title, actors, length,
                                           description, Float.parseFloat(rating), genre);
                                   //if (!movieList.contains(newMovie))
                                       movieList.add(newMovie);
                               }
                        }
                           // publishProgress(100);

                    parser.next();
                    eventType = parser.getEventType();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Finished";
        }

        @Override
        protected void onProgressUpdate(Integer ... value){
             spinner.setVisibility(View.VISIBLE);
            spinner.setProgress(value[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            movieAdapter.setMyList(movieList);
            spinner.setVisibility(View.GONE);
        }

/*    public static Drawable LoadImageFromWebOperations(String url) throws IOException {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }


    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }*/

   /* public static Bitmap loadBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;

        try {
            in = new BufferedInputStream(new URL(url).openStream(), IO_BUFFER_SIZE);

            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, IO_BUFFER_SIZE);
            copy(in, out);
            out.flush();

            final byte[] data = dataStream.toByteArray();
            BitmapFactory.Options options = new BitmapFactory.Options();
            //options.inSampleSize = 1;

            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length,options);
        } catch (IOException e) {
            Log.e(TAG, "Could not load Bitmap from: " + url);
        } finally {
            closeStream(in);
            closeStream(out);
        }

        return bitmap;
    }
*/
}






