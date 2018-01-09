package cn.lushantingyue.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

//    private final CompositeDisposable disposables = new CompositeDisposable();

    private MainActivity act;
    static ArrayList<String> list;
    static {
         list = new ArrayList<>();
        String arr[] = {"One", "Two", "Three", "Four", "Five"};
        for (int i = 0; i < 5; i++) {
            list.add(arr[i]);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        act = this;
        findViewById(R.id.btn_start_rxjava).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                onRunSchedulerRuning();
            }
        });
        findViewById(R.id.basic).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                basicObservableCreate();
            }
        });
        findViewById(R.id.basic_just).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                basicObservableCreateJust();
            }
        });
        findViewById(R.id.basic_fromiterable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                basicObservableCreateFromIterable();
            }
        });
        findViewById(R.id.btn_operator_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operatorMap();
            }
        });
        findViewById(R.id.btn_operator_flatmap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operatorFlatMap();
            }
        });
        findViewById(R.id.btn_operator_filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operatorFilter();
            }
        });
        findViewById(R.id.btn_operator_take).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operatorTake();
            }
        });
        findViewById(R.id.btn_operator_doOnNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operatorDoOnNext();
            }
        });
    }

    private void basicObservableCreate() {
//        create()
//        step 01.
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                printCurrentThreadInfo();
                Logger.i("我来发射数据01 >>> ");
                emitter.onNext("数据01");
            }
        });
//        step 02.
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                printCurrentThreadInfo();
                Toast.makeText(act, "接收到>>> " + s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
//        step 03.订阅
        observable.subscribe(observer);
//        just() 简化写法
//        fromIterable()    遍历集合, 拆分成数次传入单个的元素
//        defer() 当观察者订阅时，才创建Observable, 且针对每个观察者创建都是一个新的Observable
//        interval() 循环定时触发
//        range() 发送整数序列
//        timer()   延时发送
//        repeat() 可重复调用
    }

    private void basicObservableCreateJust() {
        Observable<String> observable = Observable.just("hello");
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                printCurrentThreadInfo();
                Toast.makeText(act, "接收到>>> " + s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);
    }

    private void basicObservableCreateFromIterable() {
        ArrayList<String> list = new ArrayList<>();
        for(int i=0; i < 10; i++) {
            list.add("data " + i);
        }
        Observable<String> observable = Observable.fromIterable(list);
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
//                Toast.makeText(act, "接收到>>> " + s, Toast.LENGTH_SHORT).show();
                printCurrentThreadInfo();
                Logger.i("接收到>>> " + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);
    }

    /**
     * //        关于操作符:
     //              |-- map()
     //              |-- flatMap()
     //              |-- filter()  按条件过滤数据
     //              |-- take() 指定输出数量
     //              |-- doOnNext() 允许我们在每次输出一个元素之前做一些额外的事情
     */
    private void operatorMap() {

//        转换被观察数据的形式, 一般用于单个数据的转换操作
        Observable<String> observable = Observable.just("One");
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {
                        printCurrentThreadInfo();
                        return s.length();
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        short str = integer.shortValue();
                        String tag = Thread.currentThread().getName();
                        Toast.makeText(act, "当前线程:" + tag + " map操作 >>> " + str, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void operatorFlatMap() {
//       flatMap 重新生成一个数据形式不同的Observable对象, 该方法专门为集合操作提供便利
        Observable<ArrayList<String>> observable = Observable.just(list);
        observable.flatMap(new Function<ArrayList<String>, ObservableSource<?>>() {
            @Override
            public ObservableSource<String> apply(ArrayList<String> list) throws Exception {
                printCurrentThreadInfo();

                return Observable.fromIterable(list);
            }
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                String str = (String) o;
                String tag = Thread.currentThread().getName();
                Toast.makeText(act, "当前线程:" + tag + " map操作 >>> " + str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void operatorFilter() {
//        定义过滤规则, 根据规则过滤传递给观察者的数据
        Observable<ArrayList<String>> observable = Observable.just(list);
        observable.flatMap(new Function<ArrayList<String>, ObservableSource<?>>() {
            @Override
            public ObservableSource<String> apply(ArrayList<String> list) throws Exception {
                printCurrentThreadInfo();
                return Observable.fromIterable(list);
            }
        }).map(new Function<Object, String>() {
            @Override
            public String apply(Object o) throws Exception {
                return (String) o;
            }
        }).filter(new Predicate<String>() {
            @Override
            public boolean test(String str) throws Exception {
                printCurrentThreadInfo();
                return str.length() > 3;    // 只传递长度大于3的字符串
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String str) throws Exception {
                        String tag = Thread.currentThread().getName();
                        Toast.makeText(act, "当前线程:" + tag + " map操作 >>> " + str, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void operatorTake() {
        Observable<ArrayList<String>> observable = Observable.just(list);
        observable.flatMap(new Function<ArrayList<String>, ObservableSource<?>>() {
            @Override
            public ObservableSource<String> apply(ArrayList<String> list) throws Exception {
                printCurrentThreadInfo();

                return Observable.fromIterable(list);
            }
        }).map(new Function<Object, String>() {
            @Override
            public String apply(Object o) throws Exception {
                return (String) o;
            }
        }).take(3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String str) throws Exception {
                        String tag = Thread.currentThread().getName();
                        Toast.makeText(act, "当前线程:" + tag + " map操作 >>> " + str, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void operatorDoOnNext() {
//        在通知观察者之间的做一些附加操作
        Observable<ArrayList<String>> observable = Observable.just(list);
        observable.flatMap(new Function<ArrayList<String>, ObservableSource<?>>() {
            @Override
            public ObservableSource<String> apply(ArrayList<String> list) throws Exception {
                printCurrentThreadInfo();

                return Observable.fromIterable(list);
            }
        }).map(new Function<Object, String>() {
            @Override
            public String apply(Object o) throws Exception {
                return (String) o;
            }
        }).take(3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Logger.i("附加操作>>> ");
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String str) throws Exception {
                        String tag = Thread.currentThread().getName();
                        Toast.makeText(act, "当前线程:" + tag + " map操作 >>> " + str, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Scheduler 的 API

     ● Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。

     ●Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。

     ●Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。行为模式和 newThread() 差不多，区别在于 io() 的内部实现是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io() 中，可以避免创建不必要的线程。

     ●Schedulers.computation(): **计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，例如图形的计算。这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。

     ● Android 还有一个专用的** AndroidSchedulers.mainThread()**，它指定的操作将在 Android 主线程运行。
     */
    private void onRunSchedulerRuning() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                printCurrentThreadInfo();
                Logger.i("当前线程: " + Thread.currentThread().getName());

                emitter.onNext(6);  // 事件的生产过程
            }
        }).subscribeOn(Schedulers.io())     // 生产者线程
                .observeOn(AndroidSchedulers.mainThread())  // 消费者线程
                .subscribe(new Consumer<Integer>() { // 观察者触发消费
                    @Override
                    public void accept(Integer integer) throws Exception {  // 事件的消费过程
                        printCurrentThreadInfo();
                        Logger.i(integer + "");
                    }

                });
    }

    private void printCurrentThreadInfo() {
        String tag = Thread.currentThread().getName();
        Logger.i("当前线程: " + tag);
    }

}
