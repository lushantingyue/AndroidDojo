package cn.lushantingyue.materialdesign_demo.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/5/24 15.
 * Responsibilities:
 * Description:
 * ProjectName:
 */

public class ToastUtil {

    public static void show(Context ctx, String info) {
        Toast.makeText(ctx, info, Toast.LENGTH_LONG).show();
    }
}
