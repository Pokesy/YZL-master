package com.thinksky.tox;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.thinksky.rsen.RsenUrlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaikeItemActivity extends Activity {
    private RelativeLayout fanhui;
    private String id;
    private WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baike_xiangqing);
        id = getIntent().getStringExtra("id");
        initView();
    }

    private void initView() {
        fanhui = (RelativeLayout) findViewById(R.id.guanyuqibai_back);
        web = (WebView) findViewById(R.id.webView1);

        MyOnclickListener mOnclickListener = new MyOnclickListener();
        fanhui.setOnClickListener(mOnclickListener);
        Map map = new HashMap();
        map.put("id", id);
        RsenUrlUtil.executeGetWidthMap(this, RsenUrlUtil.URL_BKXQ, map, new RsenUrlUtil.OnJsonResultListener<BaikeBean>() {

            @Override
            public void onNoNetwork(String msg) {

            }

            @Override
            public void onParseJsonBean(List<BaikeBean> beans, JSONObject jsonObject) {
                BaikeBean bean = new BaikeBean();
                try {
                    bean.content = jsonObject.getString("content");
                    bean.title = jsonObject.getString("title");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                beans.add(bean);
            }

            @Override
            public void onResult(boolean state, List<BaikeBean> beans) {
                web.loadUrl("beans.get(0).content");
            }
        });
    }

    private class MyOnclickListener implements View.OnClickListener {

        @Override
        public void onClick(View arg0) {
            int mID = arg0.getId();
            if (mID == R.id.guanyuqibai_back) {
                BaikeItemActivity.this.finish();
            }
        }

    }

    public static class BaikeBean {
        /**
         * "category": "0",
         * "content": "<p>sdfad</p>",
         * "cover": "",
         * "create_time": "02月14日 10:04",
         * "dead_line": "2016-02-18 15:29",
         * "id": "6",
         * "sort": "0",
         * "status": "1",
         * "title": "1111",
         * <p/>
         * "uid": "1",
         */
        public String id;
        public String content;
        public String status;
        public String title;
        public String category;
    }
}
