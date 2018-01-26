package cn.lushantingyue.materialdesign_demo.multitype;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.lushantingyue.materialdesign_demo.R;
import cn.lushantingyue.materialdesign_demo.bean.Articles;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2018/1/26 15.
 * Responsibilities:
 * Description:
 * ProjectName:
 */

public class ArticalViewBinder extends ItemViewBinder<Articles, ArticalViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_article, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Articles item) {
        holder.tv_title.setText(item.get_abstract());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @NonNull private TextView tv_title;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tv_title = itemView.findViewById(R.id.tv_title);
        }
    }
}
