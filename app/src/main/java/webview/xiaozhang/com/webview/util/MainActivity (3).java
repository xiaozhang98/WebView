//package webview.xiaozhang.com.webview.util;
//
//import android.Manifest;
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.ApplicationInfo;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.provider.Settings;
//import android.support.annotation.NonNull;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//
//import com.example.leaves.okhttpdemo.bean.TalkBean;
//import com.example.leaves.okhttpdemo.net.DownloadCallback;
//import com.example.leaves.okhttpdemo.net.DownloadManager;
//import com.example.leaves.okhttpdemo.net.JavaBeanCallBack;
//import com.example.leaves.okhttpdemo.net.NetApi;
//import com.example.leaves.okhttpdemo.net.NetService;
//
//import okhttp3.Call;
//
//public class MainActivity extends AppCompatActivity {
//
//    private static final String TAG = "MainActivity";
//
//    private Button btnGet;
//    private Button btnPost;
//    private ProgressBar progressBar;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        btnGet = (Button) findViewById(R.id.btn_get);
//        btnPost = (Button) findViewById(R.id.btn_post);
//        progressBar = (ProgressBar) findViewById(R.id.progressBar);
//        btnGet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE))
//                        showDialogToSettting();
//                    else {
//                        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED) {
//
//                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
//                        } else {
//                            NetService.talk(new JavaBeanCallBack<TalkBean>(TalkBean.class) {
//                                @Override
//                                public void onResponse(Call call, TalkBean result) {
//                                    DownloadManager manager = new DownloadManager(MainActivity.this);
//                                    manager.download(NetApi.API_DOWNLOAD_WEIBO, new DownloadCallback() {
//                                        @Override
//                                        public void onProgress(final int total, final int progress) {
//                                            runOnUiThread(new Runnable() {
//                                                @Override
//                                                public void run() {
//                                                    int pro = (int) (progress / (double) total * 100);
//
//                                                    progressBar.setProgress(pro);
//                                                }
//                                            });
//                                        }
//
//                                        @Override
//                                        public void onSuccess(String fileName) {
//                                            Log.v("TTT", fileName);
//                                        }
//
//                                        @Override
//                                        public void onError(Exception ex) {
//                                            Log.v("TTT", ex.getMessage());
//
//                                        }
//                                    });
//                                }
//                            });
//                        }
//                    }
//                }
//
//            }
//        });
//
//        try {
//            ApplicationInfo info = getPackageManager().getApplicationInfo(getPackageName(),PackageManager.GET_META_DATA);
//            String channel =  info.metaData.getString("UMENG_CHANNEL");
//            Toast.makeText(this, channel, Toast.LENGTH_SHORT).show();
//
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 100) {
//            if (grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                NetService.talk(new JavaBeanCallBack<TalkBean>(TalkBean.class) {
//                    @Override
//                    public void onResponse(Call call, TalkBean result) {
//                        DownloadManager manager = new DownloadManager(MainActivity.this);
//                        manager.download(NetApi.API_DOWNLOAD_WEIBO, new DownloadCallback() {
//                            @Override
//                            public void onProgress(final int total, final int progress) {
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        int pro = (int) (progress / (double) total * 100);
//
//                                        progressBar.setProgress(pro);
//                                    }
//                                });
//                            }
//
//                            @Override
//                            public void onSuccess(String fileName) {
//                                Log.v("TTT", fileName);
//                            }
//
//                            @Override
//                            public void onError(Exception ex) {
//                                Log.v("TTT", ex.getMessage());
//
//                            }
//                        });
//                    }
//                });
//            } else {
//
//
//            }
//        }
//    }
//
//    private void showDialogToSettting() {
//
//        Dialog dialog = new AlertDialog.Builder(this)
//                .setTitle("存储权限不可用")
//                .setMessage("请在-应用设置-权限-中，允许使用存储权限来保存下载数据")
//                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        // 跳转到应用设置界面
//                        goToAppSetting();
//                    }
//                })
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(MainActivity.this, "没有使用sd卡权限 无法下载", Toast.LENGTH_SHORT).show();
//                    }
//                }).setCancelable(false).show();
//    }
//
//    // 跳转到当前应用的设置界面
//    private void goToAppSetting() {
//        Intent intent = new Intent();
//        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//        Uri uri = Uri.fromParts("package", getPackageName(), null);
//        intent.setData(uri);
//        startActivityForResult(intent, 123);
//    }
//}
