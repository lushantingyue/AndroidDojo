package cn.lushantingyue.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/1/10 10.
 * Responsibilities:
 * Description:
 * ProjectName:
 */

public class RetroLamdaActivity extends AppCompatActivity implements View.OnClickListener {

    private RetroLamdaActivity act;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrolambda);
        findViewById(R.id.uselambda).setOnClickListener(this);

        act = this;
        result = "";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.uselambda:
                retroLambda();
                break;
//                case :
//            break;
            default:
                break;
        }
    }

    private void retroLambda() {
        Observable<String> observable = Observable.just("One", "Two", "Three", "Four", "Five");
        observable.map(str -> "Number:" + str)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(str -> Logger.i("currentThread: " + Thread.currentThread().getName() + ">>> " + str) )
                .subscribe(str -> setResult(str));
    }

    private void setResult(String result) {
        Toast.makeText(act, "currentThread: " + Thread.currentThread().getName() + ">>> " + result, Toast.LENGTH_SHORT).show();
        this.result = result;
    }
}
