package cn.lushantingyue.materialdesign_demo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/12 16.
 * Responsibilities:
 * Description:
 * ProjectName:
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private final int PAGE_COUNT = 5;
    private String tabTitles[] = new String[]{"简书七日热门", "电影", "音乐", "任务清单", "同城"};
    private List<Fragment> fragments = new ArrayList<>();
    private Context context;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        for (int i = 0; i < PAGE_COUNT; i++) {
            this.fragments.add(PageFragment.newInstance(i + 1));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

}
