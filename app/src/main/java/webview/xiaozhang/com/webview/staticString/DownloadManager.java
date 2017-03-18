package webview.xiaozhang.com.webview.staticString;

import android.content.Context;
import android.os.Environment;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import webview.xiaozhang.com.webview.util.SPUtils;

/**
 * Created by Leaves on 2017/3/10.
 */

public class DownloadManager {
    private Context mContext;
    private String downloadFilePath;
    private String sdPath = Environment.getExternalStorageDirectory().toString();
    private File rootFile;

    public DownloadManager(Context ctx) {
        downloadFilePath = "test";
        //判断sd是否存在
        rootFile = new File(sdPath + "/" + downloadFilePath);
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        this.mContext = ctx;
    }

    public void setDownloadFilePath(String downloadFilePath) {
        this.downloadFilePath = downloadFilePath;
    }

    public void download(final String url,final DownloadCallback call)  {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    String fileName = url.substring(url.lastIndexOf("/"));
//                  File downLoadFile = new File(rootFile, fileName);

// RandomAcces sFile accessFile = new RandomAccessFile()
                    //HTTP 支持部分下载  需要在请求头 Range  "100-200"
                    OkHttpClient client = new OkHttpClient.Builder().build();
                    int downloadSize = SPUtils.defaultSP(mContext).getValue(url,-1);
                    int fileTotalSize = SPUtils.defaultSP(mContext).getValue(url+"length",-1);
                    Request request ;
                    if ( downloadSize == -1){
                        request = new Request.Builder()
                                .url(url)
                                .build();
                    }else{
                       request = new Request.Builder()
                                .url(url)
                                .addHeader("Range","bytes="+(downloadSize + 1) +"-"+fileTotalSize)
                                .build();
                    }

                    Response response = client.newCall(request).execute();
                    int fileLenght = (int) response.body().contentLength();
                    InputStream is = response.body().byteStream();
                    RandomAccessFile downLoadFile = new RandomAccessFile(new File(rootFile, fileName),"rw");
                    downLoadFile.setLength(fileLenght);
//                  BufferedOutputStream baos = new BufferedOutputStream(new FileOutputStream(downLoadFile));

                    byte[] buffer = new byte[1024 * 10];
                    int length = -1;
                    int totalLength = 0;
                    if (downloadSize != -1){
                        downLoadFile.seek( downloadSize+ 1);
                        totalLength = downloadSize;
                    }
                    while ((length = is.read(buffer)) != -1) {

                        downLoadFile.write(buffer, 0, length);
                        totalLength += length;
                        //存储当前下载记录
                        SPUtils.defaultSP(mContext).setValue(url,totalLength);
                        SPUtils.defaultSP(mContext).setValue(url+"length",fileLenght);
                        call.onProgress(fileLenght,totalLength);
                    }
                    is.close();
                    downLoadFile.close();
                    SPUtils.defaultSP(mContext).remove(url);
                    SPUtils.defaultSP(mContext).remove(url+"length");
                    call.onSuccess(downLoadFile.getFD().toString());
                }catch (Exception ex){
                    call.onError(ex);
                }
            }
        }.start();
    }
}
