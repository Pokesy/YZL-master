package com.thinksky.rsen.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.thinksky.rsen.ResUtil;
import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.tox.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dajiao on 16-03-02-002.
 */
public class IssueDetailFragment extends RNetBaseFragment<IssueDetailFragment.ZJXQBean> {

    public static final String KEY = "id";
    private String id;
    private EditText best_answer;

    public static IssueDetailFragment newInstance(String group_id) {
        IssueDetailFragment groupDetailFragment = new IssueDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY, group_id);
        groupDetailFragment.setArguments(bundle);
        return groupDetailFragment;
    }

    @Override
    protected void initAfter() {

        mViewHolder.ET(R.id.best_answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewHolder.tV(R.id.sendPostButn).setVisibility(View.VISIBLE);
                mViewHolder.tV(R.id.sendPostButn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
        mViewHolder.tV(R.id.sendPostButn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        id = getArguments().getString(KEY);
    }

    @Override
    protected String getUrl() {
        String url = RsenUrlUtil.URL_ZJXQ;
        return url;
    }

    @Override
    protected Map getMap() {
        Map map = new HashMap();
        map.put("id", id);
        return map;
    }

    @Override
    protected void parseJson(List<ZJXQBean> beans, JSONObject jsonObject) throws JSONException {
        ZJXQBean bean = new ZJXQBean();

        bean.title = jsonObject.getString("title");//title 赋值
        bean.create_time = jsonObject.getString("create_time");
        bean.content = jsonObject.getString("content");
        bean.cover_url = jsonObject.getString("cover_url");
        bean.reply_count = jsonObject.getString("reply_count");
        bean.support_count = jsonObject.getString("support_count");
        bean.is_supported = jsonObject.getString("is_supported");


//                    其他字段。。。赋值

        // TODO: 2016/2/17
        beans.add(bean);
    }

    @Override
    protected void onResult(List<ZJXQBean> beans) {
        ZJXQBean bean = beans.get(0);
        mViewHolder.tV(R.id.title).setText(bean.title);
        mViewHolder.tV(R.id.fb_time).setText(bean.create_time);
        mViewHolder.tV(R.id.zw_content).setText(bean.content);
        mViewHolder.tV(R.id.support_count).setText(bean.support_count);
        mViewHolder.tV(R.id.reply_count).setText(bean.reply_count);
        ResUtil.setRoundImage(bean.cover_url, mViewHolder.imgV(R.id.video));

    }

    @Override
    protected int getBaseLayoutId() {
        return R.layout.activity_issue_xq;
    }

    @Override
    protected void initView(View rootView) {

    }


    public static class ZJXQBean {
        //需要什么字段，自己添加
        public String title;
        public String create_time;
        public String cover_url;
        public String reply_count;
        public String support_count;
        public String is_supported;
        public String content;
    }
}
