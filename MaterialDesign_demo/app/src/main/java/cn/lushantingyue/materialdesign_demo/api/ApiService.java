package cn.lushantingyue.materialdesign_demo.api;

import java.util.ArrayList;
import java.util.HashMap;

import cn.lushantingyue.materialdesign_demo.bean.ArticleDetail;
import cn.lushantingyue.materialdesign_demo.bean.Articles;
import cn.lushantingyue.materialdesign_demo.bean.LoginBean;
import cn.lushantingyue.materialdesign_demo.bean.Status;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by Lushantingyue on 2017/12/25 16.
 * Responsibilities:
 * Description:
 * ProjectName:
 */

public interface ApiService {

    //    请求全部数据
    @GET("data/jianshu")
    Observable<ArrayList<Articles>> listData();

    //    分页请求数据
    @POST("data/jianshuList")
    Observable<ArrayList<Articles>> listDataByPage(@Body HashMap<String, Integer> page);

    //    http://localhost:3000/data/jianshuDetail/:articalId
    //  根据文章关联href 请求文章详情
    @GET("data/jianshuDetail/{articalId}")
    Observable<ArticleDetail> articleDetail(@Path("articalId") String articalId);

    // 验证登陆状态
    @POST("/xauth/test")
    Observable<Status> checkPassport();

    @POST("/xauth/logout")
    Observable<LoginBean> logout();

    @POST("xauth/login")
    Observable<LoginBean> login01(@Header("username") String usrname, @Header("password") String psw);

    @POST("xauth/login")
    Observable<LoginBean> login(@Body HashMap<String, String> params);

    // 图片上传
    @Multipart
    @POST("upload/pic")
//    form-data; name="file"; filename=""
    Observable<Status> upload (@Part("file\"; filename=\"test.jpg\"") RequestBody img);
}
