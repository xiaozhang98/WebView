//package webview.xiaozhang.com.webview.util;
//
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//
//import com.example.leaves.rxjavademo.bean.NetBean;
//import com.example.leaves.rxjavademo.bean.Student;
//import com.example.leaves.rxjavademo.bean.StudentClass;
//import com.google.gson.Gson;
//
//import org.reactivestreams.Subscriber;
//import org.reactivestreams.Subscription;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import io.reactivex.BackpressureStrategy;
//import io.reactivex.Flowable;
//import io.reactivex.FlowableEmitter;
//import io.reactivex.FlowableOnSubscribe;
//import io.reactivex.Observable;
//import io.reactivex.ObservableEmitter;
//import io.reactivex.ObservableOnSubscribe;
//import io.reactivex.ObservableSource;
//import io.reactivex.Observer;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.disposables.Disposable;
//import io.reactivex.functions.Action;
//import io.reactivex.functions.BiFunction;
//import io.reactivex.functions.Consumer;
//import io.reactivex.functions.Function;
//import io.reactivex.functions.Predicate;
//import io.reactivex.schedulers.Schedulers;
//import okhttp3.FormBody;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//
//public class MainActivity extends AppCompatActivity {
//    private static final String TAG = "MainActivity";
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//    }
//    private Disposable mDis;
//
//    public void demo1(View view){
////        // 被观察者
////        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
////            @Override
////            public void subscribe(ObservableEmitter<String> e) throws Exception {
////                Log.i(TAG, "subscribe: 哈哈" );
////                e.onNext("哈哈");
////                Log.i(TAG, "subscribe: 嘿嘿" );
////                e.onNext("嘿嘿");
////                Log.i(TAG, "subscribe: onComplete" );
////                e.onComplete();
////                Log.i(TAG, "subscribe: 呵呵" );
////                e.onNext("呵呵");
////            }
////        });
////        //观察者
////        Observer<String> observer = new Observer<String>() {
////            @Override
////            public void onSubscribe(Disposable d) {
////
////            }
////
////            @Override
////            public void onNext(String value) {
////                Toast.makeText(MainActivity.this, value, Toast.LENGTH_SHORT).show();
////            }
////
////            @Override
////            public void onError(Throwable e) {
////
////            }
////
////            @Override
////            public void onComplete() {
////                Toast.makeText(MainActivity.this, "onComplete", Toast.LENGTH_SHORT).show();
////            }
////        };
////
////        //订阅
////        observable.subscribe(observer);
//
//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                Log.i(TAG, "subscribe: 哈哈" );
//                e.onNext("哈哈");
//                Log.i(TAG, "subscribe: 嘿嘿" );
//                e.onNext("嘿嘿");
//                Log.i(TAG, "subscribe: 哈哈" );
//                e.onNext("哈哈");
//                Log.i(TAG, "subscribe: 嘿嘿" );
//                e.onNext("嘿嘿");
//                Log.i(TAG, "subscribe: onComplete" );
//                e.onComplete();
//                Log.i(TAG, "subscribe: 呵呵" );
//                e.onNext("呵呵");
//            }
//        }).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
//                mDis.dispose();
//
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        }, new Action() {
//            @Override
//            public void run() throws Exception {
//                Toast.makeText(MainActivity.this, "OnComplete", Toast.LENGTH_SHORT).show();
//            }
//        }, new Consumer<Disposable>() {
//            @Override
//            public void accept(Disposable disposable) throws Exception {
//                mDis = disposable;
//            }
//        });
//    }
//
//    public void demo_just(View view){
//        Observable.just("hehe","haha","heihei")
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) throws Exception {
//                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//
//    public void demo_from(View view){
//        String[] strArr = {"hehe","haha","heihei"};
//        Observable.fromArray(strArr)
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) throws Exception {
//                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//    public void demo_on(View view) {
//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                Log.i(TAG, "subscribe: " + Thread.currentThread().getName());
//                OkHttpClient client = new OkHttpClient.Builder().build();
//
//                FormBody body = new FormBody.Builder()
//                        .add("info", "你好啊")
//                        .add("key", "c42e8ba0246500486843f471dd36ab28")
//                        .build();
//                Request request = new Request.Builder()
//                        .url("http://op.juhe.cn/robot/index")
//                        .post(body)
//                        .build();
//
//                Response response = client.newCall(request).execute();
//
//                e.onNext(response.body().string());
//            }
//        })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .observeOn(Schedulers.io())
//                .subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                Log.i(TAG, "accept: " + Thread.currentThread().getName());
//                Log.i(TAG, "accept: " + s);
//            }
//        });
//    }
//
//    public void demo_map(View view) {
//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                Log.i(TAG, "subscribe: " + Thread.currentThread().getName());
//                OkHttpClient client = new OkHttpClient.Builder().build();
//
//                FormBody body = new FormBody.Builder()
//                        .add("info", "你好啊")
//                        .add("key", "c42e8ba0246500486843f471dd36ab28")
//                        .build();
//                Request request = new Request.Builder()
//                        .url("http://op.juhe.cn/robot/index")
//                        .post(body)
//                        .build();
//
//                Response response = client.newCall(request).execute();
//
//                e.onNext(response.body().string());
//            }
//        })
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
//                .map(new Function<String, NetBean>() {
//                    @Override
//                    public NetBean apply(String s) throws Exception {
//                        Log.i(TAG, "apply: " + Thread.currentThread().getName());
//                        Gson  gson = new Gson();
//                        return gson.fromJson(s,NetBean.class);
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<NetBean>() {
//                        @Override
//                        public void accept(NetBean netBean) throws Exception {
//                            Log.i(TAG, "accept: " + Thread.currentThread().getName());
//                            Toast.makeText(MainActivity.this, netBean.getReason(), Toast.LENGTH_SHORT).show();
//                        }
//                });
//
//
//
//    }
//
//    private List<StudentClass> getClasses(){
//        List<StudentClass> list = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            StudentClass studentClass = new StudentClass();
//            studentClass.setClassName((i+1)+"班");
//            List<Student> stus = new ArrayList<>();
//            for (int j = 0; j < 3; j++) {
//                Student stu = new Student();
//                stu.setAge(1);
//                stu.setName( (i+1)+"班  :张" + j);
//                stus.add(stu);
//            }
//
//            studentClass.setStudents(stus);
//            list.add(studentClass);
//        }
//        return list;
//    }
//
//    public void demo_flatmap(View view) {
//        List<StudentClass> list = getClasses();
//
//        Observable.fromIterable(list)
//                .flatMap(new Function<StudentClass, ObservableSource<Student>>() {
//                    @Override
//                    public ObservableSource<Student> apply(StudentClass studentClass) throws Exception {
//                        return Observable.fromIterable(studentClass.getStudents());
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Student>() {
//                    @Override
//                    public void accept(Student student) throws Exception {
//                        Log.e(TAG, "accept: " + student.getName() );
//                    }
//                });
//
//    }
//
//    public void demo_zip(View view) {
//        Observable<String> ob1 = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                e.onNext("张三");
//                e.onNext("张4");
//                e.onNext("张4");
//                e.onNext("张6");
//                e.onNext("张7");
//            }
//        });
//
//        Observable<Integer> ob2 = Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                e.onNext(1);
//                e.onNext(2);
//                e.onNext(3);
//                e.onNext(4);
//
//            }
//        });
//
//        Observable.zip(ob1, ob2, new BiFunction<String, Integer, String>() {
//            @Override
//            public String apply(String s, Integer integer) throws Exception {
//                return "姓名：" + s + ";年龄：" + integer;
//            }
//        }).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                Log.e(TAG, "accept: " + s );
//            }
//        });
//
//
//    }
//
//
//    public void demo_filter(View view) {
//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                Log.i(TAG, "subscribe: 张三");
//                e.onNext("张三");
//                Log.i(TAG, "subscribe: 李四");
//                e.onNext("李四");
//                Log.i(TAG, "subscribe: 李五");
//                e.onNext("李五");
//                Log.i(TAG, "subscribe: 李六");
//                e.onNext("李六");
//                Log.i(TAG, "subscribe: 李七");
//                e.onNext("李七");
//            }
//        })
//
//                .filter(new Predicate<String>() {
//                    @Override
//                    public boolean test(String s) throws Exception {
//                        return s.contains("李");
//                    }
//                })
//
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) throws Exception {
//                        Log.i(TAG, "accept: " + s);
//                    }
//                });
//
//    }
//
//    public void demo_sample(View view) {
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                for (int i = 0; ; i++) {
//                    e.onNext(i);
//                }
//            }
//        })
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.newThread())
//                .sample(2,TimeUnit.SECONDS)
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.i(TAG, "accept: " + integer);
//                    }
//                });
//    }
//    private Subscription sub;
//    int i= 0;
//    public void demo_flowable(View view) {
//        Flowable<Integer> flowable = Flowable.create(new FlowableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
//                Log.i(TAG, "subscribe: " + e.requested());
//
//                while (true) {
//                    if (e.requested() > 0) {
//                        e.onNext(i);
//                        i++;
//                        Log.i(TAG, "subscribe: " + e.requested());
//                    }
//                }
//
//            }
//        }, BackpressureStrategy.BUFFER);
//
//        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
//            @Override
//            public void onSubscribe(Subscription s) {
//                Log.i(TAG, "onSubscribe: ");
//                sub = s;
//                s.request(10);
//                s.request(20);
//
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                Log.i(TAG, "onNext: " + integer);
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                Log.i(TAG, "onError: " + t);
//            }
//
//            @Override
//            public void onComplete() {
//                Log.i(TAG, "onComplete: ");
//            }
//        };
//
//        flowable
//                .subscribeOn(Schedulers.io())
//                .subscribe(subscriber);
//    }
//
//    public void demo_request(View view) {
//        sub.request(5);
//    }
//}
