package webview.xiaozhang.com.webview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import webview.xiaozhang.com.webview.adapter.ImageAdapter;

public class ImageListActivity extends AppCompatActivity {
    private Context mContext;
    private List<String> datas;
    private List<item> BitmapList=new ArrayList<>();
    private byte[] picByte;
    private RecyclerView recyclerView;
    private static final String TAG = "ImageListActivity";
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                if (picByte!=null){
                    Bitmap bitmap = BitmapFactory.decodeByteArray(picByte, 0, picByte.length);
                    Log.e(TAG, "handleMessage: bitmap");
                }
                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                ImageAdapter adapter=new ImageAdapter(BitmapList);
                Log.e(TAG, "handleMessage: "+BitmapList.size() );
                recyclerView.setAdapter(adapter);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);
        mContext=ImageListActivity.this;
        recyclerView= (RecyclerView) findViewById(R.id.Image_list);



        datas=indata();
        new Thread(new Runnable() {
            private URL url=null;
            private HttpURLConnection conn=null;
            @Override
            public void run() {
                try {
                    for (int i=0;i<datas.size();i++){
                        url=new URL(datas.get(i));
                        conn= (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setReadTimeout(10000);
                        if (conn.getResponseCode()==200){
                            InputStream fis=conn.getInputStream();
                            ByteArrayOutputStream bos=new ByteArrayOutputStream();
                            byte[] bytes=new byte[1024];
                            int length=-1;
                            while ((length=fis.read(bytes))!=-1){
                                bos.write(bytes,0,length);
                            }
                            picByte=bos.toByteArray();
                            bos.close();
                            fis.close();
                            Bitmap bitmap = BitmapFactory.decodeByteArray(picByte, 0, picByte.length);
                            item it=new item();
                            it.setBitmap(bitmap);
                            BitmapList.add(it);
                            Log.e(TAG, "run: addd" );
                        }
                    }
                    Message message=new Message();
                    message.what=1;
                    handler.sendMessage(message);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private List<String> indata() {
        List<String> data=new ArrayList<>();
//        data.add("http://img4.imgtn.bdimg.com/it/u=2227291301,3842994837&fm=23&gp=0.jpg");
//        data.add("http://pic.58pic.com/58pic/13/86/90/43A58PICuUn_1024.jpg");
//        data.add("http://scimg.jb51.net/allimg/151103/14-151103142423535.jpg");
        data.add("http://pic66.nipic.com/file/20150511/13629256_135451223000_2.jpg");
        data.add("http://img3.duitang.com/uploads/item/201412/28/20141228212401_XiSQm.jpeg");
        data.add("http://2t.5068.com/uploads/allimg/151105/48-1511051J947-51.jpg");
        data.add("http://img4.duitang.com/uploads/item/201303/18/20130318134246_2rvQh.jpeg");
        data.add("http://imgstore.cdn.sogou.com/app/a/100540002/690778.jpg");
        data.add("http://pic.58pic.com/58pic/13/65/34/61x58PIC8IQ_1024.jpg");
//        data.add("http://pic33.nipic.com/20130927/12045420_085444259113_2.png");
//        data.add("http://imgsrc.baidu.com/forum/pic/item/037cc7bf6c81800a921df881b13533fa838b476d.jpg");
//        data.add("http://www.danji8.com/uploadpic/201312/09/2013120910591718601386557957.jpg");
//        data.add("http://bizhi.4493.com/uploads/allimg/141002/1-141002103553.jpg");
//        data.add("http://img3.duitang.com/uploads/item/201308/14/20130814162009_ZHSaQ.jpeg");
//        data.add("http://bizhi.zhuoku.com/2016/06/20/haizeiwang/haizeiwang09.jpg");
//        data.add("http://dl.bizhi.sogou.com/images/2012/04/15/283055.png");
//        data.add("http://imgsrc.baidu.com/forum/pic/item/ac4bd11373f082022da9dc934bfbfbedab641b66.jpg");
//        data.add("http://bbs.zhezhe168.com/data/attachment/forum/201512/31/235456dkk1zk13jk6e6x6p.jpg");
//        data.add("http://att.x2.hiapk.com/forum/201405/03/083857zx0qaacim0iccmkc.jpg");
//        data.add("http://bbs.zhezhe168.com/data/attachment/forum/201512/31/235456dcqqegpe99g8qm1q.jpg");
//
//
//
//
//        data.add("http://imgsrc.baidu.com/forum/pic/item/5fdf8db1cb13495457939ffd564e9258d0094a9d.jpg");
//        data.add("http://imgsrc.baidu.com/forum/pic/item/832bd2f9d72a6059155590952834349b023bba98.jpg");
//        data.add("http://imgsrc.baidu.com/forum/pic/item/396459706226227e972b43b5.jpg");
//        data.add("http://img4.imgtn.bdimg.com/it/u=2227291301,3842994837&fm=23&gp=0.jpg");
//        data.add("http://pic.58pic.com/58pic/13/86/90/43A58PICuUn_1024.jpg");
//        data.add("http://scimg.jb51.net/allimg/151103/14-151103142423535.jpg");
//        data.add("http://pic66.nipic.com/file/20150511/13629256_135451223000_2.jpg");
//        data.add("http://img3.duitang.com/uploads/item/201412/28/20141228212401_XiSQm.jpeg");
//        data.add("http://2t.5068.com/uploads/allimg/151105/48-1511051J947-51.jpg");
//        data.add("http://img4.duitang.com/uploads/item/201303/18/20130318134246_2rvQh.jpeg");
//        data.add("http://imgstore.cdn.sogou.com/app/a/100540002/690778.jpg");
//        data.add("http://pic.58pic.com/58pic/13/65/34/61x58PIC8IQ_1024.jpg");
//        data.add("http://imgsrc.baidu.com/forum/pic/item/5fdf8db1cb13495457939ffd564e9258d0094a9d.jpg");
//        data.add("http://imgsrc.baidu.com/forum/pic/item/832bd2f9d72a6059155590952834349b023bba98.jpg");
//        data.add("http://imgsrc.baidu.com/forum/pic/item/396459706226227e972b43b5.jpg");



        return data;
    }
}
