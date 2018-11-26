package com.example.toshiba.mymovie.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.toshiba.mymovie.R;
import com.example.toshiba.mymovie.adapter.AMovie;
import com.example.toshiba.mymovie.model.ResponseMovie;
import com.example.toshiba.mymovie.model.ResultsItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CariActivity extends AppCompatActivity {

    private List<ResultsItem> movieList = new ArrayList<>();
    private RecyclerView rvMoview;
    private SearchView searchView;
    private AMovie aMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cari_activity);

        initView();
        initEvent();
        getSearchMoview("america");
    }

    private void initEvent() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getSearchMoview(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getSearchMoview(newText);
                return false;
            }
        });
    }

    private void getSearchMoview(String query) {
        Locale locale = getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (locale.toString().equalsIgnoreCase("in_id")) {
            language = "id";
        }
        AndroidNetworking.get("https://api.themoviedb.org/3/search/movie?api_key=221fbf2bcc939e0de03af53af4e1744a")
                .addQueryParameter("language", language)
                .addQueryParameter("query", "" + query)
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(ResponseMovie.class, new ParsedRequestListener<ResponseMovie>() {
                    @Override
                    public void onResponse(ResponseMovie response) {
                        movieList.clear();
                        movieList.addAll(response.getResults());
                        aMovie.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getBaseContext(), getString(R.string.failure), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initView() {
        searchView = findViewById(R.id.svMoview);
        rvMoview = findViewById(R.id.rvMoview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvMoview.setLayoutManager(linearLayoutManager);
        rvMoview.setNestedScrollingEnabled(false);
        aMovie = new AMovie(this, movieList);
        rvMoview.setAdapter(aMovie);
    }
}
