package id.co.gitsolution.mymoviefav.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import id.co.gitsolution.mymoviefav.R;
import id.co.gitsolution.mymoviefav.model.ResultsItem;

public class DetailActivity extends AppCompatActivity {

    private ResultsItem resultsItem;
    private TextView tvName, tvDesc, tvDate;
    private ImageView ivMovie, ivFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        resultsItem = getIntent().getParcelableExtra("movie");
        getSupportActionBar().setTitle(resultsItem.getTitle());

        initView();
        initEvent();
    }


    private void initEvent() {
        tvName.setText(resultsItem.getTitle());
        tvDesc.setText(resultsItem.getOverview());
        tvDate.setText(resultsItem.getReleaseDate());
        Picasso.get()
                .load("http://image.tmdb.org/t/p/w500" + resultsItem.getPosterPath())
                .into(ivMovie);

    }

    private void initView() {
        tvName = findViewById(R.id.tvName);
        tvDate = findViewById(R.id.tvDate);
        tvDesc = findViewById(R.id.tvDesc);
        ivMovie = findViewById(R.id.ivMovie);
    }
}
