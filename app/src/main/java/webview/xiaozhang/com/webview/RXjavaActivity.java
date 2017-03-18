package webview.xiaozhang.com.webview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.schedulers.ScheduledRunnable;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import webview.xiaozhang.com.webview.bean.NetBean;
import webview.xiaozhang.com.webview.bean.Student;
import webview.xiaozhang.com.webview.bean.StudentClass;

public class RXjavaActivity extends AppCompatActivity {
    private static final String TAG = "RXjavaActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
    }

    public void demo1(View view) {
//        Observable<String> observable= Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                e.onNext("呵呵");
//                e.onNext("haha");
//                e.onNext("xixi");
//                e.onComplete();
//            }
//        });
//
//        Observer<String> observer=new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(String value) {
//                Toast.makeText(RXjavaActivity.this, value, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        };
//        observable.subscribe(observer);


        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("呵呵");
                e.onNext("haha");
                e.onNext("xixi");
                e.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(RXjavaActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void demo1_just(View view) {
        Observable.just("hehe", "哈哈")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(RXjavaActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void demo1_from(View view) {
        String[] str = {"a", "b"};
        Observable.fromArray(str)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(RXjavaActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void demo_on(View view) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                OkHttpClient client = new OkHttpClient.Builder().build();
                FormBody body = new FormBody.Builder()
                        .add("info", "你好啊")
                        .add("key", "c42e8ba0246500486843f471dd36ab28")
                        .build();
                Request request = new Request.Builder()
                        .url("http://op.juhe.cn/robot/index")
                        .post(body)
                        .build();
                Response response = client.newCall(request).execute();
                e.onNext(response.body().string());
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i(TAG, "accept: " + Thread.currentThread().getName());
                        Log.i(TAG, "accept: " + s);
                    }
                });
    }

    public void demo_map(View view) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                OkHttpClient client = new OkHttpClient.Builder().build();
                FormBody body = new FormBody.Builder()
                        .add("info", "你好啊")
                        .add("key", "c42e8ba0246500486843f471dd36ab28")
                        .build();
                Request request = new Request.Builder()
                        .url("http://op.juhe.cn/robot/index")
                        .post(body)
                        .build();

                Response response = client.newCall(request).execute();

                e.onNext(response.body().string());
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Function<String, NetBean>() {
                    @Override
                    public NetBean apply(String s) throws Exception {
                        Gson gson = new Gson();
                        return gson.fromJson(s, NetBean.class);
                    }
                })
                .subscribe(new Consumer<NetBean>() {
                    @Override
                    public void accept(NetBean netBean) throws Exception {
                        Log.i(TAG, "accept: " + Thread.currentThread().getName());
                        Toast.makeText(RXjavaActivity.this, netBean.getReason(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void demp_fliter(View view) {
        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("张三");
                e.onNext("李四");
                e.onNext("王五");
                e.onNext("张三");
            }
        })
                .subscribeOn(Schedulers.io())
                .filter(new AppendOnlyLinkedArrayList.NonThrowingPredicate<String>() {
                    @Override
                    public boolean test(String s) {
                        return s.contains("张");
                    }
                })
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i(TAG, "accept: " + s);
                    }
                });
    }

    public void demo_sample(View view) {
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                for (int i = 0; ; i++) {
                    e.onNext(i);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .sample(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "accept: " + integer);
                    }
                });
    }


    private List<StudentClass> getClasses() {
        List<StudentClass> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            StudentClass studentClass = new StudentClass();
            studentClass.setClassName((i + 1) + "班");
            List<Student> stus = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                Student stu = new Student();
                stu.setAge(1);
                stu.setName((i + 1) + "班  :张" + j);
                stus.add(stu);
            }

            studentClass.setStudents(stus);
            list.add(studentClass);
        }
        return list;
    }

    public void demo_zip(View view) {
        Observable<String> ob1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("张三");
            }
        });
    }

    private Subscription su;

    public void flowable(View view) {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                su.request(2);
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        su = s;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
