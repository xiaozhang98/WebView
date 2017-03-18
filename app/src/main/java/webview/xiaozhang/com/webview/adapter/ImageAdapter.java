package webview.xiaozhang.com.webview.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import webview.xiaozhang.com.webview.R;
import webview.xiaozhang.com.webview.item;

/**
 * Created by Administrator on 2017/3/7.
 */

public class ImageAdapter extends RecyclerView.Adapter {
    private List<item> datas;
    private static final String TAG = "ImageAdapter";
    public ImageAdapter( List<item> datas) {
        this.datas = datas;
        Log.e(TAG, "ImageAdapter: size"+datas.size() );
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image,parent,false);
        return new ViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        item a=datas.get(position);
        Log.e(TAG, "position  : "+datas.get(position) );
        if (holder instanceof ViewHolder1){
            ViewHolder1 viewHolder= (ViewHolder1) holder;
            Log.e(TAG, "onBindViewHolder: "+a );
            viewHolder.image.setImageBitmap(a.getBitmap());
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder1 extends RecyclerView.ViewHolder{
        private ImageView image;

        public ViewHolder1(View itemView) {
            super(itemView);
            image= (ImageView) itemView.findViewById(R.id.itemimage);
            Log.e(TAG, "ViewHolder1: tttts" );
        }
    }
}
