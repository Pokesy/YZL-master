package com.thinksky.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thinksky.model.ActivityModel;
import com.thinksky.rsen.RBaseAdapter;
import com.thinksky.rsen.RViewHolder;
import com.thinksky.rsen.ResUtil;
import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.rsen.fragment.RBaseFragment;
import com.thinksky.rsen.view.RGridView;
import com.thinksky.tox.GroupInfoActivity;
import com.thinksky.tox.LoginActivity;
import com.thinksky.tox.R;
import com.tox.BaseApi;
import com.tox.GroupApi;
import com.tox.ToastHelper;
import com.tox.Url;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WodexiaozuFragment extends RBaseFragment {
    private static final String ARG_PARAM1 = "param1";
    Context context;
    private String mParam1;
    private RGridView gridView;
    private boolean isWeGroup = true;
    ArrayList<JSONObject> jsonObjArrayList;
    ArrayList<HashMap<String, String>> allGroupList;
    private HashMap<String, String> tempMap;
    Animation animation;
    private GroupApi groupApi;
    private String session_id;
    private String userUid;
    private BaseApi baseApi;
    private int page = 1;
    private boolean isLastRow = false;
    boolean count = true;
    boolean onClick = false;
    private int entryActivity;
    protected TextView progressBarText, empty,register;
    private LinearLayout group_probar, mygroup,text;

    public static WodexiaozuFragment newInstance(String param1) {
        WodexiaozuFragment fragment = new WodexiaozuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getBaseLayoutId() {
        return R.layout.fragment_wodexiaozu_layout;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void initViewData() {

    }

    protected void init() {
        session_id = baseApi.getSeesionId();
        Map map = new HashMap();
        map.put("session_id", session_id);

        RsenUrlUtil.executeGetWidthMap(getActivity(), RsenUrlUtil.URL_WODE, map, new RsenUrlUtil.OnJsonResultListener<MyBean>() {
            @Override
            public void onNoNetwork(String msg) {
                ToastHelper.showToast(msg, Url.context);
            }

            //            @Override
//            public Map getMap() {
//                Map map = new HashMap();
//                map.put("session_id", session_id);
//                return map;
//
//            }
            @Override
            public void onParseJsonBean(List<MyBean> beans, JSONObject jsonObject) {

                try {


                    for (int i = 0; i < jsonObject.length(); i++) {
                        MyBean bean = new MyBean();
                        bean.MyGroupCount = jsonObject.getInt("MyGroupCount");
                        bean.MyPostCount = jsonObject.getInt("MyPostCount");
                        if (jsonObject.length() > 2) {
                            bean.logo = jsonObject.getJSONObject(String.valueOf(i)).getString("logo");
                            bean.title = jsonObject.getJSONObject(String.valueOf(i)).getString("title");
                            bean.id = jsonObject.getJSONObject(String.valueOf(i)).getString("id");
                            bean.memberCount = jsonObject.getJSONObject(String.valueOf(i)).getString("menmberCount");
                            bean.group_background = jsonObject.getJSONObject(String.valueOf(i)).getString("background");
                            bean.type_id = jsonObject.getJSONObject(String.valueOf(i)).getString("type_id");
                            bean.is_join = jsonObject.getJSONObject(String.valueOf(i)).getString("is_join");
                            bean.uid = jsonObject.getJSONObject(String.valueOf(i)).getString("uid");
                            bean.post_count = jsonObject.getJSONObject(String.valueOf(i)).getString("post_count");
                            bean.group_type = jsonObject.getJSONObject(String.valueOf(i)).getString("type");
//                            bean.type_name = jsonObject.getJSONObject(String.valueOf(i)).getString("type_name");
                            bean.detail = jsonObject.getJSONObject(String.valueOf(i)).getString("detail");
                            bean.activity = jsonObject.getJSONObject(String.valueOf(i)).getString("activity");
                            JSONObject jsonObject1 = jsonObject.getJSONObject(String.valueOf(i)).getJSONObject("user");
//                        bean.userList.add(RsenUrlUtil.URL_BASE + jsonObject1.getString("avatar32"));
                            bean.gm_logo = RsenUrlUtil.URL_BASE + jsonObject1.getString("avatar32");
                            bean.gm_nickname = jsonObject1.getString("nickname");
                        }
//                        for (int j = 0; j < userArray.length(); j++) {
//                            try {
//                                bean.userList.add( userArray.getJSONObject(j).getString("avatar32"));
//                                bean.gm_logo = RsenUrlUtil.URL_BASE + userArray.getJSONObject(j).getString("avatar32");
//                                bean.gm_nickname = userArray.getJSONObject(j).getString("nickname");
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
                        beans.add(bean);
                    }


                } catch (JSONException e) {
                }

            }

            @Override
            public void onResult(boolean state, List<MyBean> beans) {
                if (state) {
                    mViewHolder.tV(R.id.xiaozu).setText("已加入" + String.valueOf(beans.get(0).MyGroupCount) + "个小组");
                    mViewHolder.tV(R.id.huati).setText("已发表" + String.valueOf(beans.get(0).MyPostCount) + "个话题");
                    if (beans.get(0).MyPostCount==0){
                        isLastRow = true;
                    }
                    if (beans.get(0).MyGroupCount != 0) {
                        empty.setVisibility(View.GONE);
                        gridView.setAdapter(new MyAdapter(mBaseActivity, beans));

                    } else {
                        empty.setVisibility(View.VISIBLE);


                    }
                } else {
                    ToastHelper.showToast("请求失败", Url.context);
                }
            }
        });

    }

    @Override
    protected void initView(View rootView) {
        context = getActivity();
        groupApi = new GroupApi();
        baseApi = new BaseApi();
        session_id = baseApi.getSeesionId();
        userUid = baseApi.getUid();
        group_probar = (LinearLayout) mViewHolder.v(R.id.group_probar);
        text = (LinearLayout) mViewHolder.v(R.id.text);
        mygroup = (LinearLayout) mViewHolder.v(R.id.mygroup);
        progressBarText = (TextView) mViewHolder.v(R.id.progressBar_text);
        register= (TextView) mViewHolder.v(R.id.register);
        empty = (TextView) mViewHolder.v(R.id.empty);
        if (!session_id.equals("")) {
            group_probar.setVisibility(View.GONE);

            mygroup.setVisibility(View.VISIBLE);
            init();

        } else {
            mygroup.setVisibility(View.GONE);
            text.setVisibility(View.VISIBLE);
            group_probar.setVisibility(View.GONE);

        }
        progressBarText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoginActivity.class);
                intent.putExtra("entryActivity", ActivityModel.UPLOADACTIVITY);
                startActivityForResult(intent, 8);

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(context,RegistetActivity.class);
//                intent.putExtra("opinion","2");
//                startActivityForResult(intent, 8);
                Intent intent = new Intent(context, LoginActivity.class);
                intent.putExtra("entryActivity", ActivityModel.UPLOADACTIVITY);
                startActivityForResult(intent, 8);
            }
        });
        gridView = (RGridView) mViewHolder.v(R.id.gridView);
        mViewHolder.tV(R.id.huati).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLastRow) {
                    Intent intent1 = new Intent(context, MyhuatiActivity.class);

                    startActivity(intent1);
                }else{
                    ToastHelper.showToast("您还没有发表过话题", Url.context);
                }
            }
        });
        mViewHolder.tV(R.id.xiaozu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public class MyAdapter extends RBaseAdapter<MyBean> {

        public MyAdapter(Context context, List<MyBean> datas) {
            super(context, datas);
        }

        @Override
        protected int getItemLayoutId(int viewType) {
            return R.layout.fragment_wodexiaozu_adapter_item;
        }

        @Override
        protected void onBindView(RViewHolder holder, int position, final MyBean bean) {
            ResUtil.setRoundImage(RsenUrlUtil.URL_BASE + bean.logo, holder.imgV(R.id.logo));

            holder.tV(R.id.title).setText(bean.title);
            holder.tV(R.id.memberCount).setText(bean.memberCount);

            /*item的点击事件*/
            holder.v(R.id.fragment_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    GroupInfoActivity.MyBean bean = new GroupInfoActivity.MyBean();
                    launch(mContext, isWeGroup, bean);
//                    GroupDetailFragmentActivity.launch(mContext, bean.group_id);
//                    Bundle bundle = new Bundle();
//
//                    bundle.putSerializable("group_info", bean.group_id );
//                    bundle.putBoolean("isWeGroup",isWeGroup);
//                    Intent intent = new Intent(context,GroupI.class);
//                    intent.putExtras(bundle);
////                startActivity(intent);
//                    startActivityForResult(intent,0);
                }
            });
        }
    }

    public static void launch(Context context, boolean isWeGroup, MyBean bean) {
        HashMap<String, String> map = new HashMap<>();
        Bundle bundle = new Bundle();
        map.put("id", bean.id);
        map.put("title", bean.title);
        map.put("group_type", bean.group_type);
        map.put("detail", bean.detail);
//        map.put("type_name", bean.type_name);
        map.put("post_count", bean.post_count);
        map.put("group_logo", Url.IMAGE + bean.logo);
        map.put("memberCount", bean.memberCount);
        map.put("uid", bean.uid);
        map.put("activity", bean.activity);

        map.put("is_join", bean.is_join);
        map.put("user_nickname", bean.gm_nickname);
        map.put("user_logo", bean.gm_logo);

        bundle.putSerializable("group_info", map);
        bundle.putBoolean("isWeGroup", isWeGroup);
        Intent intent = new Intent(context, GroupInfoActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static class MyBean {
        public String id;
        public String logo;
        public String title;
        public int MyGroupCount;
        public int MyPostCount;
        //        public String group_id;
        public String group_type;
        public String detail;
        //        public String type_name;
        public String post_count;
        public String memberCount;
        public String uid;

        public String group_background;
        public String type_id;
        public String activity;
        public String is_join;
        public String gm_logo;
        public String gm_nickname;
        public List<String> userList;//用户头像
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        //如果注册成功将用户名复制给输入框
        if (requestCode == 8 && resultCode == Activity.RESULT_OK) {
            group_probar.setVisibility(View.GONE);
            progressBarText.setVisibility(View.GONE);
            mygroup.setVisibility(View.VISIBLE);
            init();
        }

    }

}
/*
type_id = jsonObj.getString("type_id");
            map.put("id", jsonObj.getString("id"));
            map.put("title", jsonObj.getString("title"));
            map.put("type_name", tempMap.get(type_id));
            map.put("uid", jsonObj.getString("uid"));
            map.put("post_count", jsonObj.getString("post_count"));
            map.put("group_logo", Url.USERHEADURL + jsonObj.getString("logo"));
            map.put("group_background", Url.USERHEADURL + jsonObj.getString("background"));
            map.put("type_id", type_id);
            map.put("detail", jsonObj.getString("detail"));
            map.put("group_type", jsonObj.getString("type"));
            map.put("activity", jsonObj.getString("activity"));
            map.put("memberCount", jsonObj.getString("menmberCount"));
            map.put("is_join", jsonObj.getString("is_join"));
            JSONObject tempObj = jsonObj.getJSONObject("user");
            map.put("user_uid", tempObj.getString("uid"));
            map.put("user_username", tempObj.getString("username"));
            map.put("user_nickname", tempObj.getString("nickname"));
            map.put("user_avatar128", Url.USERHEADURL + tempObj.getString("avatar128").replace("opensns///opensns","opensns").replace("opensns//opensns","opensns"));

 */