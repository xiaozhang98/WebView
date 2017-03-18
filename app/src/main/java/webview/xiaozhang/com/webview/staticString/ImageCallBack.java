package webview.xiaozhang.com.webview.staticString;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import webview.xiaozhang.com.webview.util.ImageUtils;

/**
 * Created by Administrator on 2017/3/8.
 */

public abstract class ImageCallBack implements Callback {
    private static final String TAG = "ImageCallBack";

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(final Call call, final Response response) {
        Handler handler=new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                InputStream is = null;
                try {
                    is = response.body().byteStream();
                    is.reset();
                    ImageUtils.ImageSize actualImageSize = ImageUtils.getImageSize(is);

                    int inSampleSize = ImageUtils.calculateInSampleSize(actualImageSize, null);
                    BitmapFactory.Options ops = new BitmapFactory.Options();
                    ops.inJustDecodeBounds = false;
                    ops.inSampleSize = inSampleSize;
                    Bitmap bm = BitmapFactory.decodeStream(is, null, ops);

                    is.close();
//            Bitmap bitmap = BitmapFactory.decodeByteArray(picByte, 0, picByte.length);
                    onResponse(call, bm);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    public abstract void onResponse(Call call, Bitmap result);


}
