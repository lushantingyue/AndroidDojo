package cn.lushantingyue.materialdesign_demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import cn.lushantingyue.materialdesign_demo.bean.Movie;
import cn.lushantingyue.materialdesign_demo.bean.MovieInfo;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private MainActivity act;

    ArrayList<Movie> movies = new ArrayList<>();
    private DrawerLayout mDrawerLayout;
    private CoordinatorLayout root_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        act = this;

        /**
         *  android.support.v7.widget.Toolbar的使用
         */
//        CoordinatorLayout，AppBarLayout，CollapsingToolbarLayout，Toolbar，TabLayout
        // 初始化Toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

//        mToolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(mToolbar);

        /**
         *  抽屉布局Drawerlayout + NavigationView导航视图配合使用
         */
        // 初始化抽屉布局
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mDrawerToogle = new ActionBarDrawerToggle(act, mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
        mDrawerToogle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToogle);

        // 设置抽屉布局导航视图
        final NavigationView mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nickname:
                        Toast.makeText(act, "my nickname", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.contact:
                        Toast.makeText(act, "welcome to my github to star me", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.message:
                        Toast.makeText(act, "send an e-mail to me", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                item.setChecked(true);
                mDrawerLayout.closeDrawers();

                return true;
            }
        });
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(mNavigationView);
            }
        });

        root_layout = (CoordinatorLayout) findViewById(R.id.root_layout);

        final CollapsingToolbarLayout mCollapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_layout);

        final LinearLayout head_layout = findViewById(R.id.head_layout);
        AppBarLayout app_bar_layout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        app_bar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {  // 监听AppBarLayout的滑动状态
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(verticalOffset <= -head_layout.getHeight()/2) {
                    mCollapsingToolbarLayout.setTitle("庐山听月的豆瓣空间");
                } else {
                    mCollapsingToolbarLayout.setTitle("");
                }
            }
        });
        TabLayout slidingTabs = findViewById(R.id.sliding_tabs);
        NestedScrollView nestedScrollView = findViewById(R.id.nestedScrollView);
        final ViewPager viewPager = findViewById(R.id.viewpager);
        ViewPagerAdapter vpAdapter = new ViewPagerAdapter(getSupportFragmentManager(), act);
        viewPager.setAdapter(vpAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(slidingTabs));
        slidingTabs.setupWithViewPager(viewPager);
//        slidingTabs.setSelectedTabIndicatorColor(getResources().getColor(R.color.titleGrass));
        viewPager.setCurrentItem(0, false);
//        slidingTabs.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        slidingTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                viewPager.setCurrentItem(pos, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//        CollapsingToolbarLayout
//        mCollapsingToolbarLayout.setContentScrim();
//        AppBarLayout
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                Snackbar.make(root_layout, "share", Snackbar.LENGTH_SHORT).show();
                return true;
//                break;
            case R.id.more:
                Snackbar.make(root_layout, "more", Snackbar.LENGTH_SHORT).show();
                return true;
            case R.id.about:
                Snackbar.make(root_layout, "about", Snackbar.LENGTH_SHORT).show();
                return true;
            case R.id.thanks:
                // SnackBar的使用依赖于根布局
                Snackbar.make(root_layout, "thanks", Snackbar.LENGTH_SHORT).show();
                return true;
//                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
