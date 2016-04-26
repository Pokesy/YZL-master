package com.thinksky.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.thinksky.holder.BaseBActivity;
import com.thinksky.rsen.RBaseAdapter;
import com.thinksky.rsen.RViewHolder;
import com.thinksky.rsen.ResUtil;
import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.rsen.view.RGridView;
import com.thinksky.tox.GroupInfoActivity;
import com.thinksky.tox.R;
import com.thinksky.utils.MyJson;
import com.tox.ToastHelper;
import com.tox.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class XiaozujingxuanActivity extends BaseBActivity {
    private static final String ARG_PARAM1 = "param1";
    private RGridView recyclerView;
    private boolean isWeGroup = true;
    private  MyJson myjson =new MyJson();
    ListView listView;

    ImageView back_menu;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiaozujingxuan_layout);

        back_menu= (ImageView) findViewById(R.id.back_menu);
        recyclerView = (RGridView)findViewById(R.id.gridView);
        back_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initViewData();
    }




    private void initViewData() {
        String url = RsenUrlUtil.URL_XIAOZU_JINGXUAN;


        RsenUrlUtil.execute(url, new RsenUrlUtil.OnJsonResultListener<MyBean>() {
            @Override
            public void onNoNetwork(String msg) {
                ToastHelper.showToast(msg, Url.context);
            }

            @Override
            public void onParseJsonBean(List<MyBean> beans, JSONObject jsonObject) {
                MyBean bean = new MyBean();
                try {
                    bean.logo = RsenUrlUtil.URL_BASE + jsonObject.getString("logo");
                    bean.title = jsonObject.getString("title");
                    bean.detail = jsonObject.getString("detail");
                    bean.menmberCount = jsonObject.getString("menmberCount");
                    bean.member_count = jsonObject.getString("member_count");

                    bean.group_background = jsonObject.getString("background");
                    bean.type_id = jsonObject.getString("type_id");
                    bean.is_join = jsonObject.getString("is_join");
                    bean.uid = jsonObject.getString("uid");
                    bean.post_count = jsonObject.getString("post_count");
                    bean.group_type = jsonObject.getString("type");
//                    bean.type_name = jsonObject.getString("type_name");
//
                    bean.activity = jsonObject.getString("activity");

                    bean.id = jsonObject.getString("id");

                    bean.gm_logo=jsonObject.getJSONObject("user").getString("avatar32");
                    bean.gm_nickname=jsonObject.getJSONObject("user").getString("nickname");


                } catch (JSONException e) {
                }
                beans.add(bean);
            }

            @Override
            public void onResult(boolean state, List<MyBean> beans) {
                if (state) {
                    recyclerView.setAdapter(new MyAdapter(XiaozujingxuanActivity.this, beans));
                } else {
                    ToastHelper.showToast("请求失败", Url.context);
                }
            }
        });

    }


    private List<String> parseUserList(JSONArray userArray) {
        List<String> userList = new ArrayList<>();
        for (int i = 0; i < userArray.length(); i++) {
            try {
                JSONObject jsonObject = userArray.getJSONObject(i);

                JSONObject user = jsonObject.getJSONObject("user");
                userList.add(RsenUrlUtil.URL_BASE + user.getString("avatar32"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return userList;
    }




    public class MyAdapter extends RBaseAdapter<MyBean> {

        public MyAdapter(Context context, List<MyBean> datas) {
            super(context, datas);
        }

        @Override
        protected int getItemLayoutId(int viewType) {
            return R.layout.fragment_xiaozujingxuan_adapter;
        }

        @Override
        protected void onBindView(RViewHolder holder, int position, final MyBean bean) {

            ResUtil.setRoundImage(bean.logo, holder.imgV(R.id.logo));
            holder.tV(R.id.title).setText(bean.title);
            holder.tV(R.id.detail).setText(bean.detail);
            holder.tV(R.id.post_count).setText(bean.post_count);
            holder.tV(R.id.member_count).setText(bean.menmberCount);
            holder.v(R.id.fragment_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launch(mContext, isWeGroup, bean);
//                    GroupInfoActivity.MyBean bean = new GroupInfoActivity.MyBean();
//                    GroupInfoActivity.launch(mContext,isWeGroup,bean);
                }
            });
        }
    }
    public static class MyBean {
        public String menmberCount;
        public String member_count;
        public List<String> userList;//用户头像
        public List<String> postList;
        public String id;
        public String logo;
        public String title;
        //        public String group_id;
        public String group_type;
        public String detail;
        public String type_name;
        public String post_count;

        public String uid;
        public String group_logo;
        public String group_background;
        public String type_id;
        public String activity;
        public String is_join;

        public String gm_logo;
        public String gm_nickname;
    }

    public static void launch(Context context, boolean isWeGroup, MyBean bean) {
        HashMap<String, String> map = new HashMap<>();
        Bundle bundle = new Bundle();
        map.put("id", bean.id);
        map.put("title", bean.title);
        map.put("group_type", bean.group_type);
        map.put("detail", bean.detail);
        map.put("type_name", bean.type_name);
        map.put("post_count", bean.post_count);
        map.put("group_logo", bean.logo);
        map.put("memberCount", bean.menmberCount);
        map.put("uid", bean.uid);
        map.put("is_join", bean.is_join);
        map.put("user_nickname", bean.gm_nickname);
        map.put("user_logo", Url.IMAGE + bean.gm_logo);
        bundle.putSerializable("group_info", map);
        bundle.putBoolean("isWeGroup", isWeGroup);
        Intent intent = new Intent(context, GroupInfoActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
