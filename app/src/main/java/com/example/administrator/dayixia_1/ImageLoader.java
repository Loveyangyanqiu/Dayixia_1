package com.example.administrator.dayixia_1;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
public  class ImageLoader{

    private LruCache<String, Bitmap> mCache;
    ImageView mImage;

    public ImageLoader() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory()/1024/1024;//因为内存大小只除1024都还有30多万。。所以再除
        Log.d("内存大小为",maxMemory+"");
        int cacheSize = maxMemory / 8;
        //设置缓存的大小
        mCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //在每次存入缓存的时候调用
                return value.getByteCount();
            }
        };
    }
    public static Bitmap getBitmapFromURL(String urlString) {
        Bitmap bitmap;
        InputStream is = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(is);
            connection.disconnect();
            return  bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 将bitmap加入到Lrucache缓存中
     *
     * @param url LruCache的键，即图片的下载路径
     * @param bitmap LruCache的值，即图片的Bitmap对象
     */

    public void addBitMapToCache(String url,Bitmap bitmap){

        mCache.put(url, bitmap);

    }

    /**
     * 从缓存中获取bitmap
     *
     * @param url 是 LruCache的键，即图片的下载路径
     * @return  返回的是bitmap
     */
    public Bitmap getBitmapFromCache(String url) {
        Bitmap bitmap = mCache.get(url);
        return bitmap;
    }
    /*
    *
    *imageView 硬引用
    *imageUrl 从Json中读取出来的图片对应的URL
     */


    public void showIamges(ImageView imageView,String imageUrl) {
            //从缓存中取图片
        mImage = imageView;
            Bitmap bitmap = getBitmapFromCache(imageUrl);
            //如果缓存中没有，则通过URL下载bitmap然后再加载
            if (bitmap == null) {
                new ImageLoaderTask().execute(imageUrl);
            } else {
                mImage.setImageBitmap(bitmap);
            }
    }
       class  ImageLoaderTask  extends  AsyncTask<String,Void,Bitmap>{



          @Override
          protected Bitmap doInBackground(String... strings) {
              Bitmap bitmap = getBitmapFromURL(strings[0]);
              if (bitmap != null) {
                  addBitMapToCache(strings[0], bitmap);
              }
              return bitmap;
          }

           @Override
           protected void onPostExecute(Bitmap bitmap) {
               super.onPostExecute(bitmap);
                mImage.setImageBitmap(bitmap);//当url所对应的bitmap在LruCache中有时直接调用，没有则通过返回的bitmap设置图片
           }
       }

}