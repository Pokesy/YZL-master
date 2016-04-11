package com.thinksky.tox;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinksky.rsen.ResUtil;
import com.thinksky.rsen.RsenUrlUtil;
import com.tox.ToastHelper;
import com.tox.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiao on 2016/2/22.
 */
public class IssueActivity1 extends AppCompatActivity {

    private TextView title;
    private TextView fb_time;
    private TextView zw_content;
    private ImageView image;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_issue_xq);
        title = (TextView) findViewById(R.id.title);
        fb_time = (TextView) findViewById(R.id.fb_time);
        zw_content = (TextView) findViewById(R.id.zw_content);
        image = (ImageView) findViewById(R.id.image);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getExtras();
        final String id = bundle.getString("id");


        RsenUrlUtil.execute(this, RsenUrlUtil.URL_ZJXQ, new RsenUrlUtil.OnNetHttpResultListener() {
            @Override
            public void onNoNetwork(String msg) {
                ToastHelper.showToast(msg, Url.context);
            }

            public Map getMap() {
                Map map = new HashMap();
                map.put("id", id);
                return map;
            }
            @Override
            public void onResult(boolean state, String result, JSONObject jsonObject) {
                if (state) {
                    ArrayList<ZJXQBean> beans = parseJson(jsonObject);
                    ZJXQBean bean = beans.get(0);
                    title.setText(bean.title);
                    fb_time.setText(bean.creat_time);
                    zw_content.setText(bean.content);
                    ResUtil.setRoundImage(bean.cover_url, image);
                }
            }
        });
    }


    public static ArrayList<ZJXQBean> parseJson(JSONObject object) {
        ArrayList<ZJXQBean> beans = new ArrayList<>();
        if (object != null) {
            try {
                JSONArray array = object.getJSONArray("list");
                for (int i = 0; i < array.length(); i++) {
                    ZJXQBean bean = new ZJXQBean();
                    JSONObject jsonObject = array.getJSONObject(i);
                    bean.title = jsonObject.getString("title");//title 赋值
                    bean.creat_time = jsonObject.getString("creat_time");
                    bean.cover_url = jsonObject.getString("cover_url");
                    bean.reply_count = jsonObject.getString("reply_count");
                    bean.support_count = jsonObject.getString("support_count");
                    bean.is_supported = jsonObject.getString("is_supported");
                    bean.content = jsonObject.getString("content");


//                    其他字段。。。赋值

                    // TODO: 2016/2/17
                    beans.add(bean);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return beans;
    }

    public static class ZJXQBean {
        //需要什么字段，自己添加


        public String title;
        public String creat_time;
        public String cover_url;
        public String reply_count;
        public String support_count;
        public String is_supported;
        public String content;

    }


}




