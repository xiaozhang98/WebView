package webview.xiaozhang.com.webview.staticString;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/8.
 */

public abstract class StringCallBack implements Callback {

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(final Call call, final Response response) {
        Handler handler=new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {

                try {
                    onResponse(call,response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    public abstract void onResponse(Call call, String result);


}
