package cn.lushantingyue.materialdesign_demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private MainActivity act;

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
        mToolbar.inflateMenu(R.menu.toolbar_menu);

        setSupportActionBar(mToolbar);

        /**
         *  抽屉布局Drawerlayout + NavigationView导航视图配合使用
         */
        // 初始化抽屉布局
        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle mDrawerToogle = new ActionBarDrawerToggle(act, mDrawerLayout, R.string.drawer_open, R.string.drawer_close);
        mDrawerToogle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToogle);

        // 设置抽屉布局导航视图
        NavigationView mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
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
    }
}
