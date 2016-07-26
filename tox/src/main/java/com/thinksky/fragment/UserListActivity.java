package com.thinksky.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.rsen.RBaseAdapter;
import com.thinksky.rsen.RViewHolder;
import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.tox.R;
import com.thinksky.tox.UserInfoActivity;
import com.thinksky.utils.imageloader.ImageLoader;
import com.tox.BaseFunction;
import com.tox.ToastHelper;
import com.tox.Url;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/**
 * Created by jiao on 2016/3/13.
 */
public class UserListActivity extends BaseBActivity {
  private int group_id;
  private ListView myListView;
  private RecyclerView memberRecycler;
  private ImageView back_menu;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_list);
    Intent intent = this.getIntent();
    memberRecycler = (RecyclerView) findViewById(R.id.memberRecycler);
    group_id = intent.getIntExtra("group_id", 0);
//        myListView = (ListView) findViewById(R.id.myListView);
    back_menu = (ImageView) findViewById(R.id.back_menu);
    back_menu.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    initView();
  }

  private void initView() {
    RsenUrlUtil.execute(RsenUrlUtil.URL_CYXX, new RsenUrlUtil.OnJsonResultListener<MyBean>() {
      @Override
      public void onNoNetwork(String msg) {
        ToastHelper.showToast(msg, Url.context);
      }

      @Override
      public Map getMap() {
        Map map = new HashMap();
        map.put("id", group_id);
        if (BaseFunction.isLogin()) {
          map.put("session_id", Url.SESSIONID);
        }
        return map;
      }

      @Override
      public void onParseJsonBean(List<MyBean> beans, JSONObject jsonObject) {
        try {
          MyBean bean = new MyBean();
          bean.isfollow = jsonObject.optString("isfollow");
          JSONObject userObj = jsonObject.optJSONObject("user");
          if (null != userObj) {
            bean.avatar128 = userObj.optString("avatar128");
            bean.nickname = userObj.optString("nickname");
            bean.fans = userObj.optString("fans");
            bean.uid = userObj.optString("uid");
          }
          beans.add(bean);
        } catch (Exception e) {
          //Log.e("YZZ", e.getMessage());
        }
      }

      @Override
      public void onResult(boolean state, final List<MyBean> beans) {
        if (state) {

          memberRecycler.setAdapter(new MySubAdapter(UserListActivity.this, beans));

        } else {
          ToastHelper.showToast("请求失败", Url.context);
        }
      }


    });
  }

  /*成员头像*/
  public class MySubAdapter extends RBaseAdapter<MyBean> {

    public MySubAdapter(Context context, List<MyBean> datas) {
      super(context, datas);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
      return R.layout.user_list_item;
    }

    @Override
    protected void onBindView(final RViewHolder holder, int position, final MyBean bean) {
      String url = RsenUrlUtil.URL_BASE + bean.avatar128;
      try {
        ImageLoader.loadOptimizedHttpImage(UserListActivity.this, url).error(R.drawable
            .side_user_avatar)
            .placeholder(R.drawable.side_user_avatar).into(holder.imgV(R.id.user_logo));
      } catch (Exception e) {
        e.printStackTrace();
      }
      holder.tV(R.id.user_name).setText(bean.nickname);
      holder.tV(R.id.fans).setText("粉丝数" + bean.fans);
            /*点击用户头像*/
      if (bean.isfollow.equals("0")) {
        holder.tV(R.id.guanzhu).setSelected(true);
        holder.tV(R.id.guanzhu).setText("加关注");
      } else {
        holder.tV(R.id.guanzhu).setSelected(false);
        holder.tV(R.id.guanzhu).setText("取消关注");
      }
      holder.tV(R.id.guanzhu).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (BaseFunction.isLogin()) {
            if (bean.isfollow.equals("0")) {

              holder.tV(R.id.guanzhu).setText("取消关注");
              holder.tV(R.id.guanzhu).setSelected(true);

            } else {
              holder.tV(R.id.guanzhu).setText("加关注");
              holder.tV(R.id.guanzhu).setSelected(false);
            }


          } else {
            ToastHelper.showToast("请登录", Url.context);
          }
        }
      });

      holder.v(R.id.item_layout).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent intent = new Intent(mContext, UserInfoActivity.class);
          intent.putExtra("userUid", bean.uid);
//                    intent.putStringArrayListExtra("uidlist", userlist);
          startActivity(intent);
        }
      });
    }
  }

  public static class MyBean {
    public String fans;
    public String isfollow;
    public String nickname;
    public String avatar128;
    public String uid;

  }

}