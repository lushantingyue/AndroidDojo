package cn.lushantingyue.materialdesign_demo.multitype;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import cn.lushantingyue.materialdesign_demo.R;
import cn.lushantingyue.materialdesign_demo.bean.Movie;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2018/1/16 17.
 * Responsibilities:
 * Description:
 * ProjectName:
 */

public class MovieViewBinder extends ItemViewBinder<Movie, MovieViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Movie movie) {
        holder.movieName.setText(movie.getTitle());
        holder.ratings.setText(String.valueOf(movie.getRating().getAverage()) );
        holder.genres.setText(movie.getGenres().toString());
        holder.director.setText(movie.getDirectors().get(0).getName());
        holder.actors.setText(movie.getCasts().get(0).getName());

        holder.glide
                .load(movie.getImages().getLarge())
//                .placeholder(R.mipmap.ic_launcher) //设置占位图
//                .crossFade() //设置淡入淡出效果，默认300ms，可以传参
                .into(holder.img);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @NonNull private final TextView movieName;
        @NonNull private final TextView ratings;
        @NonNull private final TextView genres;
        @NonNull private final ImageView img;
        @NonNull private final TextView director;
        @NonNull private final TextView actors;

        private final RequestManager glide;

        public ViewHolder(View itemView) {
            super(itemView);
            this.glide = Glide.with(itemView);

            this.movieName = itemView.findViewById(R.id.movie_name);
            this.ratings = itemView.findViewById(R.id.rating);
            this.genres = itemView.findViewById(R.id.genres);
            this.img = itemView.findViewById(R.id.img_wrap);
            this.director = itemView.findViewById(R.id.director);
            this.actors = itemView.findViewById(R.id.actors);
        }
    }
}
