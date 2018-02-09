package cn.lushantingyue.materialdesign_demo.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Toast;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.io.File;
import java.util.List;

import cn.lushantingyue.materialdesign_demo.MainActivity;
import cn.lushantingyue.materialdesign_demo.R;

/**
 * Created by diyik on 2018/2/2.
 */
public class ImageUtils {

    public static final int REQUEST_CODE_FROM_CAMERA = 5001;
    public static final int REQUEST_CODE_FROM_ALBUM = 5002;
    public static final int REQUEST_CODE_FROM_CUT = 5003;

    /**
     * 存放拍照图片的uri地址
     */
    public static File mPhotoFile;

    /**
     * 显示获取照片不同方式对话框
     */
    public static void showImagePickDialog(final Activity activity) {
        String title = "选择获取图片方式";
        String[] items = new String[]{"拍照", "相册"};
        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        switch (which) {
                            case 0:
                                addPermission(activity, com.yanzhenjie.permission.Permission.CAMERA);
                                pickImageFromCamera(activity);
                                break;
                            case 1:
                                addPermission(activity, com.yanzhenjie.permission.Permission.Group.STORAGE);
                                pickImageFromAlbum(activity);
                                break;
                            default:
                                break;
                        }
                    }
                })
                .show();
    }

    /**
     * 权限检查
     */
    public static void addPermission(final Activity activity, String... permissions) {
        AndPermission.with(activity)
                .permission(permissions)
                .rationale(((MainActivity)activity).getRationale())
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        List<String> permissionNames = Permission.transformText(activity, permissions);
                        String permissionText = TextUtils.join(",\n", permissionNames);
                        Toast.makeText(activity, "权限请求 成功: " + permissionText, Toast.LENGTH_LONG).show();
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        List<String> permissionNames = Permission.transformText(activity, permissions);
                        String permissionText = TextUtils.join(",\n", permissionNames);
                        Toast.makeText(activity, "权限请求 失败: " + permissionText, Toast.LENGTH_LONG).show();
                        if (AndPermission.hasAlwaysDeniedPermission(activity, permissions)) {
                            ((MainActivity)activity).showSetting(permissions);
                        }
                    }
                }).start();
    }

    /**
     * 打开相机拍照获取图片
     */
    public static void pickImageFromCamera(final Activity activity) {

        if (!hasCamera(activity)) {
            Toast.makeText(activity, R.string.no_camera, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isSdcardWritable()) {
            Toast.makeText(activity, R.string.no_sdcard, Toast.LENGTH_SHORT).show();
            return;
        }

        if(mPhotoFile==null){
            mPhotoFile = getCameraPhotoFile();
            Intent intent = new Intent();
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhotoFile));
            activity.startActivityForResult(intent, REQUEST_CODE_FROM_CAMERA);
        }
    }

    /**
     * 打开本地相册选取图片
     */
    public static void pickImageFromAlbum(final Activity activity) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        activity.startActivityForResult(intent, REQUEST_CODE_FROM_ALBUM);
    }

    public static boolean hasCamera(Context context) {
        PackageManager pm = context.getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)
                || pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);
    }

    /**
     * Check whether the SD card is writable
     */
    public static boolean isSdcardWritable() {
        final String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public static File getCameraPhotoFile() {
        File path = new File(FileUtils.getImageFileDir());
        if (!path.exists()) {
            path.mkdirs();
        }
        return new File(path, "pic_" + System.currentTimeMillis() + ".jpg");
    }

}
