package webview.xiaozhang.com.retrofitapplication.net;

import android.database.Observable;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import webview.xiaozhang.com.retrofitapplication.bean.RetrofitUser;

/**
 * Created by Administrator on 2017/3/18.
 */

public interface IRetrofitApi {
    @GET("users/{user}/repos")
    Call<List<RetrofitUser>> getRetrofit(@Path("user") String name);
    @GET("users/{name}/repos")
    rx.Observable<RetrofitUser> getMsg(@Path("name") String name);
}
