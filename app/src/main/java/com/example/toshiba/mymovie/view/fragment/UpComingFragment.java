package com.example.toshiba.mymovie.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class UpComingFragment extends Fragment {

    private List<ResultsItem> movieList = new ArrayList<>();
    private RecyclerView rvMoview;
    private AMovie aMovie;

    public UpComingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_up_coming, container, false);
        initView(view);
        getMovie();
        return view;
    }

    private void getMovie() {
        Locale locale = getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (locale.toString().equalsIgnoreCase("in_id")) {
            language = "id";
        }
        AndroidNetworking.get("https://api.themoviedb.org/3/movie/upcoming?api_key=221fbf2bcc939e0de03af53af4e1744a")
                .addQueryParameter("language", language)
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
                        if (getContext() != null) {
                            Toast.makeText(getContext(), getString(R.string.failure), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initView(View view) {
        rvMoview = view.findViewById(R.id.rvMoview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvMoview.setLayoutManager(linearLayoutManager);
        rvMoview.setNestedScrollingEnabled(false);
        aMovie = new AMovie(getContext(), movieList);
        rvMoview.setAdapter(aMovie);
    }

}
