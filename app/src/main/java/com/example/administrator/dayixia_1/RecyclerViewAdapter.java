package com.example.administrator.dayixia_1;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
  public static int i = 0;
   private List<Beans> mBeansList;
    static class  ViewHolder extends RecyclerView.ViewHolder{
       ImageView mImageView;
       TextView mTextView;
        public ViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.image);
            mTextView = (TextView) view.findViewById(R.id.textview);
        }
    }
    public RecyclerViewAdapter(List<Beans> beansList){
        mBeansList = beansList;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        ImageLoader imageLoader = new ImageLoader();
        Log.d("ffffffff",mBeansList.get(position).imageUrl);
        imageLoader.showIamges(holder.mImageView,mBeansList.get(position).imageUrl);
        i++;
        holder.mTextView.setText("这是第"+i+"张图片");
        //s接收2个howImages参数（）
        //imagLoader需要传入2个参数，一个是需要修改的imageView，一个是所对应的Url;
    }

    @Override
    public int getItemCount() {
        return mBeansList.size();
    }
}



