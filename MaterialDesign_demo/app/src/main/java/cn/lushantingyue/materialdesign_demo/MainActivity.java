package cn.lushantingyue.materialdesign_demo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.Setting;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import cn.lushantingyue.materialdesign_demo.api.RemoteData;
import cn.lushantingyue.materialdesign_demo.api.TokenInterceptor;
import cn.lushantingyue.materialdesign_demo.base.BaseModel;
import cn.lushantingyue.materialdesign_demo.base.TokenHolder;
import cn.lushantingyue.materialdesign_demo.bean.LoginBean;
import cn.lushantingyue.materialdesign_demo.bean.Movie;
import cn.lushantingyue.materialdesign_demo.bean.Status;
import cn.lushantingyue.materialdesign_demo.main.MainModel;
import cn.lushantingyue.materialdesign_demo.modules.photopicker.GlideImageLoader;
import cn.lushantingyue.materialdesign_demo.rationale.RuntimeRationale;
import cn.lushantingyue.materialdesign_demo.utils.ImageUtils;
import cn.lushantingyue.materialdesign_demo.utils.ToastUtil;
import cn.lushantingyue.materialdesign_demo.widget.LoginDialog;
import cn.lushantingyue.materialdesign_demo.widget.LoginInterface;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity implements MainModel.OnUploadPhotoListener, BaseModel.LoginListener
        , LoginInterface, TokenHolder {

    private Toolbar mToolbar;
    private MainActivity act;

    ArrayList<Movie> movies = new ArrayList<>();
    private DrawerLayout mDrawerLayout;
    private CoordinatorLayout root_layout;
    private ImagePicker imagePicker;

//    private PermissionSetting mSetting;
    private Rationale mRationaleationale;
    private WeakReference<RemoteData> remoteData;
    private String token = null;
    private PopupWindow loginPopup;
    private LoginDialog newFragment;
    private TabLayout slidingTabs;

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        act = this;
        TextView textview = new TextView(act);
        textview.setText("abc");

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
                // TODO: 图片、相册选择弹窗
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
        slidingTabs = findViewById(R.id.sliding_tabs);
        NestedScrollView nestedScrollView = findViewById(R.id.nestedScrollView);
//        final ViewPager
                viewPager = findViewById(R.id.viewpager);
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

        remoteData = new WeakReference<RemoteData>(new RemoteData());
    }

    private RemoteData remoteData() {
        if(remoteData.get() != null) {
            return remoteData.get();
        } else if (token != null) {
            TokenInterceptor tokenInterceptor = new TokenInterceptor(token);
            remoteData = new WeakReference<RemoteData>(new RemoteData(tokenInterceptor));
            return remoteData.get();
        } else {
            remoteData = new WeakReference<RemoteData>(new RemoteData());
            return remoteData.get();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // TODO: 2018/6/1  登陆弹窗
        if (token == null) {
            showLogin();
        }
    }

    private void showLogin() {
        newFragment = LoginDialog.newInstance();
        newFragment.show(act.getSupportFragmentManager(), "login_dialog");
    }

    private void dismissLogin() {
        newFragment.dismiss();
    }

    // TODO: 2018/6/1   刷新fragment数据 
    private void refreshPageTab(int page) {
//        slidingTabs.getTabAt(0);
//        viewPager.invalidate();
        viewPager.setCurrentItem(page, true);
    }

    // TODO: 2018/6/1   获取access_token
    private void getAccessToken(String usr, String psw) {
        remoteData().login(usr, psw, this);
    }

    // TODO: 2018/6/1   注册账号
    private void register(String usr, String psw) {
        remoteData().register(usr, psw, this);
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

    /**
     * Request permissions.
     */
    private void requestPermission(String... permissions) {
        AndPermission.with(act)
                .runtime()
                .permission(permissions)
                .rationale(new RuntimeRationale())
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        ToastUtil.show(act, getString(R.string.successfully));
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        ToastUtil.show(act, getString(R.string.failure));
                        if (AndPermission.hasAlwaysDeniedPermission(act, permissions)) {
                            showSettingDialog(permissions);
                        }
                    }
                })
                .start();
    }

    /**
     * Display setting dialog.
     */
    public void showSettingDialog(final List<String> permissions) {
        List<String> permissionNames = Permission.transformText(act, permissions);
        String message = act.getString(R.string.message_permission_always_failed, TextUtils.join("\n", permissionNames));

        new AlertDialog.Builder(act)
                .setCancelable(false)
                .setTitle(R.string.title_dialog)
                .setMessage(message)
                .setPositiveButton(R.string.setting, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setPermission();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    /**
     * 重新配置权限 permissions.
     */
    private void setPermission() {
        AndPermission.with(act)
                .runtime()
                .setting()
                .onComeback(new Setting.Action() {
                    @Override
                    public void onAction() {
                        Toast.makeText(act, R.string.message_setting_comeback, Toast.LENGTH_SHORT).show();
                    }
                })
                .start();
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
                    Uri newUri = Uri.parse(ImageUtils.getPath(act, data.getData()));
//                    String path = data.getData().getPath();
//                    Uri uri = data.getData();
//                    com.orhanobut.logger.Logger.e("图片路径\n：%s\nurl：%s\n", path, uri.toString());

//                    Toast.makeText(act, "触发上传" + "/// " + path, Toast.LENGTH_LONG).show();
                    // TODO：此处会崩
//                    ImageUtils.cropImage(act, uri);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                        newUri = FileProvider.getUriForFile(this, "com.donkor.demo.takephoto.fileprovider", new File(newUri.getPath()));
                    // 裁剪图片
                    ImageUtils.cropImage(this, newUri);
                    // TODO：直接上传原图
//                    ImageUtils.originImage(act, data.getData());
                }
                break;
            case ImageUtils.REQUEST_CODE_FROM_CUT:
                // TODO: 从剪切图片返回的数据
                if (null != ImageUtils.mPhotoFile) {
//                    if (resultCode == RESULT_OK) {
                        ImageUtils.scanMediaJpegFile(act, ImageUtils.mPhotoFile, new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(final String path, Uri uri) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (path == null) {
                                            ToastUtil.show(act,"图片未找到");
                                        } else {
                                            String filePath = "/storage/sdcard0/Pictures/Screenshots/S60911-224349.jpg";
                                            File picfile = new File(filePath);

                                            File file = new File(path);
                                            String name = file.getName();
                                            com.orhanobut.logger.Logger.i(path);
//                                            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                                            RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/png"), file);
                                            MultipartBody.Part photo = MultipartBody.Part.createFormData("pic", file.getName(), photoRequestBody);//pic为key
                                            // TODO： file not found exception 未找到图片错误
                                            remoteData().uploadPhoto(act, photo);

                                            // 确定 or 取消上传, 都要清空 File
                                            ImageUtils.mPhotoFile = null;
                                        }
                                    }
                                });
                            }
                        });
//                    } else {
//                        ImageUtils.mPhotoFile.delete();
//                    }

                } else {
                    ToastUtil.show(act, "图片未找到");
                }
                break;
            case ImageUtils.REQUEST_CODE_FROM_CAMERA:
                //
                if (resultCode == RESULT_OK) {
                    if (ImageUtils.mPhotoFile == null) {

                    }
                }
                break;
            default:
                break;
        }
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
        this.token = msg.getToken();
        this.remoteData = null;
        TokenInterceptor tokenInteceptor = new TokenInterceptor(token);
        remoteData = new WeakReference<>(new RemoteData(tokenInteceptor));
        ToastUtil.show(act, msg.getMessage() + token);
        dismissLogin();
        // TODO: token鉴权后刷新数据
        refreshPageTab(0);
    }

    @Override
    public void onLoginFailure(String msg, Exception e) {
        ToastUtil.show(act, msg);
        remoteData().login("Tester123", "123", act);
    }

    @Override
    public void btn_click(int id, String usr, String psw) {
        switch (id) {
            case R.id.btn_login:
                getAccessToken(usr, psw);
                break;
            case R.id.btn_register:
                register(usr, psw);
                break;
            default:
                break;
        }
    }

    @Override
    public String getToken() {
        return token;
    }

}