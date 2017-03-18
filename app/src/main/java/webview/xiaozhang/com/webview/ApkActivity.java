package webview.xiaozhang.com.webview;

import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import webview.xiaozhang.com.webview.service.ApkService;
import webview.xiaozhang.com.webview.staticString.ApkString;
import webview.xiaozhang.com.webview.staticString.DownloadCallback;
import webview.xiaozhang.com.webview.staticString.DownloadManager;
import webview.xiaozhang.com.webview.staticString.ImageCallBack;
import webview.xiaozhang.com.webview.staticString.StringCallBack;

public class ApkActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private int length = 0;
    private static final String TAG = "ApkActivity";
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apk);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        imageView = (ImageView) findViewById(R.id.imageview);
        new Thread(new Runnable() {
            @Override
            public void run() {
                DownloadManager downloadManager=new DownloadManager(ApkActivity.this);
                downloadManager.download(ApkString.API_DOWNLOAD_WEIBO, new DownloadCallback() {
                    @Override
                    public void onProgress(int total, int progress) {
                        int pro= (int) ((progress/(double)total)*100);
                        progressBar.setProgress(pro);
//                        Log.i(TAG, "onProgress: "+progress);
                    }

                    @Override
                    public void onSuccess(String fileName) {
                        Log.i(TAG, "fileName---------: "+fileName);
                    }

                    @Override
                    public void onError(Exception ex) {
                        Log.i(TAG, "onError:------------ "+ex.toString());
                    }
                });
            }
        }).start();

//        okhttpget();
//        okhttppost();
//        xzokhttppostyibu();
//        xzokhttppost();
//        okhttpgetImage();
    }

    /**
     * get访问
     */
    public void okhttpget() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .url("https://raw.github.com/square/okhttp/master/README.md")
                .build();

        okhttp3.Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.i(TAG, "onFailure: 错误");
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.i(TAG, "onResponse: " + str);
            }
        });
    }

    /**
     * post访问
     */
    public void okhttppost() {
        new OkHttpClient()
                .newCall(new Request.Builder()
                        .url("http://op.juhe.cn/robot/index")
                        .post(new FormBody.Builder()
                                .add("info", "你好啊")
                                .add("key", "c42e8ba0246500486843f471dd36ab28")
                                .build()
                        ).build()
                ).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                Log.i(TAG, "onResponse: " + response.body().string());
            }
        });
    }

    public void xzokhttppostyibu() {
        try {
            Map<String, String> canshu = new HashMap<>();
            canshu.put("info", "你好啊");
            canshu.put("key", "c42e8ba0246500486843f471dd36ab28");
            ApkService.getJqrme(canshu, new StringCallBack() {
                @Override
                public void onResponse(okhttp3.Call call, String result) {
                    Log.i(TAG, "onCreate: " + result);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void xzokhttppost() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, String> canshu = new HashMap<>();
                    canshu.put("info", "你好啊");
                    canshu.put("key", "c42e8ba0246500486843f471dd36ab28");
                    Response response = ApkService.getJqrme(canshu);
                    Log.i(TAG, "xzokhttppost: " + response.body().string());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void okhttpgetImage() {

        ApkService.getImage(new ImageCallBack() {
            @Override
            public void onResponse(okhttp3.Call call, Bitmap result) {
                imageView.setImageBitmap(result);
            }
        });

    }

}
