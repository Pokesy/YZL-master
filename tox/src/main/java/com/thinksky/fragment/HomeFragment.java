package com.thinksky.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.thinksky.info.NewsListInfo;
import com.thinksky.info.NewsListInfo1;
import com.thinksky.rsen.RBaseAdapter;
import com.thinksky.rsen.RViewHolder;
import com.thinksky.rsen.ResUtil;
import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.rsen.view.RGridView;
import com.thinksky.tox.GroupInfoActivity;
import com.thinksky.tox.GroupPostInfoActivity;
import com.thinksky.tox.ImagePagerActivity;
import com.thinksky.tox.IssueDetail;
import com.thinksky.tox.NewsActivity;
import com.thinksky.tox.NewsDetailActivity;
import com.thinksky.tox.R;
import com.thinksky.utils.LogUtils;
import com.thinksky.utils.MyJson;
import com.thinksky.utils.imageloader.ImageLoader;
import com.tox.BaseApi;
import com.tox.BaseFunction;
import com.tox.ToastHelper;
import com.tox.Url;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.aframe.bitmap.KJBitmap;

/**
 * Created by jiao on 2016/1/27.
 */
public class HomeFragment extends Fragment
    implements View.OnClickListener, MyScrollview.OnScrollListener {
  protected AppCompatActivity mBaseActivity;
  private View view;
  private TextView zx_show, rm_show, ht_show, zj_show, time, count, support_count;
  private Intent intent;
  private RecyclerView Zx_listView, Emht_listView;
  private RGridView Rm_listView;
  private ListView list;
  private LinearLayout load_progressBar;
  private RemenhuatiAdapter rm_adapter;
  private MyAdapter xz_adapter;
  private boolean isWeGroup = true;
  private MyScrollview scrollView;
  private FrameLayout bofangshipin;
  private Context mContext;
  private MyJson myjson = new MyJson();
  private ListView newsListView;
  private KJBitmap kjBitmap;
  private RViewHolder viewHolder;
  private ArrayList<NewsListInfo1> newsListInfos;
  private BaseApi baseApi;
  private String session_id;
  private LinearLayout ll_zx;
  private View mMenuHot;
  private View mMenuMon;
  private View mMenuMyQuestion;
  private View mMenuSolution;
private RelativeLayout title_tl;
  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.home_home, null);
    scrollView = (MyScrollview) view.findViewById(R.id.scrollView);
    title_tl= (RelativeLayout) view.findViewById(R.id.title_tl);
    list = (ListView) view.findViewById(R.id.list);
    baseApi = new BaseApi();
    session_id = baseApi.getSeesionId();
    scrollView.setOnScrollListener(this);
    mContext = getActivity();
    viewHolder = new RViewHolder(view);
    initView();
    initzx();
    initdata();
    initViewData();
    return view;
  }

  private void initView() {

    mMenuHot = view.findViewById(R.id.rb_rmht);
    mMenuMon = view.findViewById(R.id.rb_zgxs);
    mMenuMyQuestion = view.findViewById(R.id.rb_wdtw);
    mMenuSolution = view.findViewById(R.id.rb_yjj);

    load_progressBar = (LinearLayout) view.findViewById(R.id.load_progressBar);
    View.OnClickListener menuClickListener = new View.OnClickListener() {
      @Override public void onClick(View v) {
        switch (v.getId()) {
          case R.id.rb_rmht:
            Intent intent = new Intent(getContext(), WendaListActivity.class);
            intent.putExtra("whichActivity", "HOT");
            startActivity(intent);
            break;
          case R.id.rb_zgxs:
            Intent intent1 = new Intent(getContext(), WendaListActivity.class);
            intent1.putExtra("whichActivity", "MON");
            startActivity(intent1);
            break;
          case R.id.rb_wdtw:
            if (BaseFunction.isLogin()) {

              Intent intent2 = new Intent(getContext(), WendaMyQuestionActivity.class);
              startActivity(intent2);
            } else {

              ToastHelper.showToast("未登陆，请登陆", getContext());
            }

            break;
          case R.id.rb_yjj:
            Intent intent3 = new Intent(getContext(), WendaListActivity.class);
            intent3.putExtra("whichActivity", "SOLUTION");
            startActivity(intent3);

            break;
          default:

            break;
        }
      }
    };

    mMenuHot.setOnClickListener(menuClickListener);
    mMenuMon.setOnClickListener(menuClickListener);
    mMenuMyQuestion.setOnClickListener(menuClickListener);
    mMenuSolution.setOnClickListener(menuClickListener);

    bofangshipin = (FrameLayout) view.findViewById(R.id.bofangshipin);
    newsListView = (ListView) view.findViewById(R.id.news_listView);
    Rm_listView = (RGridView) view.findViewById(R.id.Rm_listView);
    Zx_listView = (RecyclerView) view.findViewById(R.id.Zx_listView);
    Emht_listView = (RecyclerView) view.findViewById(R.id.Emht_listView);
    time = (TextView) view.findViewById(R.id.time);
    count = (TextView) view.findViewById(R.id.count);
    support_count = (TextView) view.findViewById(R.id.support_count);
    //        Zx_listView.setLayoutManager(new LinearLayoutManager(mBaseActivity, LinearLayoutManager.VERTICAL, false));
    Emht_listView.setLayoutManager(
        new LinearLayoutManager(mBaseActivity, LinearLayoutManager.VERTICAL, false));

    rm_adapter = new RemenhuatiAdapter(mBaseActivity);

    Emht_listView.setAdapter(rm_adapter);

    zx_show = (TextView) view.findViewById(R.id.zx_show);
    rm_show = (TextView) view.findViewById(R.id.rm_show);
    ht_show = (TextView) view.findViewById(R.id.ht_show);
    zj_show = (TextView) view.findViewById(R.id.zj_show);
    zx_show.setOnClickListener(this);
    zj_show.setOnClickListener(this);
    rm_show.setOnClickListener(this);
    ht_show.setOnClickListener(this);
  }

  @Override public void onClick(View view) {
    switch (view.getId()) {
      case R.id.zj_show:
        intent = new Intent(getActivity(), ZhuanjiActivity.class);

        getActivity().startActivity(intent);
        break;
      case R.id.zx_show:
        intent = new Intent(getActivity(), NewsActivity.class);

        getActivity().startActivity(intent);
        break;
      case R.id.rm_show:
        intent = new Intent(getActivity(), XiaozujingxuanActivity.class);

        getActivity().startActivity(intent);
        break;
      case R.id.ht_show:
        intent = new Intent(getActivity(), RemenhuatiActivity.class);

        getActivity().startActivity(intent);
        break;
      case R.id.bofangshipin:
        String url =
            "http://112.253.22.157/15/t/d/r/m/tdrmhygxlsciucrdujaezpoynryauu/dd.yinyuetai.com/476801528BD25AF829FEDB10C1296FB9.mp4?sc=5b0ea4268a475629&br=743&rd=Android";
        url.replace("<p>", "").replace("</p>", "");
        Log.d("url------------------>", "url");
        Bundle bundle = new Bundle();
        bundle.putString("url", url);

        Intent intent = new Intent(mContext, VedioView.class);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    mBaseActivity = (AppCompatActivity) context;
  }

  @SuppressWarnings("deprecation") @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    mBaseActivity = (AppCompatActivity) activity;
  }

  private void initzx() {
    newsListInfos = new ArrayList<NewsListInfo1>();
    RsenUrlUtil.execute(mContext, RsenUrlUtil.NEWSALL, new RsenUrlUtil.OnNetHttpResultListener() {
      @Override public void onNoNetwork(String msg) {
        ToastHelper.showToast(msg, Url.context);
      }

      @Override public void onResult(boolean state, String result, JSONObject jsonObject) {
        if (state) {
          NewsListInfo1 list = JSON.parseObject(result, NewsListInfo1.class);

          Zx_listView.setAdapter(new ZixunAdapter(mBaseActivity, list.getList()));
        }
      }
    });
  }

  @Override public void onScroll(int scrollY) {

      ToastHelper.showToast("sdadsaddsa", getActivity());
      float alpha = (float)scrollY/400;
    LogUtils.e(alpha+"");
      title_tl.setAlpha(alpha >= 0.8f ? 0.8f : (alpha));

  }

  /*数据适配器*/
  public class ZixunAdapter extends RBaseAdapter<NewsListInfo> {
    Context context;
    KJBitmap kjBitmap;

    public ZixunAdapter(Context context) {
      super(context);
    }

    public ZixunAdapter(Context context, List<NewsListInfo> datas) {
      super(context, datas);
    }

    @Override protected int getItemLayoutId(int viewType) {
      return R.layout.home_new_list_item;
    }

    @Override protected void onBindView(RViewHolder holder, int position, final NewsListInfo bean) {
      holder.tV(R.id.news_title).setText(bean.getTitle());
      //            holder.tV(R.id.news_author_name).setText(newsListInfos.get(position).getUser().getNickname());
      holder.tV(R.id.time).setText(bean.getCreate_time());
      holder.tV(R.id.news_description).setText(bean.getDescription());
      holder.tV(R.id.view_count).setText(bean.getView());
      holder.tV(R.id.comment_count).setText(bean.getComment());

      kjBitmap = KJBitmap.create();
      //            kjBitmap.display(holder.imgV(R.id.snapshots), Url.IMAGE + bean.getCover());
      ImageLoader.loadOptimizedHttpImage(getActivity(), Url.IMAGE + bean.getCover())
          .into(holder.imgV(R.id.snapshots));
      //ImageLoader.getInstance().displayImage(Url.IMAGE + bean.getCover(), holder.imgV(R.id.snapshots));
      //            ResUtil.setRoundImage(bean.user_logo, holder.imgV(R.id.user_logo));
      //            ImageLoader.getInstance().displayImage(bean.user_logo, holder.imgV(R.id.user_logo),
      //                    new DisplayImageOptions.Builder()
      //                            .showImageOnLoading(R.drawable.ic_launcher)
      //                            .showImageForEmptyUri(R.drawable.ic_launcher)
      //                            .showImageOnFail(R.drawable.ic_launcher)
      //                            .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
      //                            .displayer(new RoundedBitmapDisplayer(100)).build());

      holder.v(R.id.ll_zx).setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          Intent tempIntent = new Intent(mContext, NewsDetailActivity.class);
          NewsListInfo newsListInfo = new NewsListInfo();
          tempIntent.putExtra("support", bean.getSupport_count());
          tempIntent.putExtra("isSupport", bean.getSupport_count());
          tempIntent.putExtra("newsInfo", bean);
          getActivity().startActivity(tempIntent);
        }
      });
    }

    @Override public int getItemCount() {
      return mAllDatas.size() > 3 ? 3 : mAllDatas.size();
    }
  }

  protected void initdata() {
    RsenUrlUtil.execute(RsenUrlUtil.URL_XIAOZU_JINGXUAN,
        new RsenUrlUtil.OnJsonResultListener<MyBean>() {
          @Override public void onNoNetwork(String msg) {
            ToastHelper.showToast(msg, Url.context);
          }

          @Override public void onParseJsonBean(List<MyBean> beans, JSONObject jsonObject) {
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
              bean.gm_logo = jsonObject.getJSONObject("user").getString("avatar32");
              bean.gm_nickname = jsonObject.getJSONObject("user").getString("nickname");
            } catch (JSONException e) {
            }
            beans.add(bean);
          }

          @Override public void onResult(boolean state, List<MyBean> beans) {
            //                xz_adapter.resetData(beans);
            if (state) {
              Rm_listView.setAdapter(new MyAdapter(mBaseActivity, beans));
            } else {
              ToastHelper.showToast("请求失败", Url.context);
            }
          }
        });
  }

  protected void initViewData() {
    RsenUrlUtil.execute(RsenUrlUtil.URL_REMEN_HUATI,
        new RsenUrlUtil.OnJsonResultListener<RemenhuatiBean>() {
          @Override public void onNoNetwork(String msg) {
            ToastHelper.showToast(msg, Url.context);
          }

          @Override public void onParseJsonBean(List<RemenhuatiBean> beans, JSONObject jsonObject) {
            RemenhuatiBean bean = new RemenhuatiBean();
            try {
              bean.title = jsonObject.getString("title");
              bean.content = jsonObject.getString("content");
              bean.supportCount = jsonObject.getString("supportCount");
              bean.is_support = jsonObject.getString("is_support");
              bean.nickname = jsonObject.getJSONObject("user").getString("nickname");

              bean.user_logo =
                  RsenUrlUtil.URL_BASE + jsonObject.getJSONObject("user").getString("avatar32");

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
              JSONArray imgList = jsonObject.getJSONArray("imgList");
              List<String> imgs = new ArrayList<String>();
              for (int i = 0; imgList != null && i < imgList.length(); i++) {
                imgs.add(imgList.getString(i));
              }
              bean.imgList = imgs;
              JSONArray posts_rply = jsonObject.getJSONArray("posts_rply");
              if (("").equals(posts_rply)) {
                bean.logolist = parseUserList(posts_rply);
              }
            } catch (JSONException e) {
            }
            beans.add(bean);
          }

          @Override public void onResult(boolean state, List<RemenhuatiBean> beans) {

            if (state) {
              scrollView.setVisibility(View.VISIBLE);
              load_progressBar.setVisibility(View.GONE);
              rm_adapter.resetData(beans);
            }
          }
        });
  }

  private List<String> parseUserList(JSONArray userArray) {
    List<String> userList = new ArrayList<>();
    for (int i = 0; i < userArray.length(); i++) {
      try {
        JSONObject jsonObject = userArray.getJSONObject(i);
        JSONObject user = jsonObject.getJSONObject("rp_user");
        userList.add(RsenUrlUtil.URL_BASE + user.getString("avatar32"));
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
    return userList;
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

    @Override protected int getItemLayoutId(int viewType) {
      return R.layout.fragment_remen_ylq_adapter_item;
    }

    @Override
    protected void onBindView(RViewHolder holder, int position, final RemenhuatiBean bean) {
      holder.tV(R.id.title).setText(bean.title);
      if (!"".equals(bean.content)) {
        holder.tV(R.id.content).setText(bean.content.replace("\\n", "\n"));
      } else {
        holder.tV(R.id.content).setVisibility(View.GONE);
      }
      holder.tV(R.id.supportCount).setText(bean.supportCount);
      holder.tV(R.id.reply_count).setText(bean.reply_count);
      holder.tV(R.id.nickname).setText(bean.nickname);
      //            ResUtil.setRoundImage(bean.user_logo, holder.imgV(R.id.user_logo));
      //            ImageLoader.getInstance().displayImage(bean.user_logo, holder.imgV(R.id.user_logo),
      //                    new DisplayImageOptions.Builder()
      //                            .showImageOnLoading(R.drawable.ic_launcher)
      //                            .showImageForEmptyUri(R.drawable.ic_launcher)
      //                            .showImageOnFail(R.drawable.ic_launcher)
      //                            .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
      //                            .displayer(new RoundedBitmapDisplayer(100)).build());
      ImageLoader.loadOptimizedHttpImage(getActivity(), bean.user_logo)
          .
              bitmapTransform(new CropCircleTransformation(getActivity()))
          .into(holder.imgV(R.id.user_logo));
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
            ImageLoader.loadOptimizedHttpImage(getActivity(), url).into(imageView);
            //ImageLoader.getInstance().displayImage(url, imageView);
            final int in = i;
            imageView.setOnClickListener(new View.OnClickListener() {

              @Override public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImagePagerActivity.class);
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
        @Override public void onClick(View v) {
          //                    Bundle bundle = new Bundle();
          if (!"".equals(bean)) {
            launch(mContext, isWeGroup, bean);
          }
        }
      });
    }

    @Override public int getItemCount() {
      return mAllDatas.size() > 50 ? 50 : mAllDatas.size();
    }
  }

  /*数据bean*/
  public static class RemenhuatiBean {
    public String title;
    public String content;
    public String supportCount;
    public String is_support;
    public String nickname;
    public List<String> imgList;
    public List<String> logolist;
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
    bundle.putStringArrayList("logolist", (ArrayList<String>) bean.logolist);
    Intent intent = new Intent(context, GroupPostInfoActivity.class);
    intent.putExtras(bundle);
    context.startActivity(intent);
  }

  //    private List<String> parseUserList(JSONArray userArray) {
  //        List<String> userList = new ArrayList<>();
  //        for (int i = 0; i < userArray.length(); i++) {
  //            try {
  //                JSONObject jsonObject = userArray.getJSONObject(i);
  //                JSONObject user = jsonObject.getJSONObject("user");
  //                userList.add(RsenUrlUtil.URL_BASE + user.getString("avatar32"));
  //            } catch (JSONException e) {
  //                e.printStackTrace();
  //            }
  //        }
  //        return userList;
  //    }

  public class MyAdapter extends RBaseAdapter<MyBean> {
    public MyAdapter(Context context) {
      super(context);
    }

    public MyAdapter(Context context, List<MyBean> datas) {
      super(context, datas);
    }

    @Override protected int getItemLayoutId(int viewType) {
      return R.layout.fragment_xiaozujingxuan_adapter;
    }

    @Override protected void onBindView(RViewHolder holder, int position, final MyBean bean) {
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
      //                    bean_xz.userList.add(bean_xz.userList.get(0));
      //                }
      //            }

      //            ((RRecyclerView) holder.v(R.id.recycler)).setAdapter(new MySubAdapter(mContext, bean_xz.userList));

            /*item的点击事件*/
      holder.v(R.id.fragment_layout).setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          launch_xz(mContext, isWeGroup, bean);
          //                    GroupInfoActivity.MyBean bean = new GroupInfoActivity.MyBean();
          //                    GroupInfoActivity.launch(mContext,isWeGroup,bean);
        }
      });
    }

    @Override public int getItemCount() {
      return mAllDatas.size() > 4 ? 4 : mAllDatas.size();
    }
  }

  //    /*成员头像*/
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

  public static void launch_xz(Context context, boolean isWeGroup, MyBean bean) {
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
    map.put("user_logo", bean.gm_logo);
    bundle.putSerializable("group_info", map);
    bundle.putBoolean("isWeGroup", isWeGroup);
    Intent intent = new Intent(context, GroupInfoActivity.class);
    intent.putExtras(bundle);
    context.startActivity(intent);
  }

  @Override public void onResume() {
    super.onResume();
    RsenUrlUtil.execute(this.getActivity(), RsenUrlUtil.URL_ZJ,
        new RsenUrlUtil.OnNetHttpResultListener() {
          @Override public void onNoNetwork(String msg) {
            ToastHelper.showToast(msg, Url.context);
          }

          @Override public void onResult(boolean state, String result, JSONObject jsonObject) {
            if (state) {
              final ArrayList<ZhuanjiFragment.ZjBean> beans = parseJson(jsonObject);
              //                为图片控件加载数据
              kjBitmap = KJBitmap.create();
              if (!beans.isEmpty()) {
                //                    kjBitmap.display(viewHolder.imgV(R.id.issue_image), RsenUrlUtil.URL_BASE + beans.get(0).IssueList.get(0).cover_url);
                ImageLoader.loadOptimizedHttpImage(getActivity(),
                    RsenUrlUtil.URL_BASE + beans.get(0).IssueList.get(0).cover_url)
                    .into(viewHolder.imgV(R.id.issue_image));
                //ImageLoader.getInstance().displayImage(RsenUrlUtil.URL_BASE + beans.get(0).IssueList.get(0).cover_url, viewHolder.imgV(R.id.issue_image));

                time.setText(beans.get(0).IssueList.get(0).create_time);
                count.setText(beans.get(0).IssueList.get(0).reply_count);
                support_count.setText(beans.get(0).IssueList.get(0).support_count);
                bofangshipin.setOnClickListener(new View.OnClickListener() {
                  @Override public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", beans.get(0).IssueList.get(0).id);
                    Intent intent = new Intent(getActivity(), IssueDetail.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                  }
                });
              }
            }
          }
        });
  }

  public static ArrayList<ZhuanjiFragment.ZjBean> parseJson(JSONObject object) {
    ArrayList<ZhuanjiFragment.ZjBean> beans = new ArrayList<>();
    if (object != null) {
      try {
        JSONArray array = object.getJSONArray("list");
        for (int i = 0; i < array.length(); i++) {
          ZhuanjiFragment.ZjBean bean = new ZhuanjiFragment.ZjBean();
          JSONObject jsonObject = array.getJSONObject(i);
          bean.title = jsonObject.getString("title");//title 赋值
          bean.id = jsonObject.getInt("id");
          //其他字段。。。赋值

          // TODO: 2016/2/17

          ArrayList<ZhuanjiFragment.IssueBean> issueBeens = new ArrayList<>();
          JSONArray issueList = jsonObject.getJSONArray("$IssueList");
          for (int j = 0; j < issueList.length(); j++) {
            JSONObject issueListJSONObject = issueList.getJSONObject(j);
            ZhuanjiFragment.IssueBean issueBean = new ZhuanjiFragment.IssueBean();
            issueBean.title = issueListJSONObject.getString("title");// issue title 赋值
            issueBean.cover_url = issueListJSONObject.getString("cover_url");// issue cover_url 赋值
            issueBean.id = issueListJSONObject.getInt("id");
            issueBean.create_time = issueListJSONObject.getString("create_time");
            issueBean.reply_count = issueListJSONObject.getString("reply_count");
            issueBean.support_count = issueListJSONObject.getString("support_count");
            //其他字段。。。赋值
            // TODO: 2016/2/17

            issueBeens.add(issueBean);
          }

          bean.IssueList = issueBeens;
          beans.add(bean);
        }
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }

    return beans;
  }
}
