package cn.lushantingyue.materialdesign_demo.multitype;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @NonNull private final TextView movieName;

        public ViewHolder(View itemView) {
            super(itemView);
            this.movieName = itemView.findViewById(R.id.movie_name);
        }
    }
}
