package cn.lushantingyue.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
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
        findViewById(R.id.action01).setOnClickListener(this);
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
                useLambda();
                break;
            case R.id.action01:
                useAction();
                break;
            default:
                break;
        }
    }

    private void useLambda() {
        Observable<String> observable = Observable.just("One", "Two", "Three", "Four", "Five");
        observable.map(str -> "Number:" + str)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(str -> Logger.i("currentThread: " + Thread.currentThread().getName() + ">>> " + str))
                .subscribe(str -> setResult(str));
    }

//    ActionN 和 FuncN 遵循Java 8的命名规则。
//    RxJava1.x RxJava2.X Action类名变更
//    其中，Action0 --> Action，
//               Action1 --> Consumer，
//               Action2 --> BiConsumer，Action3 至 Action9不再使用了
//               ActionN --> Consumer<Object[]>
//
//    同样，Func --> Function，
//               Func2 --> BiFunction，
//               Func3 ~ Func9 --> Function3 ~ Function9，
//               FuncN --> Function<Object[], R>

    private void toastTips(String str) {
        Toast.makeText(act, str, Toast.LENGTH_SHORT).show();
    }

    private void useAction() {
        Observable<List<String>> observable = Observable.just(Arrays.asList("Apple", "Banana", "Orange", "Pineapple", "Pear"));

        /**
         * 定义observer 内单个响应事件
         */
        // 无参无返回值的响应动作
        Action onCompletedAction = new Action() {
            @Override
            public void run() throws Exception {
                toastTips("completed");
            }
        };

        //处理onNext()中的内容
        Consumer<String> onNextAction = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Logger.i(s);
            }
        };
        //处理onError()中的内容
        Consumer<Throwable> onErrorAction = new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Logger.i("error>>> ");
            }
        };

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(list -> Observable.fromIterable(list))
                .subscribe(onNextAction, onErrorAction, onCompletedAction);
    }

    private void setResult(String result) {
        Toast.makeText(act, "currentThread: " + Thread.currentThread().getName() + ">>> " + result, Toast.LENGTH_SHORT).show();
        this.result = result;
    }
}
