package cn.lushantingyue.materialdesign_demo.rationale;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import java.io.File;

import cn.lushantingyue.materialdesign_demo.R;

/**
 * Created by Lushantingyue on 2018/8/24.
 */
public class InstallRationale implements Rationale<File> {

    @Override
    public void showRationale(Context context, File data, final RequestExecutor executor) {
        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(R.string.title_dialog)
                .setMessage(R.string.message_install_failed)
                .setPositiveButton(R.string.setting, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        executor.execute();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        executor.cancel();
                    }
                })
                .show();
    }
}