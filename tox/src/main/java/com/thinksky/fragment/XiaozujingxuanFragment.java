package com.thinksky.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.thinksky.rsen.RBaseAdapter;
import com.thinksky.rsen.RViewHolder;
import com.thinksky.rsen.ResUtil;
import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.rsen.fragment.RBaseFragment;
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


public class XiaozujingxuanFragment extends RBaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private RGridView recyclerView;
    private boolean isWeGroup = true;
    private  MyJson myjson =new MyJson();
    public static XiaozujingxuanFragment newInstance(String param1) {
        XiaozujingxuanFragment fragment = new XiaozujingxuanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getBaseLayoutId() {
        return R.layout.fragment_xiaozujingxuan_layout;
    }

    @Override
    protected void initViewData() {
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
                    JSONArray userArray = jsonObject.getJSONArray("GroupMenmber");
                    bean.userList = parseUserList(userArray);
                    for (int i = 0; i < userArray.length(); i++) {
                        try {
                            JSONObject jsonObject2 = userArray.getJSONObject(i);

                            JSONObject user = jsonObject2.getJSONObject("user");
                            bean.gm_logo=RsenUrlUtil.URL_BASE + user.getString("avatar32");
                            bean.gm_nickname=user.getString("nickname");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    JSONArray postArray = jsonObject.getJSONArray("PostNew");

                    for (int i = 0; i < postArray.length(); i++) {

                        JSONObject jsonObject1 = postArray.getJSONObject(i);
                        JSONObject user = jsonObject1.getJSONObject("user");
                        bean.ht_reply_count = jsonObject1.getString("reply_count");
                        bean.ht_support_count = jsonObject1.getString("supportCount");
                        bean.ht_content = jsonObject1.getString("content");
                        bean.ht_logo=RsenUrlUtil.URL_BASE + user.getString("avatar32");
                        bean.ht_nickname=user.getString("nickname");
                        bean.ht_creat_time = myjson.getStandardDate(jsonObject1.getString("create_time"));

                    }

                } catch (JSONException e) {
                }
                beans.add(bean);
            }

            @Override
            public void onResult(boolean state, List<MyBean> beans) {
                if (state) {
                    recyclerView.setAdapter(new MyAdapter(mBaseActivity, beans));
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

    @Override
    protected void initView(View rootView) {
        recyclerView = (RGridView) mViewHolder.v(R.id.gridView);
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
//            ImageLoader.getInstance().displayImage(bean.logo, holder.imgV(R.id.logo),
//                    new DisplayImageOptions.Builder()
//                            .showImageOnLoading(R.drawable.ic_launcher)
//                            .showImageForEmptyUri(R.drawable.ic_launcher)
//                            .showImageOnFail(R.drawable.ic_launcher)
//                            .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
//                            .displayer(new RoundedBitmapDisplayer(100)).build());
            ResUtil.setRoundImage(bean.logo, holder.imgV(R.id.logo));
            holder.tV(R.id.title).setText(bean.title);
            holder.tV(R.id.detail).setText(bean.detail);
            holder.tV(R.id.post_count).setText(bean.post_count);
            holder.tV(R.id.member_count).setText(bean.menmberCount);
//            holder.tV(R.id.reply_count).setText(bean.ht_reply_count);
//            holder.tV(R.id.support_count).setText(bean.ht_support_count);
//            holder.tV(R.id.nickname).setText(bean.ht_nickname);
//            holder.tV(R.id.time).setText(bean.ht_creat_time);
//            holder.tV(R.id.content_xz).setText(bean.ht_content);
//            ImageLoader.getInstance().displayImage(bean.ht_logo, holder.imgV(R.id.user_logo),
//                    new DisplayImageOptions.Builder()
//                            .showImageOnLoading(R.drawable.ic_launcher)
//                            .showImageForEmptyUri(R.drawable.ic_launcher)
//                            .showImageOnFail(R.drawable.ic_launcher)
//                            .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
//                            .displayer(new RoundedBitmapDisplayer(100)).build());
//            if (BuildConfig.DEBUG) {
//                for (int i = 0; i < 20; i++) {
//                    bean.userList.add(bean.userList.get(0));
//                }
//            }

//            ((RRecyclerView) holder.v(R.id.recycler)).setAdapter(new MySubAdapter(mContext, bean.userList));

            /*item的点击事件*/
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

    /*成员头像*/
//    public static class MySubAdapter extends RBaseAdapter<String> {
//
//        public MySubAdapter(Context context, List<String> datas) {
//            super(context, datas);
//        }
//
//        @Override
//        protected int getItemLayoutId(int viewType) {
//            return R.layout.fragment_xiaozujingxuan_adapter_sub_item;
//        }
//
//        @Override
//        protected void onBindView(RViewHolder holder, int position, String bean) {
//            ImageLoader.getInstance().displayImage(bean, holder.imgV(R.id.logo),
//                    new DisplayImageOptions.Builder()
//                            .showImageOnLoading(R.drawable.ic_launcher)
//                            .showImageForEmptyUri(R.drawable.ic_launcher)
//                            .showImageOnFail(R.drawable.ic_launcher)
//                            .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
//                            .displayer(new RoundedBitmapDisplayer(100)).build());
//            /*点击用户头像*/
//            holder.v(R.id.item_layout).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
//        }
//    }


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
        public String memberCount;
        public String uid;
        public String group_logo;
        public String group_background;
        public String type_id;
        public String activity;
        public String is_join;
        public String ht_reply_count;
        public String ht_support_count;
        public String ht_logo;
        public String ht_content;
        public String ht_creat_time;
        public String ht_nickname;
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
        map.put("post_count", bean.post_count);
        map.put("group_logo", bean.logo);
        map.put("memberCount", bean.memberCount);
        map.put("uid", bean.uid);
        map.put("is_join", bean.is_join);
        map.put("user_nickname", bean.gm_nickname);
        map.put("user_logo",  bean.gm_logo);
        bundle.putSerializable("group_info", map);
        bundle.putBoolean("isWeGroup", isWeGroup);
        Intent intent = new Intent(context, GroupInfoActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
