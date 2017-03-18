package webview.xiaozhang.com.webview.service;

import java.io.IOException;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.Response;
import webview.xiaozhang.com.webview.staticString.ApkString;
import webview.xiaozhang.com.webview.staticString.StringCallBack;
import webview.xiaozhang.com.webview.util.XZOKHttpUtil;

/**
 * Created by Administrator on 2017/3/8.
 */

public class ApkService {
    public static void getReademe(Callback callback){
        XZOKHttpUtil.getMxXzokHttpUtil().doGetAsyoc(ApkString.API_GITHUB,callback);
    }
    public static void getImage(Callback callback){
        XZOKHttpUtil.getMxXzokHttpUtil().doGetAsyoc(ApkString.API_IMAGE,callback);
    }
    public static Response getReadme() throws IOException {
        return XZOKHttpUtil.getMxXzokHttpUtil().doGet(ApkString.API_GITHUB);
    }

    public static Response getJqrme(Map<String,String> canshu) throws IOException {
       return XZOKHttpUtil.getMxXzokHttpUtil().doPost(ApkString.API_JQR,canshu);
    }

    public static void getJqrme(Map<String,String> canshu, Callback callback) throws IOException {
        XZOKHttpUtil.getMxXzokHttpUtil().doPostAsyoc(ApkString.API_JQR,canshu,callback);
    }


}
