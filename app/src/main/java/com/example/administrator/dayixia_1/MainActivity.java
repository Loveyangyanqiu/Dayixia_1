package com.example.administrator.dayixia_1;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class MainActivity extends AppCompatActivity {
private List<Beans> mBeansList = new ArrayList<>();
public String url = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/0/0";
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  initBeans();
        new DownLoadJsonData().execute(url);//这是关键
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory/8;
    }

    private void initBeans() {
        for(int i =0;i<100;i++) {
            Beans apple = new Beans();
            apple.imageUrl = "http://7xi8d6.com1.z0.glb.clouddn.com/2017-03-17-17332809_1277469728986540_3201752429582352384_n.jpg";
            mBeansList.add(apple);
        }
    }
    class DownLoadJsonData extends AsyncTask<String,Void,List<Beans>> {//每一行NewsBean代表一组数据
        @Override
        protected List<Beans> doInBackground(String... params) {
            List<Beans> beanList = new ArrayList<>();
            GetJsonData getjson = new GetJsonData();
            beanList = getjson.getJsonData(params[0],1);
            return beanList;
        }

        @Override
        protected void onPostExecute( List<Beans> newsBeans) {
            super.onPostExecute(newsBeans);

            RecyclerViewAdapter reAdpater = new RecyclerViewAdapter(newsBeans);
            recyclerView.setAdapter(reAdpater);


        }
    }
}
