package com.thinksky.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.rsen.RBaseAdapter;
import com.thinksky.rsen.RViewHolder;
import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.rsen.view.RGridView;
import com.thinksky.tox.GroupInfoActivity;
import com.thinksky.tox.R;
import com.thinksky.utils.MyJson;
import com.thinksky.utils.imageloader.ImageLoader;
import com.tox.ToastHelper;
import com.tox.Url;
import java.util.ArrayList;
import java.util.List;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class XiaozujingxuanActivity extends BaseBActivity {
  private static final String ARG_PARAM1 = "param1";
  private RGridView recyclerView;
  private boolean isWeGroup = true;
  private MyJson myjson = new MyJson();
  ListView listView;
  ImageView back_menu;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_xiaozujingxuan_layout);
    back_menu = (ImageView) findViewById(R.id.back_menu);
    recyclerView = (RGridView) findViewById(R.id.gridView);
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
          bean.activity = jsonObject.getString("activity");
          bean.id = jsonObject.getString("id");
          bean.gm_logo = jsonObject.getJSONObject("user").getString("avatar32");
          bean.gm_nickname = jsonObject.getJSONObject("user").getString("nickname");
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
      try {
        ImageLoader.loadOptimizedHttpImage(XiaozujingxuanActivity.this, bean.logo)
            .bitmapTransform(new CropCircleTransformation(XiaozujingxuanActivity.this))
            .placeholder(R.drawable.picture_1_no).error(R
            .drawable.picture_1_no).into
            (holder.imgV(R.id.logo));
      } catch (Exception e) {
        e.printStackTrace();
      }
      holder.tV(R.id.title).setText(bean.title);
      holder.tV(R.id.detail).setText(bean.detail);
      holder.tV(R.id.post_count).setText(bean.post_count);
      holder.tV(R.id.member_count).setText(bean.menmberCount);
      holder.v(R.id.fragment_layout).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          launch(mContext, isWeGroup, bean);
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
    public String create_time;
  }

  public static void launch(Context context, boolean isWeGroup, MyBean bean) {
    context.startActivity(GroupInfoActivity.makeIntent(context, bean.id));
    ;
  }
}
