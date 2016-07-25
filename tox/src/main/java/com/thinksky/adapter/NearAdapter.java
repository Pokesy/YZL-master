package com.thinksky.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.thinksky.info.UserInfo;
import com.thinksky.tox.R;
import com.thinksky.utils.LoadImg;
import com.thinksky.utils.imageloader.ImageLoader;
import com.tox.Url;
import java.util.List;

public class NearAdapter extends BaseAdapter {

  private List<UserInfo> list;
  private Context ctx;
  private LoadImg loadImgHeadImg;

  public NearAdapter(Context ctx, List<UserInfo> list) {
    this.ctx = ctx;
    this.list = list;
    loadImgHeadImg = new LoadImg(ctx);
  }

  @Override
  public int getCount() {
    return list.size();
  }

  @Override
  public Object getItem(int arg0) {
    return list.get(arg0);
  }

  @Override
  public long getItemId(int arg0) {
    return arg0;
  }

  @Override
  public View getView(final int arg0, View arg1, ViewGroup arg2) {
    final Holder hold;
    if (arg1 == null) {
      hold = new Holder();
      arg1 = View.inflate(ctx, R.layout.item_near, null);
      hold.Near_UserName = (TextView) arg1
          .findViewById(R.id.Near_UserName);
      hold.Near_Distance = (TextView) arg1
          .findViewById(R.id.Near_Distance);
      hold.Near_Sex = (TextView) arg1.findViewById(R.id.Near_Sex);
      hold.Near_UserInfo = (TextView) arg1
          .findViewById(R.id.Near_UserInfo);
      hold.Near_Img = (ImageView) arg1.findViewById(R.id.Near_Img);
      arg1.setTag(hold);
    } else {
      hold = (Holder) arg1.getTag();
    }
    hold.Near_UserName.setText(list.get(arg0).getNickname());
    hold.Near_Distance.setText("0.1km | 1天前");
    hold.Near_Sex.setBackgroundResource(R.drawable.nearby_gender_male);
    hold.Near_Sex.setText("0");
    hold.Near_Sex.setVisibility(View.VISIBLE);
    if (!list.get(arg0).getNickname().equalsIgnoreCase("null")) {
      hold.Near_Sex.setText(list.get(arg0).getNickname());
      if (list.get(arg0).getNickname().equals("0")) {
        hold.Near_Sex
            .setBackgroundResource(R.drawable.nearby_gender_female);
      } else {
        hold.Near_Sex
            .setBackgroundResource(R.drawable.nearby_gender_male);
      }
    } else {
      hold.Near_Sex.setVisibility(View.GONE);
    }
    hold.Near_UserInfo.setVisibility(View.VISIBLE);
    if (!list.get(arg0).getNickname().equalsIgnoreCase("null")) {
      hold.Near_UserInfo.setText(list.get(arg0).getNickname());
    } else {
      hold.Near_UserInfo.setVisibility(View.GONE);
    }
    try {
      ImageLoader.loadOptimizedHttpImage(ctx, Url.USERHEADURL + list.get(arg0).getAvatar())
          .placeholder(R.drawable.side_user_avatar)
          .dontAnimate().into(hold.Near_Img);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return arg1;
  }

  static class Holder {
    TextView Near_UserName;// 用户名
    TextView Near_Distance;// 距离和上次登录格式(0.1km | 1天前)
    TextView Near_Sex;// text是年龄 背景是性别
    TextView Near_UserInfo;// 用户的简介
    ImageView Near_Img;// 用户头像

  }

}
