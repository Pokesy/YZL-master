package com.thinksky.tox;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.thinksky.myview.MoreTextView;
import com.thinksky.rsen.RBaseAdapter;
import com.thinksky.rsen.RViewHolder;
import com.thinksky.rsen.RsenUrlUtil;
import com.tox.BaseApi;
import com.tox.GroupApi;
import com.tox.ToastHelper;
import com.tox.Url;

import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.aframe.bitmap.KJBitmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/5/18 0018.
 */
public class GroupInfoActivityCopy extends Activity implements View.OnClickListener {
    private List<String> imgs;
    private static boolean PUBLICGROUP = false;
    private static boolean PRIVATEGROUP = false;
    private static boolean DISMISSGROUP = false;
    protected ScrollView group_scro;
    protected ImageView back_menu;
    protected ImageView cate_menu;
    protected RelativeLayout refreshButn;
    protected ArrayList<HashMap<String, String>> categoryList;
    private RemenhuatiAdapter rm_adapter;
    Context mContext;
    HashMap<String, String> groupInfoMap;
    Bundle GroupBundle;
    String session_id;
    Boolean isWeGroup;
    Intent postIntent;
    Bundle postBundle;
    RecyclerView group_post_listView;
    KJBitmap kjbImage;
    PopupWindow cateWindow;
    private GroupApi groupApi;
    private int group_id;
    private int cateID = 0;
    private Context ctx;
    private LinearLayout linear_list;
    private LinearLayout linear_body;
    private LinearLayout linear_isnull;
    private RelativeLayout body_probar;
    private ImageView group_post;
    private ImageView group_logo;
    private TextView group_name;
    private TextView group_type;
    private TextView group_type_name;
    private TextView post_count;
    private TextView man_count;
    private TextView join_status;
    private LinearLayout join_group;
    private LinearLayout post_at_top;
    private MoreTextView group_detail;
    private ImageView refreshImage;
    private HashMap<String, String> titleMap;
    private ArrayList<HashMap<String, String>> postInfoList;
    private boolean count = true;
    private int maxNumber = 0;
    private int page = 1;
    private int index = 0;
    private Intent post;
    private boolean joinFlag = false;
    private int isJoin;
    private long lastClick;
    //对加入群组的状态进行实时判断
    private Handler tempHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                String result = (String) msg.obj;
                groupInfoMap = groupApi.getGroupInfoMap(result, groupInfoMap);
                Log.e("groupInfoMap>>>>>>>>", groupInfoMap.toString());
                isJoin = Integer.parseInt(groupInfoMap.get("is_join"));
                if (isJoin == 1) {
                    joinFlag = false;
                    join_status.setText("退出群组");
                }
                if (groupInfoMap.get("uid").equals(groupApi.getUid())) {
                    join_status.setText("解散群组");
                }
                if (isJoin == -1) {
                    join_status.setText("已申请，审核中");
                    join_group.setClickable(false);
                } else if (isJoin != 1) {
                    join_status.setText("+加入群组");
                    joinFlag = true;
                }
            }
        }
    };
    private Handler myHandler = new Handler() {

        private ArrayList<HashMap<String, String>> categoryList;
        private ArrayList<HashMap<String, String>> topPostInfoList;

        @Override
        @SuppressWarnings(value = {"unchecked"})
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (DISMISSGROUP) {
                        Intent intent = new Intent();
                        intent.putExtra("isWeGroup", isWeGroup);
                        setResult(1, intent);
                        GroupInfoActivityCopy.this.finish();
                        break;
                    }
                    if (joinFlag) {
                        joinFlag = true;
                        ToastHelper.showToast("退出群组成功", ctx);
                        join_status.setText("+加入群组");
                    } else {
                        if (PUBLICGROUP) {
                            joinFlag = false;
                            ToastHelper.showToast("加入群组成功", ctx);
                            join_status.setText("退出群组");
                        }
                        if (PRIVATEGROUP) {
                            join_group.setClickable(false);
                            ToastHelper.showToast("申请加入群组成功", ctx);
                            join_status.setText("正在审核");
                        }
                    }
                    break;
                case 800:
                    if (PUBLICGROUP) {
                        if (joinFlag) {
                            join_status.setText("+加入群组");
                            ToastHelper.showToast("退群失败，还未加入该群", ctx);
                        } else {
                            join_status.setText("+退出群组");
                            ToastHelper.showToast("你已加入该群", ctx);
                        }
                    }
                    if (PRIVATEGROUP) {
                        join_status.setText("已申请，审核中");
                        join_group.setClickable(false);
                    }
                    break;
                case 0x120:
//                    categoryList = (ArrayList<HashMap<String, String>>) msg.obj;
////                    Log.e("categoryList>>>>>>>>",categoryList.toString());
//                    createCatePopWindow(categoryList);
                    break;
                case 0x122:


                    break;
                //置顶区帖子
                case 0x124:
//                    topPostInfoList = (ArrayList<HashMap<String, String>>) msg.obj;
//                    //设置子控件间距
//                    LinearLayout.LayoutParams linerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//                    linerParams.setMargins(0, 0, 0, 2);
//                    if (topPostInfoList.size() > 0) {
//                        for (HashMap<String, String> map : topPostInfoList) {
//                            LinearLayout tempLinear = new LinearLayout(mContext);
//                            tempLinear.setOrientation(LinearLayout.HORIZONTAL);
//                            tempLinear.setLayoutParams(linerParams);
//                            tempLinear.setPadding(5, 5, 5, 5);
//                            tempLinear.setId(Integer.parseInt(map.get("id")));
//                            tempLinear.setTag(map);
//                            tempLinear.setBackgroundResource(R.drawable.item_bg);
//                            tempLinear.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    postInfoTo((HashMap<String, String>) v.getTag());
////                                    Log.e("postID>>>>>>>>>", v.getTag().toString());
//                                }
//                            });
//                            ImageView topImage = new ImageView(mContext);
//                            topImage.setPadding(0, 0, 20, 0);
//                            topImage.setImageResource(R.drawable.ic_top);
//                            TextView topText = new TextView(mContext);
//                            topText.setTextColor(Color.parseColor("#000000"));
//                            topText.setSingleLine(true);
//                            topText.setEllipsize(TextUtils.TruncateAt.END);
//                            topText.setText(map.get("title"));
//                            tempLinear.addView(topImage);
//                            tempLinear.addView(topText);
//                            post_at_top.addView(tempLinear);
//                        }
//                    } else {
//                        TextView tempView = new TextView(mContext);
//                        tempView.setBackgroundResource(R.drawable.item_bg);
//                        tempView.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                sendPost();
//                            }
//                        });
//                        tempView.setTextColor(Color.parseColor("#666666"));
//                        tempView.setText("大家一起来讨论吧");
//                        post_at_top.addView(tempView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                    }
                    break;
                default:
                    break;
            }
        }
    };

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



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);
        ctx = this;
        mContext = GroupInfoActivityCopy.this;
        session_id = new BaseApi().getSeesionId();
        groupApi = new GroupApi();
        kjbImage = KJBitmap.create();
        //获得上个activity传递的群组信息
        Intent groupIntent = this.getIntent();
        GroupBundle = groupIntent.getExtras();
        isWeGroup = GroupBundle.getBoolean("isWeGroup");
        groupInfoMap = (HashMap<String, String>) GroupBundle.getSerializable("group_info");
        Log.e("groupInfoMap>>>>>>>>", groupInfoMap.toString());

        group_id = Integer.parseInt(groupInfoMap.get("id"));
        //即时获取群组信息线程
        groupApi.setHandler(tempHandler);
        groupApi.getGroupInfo(String.valueOf(group_id));

        post = new Intent(mContext, PostGroupActivity.class);
        post.putExtra("group_id", group_id);

        back_menu = (ImageView) findViewById(R.id.back_menu);
        group_post = (ImageView) findViewById(R.id.group_post);
        group_scro = (ScrollView) findViewById(R.id.group_scro);
        linear_list = (LinearLayout) findViewById(R.id.linear_list);
        linear_body = (LinearLayout) findViewById(R.id.linear_body);
        linear_isnull = (LinearLayout) findViewById(R.id.linear_isnull);
        body_probar = (RelativeLayout) findViewById(R.id.body_probar);
        group_logo = (ImageView) findViewById(R.id.group_logo);
        group_name = (TextView) findViewById(R.id.group_name);
        group_type = (TextView) findViewById(R.id.group_type);
//        group_type_name = (TextView) findViewById(R.id.group_type_name);
        post_count = (TextView) findViewById(R.id.post_count);
        man_count = (TextView) findViewById(R.id.man_count);
        join_status = (TextView) findViewById(R.id.join_status);
        cate_menu = (ImageView) findViewById(R.id.cate_menu);

        join_group = (LinearLayout) findViewById(R.id.join_group);
        post_at_top = (LinearLayout) findViewById(R.id.post_at_top);
        group_post_listView = (RecyclerView) findViewById(R.id.group_post_listView);
        group_detail = (MoreTextView) findViewById(R.id.group_detail);
        refreshButn = (RelativeLayout) findViewById(R.id.refresh_butn);
        refreshImage = (ImageView) findViewById(R.id.refresh_image);


        group_logo.setOnClickListener(this);
        back_menu.setOnClickListener(this);
        group_post.setOnClickListener(this);
        linear_isnull.setOnClickListener(this);
        cate_menu.setOnClickListener(this);
        join_group.setOnClickListener(this);
        refreshButn.setOnClickListener(this);

        InitGroupView(groupInfoMap);


        //获取帖子分类线程
        postInfoList = new ArrayList<HashMap<String, String>>();
//        new CategoryThread().start();
//
        group_scro.smoothScrollTo(0, 0);
    }

    private void init() {
        rm_adapter=new RemenhuatiAdapter(mContext);
        group_post_listView.setAdapter(rm_adapter);
        RsenUrlUtil.execute(RsenUrlUtil.URL_XIAOZU_XIANGQINGTZ, new RsenUrlUtil.OnJsonResultListener<RemenhuatiBean>() {
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
            public void onParseJsonBean(List<RemenhuatiBean> beans, JSONObject jsonObject) {
                RemenhuatiBean bean = new RemenhuatiBean();
                try {
                    bean.title = jsonObject.getString("title");
                    bean.content = jsonObject.getString("content");
                    bean.supportCount = jsonObject.getString("supportCount");
                    bean.is_support = jsonObject.getString("is_support");
                    bean.nickname = jsonObject.getJSONObject("user").getString("nickname");

                    bean.user_logo = RsenUrlUtil.URL_BASE + jsonObject.getJSONObject("user").getString("avatar32");

                    bean.id = jsonObject.getString("id");
                    bean.uid = jsonObject.getString("uid");
                    bean.group_id = jsonObject.getString("group_id");
                    bean.create_time = jsonObject.getString("create_time");
                    bean.update_time = jsonObject.getString("update_time");
                    bean.last_reply_time = jsonObject.getString("last_reply_time");
                    bean.status = jsonObject.getString("status");
//                    bean.view_count = jsonObject.getString("view_count");
                    bean.reply_count = jsonObject.getString("reply_count");
                    bean.is_top = jsonObject.getString("is_top");

                    bean.cate_id = jsonObject.getString("cate_id");
//                    JSONArray imgList = jsonObject.getJSONArray("imgList");
//                    List<String> imgs = new ArrayList<String>();
//                    for (int i = 0; imgList != null && i < imgList.length(); i++) {
//                        imgs.add(imgList.getString(i));
//                    }
//                    bean.imgList = imgs;
                    beans.add(bean);
                } catch (JSONException e) {
                }

            }

            @Override
            public void onResult(boolean state, List<RemenhuatiBean> beans) {
                if (state) {
                    maxNumber = beans.size();
                    if (beans.size() != 0) {
                        linear_list.setVisibility(View.VISIBLE);
                        linear_isnull.setVisibility(View.GONE);

                                rm_adapter.resetData(beans);
//                        group_post_listView.setAdapter(new GroupListAdapter(mContext, postInfoList, R.layout.group_post_item, null, null));
//                        Utility.setListViewHeightBasedOnChildren(group_post_listView);
                    } else {
                        linear_isnull.setVisibility(View.VISIBLE);
                        linear_list.setVisibility(View.GONE);
                    }
                    linear_body.setVisibility(View.VISIBLE);
                    body_probar.setVisibility(View.GONE);
                } else {
                    ToastHelper.showToast("请求失败", Url.context);
                }
            }
        });
    }

    //初始化群组固有信息
    public void InitGroupView(HashMap<String, String> groupInfoMap) {

        group_name.setText(groupInfoMap.get("title"));
        if (groupInfoMap.get("group_type").equals("1")) {
            group_type.setText("私有群组");
        }
        group_detail.setText("群组简介：" + groupInfoMap.get("detail"));
//        group_type_name.setText(groupInfoMap.get("type_name"));
        post_count.setText(groupInfoMap.get("post_count"));
        man_count.setText(groupInfoMap.get("memberCount"));
        if (groupInfoMap.get("group_logo").equals(Url.USERHEADURL + "Public/Core/images/nopic.png")) {
            kjbImage.display(group_logo, Url.USERHEADURL + "Public/Group/images/icon.jpg");
        } else {
            kjbImage.display(group_logo, groupInfoMap.get("group_logo"));
        }
        init();
    }

    @Override
    public void onClick(View v) {
        int viewID = v.getId();
        switch (viewID) {
            case R.id.back_menu:
                finish();
                break;
            case R.id.group_post:
                sendPost();
                break;
            case R.id.cate_menu:
                cateWindow.showAsDropDown(group_post);
                break;
            case R.id.linear_isnull:
                sendPost();
                break;
            case R.id.group_logo:
                Intent intent = new Intent(mContext, GroupDetailActivity.class);
                intent.putExtra("groupInfoMap", groupInfoMap);
                startActivity(intent);
                break;
            case R.id.join_group:
                if (!groupApi.getSeesionId().equals("")) {
                    if (groupInfoMap.get("uid").equals(groupApi.getUid())) {
                        new AlertDialog.Builder(mContext)
                                .setTitle("解散群组")
                                .setMessage("确定吗？")
                                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        initFlag(false, false, true);
                                        groupApi.setHandler(myHandler);
                                        groupApi.dismissGroup(group_id + "");
                                    }
                                })
                                .setNegativeButton("否", null)
                                .show();
                        break;
                    }
                    if (isJoin == -1) {
                        join_group.setClickable(false);
                    }
                    if (joinFlag) {
                        //加入群组
                        if (groupInfoMap.get("group_type").equals("1")) {
                            //加入的是私有群组
                            initFlag(false, true, false);
                        } else {
                            initFlag(true, false, false);
                        }
                        groupApi.setHandler(myHandler);
                        groupApi.joinGroupPost(group_id + "");
                        joinFlag = false;
                    } else {
                        //退出群组
                        new AlertDialog.Builder(mContext)
                                .setTitle("退出群组")
                                .setMessage("确定吗？")
                                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        groupApi.setHandler(myHandler);
                                        groupApi.quitGroupPost(group_id + "");
                                        joinFlag = true;
                                    }
                                })
                                .setNegativeButton("否", null)
                                .show();
                    }
                } else {
                    ToastHelper.showToast("请登陆后操作", mContext);
                    join_group.setClickable(false);
                }
                break;
            case R.id.refresh_butn:
                //大于2秒可以通过
                if (System.currentTimeMillis() - lastClick <= 2000) {
                    Toast.makeText(GroupInfoActivityCopy.this, "请勿重复刷新", Toast.LENGTH_SHORT).show();
                    return;
                }
                lastClick = System.currentTimeMillis();
                postInfoList.clear();
                page = 1;
                count = true;
//                new GroupPostThread(page, cateID).start();
                AnimationSet animationSet = new AnimationSet(true);
                RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setDuration(2000);
                animationSet.addAnimation(rotateAnimation);
                refreshImage.startAnimation(animationSet);
                break;
            default:
                break;
        }
    }

    //判断发帖权限
    public void sendPost() {
        if (!joinFlag) {
            post.putExtra("categoryList", categoryList);
            startActivity(post);
        } else {
            ToastHelper.showToast("加群才能发帖", mContext);
        }
    }



    //标记加入的是私有还是共有群组
    public void initFlag(boolean publicgroup, boolean privategroup, boolean dismissgroup) {
        PUBLICGROUP = publicgroup;
        PRIVATEGROUP = privategroup;
        DISMISSGROUP = dismissgroup;
    }


    //控件缓存器
    private class ViewHolder {
        ImageView user_image;
        TextView post_title;
        TextView user_name;
        TextView post_category;
        TextView view_count;
        TextView reply_count;
    }
//
//    //帖子分类导航线程
//    class CategoryThread extends Thread implements Runnable {
//
//        private ArrayList<JSONObject> jsonObjArrayList;
//
//        public CategoryThread() {
//            super();
//        }
//
//        @Override
//        public void run() {
//            categoryList = new ArrayList<HashMap<String, String>>();
//            titleMap = new HashMap<String, String>();
//            jsonObjArrayList = groupApi.getGroupCategory("?s=" + Url.POSTCATEGORY, group_id);
//            HashMap<String, String> map = new HashMap<String, String>();
//            map.put("id", "0");
//            map.put("group_id", String.valueOf(group_id));
//            map.put("title", "全部分类");
//            map.put("status", "1");
//            categoryList.add(map);
//            for (int i = 0; i < jsonObjArrayList.size(); i++) {
//                JSONObject jsonObj = jsonObjArrayList.get(i);
//                HashMap<String, String> map1 = new HashMap<String, String>();
//                try {
//                    titleMap.put(jsonObj.getString("id"), jsonObj.getString("title"));
//                    map1.put("id", jsonObj.getString("id"));
//                    map1.put("group_id", jsonObj.getString("group_id"));
//                    map1.put("title", jsonObj.getString("title"));
//                    map1.put("status", jsonObj.getString("status"));
//
//                    categoryList.add(map1);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            Message message = new Message();
//            message.what = 0x120;
//            message.obj = categoryList;
//            myHandler.sendMessage(message);
//        }
//    }

//    //全部帖子信息线程
//    class GroupPostThread extends Thread implements Runnable {
//
//        private ArrayList<JSONObject> jsonObjArrayList;
//        private int page;
//        private int cateID;
//
//        public GroupPostThread(int page, int cateID) {
//            this.page = page;
//            this.cateID = cateID;
//        }
//
//        @Override
//        public void run() {
////            jsonObjArrayList = groupApi.getGroupPostList("?s=" + Url.POSTALL, page, group_id, cateID, session_id);
////            for (int i = 0; i < jsonObjArrayList.size(); i++) {
////                JSONObject jsonObj = jsonObjArrayList.get(i);
////                postInfoList.add(getPostMap(jsonObj));
////            }
//            rm_adapter = new RemenhuatiAdapter(mContext);
//
//            Message message = new Message();
//            message.what = 0x122;
//            myHandler.sendMessage(message);
//        }
//    }

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
            holder.tV(R.id.reply_count).setText(bean.reply_count);
            holder.tV(R.id.nickname).setText(bean.nickname);
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

                for (int i = 0; i < bean.imgList.size(); i++) {
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
                                Intent intent = new Intent(GroupInfoActivityCopy.this, ImagePagerActivity.class);
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
                }
            });

        }

        @Override
        public int getItemCount() {
            return mAllDatas.size() > 50 ? 50 : mAllDatas.size();
        }

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
//    //置顶帖子信息线程
//    class TopPostThread extends Thread implements Runnable {
//
//        ArrayList<HashMap<String, String>> topPostInfoList;
//        private ArrayList<JSONObject> jsonObjArrayList;
//        private int page;
//
//        public TopPostThread(int page) {
//            this.page = page;
//        }
//
//        @Override
//        public void run() {
//            topPostInfoList = new ArrayList<HashMap<String, String>>();
//            jsonObjArrayList = groupApi.getGroupPostList("?s=" + Url.TOPPOST, page, group_id, 0, session_id);
//            for (int i = 0; i < jsonObjArrayList.size(); i++) {
//                JSONObject jsonObj = jsonObjArrayList.get(i);
//                topPostInfoList.add(getPostMap(jsonObj));
//            }
//            Message message = new Message();
//            message.what = 0x124;
//            message.obj = topPostInfoList;
//            myHandler.sendMessage(message);
//        }
//    }
}
