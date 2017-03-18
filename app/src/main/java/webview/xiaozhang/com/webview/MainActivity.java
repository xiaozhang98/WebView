package webview.xiaozhang.com.webview;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import webview.xiaozhang.com.webview.adapter.lesson12.utils.SDCardUtils;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private WebView webView;
    private TextView textView;
    private Button btn_xiazai;
    private byte[] picByte=null;
    private Bitmap bitmap[];

//    private Button btn_javascript;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_xiazai=(Button) findViewById(R.id.xiazai_apk);
        webView= (WebView) findViewById(R.id.my_webview);
        textView= (TextView) findViewById(R.id.textView);
//        btn_javascript= (Button) findViewById(R.id.btn_javascript);
//        webView.loadUrl("http://m.com");

        webView.loadUrl("file:///android_asset/Test.html");
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                textView.setText(title);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });
        webView.setWebViewClient(new WebViewClient(){
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                textView.setText(request.getMethod());
                return true;
            }


            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                if (error.getErrorCode()==404){
                    view.loadUrl("file:///android_asset/404text.html");
                }else {
//                    view.loadUrl("file:///android_asset/404text.html");
                    super.onReceivedError(view, request, error);
                }

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                if (errorCode==404){
                    view.loadUrl("file:///android_asset/404text.html");
                }else {
//                    view.loadUrl("file:///android_asset/404text.html");
                    view.loadUrl("file:///android_asset/404text.html");
                }
            }
        });


        WebSettings settings=webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(this,"android");

        //点击下载图片
        btn_xiazai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: 111" );
                new Task().execute("http://pic.58pic.com/58pic/13/65/34/61x58PIC8IQ_1024.jpg");
//                new Task().execute("http://down.apps.sina.cn/sinasrc/fd/63/547409a65a56fb3d6401f28296d963fd.apk");
//                SavaImage(bitmap, Environment.getExternalStorageDirectory().getPath()+"/ab");

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.loadDataWithBaseURL(null,"","text/html","utf-8",null);
        ((ViewGroup)webView.getParent()).removeView(webView);
        webView.destroy();
        webView=null;
        Log.e(TAG, "onDestroy: xiao" );
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }else{
            super.onBackPressed();
        }

    }
    public void dianji(View view){
        webView.post(new Runnable() {
            @Override
            public void run() {

                webView.loadUrl("javascript:hello()");
                Log.i(TAG, "run: sssspps");
            }
        });
    }
    @JavascriptInterface
    public void hellWorld(String s){
        Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();
    }

    public void btnimg_chick(View view){
        Intent intent=new Intent(MainActivity.this,ImageListActivity.class);
        startActivity(intent);
    }
    /**
     * 获取网络图片
     * @param imageurl 图片网络地址
     * @return Bitmap 返回位图
     */
    public void GetImageInputStream(String imageurl){
        URL url;
        HttpURLConnection connection=null;
        byte[] picBy=null;
        try {
            url = new URL(imageurl);
            connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            if (connection.getResponseCode()==200) {
                Log.e(TAG, "GetImageInputStream: xiazai" );
                InputStream fis = connection.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] bytes = new byte[1024];
                int length = -1;
                while ((length = fis.read(bytes)) != -1) {
                    bos.write(bytes, 0, length);
                }
                picBy = bos.toByteArray();
                picByte = bos.toByteArray();
                Log.e(TAG, "onClick: 333" );
                bos.close();
                fis.close();
                if (picByte!=null){

                    SDCardUtils.saveFileToSDCard(picByte,Environment.getExternalStorageDirectory().getPath()+"abc",System.currentTimeMillis()+".apk");
                    Log.e(TAG, "下载成功" );
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 保存位图到本地
     * @param bitmap
     * @param path 本地路径
     * @return void
     */
    public void SavaImage(Bitmap bitmap, String path){
        File file=new File(path);
        FileOutputStream fileOutputStream=null;
        //文件夹不存在，则创建它
        if(!file.exists()){
            file.mkdir();
        }
        try {
            fileOutputStream=new FileOutputStream(path+"/"+System.currentTimeMillis()+".apk");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100,fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 异步线程下载图片
     *
     */
    class Task extends AsyncTask<String, Integer, Void> {

        protected Void doInBackground(String... params) {
            Log.e(TAG, "onClick: 222" );
            GetImageInputStream((String)params[0]);
            return null;
        }



    }

    /**
     * okhttp访问
     */

    public void okhttp(View view){

    }
    public void duandian(View view){
        //版本大于6.0
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            Log.i(TAG, "duandian: 进入");
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                shouDialogToSetting();
            } else {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                }else{
                    Log.i(TAG, "duandian: 下载");
                    //下载
//                    Intent intent=new Intent(MainActivity.this,ApkActivity.class);
//                    startActivity(intent);
                }
            }
        }
//        Intent intent=new Intent(MainActivity.this,ApkActivity.class);
//        startActivity(intent);
    }

    private void shouDialogToSetting() {
        Dialog dialog=new AlertDialog.Builder(this)
                .setTitle("存储权限不可用")
                .setMessage("请在-应用设置-权限-中，允许使用存储权限来保存下载数据")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //跳转到应用设置界面
                        goToAppSetting();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "没有使用sd卡权限 无法下载", Toast.LENGTH_SHORT).show();
                    }
                }).setCancelable(false).show();
    }

    private void goToAppSetting() {
        Intent intent=new Intent();
        intent.setAction(Settings.ACTION_SETTINGS);
        Uri uri=Uri.fromParts("package",getPackageName(),null);
        intent.setData(uri);
        startActivityForResult(intent,123);
    }
}
