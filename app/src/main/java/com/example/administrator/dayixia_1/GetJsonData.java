package com.example.administrator.dayixia_1;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2017/2/13.
 */

public class GetJsonData {
    public   List<Beans>  getJsonData(String url,int mode){//url所对应的json格式数据转化为所需要的NewsBean格式对象（List）。
        List<Beans> newsBeanList = new ArrayList<>();
        try {
            String jsonString = readStream(new URL(url).openStream());//相当于url.openConnection.getInputStream  返回值为InputStream
            String a,b;
            JSONObject jsonObject;
            Beans newsBean;
            if (mode == 1) {//对主页内容的获取
                try {
                    jsonObject = new JSONObject(jsonString);//把解析的数据放入list
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {//因为data是数组  所以jsonarray中每一个元素就是一个json object
                        jsonObject = jsonArray.getJSONObject(i);
                        newsBean = new Beans();
                        newsBean.imageUrl = jsonObject.getString("url");
                        Log.d("获取的图片url为",newsBean.imageUrl);
                        newsBeanList.add(newsBean);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            }catch (IOException e) {
            e.printStackTrace();
        }
        return newsBeanList;
    }
    private String readStream(InputStream is){//读取网上返回的stream
        InputStreamReader isr;
        String result = "";
        try {
            String line;
            isr = new InputStreamReader(is,"utf-8");//字节流转化为字符流
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine())!=null){
                result +=line;//每一行拼接到result里面还可以用StringBuilder一个result result.append（line）
            }
        }catch (Exception  e) {
            e.printStackTrace();
        }
        return result;
    }
}