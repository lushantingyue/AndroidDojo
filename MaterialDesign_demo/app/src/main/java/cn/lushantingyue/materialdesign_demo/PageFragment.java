package cn.lushantingyue.materialdesign_demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.lushantingyue.materialdesign_demo.bean.Movie;
import cn.lushantingyue.materialdesign_demo.bean.MovieInfo;
import cn.lushantingyue.materialdesign_demo.multitype.MovieViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Administrator on 2018/1/12 16.
 * Responsibilities:
 * Description:
 * ProjectName:
 */

public class PageFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private RecyclerView lv;
    private MultiTypeAdapter adapter;

    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment pageFragment = new PageFragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, null);
        lv = (RecyclerView) view.findViewById(R.id.lv);
        // 创建一个线性布局管理器

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        // 设置布局管理器

        lv.setLayoutManager(layoutManager);

        adapter = new MultiTypeAdapter();
        adapter.register(Movie.class, new MovieViewBinder());
        lv.setAdapter(adapter);
        // 加载数据

            /* 模拟加载数据，也可以稍后再加载，然后使用
         * adapter.notifyDataSetChanged() 刷新列表 */
        if (mPage == 2) {
            ArrayList<Movie> items = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                items.add(new Movie().setTitle("解忧杂货店"));
                items.add(new Movie().setTitle("前任3"));
                items.add(new Movie().setTitle("勇敢者游戏：决战丛林"));
            }
            adapter.setItems(items);
            adapter.notifyDataSetChanged();
        } else {
            List<String> list = new ArrayList<String>();
            for (int i = 0; i < 100; i++) {
                list.add(i + "");
            }
            lv.setAdapter(new MyAdapter(list));
        }
        return view;
    }
}
