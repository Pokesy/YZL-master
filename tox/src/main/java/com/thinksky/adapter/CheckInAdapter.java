package com.thinksky.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.thinksky.info.CheckInfo;
import com.thinksky.tox.R;
import com.thinksky.utils.LoadImg;
import com.thinksky.utils.imageloader.ImageLoader;
import com.tox.BaseApi;
import com.tox.BaseFunction;
import java.util.List;
import org.kymjs.aframe.bitmap.KJBitmap;

/**
 * Created by Administrator on 2014/8/11.
 */
public class CheckInAdapter extends BaseAdapter {

  private Context context;
  private List<CheckInfo> list;
  private KJBitmap kjBitmap;
  private LoadImg loadImg;
  private BaseApi baseApi;

  public CheckInAdapter(Context context, List<CheckInfo> list) {
    this.context = context;
    this.list = list;
    kjBitmap = KJBitmap.create();
    loadImg = new LoadImg(context);
  }

  @Override
  public int getCount() {
    return list.size();
  }

  @Override
  public Object getItem(int position) {
    return list.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {
    ViewHolder holder = new ViewHolder();
    baseApi = new BaseApi();
    if (holder.view == null) {
      holder.view = LayoutInflater.from(context).inflate(R.layout.check_in_item, null);
      holder.CheckTime = (TextView) holder.view.findViewById(R.id.CheckIn_time);
      holder.RankNum = (TextView) holder.view.findViewById(R.id.Rank_num);
      holder.UserHead = (ImageView) holder.view.findViewById(R.id.CheckIn_userHead);
      holder.Username = (TextView) holder.view.findViewById(R.id.CheckIn_nickname);
    } else {
      holder.view = convertView;
    }
    holder.Username.setText(list.get(position).getUserInfo().getNickname());
    holder.CheckTime.setText(BaseFunction.TimeStamp2Date(list.get(position).getCtime(),
        "HH:mm:ss"));
    holder.RankNum.setText(position + 1 + "");
    try {
      ImageLoader.loadOptimizedHttpImage(context, list.get(position).getUserInfo().getAvatar())
          .placeholder(R.drawable.side_user_avatar)
          .into(holder.UserHead);
    } catch (Exception e) {

    }
    holder.view.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        baseApi.goUserInfo(context, list.get(position).getUserInfo().getUid());
      }
    });

    return holder.view;
  }


  static class ViewHolder {
    TextView RankNum, Username, CheckTime;
    ImageView UserHead;
    View view;
  }


}
