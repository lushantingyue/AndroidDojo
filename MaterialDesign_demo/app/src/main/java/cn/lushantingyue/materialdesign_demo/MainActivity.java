package cn.lushantingyue.materialdesign_demo;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.yanzhenjie.permission.FileProvider;
import com.yanzhenjie.permission.Rationale;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cn.lushantingyue.materialdesign_demo.api.RemoteData;
import cn.lushantingyue.materialdesign_demo.base.BaseModel;
import cn.lushantingyue.materialdesign_demo.bean.LoginBean;
import cn.lushantingyue.materialdesign_demo.bean.Movie;
import cn.lushantingyue.materialdesign_demo.bean.Status;
import cn.lushantingyue.materialdesign_demo.main.MainModel;
import cn.lushantingyue.materialdesign_demo.modules.photopicker.GlideImageLoader;
import cn.lushantingyue.materialdesign_demo.utils.DefaultRationale;
import cn.lushantingyue.materialdesign_demo.utils.FileUtils;
import cn.lushantingyue.materialdesign_demo.utils.ImageUtils;
import cn.lushantingyue.materialdesign_demo.utils.PermissionSetting;
import cn.lushantingyue.materialdesign_demo.utils.ToastUtil;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity implements MainModel.OnUploadPhotoListener, BaseModel.LoginListener, BaseModel.checkPassportListener {

    private Toolbar mToolbar;
    private MainActivity act;

    ArrayList<Movie> movies = new ArrayList<>();
    private DrawerLayout mDrawerLayout;
    private CoordinatorLayout root_layout;
    private ImagePicker imagePicker;

    private DefaultRationale mRationale;
    private PermissionSetting mSetting;
    private Rationale mRationaleationale;
    private WeakReference<RemoteData> remoteData;

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

        mNavigationView.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, WxChooserActivity.class);
//                startActivity(intent);
                checkPassport();
                ImageUtils.showImagePickDialog(act);
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

        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
//        CollapsingToolbarLayout
//        mCollapsingToolbarLayout.setContentScrim();
//        AppBarLayout

        mRationale = new DefaultRationale();
        mSetting = new PermissionSetting(act);
        remoteData = new WeakReference<RemoteData>(new RemoteData());
    }

    private RemoteData remoteData() {
        if(remoteData.get() != null) {
            return remoteData.get();
        } else {
            remoteData = new WeakReference<RemoteData>(new RemoteData());
            return remoteData.get();
        }
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

    public void showSetting(List<String> permissions) {
        act.mSetting.showSetting(permissions);
    }

    public Rationale getRationale() {
        return mRationale;
    }

    /**
     *  MVP架构内应分配到那一层管理?
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case ImageUtils.REQUEST_CODE_FROM_ALBUM:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(act, "触发上传", Toast.LENGTH_LONG).show();
                    ImageUtils.cropImage(this, data.getData());
                }
                break;
            case ImageUtils.REQUEST_CODE_FROM_CUT:
                // 从剪切图片返回的数据
                if (null != ImageUtils.mPhotoFile) {
                    if (resultCode == RESULT_OK) {
                        ImageUtils.scanMediaJpegFile(this, ImageUtils.mPhotoFile, new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(final String path, Uri uri) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (path == null) {
                                            Toast.makeText(act, "图片未找到", Toast.LENGTH_LONG).show();
                                        } else {
                                            File file = new File(path);
                                            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                                            remoteData().uploadPhoto(act, requestBody);
                                        }
                                    }
                                });
                            }
                        });
                    } else {
                        ImageUtils.mPhotoFile.delete();
                    }
                    // 确定 or 取消上传, 都要清空 File
                    ImageUtils.mPhotoFile = null;
                } else {
                    ToastUtil.show(act, "图片未找到");
                }
                break;
            case ImageUtils.REQUEST_CODE_FROM_CAMERA:
                //
                break;
            default:
                break;
        }
    }

    public void checkPassport() {
        remoteData().checkPassport(this);
    }

    @Override
    public void onSuccess(Status msg) {
        // test login
        ToastUtil.show(act, msg.getMessage());
    }

    @Override
    public void onFailure(String msg, Exception e) {
        ToastUtil.show(act, msg);
    }

    @Override
    public void saveDisposable(Disposable d) {

    }

    @Override
    public void onLoginSuccess(LoginBean msg) {
        ToastUtil.show(act, msg.getMessage());
    }

    @Override
    public void onLoginFailure(String msg, Exception e) {
        ToastUtil.show(act, msg);
        remoteData().login("Tester123", "123", act);
    }

}