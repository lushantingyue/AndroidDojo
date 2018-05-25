package cn.lushantingyue.materialdesign_demo.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/5/24 15.
 * Responsibilities:
 * Description:
 * ProjectName:
 */

public class ToastUtil {

    public static void show(Context ctx, String info) {
        Toast.makeText(ctx, info, Toast.LENGTH_SHORT).show();
    }

    public static void snackToast(View root_layout, String info) {
        Snackbar.make(root_layout, info, Snackbar.LENGTH_SHORT).show();
    }
}
