package com.thinksky.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.thinksky.info.WeiboCommentInfo;
import com.thinksky.info.WeiboInfo;
import com.thinksky.redefine.FaceTextView;
import com.thinksky.tox.LoginActivity;
import com.thinksky.tox.R;
import com.thinksky.tox.SendCommentActivity;
import com.thinksky.utils.UserUtils;
import com.thinksky.utils.imageloader.ImageLoader;
import com.tox.Url;
import com.tox.WeiboApi;
import java.util.List;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class DetailListAdapter extends BaseAdapter {

  private List<WeiboCommentInfo> list;
  private Context ctx;
  private WeiboApi weiboApi;
  private WeiboInfo weiboInfo;

  public DetailListAdapter(Context ctx, List<WeiboCommentInfo> list, WeiboInfo weiboInfo) {
    this.ctx = ctx;
    this.list = list;
    this.weiboInfo = weiboInfo;
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
    final int position = list.size() - arg0 - 1;
    weiboApi = new WeiboApi();
    Holder hold = new Holder();
    arg1 = View.inflate(ctx, R.layout.detail_list_item, null);
    hold.UserName = (TextView) arg1
        .findViewById(R.id.Detail_Item_UserName);
    hold.Num = (TextView) arg1.findViewById(R.id.Detail_Item_Num);
    hold.Content = (FaceTextView) arg1.findViewById(R.id.Detail_Item_Value);
    hold.Ctime = (TextView) arg1.findViewById(R.id.Detail_com_time);
    hold.UserHead = (ImageView) arg1.findViewById(R.id.Detail_Item_UserHead);
    hold.CommentArea = (LinearLayout) arg1.findViewById(R.id.CommentArea);

    hold.UserName.setText(UserUtils.getUserName(ctx, list.get(arg0).getUser().getUid(), list.get
        (arg0).getUser().getNickname()));
    hold.Num.setText("" + (arg0 + 1));
    hold.Content.setFaceText(list.get(arg0).getContent());
    hold.Ctime.setText(list.get(arg0).getCtime());
    hold.UserName.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
//                weiboApi.goUserInfo(ctx, list.get(arg0).getUser().getUid());
//                Toast.makeText(ctx, list.get(arg0).getUser().getNickname(), Toast.LENGTH_SHORT)
// .show();
      }
    });
    try {
      ImageLoader.loadOptimizedHttpImage(ctx, list.get(arg0).getUser().getAvatar()).bitmapTransform
          (new CropCircleTransformation(ctx))
          .placeholder(R.drawable.side_user_avatar).error(R.drawable.side_user_avatar).dontAnimate
          ().into(hold.UserHead);
    } catch (Exception e) {

    }
    hold.CommentArea.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        CommentToComment(arg0);
      }
    });
    hold.UserHead.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        weiboApi.goUserInfo(ctx, list.get(arg0).getUser().getUid());
      }
    });
    return arg1;
  }

  static class Holder {
    TextView UserName;
    TextView Num;

    FaceTextView Content;
    TextView Ctime;
    LinearLayout CommentArea;
    ImageView UserHead;
  }

  private void CommentToComment(int arg0) {
    if (!Url.SESSIONID.equals("")) {
      Intent intent = new Intent(ctx, SendCommentActivity.class);
      Bundle bundle = new Bundle();
      bundle.putSerializable("comment", list.get(arg0));
      bundle.putSerializable("weiboInfo", weiboInfo);
      intent.putExtra("comment", bundle);
      //用来
      intent.putExtra("activityFrom", "weiboDetail");
      ctx.startActivity(intent);
    } else {
      Intent intent = new Intent(ctx, LoginActivity.class);
      ctx.startActivity(intent);
    }
  }
}
