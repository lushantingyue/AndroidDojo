package cn.lushantingyue.materialdesign_demo.api;

import java.util.ArrayList;
import java.util.HashMap;

import cn.lushantingyue.materialdesign_demo.base.BaseModel;
import cn.lushantingyue.materialdesign_demo.bean.Articles;
import cn.lushantingyue.materialdesign_demo.bean.LoginBean;
import cn.lushantingyue.materialdesign_demo.bean.Status;
import cn.lushantingyue.materialdesign_demo.main.MainModel;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.RequestBody;

/**
 * Created by Lushantingyue on 2017/12/28 17.
 * Responsibilities: Request network api data
 * Description: Wrapper of Retrofit
 * ProjectName:
 */

public class RemoteData {

    private ApiService service;

    public RemoteData() {
        service = RetrofitWrapper.getInstance();
    }

    public RemoteData(Interceptor interceptor) {
        service = RetrofitWrapper.getInstance(interceptor);
    }

    public void listDataByPage(final BaseModel.OnLoadArticlesListListener listener, final int curPage) {

        HashMap<String, Integer> params = new HashMap<>();
        params.put("page", curPage);

        Observable<ArrayList<Articles>> observable = service.listDataByPage(params);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Articles>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        listener.saveDisposable(d);
                    }

                    @Override
                    public void onNext(ArrayList<Articles> articles) {
                        listener.onSuccess(articles, curPage);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onFailure("数据加载错误", new Exception("retrofit request error."));
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    /**
     * jason web token 登陆认证
     * @param usrname
     * @param psw
     * @param listener
     */
    public void login(String usrname, String psw, final BaseModel.LoginListener listener) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", usrname);
        params.put("password", psw);
        Observable<LoginBean> observable = service.login(params);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        listener.saveDisposable(d);
                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        listener.onLoginSuccess(loginBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onLoginFailure("登陆失败", new Exception("login error."));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     *  注册新账号
     * @param usr
     * @param psw
     * @param listener
     */
    public void register(String usr, String psw, final BaseModel.LoginListener listener) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", usr);
        params.put("password", psw);
        Observable<LoginBean> observable = service.register(params);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        listener.saveDisposable(d);
                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        listener.onLoginSuccess(loginBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onLoginFailure("注册失败", new Exception("register error."));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

//    public void articleDetail(final DetailModelImpl.OnLoadArticlesDetailListener listener, String href) {
//
//        Observable<ArticleDetail> observable = service.articleDetail(href);
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<ArticleDetail>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        listener.saveDisposable(d);
//                    }
//
//                    @Override
//                    public void onNext(ArticleDetail articleDetail) {
//                        listener.onSuccess(articleDetail);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        listener.onFailure("数据加载错误", new Exception("retrofit request erro."));
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//
//    }

    public void uploadPhoto(final MainModel.OnUploadPhotoListener listener, final RequestBody file) {
        Observable<Status> observable = service.upload(file);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Status>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        listener.saveDisposable(d);
                    }

                    @Override
                    public void onNext(Status msg) {
                        listener.onSuccess(msg); //上传成功，返回消息
                    }

                    @Override
                    public void onError(Throwable e) {
                        // file not found exception
                        listener.onFailure("上传错误" + e.getMessage(), new Exception("retrofit request erro."));
                    }

                    @Override
                    public void onComplete() { }

                });
    }

}
