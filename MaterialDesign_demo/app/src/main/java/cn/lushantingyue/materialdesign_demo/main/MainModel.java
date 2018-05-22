package cn.lushantingyue.materialdesign_demo.main;

import cn.lushantingyue.materialdesign_demo.bean.Status;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/5/21 16.
 * Responsibilities:
 * Description:
 * ProjectName:
 */

public class MainModel {

    public interface OnUploadPhotoListener {
        void onSuccess(Status msg);
        void onFailure(String msg, Exception e);
        void saveDisposable(Disposable d);
    }
}
