package cn.lushantingyue.materialdesign_demo.multitype;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Administrator on 2018/2/2 17.
 * Responsibilities:
 * Description:
 * ProjectName:
 */

public class SimpleViewBinder extends ItemViewBinder<String, SimpleViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected SimpleViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        // 创建一个View，简单起见直接使用系统提供的布局，就是一个TextView
        View root = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull SimpleViewBinder.ViewHolder holder, @NonNull String item) {
        holder.mTextView.setText(item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        public TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
        }
    }
}
