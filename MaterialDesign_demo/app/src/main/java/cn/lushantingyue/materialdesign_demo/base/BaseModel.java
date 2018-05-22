package cn.lushantingyue.materialdesign_demo.base;

import java.util.ArrayList;

import cn.lushantingyue.materialdesign_demo.bean.Articles;
import cn.lushantingyue.materialdesign_demo.bean.Status;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/1/26 16.
 * Responsibilities:
 * Description:
 * ProjectName:
 */

public class BaseModel {

    public interface OnLoadArticlesListListener {
        void onSuccess(ArrayList<Articles> list, int page);
        void onFailure(String msg, Exception e);

        void saveDisposable(Disposable d);  // 保存Disposable 对象
    }

    public interface LoginListener {
        void onSuccess(Status status);
        void onFailure(String msg, Exception e);

        void saveDisposable(Disposable d);  // 保存Disposable 对象
    }
}
