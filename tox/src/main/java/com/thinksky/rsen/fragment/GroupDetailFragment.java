package com.thinksky.rsen.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.thinksky.rsen.RBaseAdapter;
import com.thinksky.rsen.RViewHolder;
import com.thinksky.rsen.ResUtil;
import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.tox.R;
import com.tox.ToastHelper;
import com.tox.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dajiao on 16-03-02-002.
 */
public class GroupDetailFragment extends RNetBaseFragment<GroupDetailFragment.MyBean> {

    public static final String KEY = "group_id";
    private String group_id;
    private String nickname = "";

    public static GroupDetailFragment newInstance(String group_id) {
        GroupDetailFragment groupDetailFragment = new GroupDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY, group_id);
        groupDetailFragment.setArguments(bundle);
        return groupDetailFragment;
    }

    @Override
    protected void initAfter() {
        group_id = getArguments().getString(KEY);
    }

    @Override
    protected String getUrl() {
        String url = RsenUrlUtil.URL_XIAOZU_XIANGQING ;
//        if (BuildConfig.DEBUG) {
//            url = "http://192.168.1.11:8080/HelloJsp/apptox3.app";
//        } else {
//            url = RsenUrlUtil.URL_XIAOZU_XIANGQING + group_id;
//        }
        return url;
    }

    @Override
    protected Map getMap() {
        Map map = new HashMap();
        map.put("group_id", group_id);
        return map;
    }

    @Override
    protected void parseJson(List<MyBean> beans, JSONObject jsonObject) throws JSONException {
        MyBean bean = new MyBean();
        bean.logo = RsenUrlUtil.URL_BASE + jsonObject.getString("logo");
        bean.title = jsonObject.getString("title");
        bean.is_join = jsonObject.getString("is_join");
        bean.menmberCount = jsonObject.getString("menmberCount");
//        bean.isCreator = jsonObject.getString("isCreator");
        JSONArray userArray = jsonObject.getJSONArray("GroupMenmber");
        bean.userList = parseUserList(userArray);
        bean.nickname = this.nickname;
        beans.add(bean);
    }

    private List<String> parseUserList(JSONArray userArray) {
        List<String> userList = new ArrayList<>();
        for (int i = 0; i < userArray.length(); i++) {
            try {
                JSONObject jsonObject = userArray.getJSONObject(i);
                JSONObject user = jsonObject.getJSONObject("user");
                userList.add(RsenUrlUtil.URL_BASE + user.getString("avatar32"));
                if (jsonObject.getString("isCreator").equalsIgnoreCase("1")) {
                    nickname = user.getString("nickname");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return userList;
    }

    @Override
    protected void onResult(List<MyBean> beans) {
        MyBean bean = beans.get(0);
//        bean.nickname = "组长:" + bean.nickname;
//        bean.menmberCount += "成员:" + bean.menmberCount;
//        mViewHolder.fillView(bean);

        ResUtil.setRoundImage(bean.logo, mViewHolder.imgV(R.id.logo));
        mViewHolder.tV(R.id.title).setText(bean.title);
        mViewHolder.tV(R.id.nickname).setText("组长:" + bean.nickname);
        mViewHolder.tV(R.id.menmberCount).setText("成员:" + bean.menmberCount);
        mViewHolder.tV(R.id.title).setText(bean.title);
        if (bean.is_join.equals("1")) {
            mViewHolder.tV(R.id.join).setText("加入");
        }else{
            mViewHolder.tV(R.id.join).setText("退出");
        }

        ((RecyclerView) mViewHolder.v(R.id.memberRecycler)).setAdapter(new MySubAdapter(mBaseActivity, bean.userList));

        initTalkData();
    }

    @Override
    protected int getBaseLayoutId() {
        return R.layout.fragment_groupdetail_layout;
    }

    @Override
    protected void initView(View rootView) {

    }

    /*获取话题数据*/
    public void initTalkData() {
        String url = RsenUrlUtil.URL_XIAOZU_XIANGQINGTZ ;
//        if (BuildConfig.DEBUG) {
//            url = "http://192.168.1.11:8080/HelloJsp/apptox4.app";
//        } else {
//            url = RsenUrlUtil.URL_XIAOZU_XIANGQING + group_id;
//        }


        RsenUrlUtil.execute(url, new RsenUrlUtil.OnJsonResultListener<MyTalkBean>() {
            @Override
            public void onNoNetwork(String msg) {
                ToastHelper.showToast(msg, Url.context);
            }

            @Override
            public Map getMap() {
                Map map = new HashMap();
                map.put("group_id", group_id);
                return map;
            }

            @Override
            public void onParseJsonBean(List<MyTalkBean> beans, JSONObject jsonObject) {
                try {
                    MyTalkBean bean = new MyTalkBean();
                    bean.title = jsonObject.getString("title");
                    bean.content = jsonObject.getString("content");
                    bean.reply_count = jsonObject.getString("reply_count");
                    bean.supportCount = jsonObject.getString("supportCount");
                    JSONObject user = jsonObject.getJSONObject("user");
                    bean.avatar32 = user.getString("avatar32");
                    bean.nickname = user.getString("nickname");
//                    bean.posts_rply = user.getString("posts_rply");
//                    bean.rp_user = user.getString("rp_user");
                    beans.add(bean);
                } catch (Exception e) {

                }
            }

            @Override
            public void onResult(boolean state, List<MyTalkBean> beans) {
                if (state) {
                    ((RecyclerView) mViewHolder.v(R.id.talkRecycler)).setAdapter(new MyAdapter(mBaseActivity, beans));
                } else {
                    ToastHelper.showToast("请求失败", Url.context);
                }
            }
        });

    }

    public static class MyBean {
        public String logo;
        public String title;
        public String nickname;
        public String menmberCount;
        public String is_join;
        public String isCreator;
        public List<String> userList;
    }


    public static class MyTalkBean {
        public String title;
        public String nickname;
        public String content;
        public String reply_count;
        public String supportCount;
        public String avatar32;
        public ArrayList<RplyeBean>  posts_rply;

    }
    public static class RplyeBean {
        public String content;
        public String rp_user;

    }


    public static class MyAdapter extends RBaseAdapter<MyTalkBean> {

        public MyAdapter(Context context, List<MyTalkBean> datas) {
            super(context, datas);
        }

        @Override
        protected int getItemLayoutId(int viewType) {
            return R.layout.fragment_groupdetail_talk_item;
        }

        @Override
        protected void onBindView(RViewHolder holder, int position, MyTalkBean bean) {
//            holder.tV(R.id.title).setText(bean.title);
//            holder.tV(R.id.nickname).setText(bean.nickname);
//            holder.tV(R.id.content).setText(bean.content);
//            holder.tV(R.id.reply_count).setText(bean.reply_count);
//            holder.tV(R.id.supportCount).setText(bean.supportCount);
//            ResUtil.setRoundImage(bean.avatar32, holder.imgV(R.id.avatar32));
            holder.fillView(bean);
        }
    }


    /*成员头像*/
    public static class MySubAdapter extends RBaseAdapter<String> {

        public MySubAdapter(Context context, List<String> datas) {
            super(context, datas);
        }

        @Override
        protected int getItemLayoutId(int viewType) {
            return R.layout.fragment_xiaozujingxuan_adapter_sub_item;
        }

        @Override
        protected void onBindView(RViewHolder holder, int position, String bean) {
            ResUtil.setRoundImage(bean, holder.imgV(R.id.logo));

            /*点击用户头像*/
            holder.v(R.id.item_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
