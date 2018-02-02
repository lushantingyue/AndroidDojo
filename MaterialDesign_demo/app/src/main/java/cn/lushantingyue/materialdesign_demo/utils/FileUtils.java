package cn.lushantingyue.materialdesign_demo.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by diyik on 2018/2/2.
 */
public class FileUtils {

    public static final String ROOT_DIR = "material_demo/";
    public static final String VIDEO_DIR = "video/";
    public static final String IMAGE_DIR = "image/";
    public static final String TEMP_DIR = ".temp/";
    public static final String APP_DIR = "app/";
    public static final String LOG_DIR = "log/";

    /**
     * 检查是否安装SD卡
     */
    public static boolean checkSDCard() {
        String sDCardStatus = Environment.getExternalStorageState();
        boolean status = sDCardStatus.equals(Environment.MEDIA_MOUNTED);
        return status;
    }

    public static String getSdDirectory() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 判断目录是否存在，如果不存在，则创建
     */
    public static void createDir(String path) {
        File filePath = new File(path);
        if (!filePath.exists()) {
            filePath.mkdir();
        }
    }

    /**
     * 获取根目录
     */
    public static String getRootDir() {
        if (checkSDCard()) {
            String str = getSdDirectory() + File.separator + ROOT_DIR;
            createDir(str);
            return str;
        } else {
            return null;
        }
    }

    /**
     * 获取视频文件的目录
     */
    public static String getVideoFileDir() {
        String basePath = getRootDir();
        if (basePath != null) {
            String str = basePath + VIDEO_DIR;
            createDir(str);
            return str;
        } else {
            return null;
        }
    }

    /**
     * 获取图片文件的目录
     */
    public static String getImageFileDir() {
        String basePath = getRootDir();
        if (basePath != null) {
            String str = basePath + IMAGE_DIR;
            createDir(str);
            return str;
        } else {
            return null;
        }
    }

    /**
     * 获取临时文件的目录
     */
    public static String getTempFileDir() {
        String basePath = getRootDir();
        if (basePath != null) {
            String str = basePath + TEMP_DIR;
            createDir(str);
            return str;
        } else {
            return null;
        }
    }

    /**
     * 获取应用文件的目录
     *
     * @return
     */
    public static String getAppFileDir() {
        String basePath = getRootDir();
        if (basePath != null) {
            String str = basePath + APP_DIR;
            createDir(str);
            return str;
        } else {
            return null;
        }
    }

    /**
     * 获取日志文件的目录
     */
    public static String getLogFileDir() {
        String basePath = getRootDir();
        if (basePath != null) {
            String str = basePath + LOG_DIR;
            createDir(str);
            return str;
        } else {
            return null;
        }
    }

    public static void deleteTempFileDir() {
        try {
            String str = getTempFileDir();
            if (str != null) {
                File file = new File(str);
                deleteDir(file);
            }
        } catch (Exception e) {
        }
    }

    public static void deleteImageFileDir() {
        try {
            String str = getImageFileDir();
            if (str != null) {
                File file = new File(str);
                deleteDir(file);
            }
        } catch (Exception e) {
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir == null || !dir.exists() || dir.isFile()) {
            return false;
        }
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                deleteDir(file);// 递归
            }
        }
        dir.delete();
        return true;
    }

    public static boolean writeFile(String filePath, String msg) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            byte[] data = msg.getBytes("UTF-8");
            out.write(data);
            out.flush();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
