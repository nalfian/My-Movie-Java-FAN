package id.co.gitsolution.mymoviefav.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import id.co.gitsolution.mymoviefav.R;
import id.co.gitsolution.mymoviefav.model.ResultsItem;
import id.co.gitsolution.mymoviefav.view.DetailActivity;

public class AMovie extends RecyclerView.Adapter<AMovie.viewHolderMenu> {

    private Context context;
    private List<ResultsItem> movieList;

    public AMovie(Context context, List<ResultsItem> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @Override
    public AMovie.viewHolderMenu onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new viewHolderMenu(view);
    }

    @Override
    public void onBindViewHolder(final AMovie.viewHolderMenu holder, @SuppressLint("RecyclerView") final int position) {
        holder.tvName.setText(movieList.get(position).getTitle());
        holder.tvDesc.setText(movieList.get(position).getOverview());
        holder.tvDate.setText(movieList.get(position).getReleaseDate());
        Picasso.get()
                .load("http://image.tmdb.org/t/p/w185" + movieList.get(position).getPosterPath())
                .into(holder.ivMovie);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("movie", movieList.get(position));
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class viewHolderMenu extends RecyclerView.ViewHolder {

        private TextView tvName, tvDesc, tvDate;
        private ImageView ivMovie;

        viewHolderMenu(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            ivMovie = itemView.findViewById(R.id.ivMovie);
        }
    }

}