package com.thinksky.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.rsen.RBaseAdapter;
import com.thinksky.rsen.RViewHolder;
import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.tox.GroupPostInfoActivity;
import com.thinksky.tox.ImagePagerActivity;
import com.thinksky.tox.R;
import com.tox.ToastHelper;
import com.tox.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class RemenhuatiActivity extends BaseBActivity {
    private List<String> pictureListUrls;

    ListView listView;

    ImageView back_menu;
    private static final String ARG_PARAM1 = "param1";
    RecyclerView recyclerView;
    private RemenhuatiAdapter adapter;
    private boolean isWeGroup = true;
    private List<ImageView> imgViewList;
    private ImageView iv_1;
    private ImageView iv_2;
    private ImageView iv_3;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remenhuti_layout);
        listView = (ListView) findViewById(R.id.listView);
        back_menu = (ImageView) findViewById(R.id.back_menu);
        iv_1 = (ImageView) findViewById(R.id.iv_1);
        iv_2 = (ImageView) findViewById(R.id.iv_2);
        iv_3 = (ImageView) findViewById(R.id.iv_3);
        imgViewList = new ArrayList<ImageView>();
        imgViewList.add(iv_1);
        imgViewList.add(iv_2);
        imgViewList.add(iv_3);
        //默认设置不显示图片
//        imgViewList.get(0).setVisibility(View.GONE);
//        imgViewList.get(1).setVisibility(View.GONE);
//        imgViewList.get(2).setVisibility(View.GONE);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(RemenhuatiActivity.this, LinearLayoutManager.VERTICAL, false));
        adapter = new RemenhuatiAdapter(RemenhuatiActivity.this);
        recyclerView.setAdapter(adapter);
        back_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initViewData();
    }


    private void initViewData() {
        RsenUrlUtil.execute(RsenUrlUtil.URL_REMEN_HUATI, new RsenUrlUtil.OnJsonResultListener<RemenhuatiBean>() {
            @Override
            public void onNoNetwork(String msg) {
                ToastHelper.showToast(msg, Url.context);
            }

            @Override
            public void onParseJsonBean(List<RemenhuatiBean> beans, JSONObject jsonObject) {
                RemenhuatiBean bean = new RemenhuatiBean();
                try {
                    bean.title = jsonObject.getString("title");
                    bean.content = jsonObject.getString("content");
                    bean.supportCount = jsonObject.getString("supportCount");
                    bean.is_support = jsonObject.getString("is_support");
                    bean.nickname = jsonObject.getJSONObject("user").getString("nickname");

                    bean.id = jsonObject.getString("id");
                    bean.uid = jsonObject.getString("uid");
                    bean.group_id = jsonObject.getString("group_id");
                    bean.create_time = jsonObject.getString("create_time");
                    bean.update_time = jsonObject.getString("update_time");
                    bean.last_reply_time = jsonObject.getString("last_reply_time");
                    bean.status = jsonObject.getString("status");
                    bean.view_count = jsonObject.getString("view_count");
                    bean.reply_count = jsonObject.getString("reply_count");
                    bean.is_top = jsonObject.getString("is_top");

                    bean.cate_id = jsonObject.getString("cate_id");
                    bean.user_logo = RsenUrlUtil.URL_BASE + jsonObject.getJSONObject("user").getString("avatar32");
                    JSONArray imgList = jsonObject.getJSONArray("imgList");
                    List<String> imgs = new ArrayList<String>();
                    for (int i = 0; imgList != null && i < imgList.length(); i++) {
                        imgs.add(imgList.getString(i));
                    }
                    bean.imgList = imgs;
                } catch (JSONException e) {
                }
                beans.add(bean);
            }

            @Override
            public void onResult(boolean state, List<RemenhuatiBean> beans) {
                adapter.resetData(beans);
            }
        });
    }


    /*数据bean*/
    public static class RemenhuatiBean {
        public String title;
        public String content;
        public String supportCount;
        public String is_support;
        public String nickname;
        public List<String> imgList;
        public String id;
        public String uid;
        public String group_id;
        public String create_time;
        public String update_time;
        public String last_reply_time;
        public String status;
        public String view_count;
        public String reply_count;
        public String is_top;
        public String cate_id;
        public String user_logo;
    }

    public static void launch(Context context, boolean isWeGroup, RemenhuatiBean bean) {
        HashMap<String, String> map = new HashMap<>();
        Bundle bundle = new Bundle();
//        map.put("id", bean.id);
//        map.put("title", bean.title);
//        map.put("group_type", bean.group_type);
//        map.put("detail", bean.detail);
//        map.put("type_name", bean.type_name);
//        map.put("post_count", bean.post_count);
//        map.put("group_logo", bean.logo);
//        map.put("memberCount", bean.memberCount);
//        map.put("uid", bean.uid);


        map.put("id", bean.id);
        map.put("uid", bean.uid);
        map.put("group_id", bean.group_id);
//        map.put("group_name", groupInfoMap.get("title"));
        map.put("title", bean.title);
        map.put("content", bean.content);
        map.put("create_time", bean.create_time);
        map.put("update_time", bean.update_time);
        map.put("last_reply_time", bean.last_reply_time);
        map.put("status", bean.status);
        map.put("view_count", bean.view_count);
        map.put("reply_count", bean.reply_count);
        map.put("is_top", bean.is_top);
        map.put("cate_id", bean.cate_id);
        map.put("supportCount", bean.supportCount);
        map.put("is_support", bean.is_support);

//        JSONObject tempJSONObj = jsonObj.getJSONObject("user");
        map.put("user_uid", bean.uid);
        map.put("user_nickname", bean.nickname);
        map.put("user_logo", bean.user_logo);

        bundle.putSerializable("post_info", map);
        bundle.putBoolean("isWeGroup", isWeGroup);
        bundle.putStringArrayList("imgList", (ArrayList<String>) bean.imgList);
        Intent intent = new Intent(context, GroupPostInfoActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /*数据适配器*/
    public class RemenhuatiAdapter extends RBaseAdapter<RemenhuatiBean> {
        Context context;

        public RemenhuatiAdapter(Context context) {
            super(context);
        }

        public RemenhuatiAdapter(Context context, List<RemenhuatiBean> datas) {
            super(context, datas);
        }

        @Override
        protected int getItemLayoutId(int viewType) {
            return R.layout.fragment_remen_ylq_adapter_item;
        }

        @Override
        protected void onBindView(RViewHolder holder, int position, final RemenhuatiBean bean) {
            holder.tV(R.id.title).setText(bean.title);
            holder.tV(R.id.content).setText(bean.content);
            holder.tV(R.id.supportCount).setText(bean.supportCount);
            holder.tV(R.id.nickname).setText(bean.nickname);
            holder.tV(R.id.reply_count).setText(bean.reply_count);
//            ResUtil.setRoundImage(bean.user_logo, holder.imgV(R.id.user_logo));
            ImageLoader.getInstance().displayImage(bean.user_logo, holder.imgV(R.id.user_logo),
                    new DisplayImageOptions.Builder()
                            .showImageOnLoading(R.drawable.ic_launcher)
                            .showImageForEmptyUri(R.drawable.ic_launcher)
                            .showImageOnFail(R.drawable.ic_launcher)
                            .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                            .displayer(new RoundedBitmapDisplayer(100)).build());
            if (bean.imgList != null && bean.imgList.size() > 0) {
                holder.v(R.id.img_layout).setVisibility(View.VISIBLE);

                int size = bean.imgList.size();
                holder.v(R.id.iv_1).setVisibility(size > 0 ? View.VISIBLE : View.GONE);
                holder.v(R.id.iv_2).setVisibility(size > 1 ? View.VISIBLE : View.GONE);
                holder.v(R.id.iv_3).setVisibility(size > 2 ? View.VISIBLE : View.GONE);

                for (int i = 0; i < size; i++) {
                    String url = RsenUrlUtil.URL_BASE + bean.imgList.get(i);

                    ImageView imageView = null;
                    if (i == 0) {
                        imageView = holder.imgV(R.id.iv_1);
                    } else if (i == 1) {
                        imageView = holder.imgV(R.id.iv_2);
                    } else if (i == 2) {
                        imageView = holder.imgV(R.id.iv_3);
                    }

                    if (imageView != null) {

                        ImageLoader.getInstance().displayImage(url, imageView);
                        final int in = i;
                        imageView.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(RemenhuatiActivity.this, ImagePagerActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putStringArrayList("image_urls", (ArrayList<String>) bean.imgList);
                                bundle.putInt("image_index", in);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                    }
                }
            } else {
                holder.v(R.id.img_layout).setVisibility(View.GONE);
            }

            holder.v(R.id.root_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //                    Bundle bundle = new Bundle();
                    launch(mContext, isWeGroup, bean);
//                    for (int i = 0; i < bean.imgList.size(); i++) {
//                        String url = RsenUrlUtil.URL_BASE + bean.imgList.get(i);
//                        imgViewList.get(i).setVisibility(View.VISIBLE);
//                        kjBitmap.display(imgViewList.get(i), url);
//                        final int in = i;
//                        imgViewList.get(in).setOnClickListener(new View.OnClickListener() {
//
//                            @Override
//                            public void onClick(View v) {
//                                Intent intent = new Intent(getActivity(), ImagePagerActivity.class);
//                                Bundle bundle = new Bundle();
//                                bundle.putStringArrayList("image_urls", (ArrayList<String>) bean.imgList);
//                                bundle.putInt("image_index", in);
//                                intent.putExtras(bundle);
//                                startActivity(intent);
//                            }
//                        });
//                    }
                }
            });
        }
    }
}
