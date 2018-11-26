package id.co.gitsolution.mymoviefav.view;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.co.gitsolution.mymoviefav.R;
import id.co.gitsolution.mymoviefav.adapter.AMovie;
import id.co.gitsolution.mymoviefav.model.ResultsItem;

import static id.co.gitsolution.mymoviefav.db.DatabaseContract.MovieColumns.CONTENT_URI;
import static id.co.gitsolution.mymoviefav.db.DatabaseContract.MovieColumns.ID_MOVIE;
import static id.co.gitsolution.mymoviefav.db.DatabaseContract.MovieColumns.OVERVIEW;
import static id.co.gitsolution.mymoviefav.db.DatabaseContract.MovieColumns.POSTER_PATH;
import static id.co.gitsolution.mymoviefav.db.DatabaseContract.MovieColumns.REALESE_DATE;
import static id.co.gitsolution.mymoviefav.db.DatabaseContract.MovieColumns.TITLE;

public class MainActivity extends AppCompatActivity {

    private List<ResultsItem> movieList = new ArrayList<>();
    private RecyclerView rvMoview;
    private AMovie aMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        loadDataFavorite();
    }

    private void loadDataFavorite() {
        Cursor cursor = getContentResolver().query(CONTENT_URI,
                null,
                null,
                null,
                null);

        if (cursor != null) {
            cursor.moveToFirst();
            ResultsItem fav;
            if (cursor.getCount() > 0) {
                do {
                    fav = new ResultsItem();
                    fav.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID_MOVIE)));
                    fav.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                    fav.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                    fav.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));
                    fav.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(REALESE_DATE)));
                    fav.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));

                    movieList.add(fav);
                    cursor.moveToNext();

                } while (!cursor.isAfterLast());
            }
            cursor.close();
            aMovie.notifyDataSetChanged();
        }
    }

    private void initView() {
        rvMoview = findViewById(R.id.rvMoview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvMoview.setLayoutManager(linearLayoutManager);
        rvMoview.setNestedScrollingEnabled(false);
        aMovie = new AMovie(this, movieList);
        rvMoview.setAdapter(aMovie);
    }
}
