package cn.lushantingyue.materialdesign_demo.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.io.File;
import java.util.List;
import java.util.Objects;

import cn.lushantingyue.materialdesign_demo.MainActivity;
import cn.lushantingyue.materialdesign_demo.R;
import cn.lushantingyue.materialdesign_demo.rationale.RuntimeRationale;

/**
 * Created by lushantingyue on 2018/2/2.
 */
public class ImageUtils {

    public static final int REQUEST_CODE_FROM_CAMERA = 5001;
    public static final int REQUEST_CODE_FROM_ALBUM = 5002;
    public static final int REQUEST_CODE_FROM_CUT = 5003;

    public static final int REQUEST_CODE_DIRECT_UPLOAD = 5000;

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
                                requestPermissionForAlbumForCamera(activity, Permission.CAMERA);
//                                pickImageFromCamera(activity);
                                break;
                            case 1:
                                requestPermissionForAlbum(activity, Permission.Group.STORAGE);
                                break;
                            default:
                                break;
                        }
                    }
                })
                .show();
    }

    /**
     * 请求相机权限
     */
    public static void requestPermissionForAlbumForCamera(final Activity activity, String... permissions) {
        AndPermission.with(activity)
                .runtime()
                .permission(permissions)
                .rationale(new RuntimeRationale())
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        List<String> permissionNames = Permission.transformText(activity, permissions);
                        String permissionText = TextUtils.join(",\n", permissionNames);
                        Toast.makeText(activity, "权限请求 成功: " + permissionText, Toast.LENGTH_LONG).show();
                        pickImageFromCamera(activity);
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        List<String> permissionNames = Permission.transformText(activity, permissions);
                        String permissionText = TextUtils.join(",\n", permissionNames);
                        Toast.makeText(activity, "权限请求 失败: " + permissionText, Toast.LENGTH_LONG).show();
                        if (AndPermission.hasAlwaysDeniedPermission(activity, permissions)) {
                            ((MainActivity)activity).showSettingDialog(permissions);
                        }
                    }
                }).start();
    }

    /**
     * 请求相册权限
     */
    public static void requestPermissionForAlbum(final Activity activity, String... permissions) {
        AndPermission.with(activity)
                .runtime()
                .permission(permissions)
                .rationale(new RuntimeRationale())
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        List<String> permissionNames = Permission.transformText(activity, permissions);
                        String permissionText = TextUtils.join(",\n", permissionNames);
                        Toast.makeText(activity, "权限请求 成功: " + permissionText, Toast.LENGTH_LONG).show();
                        pickImageFromAlbum(activity);
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        List<String> permissionNames = Permission.transformText(activity, permissions);
                        String permissionText = TextUtils.join(",\n", permissionNames);
                        Toast.makeText(activity, "权限请求 失败: " + permissionText, Toast.LENGTH_LONG).show();
                        if (AndPermission.hasAlwaysDeniedPermission(activity, permissions)) {
                            ((MainActivity) activity).showSettingDialog(permissions);
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

        if(mPhotoFile == null) {
            mPhotoFile = getCameraPhotoFile();
            Intent intent = new Intent();
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

            // 兼容 Android7.0文件分享策略
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件

//            android:authorities="cn.lushantingyue.materialdesign_demo.file_provider"
                Uri photoOutputUri = FileProvider.getUriForFile(
                        activity,
                        activity.getPackageName() + ".file_provider",
                        mPhotoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoOutputUri);
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhotoFile));
            }

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

    /**
     * 裁剪本地图片
     * @param activity
     * @param uri
     */
    public static void cropImage(final Activity activity, Uri uri) {
        if (!isSdcardWritable()) {
            Toast.makeText(activity, R.string.no_sdcard, Toast.LENGTH_SHORT).show();
            return;
        }
        // 裁剪图片的临时存放目录
        if (mPhotoFile == null) {
            mPhotoFile = getCameraPhotoFile();
        }
        // 根据Uri获取图片绝对路径, 兼容API19以上的Uri转换
        String absolutePath19 = getImageAbsolutePath19(activity, uri);

        Uri photoInputUri; // 原始路径

        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        // 兼容 Android7.0文件分享策略
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            photoInputUri = FileProvider.getUriForFile(
                    activity,
                    activity.getPackageName() + ".file_provider",
                    new File(absolutePath19));
        } else {
            photoInputUri = Uri.fromFile(new File(absolutePath19));
        }

        intent.setDataAndType(photoInputUri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        // 取消人脸识别
        intent.putExtra("noFaceDetection", true); // no face detection

        // 裁剪处理后的临时URI
        Uri tempUri = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");

        // 兼容 Android7.0文件分享策略
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhotoFile));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        activity.startActivityForResult(intent, REQUEST_CODE_FROM_CUT);
    }

    /**
     * 根据Uri获取图片绝对路径，解决Android4.4以上版本Uri转换, 解决 Android7.0 FileURI expose exception的问题
     * @param context
     * @param imageUri
     */
//    @TargetApi(Build.VERSION_CODES.KITKAT)
    @TargetApi(Build.VERSION_CODES.N)
    private static String getImageAbsolutePath19(Context context, Uri imageUri) {
        if (context == null || imageUri == null) {
            return null;
        }
//        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.N) {
//            /**
//             * ****************************************************************
//             */
//            if (isExternalStorageDocument(imageUri)) {
//                String docId = DocumentsContract.getDocumentId(imageUri);
//                String[] split = docId.split(":");
//                String type = split[0];
//                if ("primary".equalsIgnoreCase(type)) {
//                    return Environment.getExternalStorageDirectory() + "/" + split[1];
//                }
//            } else if (isDownloadsDocument(imageUri)) {
//                String id = DocumentsContract.getDocumentId(imageUri);
//                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
//                return getDataColumn(context, contentUri, null, null);
//            } else if (isMediaDocument(imageUri)) {
//                String docId = DocumentsContract.getDocumentId(imageUri);
//                String[] split = docId.split(":");
//                String type = split[0];
//                Uri contentUri = null;
//                if ("image".equals(type)) {
//                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//                } else if ("video".equals(type)) {
//                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
//                } else if ("audio".equals(type)) {
//                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//                }
//                String selection = MediaStore.Images.Media._ID + "=?";
//                String[] selectionArgs = new String[]{split[1]};
//                return getDataColumn(context, contentUri, selection, selectionArgs);
//            }
//            /**
//             * *********************************************************************
//             */
//        }
//        else
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && DocumentsContract.isDocumentUri(context, imageUri)) {
                if (isExternalStorageDocument(imageUri)) {
                    String docId = DocumentsContract.getDocumentId(imageUri);
                    String[] split = docId.split(":");
                    String type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }
                } else if (isDownloadsDocument(imageUri)) {
                    String id = DocumentsContract.getDocumentId(imageUri);
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                    return getDataColumn(context, contentUri, null, null);
                } else if (isMediaDocument(imageUri)) {
                    String docId = DocumentsContract.getDocumentId(imageUri);
                    String[] split = docId.split(":");
                    String type = split[0];
                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    String selection = MediaStore.Images.Media._ID + "=?";
                    String[] selectionArgs = new String[]{split[1]};
                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }
            }

        // MediaStore (and general)
        if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(imageUri))
                return imageUri.getLastPathSegment();
            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }

        return null;
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = { column };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
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
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public static File getCameraPhotoFile() {
        File path = new File(Objects.requireNonNull(FileUtils.getImageFileDir()));
        if (!path.exists()) {
            path.mkdirs();
        }
        Logger.i(path + "pic_timestamp.jpg");
        return new File(path, "pic_" + System.currentTimeMillis() + ".jpg");
    }

    public static void scanMediaJpegFile(final Context context,
                                         final File file, final MediaScannerConnection.OnScanCompletedListener listener) {
        MediaScannerConnection.scanFile(context,
                new String[] { file.getAbsolutePath() },
                new String[] { "image/jpg" }, listener);
    }

    /**
     * @param context 上下文对象
     * @param uri     当前相册照片的Uri
     * @return 解析后的Uri对应的String
     */
    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        String pathHead = "file:///";
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return pathHead + Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);

                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return pathHead + getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return pathHead + getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return pathHead + getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return pathHead + uri.getPath();
        }
        return null;
    }

}
