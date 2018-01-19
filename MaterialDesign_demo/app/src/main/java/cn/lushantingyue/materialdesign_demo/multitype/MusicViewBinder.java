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

import java.util.List;

import cn.lushantingyue.materialdesign_demo.R;
import cn.lushantingyue.materialdesign_demo.bean.Movie;
import cn.lushantingyue.materialdesign_demo.bean.Music;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2018/1/16 17.
 * Responsibilities:
 * Description:
 * ProjectName:
 */

public class MusicViewBinder extends ItemViewBinder<Music.MusicsBean, MusicViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_music, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Music.MusicsBean music) {

        List<Music.MusicsBean.TagsBean> tags = music.getTags();
        String tagString = "", singer = "";

        int length = tags.size();
        for (int i = 0; i < length; i++) {
            tagString += tags.get(i).getName() + "/";
        }
        length = music.getAuthor().size();
        List<Music.MusicsBean.AuthorBean> bean = music.getAuthor();
        for (int i = 0; i < length; i++) {
            singer += bean.get(i).getName() + "/";
        }
        holder.songName.setText(music.getAlt_title());
        holder.id.setText(music.getId());
        holder.ratings.setText("平均: " + music.getRating().getAverage());
        holder.singer.setText(singer);
        holder.tags.setText(tagString);

        holder.glide
                .load(music.getImage())
                .into(holder.img);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @NonNull private final TextView id;
        @NonNull private final TextView songName;
        @NonNull private final TextView singer;
        @NonNull private final TextView ratings;
        @NonNull private final ImageView img;
        @NonNull private final TextView tags;

        private final RequestManager glide;

        public ViewHolder(View itemView) {
            super(itemView);
            this.glide = Glide.with(itemView);

            this.id = itemView.findViewById(R.id.id);
            this.songName = itemView.findViewById(R.id.song);
            this.singer = itemView.findViewById(R.id.singer);
            this.ratings = itemView.findViewById(R.id.ratings);
            this.img = itemView.findViewById(R.id.img_wrap);
            this.tags = itemView.findViewById(R.id.tags);
        }
    }
}
