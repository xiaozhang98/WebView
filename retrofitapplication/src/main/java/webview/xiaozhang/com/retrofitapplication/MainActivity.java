package webview.xiaozhang.com.retrofitapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import webview.xiaozhang.com.retrofitapplication.bean.RetrofitUser;
import webview.xiaozhang.com.retrofitapplication.net.IRetrofitApi;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void retrofit(View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        IRetrofitApi iRetrofitApi = retrofit.create(IRetrofitApi.class);
        iRetrofitApi.getRetrofit("octocat")
                .enqueue(new Callback<List<RetrofitUser>>() {
                    @Override
                    public void onResponse(Call<List<RetrofitUser>> call, Response<List<RetrofitUser>> response) {
                        Log.e(TAG, "onResponse: " + response.body().size());

                    }

                    @Override
                    public void onFailure(Call<List<RetrofitUser>> call, Throwable t) {

                    }
                });
    }

    public void rxMsg(View view) {
        new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(IRetrofitApi.class)
                .getMsg("octocat")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RetrofitUser>() {
                    @Override
                    public void call(RetrofitUser retrofitUser) {
                        Log.e(TAG, "call: " +retrofitUser.toString());
                    }
                });


    }
}
