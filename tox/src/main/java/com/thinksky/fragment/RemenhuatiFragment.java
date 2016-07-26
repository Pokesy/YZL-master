package com.thinksky.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.thinksky.rsen.RBaseAdapter;
import com.thinksky.rsen.RViewHolder;
import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.rsen.fragment.RBaseFragment;
import com.thinksky.tox.GroupPostInfoActivity;
import com.thinksky.tox.ImagePagerActivity;
import com.thinksky.tox.R;
import com.thinksky.utils.imageloader.ImageLoader;
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


public class RemenhuatiFragment extends RBaseFragment {
  private static final String ARG_PARAM1 = "param1";
  RecyclerView recyclerView;
  private RemenhuatiAdapter adapter;
  private boolean isWeGroup = true;
  KJBitmap kjBitmap;
//    private List<ImageView> imgViewList = new ArrayList<ImageView>();
//    private ImageView iv_1;
//    private ImageView iv_2;
//    private ImageView iv_3;

  public static RemenhuatiFragment newInstance(String param1) {
    RemenhuatiFragment fragment = new RemenhuatiFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    fragment.setArguments(args);
    return fragment;
  }


  @Override
  protected int getBaseLayoutId() {
    return R.layout.fragment_remenhuti_layout;
  }

  @Override
  protected void initViewData() {
    RsenUrlUtil.execute(RsenUrlUtil.URL_REMEN_HUATI, new RsenUrlUtil
        .OnJsonResultListener<RemenhuatiBean>() {
      @Override
      public void onNoNetwork(String msg) {
        ToastHelper.showToast(msg, Url.context);
      }

      @Override
      public void onParseJsonBean(List<RemenhuatiBean> beans, JSONObject jsonObject) {
        RemenhuatiBean bean = new RemenhuatiBean();
        try {
          bean.title = jsonObject.optString("title");
          bean.content = jsonObject.optString("content");
          bean.supportCount = jsonObject.optString("supportCount");
          bean.is_support = jsonObject.optString("is_support");
          bean.nickname = jsonObject.getJSONObject("user").optString("nickname");
//                    bean.logo = jsonObject.getString("logo");
          bean.id = jsonObject.optString("id");
          bean.uid = jsonObject.optString("uid");
          bean.group_id = jsonObject.optString("group_id");
          bean.create_time = jsonObject.optString("create_time");
          bean.update_time = jsonObject.optString("update_time");
          bean.last_reply_time = jsonObject.optString("last_reply_time");
          bean.status = jsonObject.optString("status");
          bean.view_count = jsonObject.optString("view_count");
          bean.reply_count = jsonObject.optString("reply_count");
          bean.is_top = jsonObject.optString("is_top");
//                    bean.memberCount = jsonObject.getString("memberCount");
          bean.cate_id = jsonObject.optString("cate_id");
          bean.user_logo = RsenUrlUtil.URL_BASE + jsonObject.getJSONObject("user").optString
              ("avatar32");
          bean.signature = jsonObject.getJSONObject("user").optString("signature");
          JSONArray imgList = jsonObject.optJSONArray("imgList");
          List<String> imgs = new ArrayList<String>();
          for (int i = 0; imgList != null && i < imgList.length(); i++) {
            imgs.add(imgList.getString(i));
          }
          bean.imgList = imgs;
          JSONArray posts_rply = jsonObject.optJSONArray("posts_rply");

          if (null != posts_rply) {
            bean.logolist = parseUserList(posts_rply);
          }

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

  @Override
  protected void initView(View rootView) {
//        iv_1 = (ImageView) rootView.findViewById(R.id.iv_1);
//        iv_2 = (ImageView) rootView.findViewById(R.id.iv_2);
//        iv_3 = (ImageView) rootView.findViewById(R.id.iv_3);
//        imgViewList = new ArrayList<ImageView>();
//        imgViewList.add(iv_1);
//        imgViewList.add(iv_2);
//        imgViewList.add(iv_3);
//        默认设置不显示图片
//        imgViewList.get(0).setVisibility(View.GONE);
//        imgViewList.get(1).setVisibility(View.GONE);
//        imgViewList.get(2).setVisibility(View.GONE);
    recyclerView = (RecyclerView) mViewHolder.v(R.id.recycler);
    recyclerView.setLayoutManager(new LinearLayoutManager(mBaseActivity, LinearLayoutManager
        .VERTICAL, false));
    adapter = new RemenhuatiAdapter(mBaseActivity);
    recyclerView.setAdapter(adapter);


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
    public String memberCount;
    public String id;
    public String uid;
    public String logo;
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
    public String signature;
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
    map.put("signature", bean.signature);

//        JSONObject tempJSONObj = jsonObj.getJSONObject("user");
    map.put("user_uid", bean.uid);
    map.put("user_nickname", bean.nickname);
    map.put("user_logo", bean.user_logo);

    bundle.putSerializable("post_info", map);
    bundle.putStringArrayList("imgList", (ArrayList<String>) bean.imgList);
    bundle.putStringArrayList("logolist", (ArrayList<String>) bean.logolist);
    bundle.putBoolean("isWeGroup", isWeGroup);
    Intent intent = new Intent(context, GroupPostInfoActivity.class);
    intent.putExtras(bundle);
    context.startActivity(intent);
  }

  /*数据适配器*/
  public class RemenhuatiAdapter extends RBaseAdapter<RemenhuatiBean> {
    Context context;

    public RemenhuatiAdapter(Context context) {
      super(context);
      this.context = context;
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
      if (!"".equals(bean.content)) {
        holder.tV(R.id.content).setText(bean.content.replace("\\n", "\n"));
      } else {
        holder.tV(R.id.content).setVisibility(View.GONE);
      }
      holder.tV(R.id.supportCount).setText(bean.supportCount);
      holder.tV(R.id.nickname).setText(bean.nickname);
      holder.tV(R.id.reply_count).setText(bean.reply_count);
//            ResUtil.setRoundImage(bean.user_logo, holder.imgV(R.id.user_logo));
      //为图片控件加载数据
      kjBitmap = KJBitmap.create();
      //ImageLoader.getInstance().displayImage(bean.user_logo, holder.imgV(R.id.user_logo),
      //        new DisplayImageOptions.Builder()
      //                .showImageOnLoading(R.drawable.ic_launcher)
      //                .showImageForEmptyUri(R.drawable.ic_launcher)
      //                .showImageOnFail(R.drawable.ic_launcher)
      //                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
      //                .displayer(new RoundedBitmapDisplayer(100)).build());
      try {
        ImageLoader.loadOptimizedHttpImage(context,
            bean.user_logo).
            bitmapTransform(new CropCircleTransformation(context)).into(holder.imgV(R.id.user_logo));
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (bean.imgList != null && bean.imgList.size() > 0) {
        holder.v(R.id.img_layout).setVisibility(View.VISIBLE);

        int size = bean.imgList.size();
        holder.v(R.id.iv_1).setVisibility(size > 0 ? View.VISIBLE : View.GONE);
        holder.v(R.id.iv_2).setVisibility(size > 1 ? View.VISIBLE : View.GONE);
        holder.v(R.id.iv_3).setVisibility(size > 2 ? View.VISIBLE : View.GONE);
        int height = getResources().getDimensionPixelSize(R.dimen.grid_img_height_three);
        if (size == 1) {
          height = getResources().getDimensionPixelSize(R.dimen.grid_img_height_single);
          RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup
              .LayoutParams
              .MATCH_PARENT, height);
          holder.v(R.id.images).setLayoutParams(params);
        } else if (size == 2) {
          height = getResources().getDimensionPixelSize(R.dimen.grid_img_height_two);
          RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup
              .LayoutParams
              .MATCH_PARENT, height);
          holder.v(R.id.images).setLayoutParams(params);
        } else {
          RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup
              .LayoutParams
              .MATCH_PARENT, height);
          holder.v(R.id.images).setLayoutParams(params);
        }

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
            try {
              ImageLoader.loadOptimizedHttpImage(getActivity(), url).into(imageView);
            } catch (Exception e) {
              e.printStackTrace();
            }
            //ImageLoader.getInstance().displayImage(url, imageView);
            final int in = i;
            imageView.setOnClickListener(new View.OnClickListener() {

              @Override
              public void onClick(View v) {
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
        @Override
        public void onClick(View v) {
          //                    Bundle bundle = new Bundle();
          launch(mContext, isWeGroup, bean);

        }
      });
    }
  }
}
