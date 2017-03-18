package webview.xiaozhang.com.webview;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2017/3/8.
 */

public class apkxiazaiTask extends AsyncTask<String,Intent,String> {
    @Override
    protected String doInBackground(String... params) {
        try {
            URL url=new URL("http://down.apps.sina.cn/sinasrc/fd/63/547409a65a56fb3d6401f28296d963fd.apk");
            URLConnection connection=url.openConnection();
            int length =connection.getContentLength();//获取内容的长度
            InputStream is=connection.getInputStream();
            File file=new File(Environment.getExternalStorageDirectory(),"aaabbb");
            if (!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fos=new FileOutputStream(file);
            byte[] array=new byte[1024];
            int i = is.read(array);
            int sum = 0;
            while (i!=-1){
                fos.write(array);
                sum += i;
                publishProgress();
//                publishProgress(length,sum);
                i = is.read(array);
            }
            fos.flush();
            fos.close();
            is.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
