package webview.xiaozhang.com.webview.staticString;

/**
 * Created by Leaves on 2017/3/10.
 */

public interface DownloadCallback {

    void onProgress(int total, int progress);
    void onSuccess(String fileName);
    void onError(Exception ex);

}
