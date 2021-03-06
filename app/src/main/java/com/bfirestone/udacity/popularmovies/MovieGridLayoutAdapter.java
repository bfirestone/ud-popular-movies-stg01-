package com.bfirestone.udacity.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bfirestone.udacity.popularmovies.models.Movie;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieGridLayoutAdapter extends RecyclerView.Adapter<MovieGridLayoutAdapter.MovieViewHolder> {
    private List<Movie> dataList;
    private Context context;
    final private MovieItemClickListener mOnMovieItemClickListener;

    public interface MovieItemClickListener {
        void onMovieItemClick(int clickedItemIndex);
    }

    public MovieGridLayoutAdapter(Context context, List<Movie> dataList, MovieItemClickListener listener) {
        this.context = context;
        this.dataList = dataList;
        this.mOnMovieItemClickListener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.layout_grid_movie_item,
                parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bindMovie(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Context mContext;
        TextView movieTitle;
        ImageView coverImage;
        TextView releaseDate;

        MovieViewHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            movieTitle = itemView.findViewById(R.id.tv_movie_title);
            releaseDate = itemView.findViewById(R.id.tv_movie_release_date);
            coverImage = itemView.findViewById(R.id.iv_movie_poster_image);
            itemView.setOnClickListener(this);
        }

        void bindMovie(Movie movie) {
            String releaseDateText = "Release Date: " + movie.getReleaseDate();
            String moviePosterUrl = context.getResources().getString(R.string.TMDB_BASE_IMG_URL)
                    + movie.getPosterPath();

            movieTitle.setText(movie.getOriginalTitle());
            releaseDate.setText(releaseDateText);

            Picasso.Builder builder = new Picasso.Builder(context);

            builder.downloader(new OkHttp3Downloader(context));
            builder.build().load(moviePosterUrl)
                    .placeholder((R.drawable.gradient_background))
                    .error(R.drawable.ic_launcher_background)
                    .into(coverImage);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnMovieItemClickListener.onMovieItemClick(clickedPosition);
        }
    }
}
