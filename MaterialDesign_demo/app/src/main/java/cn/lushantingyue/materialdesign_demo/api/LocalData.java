package cn.lushantingyue.materialdesign_demo.api;

import android.app.Activity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import cn.lushantingyue.materialdesign_demo.BuildConfig;
import cn.lushantingyue.materialdesign_demo.bean.Movie;
import cn.lushantingyue.materialdesign_demo.bean.MovieInfo;
import cn.lushantingyue.materialdesign_demo.bean.Music;

/**
 * Created by Administrator on 2018/1/26 14.
 * Responsibilities:
 * Description:
 * ProjectName:
 */

public class LocalData {

    /**
     * 豆瓣电影API：https://api.douban.com/v2/movie/in_theaters
     */
    public static void loadData(ArrayList<Movie> movies, Activity act) {
        String jsonData = getAsset("douban_movie.json", act);
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
        Logger.i("LocalData >>>");
        Toast.makeText(act, title, Toast.LENGTH_SHORT).show();
    }

    public static void loadMusic(ArrayList<Music.MusicsBean> music, Activity act) {
        String jsonData = getAsset("douban_music.json", act);
        Gson gson = new Gson();
        Music res = gson.fromJson(jsonData, Music.class);
        ArrayList<Music.MusicsBean> musicBean = (ArrayList<Music.MusicsBean>) res.getMusics();
        music.addAll(musicBean);
    }

    private static String getAsset(String fileName, Activity act) {
        String Result = "";
        try {
            InputStreamReader inputReader = new InputStreamReader(act.getResources().getAssets().open(fileName));
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
