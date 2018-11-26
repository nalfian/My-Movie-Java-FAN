package com.example.toshiba.mymovie.view.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toshiba.mymovie.R;
import com.example.toshiba.mymovie.db.MovieHelper;
import com.example.toshiba.mymovie.model.ResultsItem;
import com.squareup.picasso.Picasso;

import static com.example.toshiba.mymovie.db.DatabaseContract.MovieColumns.CONTENT_URI;
import static com.example.toshiba.mymovie.db.DatabaseContract.MovieColumns.ID;
import static com.example.toshiba.mymovie.db.DatabaseContract.MovieColumns.ID_MOVIE;
import static com.example.toshiba.mymovie.db.DatabaseContract.MovieColumns.OVERVIEW;
import static com.example.toshiba.mymovie.db.DatabaseContract.MovieColumns.POSTER_PATH;
import static com.example.toshiba.mymovie.db.DatabaseContract.MovieColumns.REALESE_DATE;
import static com.example.toshiba.mymovie.db.DatabaseContract.MovieColumns.TITLE;

public class DetailActivity extends AppCompatActivity {

    private ResultsItem resultsItem;
    private TextView tvName, tvDesc, tvDate;
    private ImageView ivMovie, ivFav;
    private boolean isFavorite;
    private MovieHelper movieHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        resultsItem = getIntent().getParcelableExtra("movie");
        getSupportActionBar().setTitle(resultsItem.getTitle());

        initView();
        initEvent();
        loadDataFavorite();
    }

    private void loadDataFavorite() {
        movieHelper = new MovieHelper(this);
        movieHelper.open();
        Cursor cursor = getContentResolver().query(
                Uri.parse(CONTENT_URI + "/" + resultsItem.getId()),
                null,
                null,
                null,
                null);

        if (cursor != null) {
            if (cursor.getCount() != 0) {
                isFavorite = true;
            }
            cursor.close();
            movieHelper.close();
        }
        setFavoriteButton();
    }

    private void initEvent() {
        tvName.setText(resultsItem.getTitle());
        tvDesc.setText(resultsItem.getOverview());
        tvDate.setText(resultsItem.getReleaseDate());
        Picasso.get()
                .load("http://image.tmdb.org/t/p/w500" + resultsItem.getPosterPath())
                .into(ivMovie);


        ivFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFavorite) deleteMyFavoriteMovie();
                else saveMyFavoriteMovie();
            }
        });
    }

    private void deleteMyFavoriteMovie() {
        isFavorite = false;
        setFavoriteButton();
        getContentResolver().delete(Uri.parse(CONTENT_URI + "/" + resultsItem.getId()), null, null);
        Toast.makeText(this, "Menghapus dari favorit", Toast.LENGTH_SHORT).show();
    }

    private void setFavoriteButton() {
        if (isFavorite) {
            ivFav.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_black_24dp));
        } else {
            ivFav.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_favorite_border_black_24dp));
        }
    }

    private void saveMyFavoriteMovie() {
        isFavorite = true;
        setFavoriteButton();
        ContentValues values = new ContentValues();
        values.put(ID, resultsItem.getId());
        values.put(ID_MOVIE, resultsItem.getId());
        values.put(TITLE, resultsItem.getTitle());
        values.put(OVERVIEW, resultsItem.getOverview());
        values.put(REALESE_DATE, resultsItem.getReleaseDate());
        values.put(POSTER_PATH, resultsItem.getPosterPath());
        getContentResolver().insert(CONTENT_URI, values);
        Toast.makeText(this, "Menambah ke favorit", Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        tvName = findViewById(R.id.tvName);
        tvDate = findViewById(R.id.tvDate);
        tvDesc = findViewById(R.id.tvDesc);
        ivMovie = findViewById(R.id.ivMovie);
        ivFav = findViewById(R.id.ivFav);
    }
}
