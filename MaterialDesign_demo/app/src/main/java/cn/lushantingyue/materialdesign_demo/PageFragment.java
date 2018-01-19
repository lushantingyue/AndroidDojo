package cn.lushantingyue.materialdesign_demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import cn.lushantingyue.materialdesign_demo.bean.Movie;
import cn.lushantingyue.materialdesign_demo.bean.MovieInfo;
import cn.lushantingyue.materialdesign_demo.bean.Music;
import cn.lushantingyue.materialdesign_demo.multitype.MovieViewBinder;
import cn.lushantingyue.materialdesign_demo.multitype.MusicViewBinder;
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

    ArrayList<Movie> movies = new ArrayList<>();
    ArrayList<Music.MusicsBean> music = new ArrayList<>();

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
        if(mPage == 2) {
            loadData();
        } else if (mPage == 3) {
            loadMusic();
        }
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

            /* 模拟加载数据，也可以稍后再加载，然后使用
         * adapter.notifyDataSetChanged() 刷新列表 */
        if (mPage == 2) {
//            ArrayList<Movie> items = new ArrayList<>();
//            for (int i = 0; i < 20; i++) {
//                items.add(new Movie().setTitle("解忧杂货店").setGenres(Arrays.asList("喜剧", "爱情")).setImages(new Movie.ImagesBean().setLarge("https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2503544593.jpg")) );
//                items.add(new Movie().setTitle("前任3").setGenres(Arrays.asList("动作", "奇幻", "冒险")));
//                items.add(new Movie().setTitle("勇敢者游戏：决战丛林").setGenres(Arrays.asList("剧情", "历史", "战争")));
//            }
            adapter.register(Movie.class, new MovieViewBinder());
            lv.setAdapter(adapter);

            adapter.setItems(movies);
            adapter.notifyDataSetChanged();
        } else if(mPage ==3) {

            adapter.register(Music.MusicsBean.class, new MusicViewBinder());
            lv.setAdapter(adapter);

            adapter.setItems(music);
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

    /**
     * 豆瓣电影API：https://api.douban.com/v2/movie/in_theaters
     */
    private void loadData() {
        String jsonData = getAsset("douban_movie.json");
        Gson gson = new Gson();
        MovieInfo res = gson.fromJson(jsonData, MovieInfo.class);
        String title = res.getSubjects().get(0).getTitle();
        ArrayList<Movie> moviesBean = res.getSubjects();
        movies.addAll(moviesBean);
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(2)         // (Optional) How many method line to show. Default 2
                .methodOffset(3)        // (Optional) Skips some method invokes in stack trace. Default 5
                .tag("MD_demo")   // (Optional) Custom tag for each log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
        Logger.i(title);
        Logger.i(movies.size() + "");
        Toast.makeText(getActivity(), title, Toast.LENGTH_SHORT).show();
    }

    private void loadMusic() {
        String jsonData = getAsset("douban_music.json");
        Gson gson = new Gson();
        Music res = gson.fromJson(jsonData, Music.class);
        ArrayList<Music.MusicsBean> musicBean = (ArrayList<Music.MusicsBean>) res.getMusics();
        music.addAll(musicBean);
    }

    private String getAsset(String fileName) {
        String Result = "";
        try {
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";

            while ((line = bufReader.readLine()) != null) {
                Result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return Result;
    }
}
