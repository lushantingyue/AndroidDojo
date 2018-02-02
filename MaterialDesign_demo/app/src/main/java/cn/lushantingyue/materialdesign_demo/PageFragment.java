package cn.lushantingyue.materialdesign_demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.lushantingyue.materialdesign_demo.api.LocalData;
import cn.lushantingyue.materialdesign_demo.api.RemoteData;
import cn.lushantingyue.materialdesign_demo.base.BaseModel;
import cn.lushantingyue.materialdesign_demo.bean.Articles;
import cn.lushantingyue.materialdesign_demo.bean.Movie;
import cn.lushantingyue.materialdesign_demo.bean.Music;
import cn.lushantingyue.materialdesign_demo.multitype.ArticalViewBinder;
import cn.lushantingyue.materialdesign_demo.multitype.MovieViewBinder;
import cn.lushantingyue.materialdesign_demo.multitype.MusicViewBinder;
import cn.lushantingyue.materialdesign_demo.multitype.SimpleViewBinder;
import io.reactivex.disposables.Disposable;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Administrator on 2018/1/12 16.
 * Responsibilities:
 * Description:
 * ProjectName:
 */

public class PageFragment extends Fragment implements BaseModel.OnLoadArticlesListListener {

    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private RecyclerView lv;
    private MultiTypeAdapter adapter;

    ArrayList<Articles> articlesList = new ArrayList<>();
    ArrayList<Movie> movies = new ArrayList<>();
    ArrayList<Music.MusicsBean> music = new ArrayList<>();
    private int curPage = 1;
    private FragmentActivity act;

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
        act = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, null);
        lv = (RecyclerView) view.findViewById(R.id.lv);
        // 创建并配置一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        lv.setLayoutManager(layoutManager);
        adapter = new MultiTypeAdapter();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        /*
         * 模拟加载数据，也可以稍后再加载，
         * 然后使用 adapter.notifyDataSetChanged() 刷新列表
         * */
        switch (mPage) {
            case 1:
                adapter.register(Articles.class, new ArticalViewBinder());
                lv.setAdapter(adapter);

                adapter.setItems(articlesList);
                adapter.notifyDataSetChanged();
                new RemoteData().listDataByPage(this, curPage);
                break;
            case 2:
                adapter.register(Movie.class, new MovieViewBinder());
                lv.setAdapter(adapter);

                adapter.setItems(movies);
                adapter.notifyDataSetChanged();
                LocalData.loadData(movies, act);
                break;
            case 3:
                adapter.register(Music.MusicsBean.class, new MusicViewBinder());
                lv.setAdapter(adapter);

                adapter.setItems(music);
                adapter.notifyDataSetChanged();
                LocalData.loadMusic(music, act);
                break;
            default:
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < 100; i++) {
                    list.add(i + "");
                }
//                lv.setAdapter(new MyAdapter(list));

                adapter.register(String.class, new SimpleViewBinder());
                lv.setAdapter(adapter);

                adapter.setItems(list);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onSuccess(ArrayList<Articles> list, int page) {
        if (list.size() > 0) {
            articlesList.addAll(list);
        }
//        else {
//            this.canloadMore = false;
//        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(String msg, Exception e) {

    }

    @Override
    public void saveDisposable(Disposable d) {

    }

}
