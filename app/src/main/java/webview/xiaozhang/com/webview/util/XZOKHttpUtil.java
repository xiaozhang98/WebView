package webview.xiaozhang.com.webview.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import webview.xiaozhang.com.webview.staticString.StringCallBack;

/**
 * Created by Administrator on 2017/3/8.
 */

public class XZOKHttpUtil {
    private OkHttpClient okclient;

    private static XZOKHttpUtil mxXzokHttpUtil;

    private XZOKHttpUtil(){
        okclient=new OkHttpClient.Builder().build();
    }

    public static XZOKHttpUtil getMxXzokHttpUtil(){
        if (mxXzokHttpUtil==null){
            mxXzokHttpUtil=new XZOKHttpUtil();
        }
        return mxXzokHttpUtil;
    }

    /**
     * get请求同步
     */
    public Response doGet(String url) throws IOException {
        Request request=new Request.Builder().url(url).build();
        return okclient.newCall(request).execute();
    }
    /**
     * get请求同步
     */
    public void doGetAsyoc(String url, Callback callback){
        Request request=new Request.Builder().url(url).build();
        okclient.newCall(request).enqueue(callback);
    }



    /**
     * post请求同步
     */
    public Response doPost(String url, Map<String,String> canshu) throws IOException {
        FormBody.Builder fb=new FormBody.Builder();
        Set set=canshu.keySet();
        Iterator it=set.iterator();
        while (it.hasNext()){
            String key= (String) it.next();
            fb.add(key,canshu.get(key));
        }
        Request request=new Request.Builder().url(url).post(fb.build()).build();
        return okclient.newCall(request).execute();
    }
    /**
     * post请求同步
     */
    public void doPostAsyoc(String url, Map<String,String> canshu,Callback callback) throws IOException {
        FormBody.Builder fb=new FormBody.Builder();
        Set set=canshu.keySet();
        Iterator it=set.iterator();
        while (it.hasNext()){
            String key= (String) it.next();
            fb.add(key,canshu.get(key));
        }
        Request request=new Request.Builder().url(url).post(fb.build()).build();
        okclient.newCall(request).enqueue(callback);
    }

}
