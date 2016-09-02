package com.thinksky.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.thinksky.info.WendaXianqingInfo;
import com.thinksky.tox.R;
import com.thinksky.utils.LoadImg;
import com.thinksky.utils.imageloader.ImageLoader;
import java.util.List;

/**
 * Created by Administrator on 2014/8/11.
 */
public class WentixiangqingListAdapter extends BaseAdapter {

  private Context context;
  List<WendaXianqingInfo> beans;
  private List<WendaXianqingInfo.QuestionAnswerEntity> list;
  private LoadImg loadImg;

  public WentixiangqingListAdapter(Context context, List<WendaXianqingInfo> beans) {
    this.context = context;
    this.beans = beans;
    loadImg = new LoadImg(context);
  }


  @Override
  public int getCount() {
    if (beans == null) {
      return 0;
    }
    return beans.size();
  }

  @Override
  public Object getItem(int position) {
    return beans.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;
    if (convertView == null) {
      convertView = LayoutInflater.from(context).inflate(R.layout
          .activity_wentixiangqing_list_item_zjw, null);
      holder = new ViewHolder();
      holder.avatar32 = (ImageView) convertView.findViewById(R.id.avatar32);
      holder.dianzan = (ImageView) convertView.findViewById(R.id.dianzan);
      holder.nickname = (TextView) convertView.findViewById(R.id.nickname);
      holder.content = (TextView) convertView.findViewById(R.id.content);
      holder.creat_time = (TextView) convertView.findViewById(R.id.creat_time);
      holder.reply_count = (TextView) convertView.findViewById(R.id.reply_count);
      holder.acept = (TextView) convertView.findViewById(R.id.acept);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }
    WendaXianqingInfo bean = beans.get(position);
    list = bean.getQuestionAnswer();
    holder.nickname.setText(list.get(position).getUser().getNickname());
    holder.content.setText(list.get(position).getContent());
    holder.creat_time.setText(list.get(position).getCreate_time());
    holder.reply_count.setText(list.get(position).getSupport());
    if ((list.get(position).getIs_supported().equals("0"))) {//已点赞
      holder.dianzan.setBackgroundResource(R.drawable.icon_like_blue_stroke);
    } else {
      holder.dianzan.setBackgroundResource(R.drawable.icon_like_blue);
    }
    try {
      ImageLoader.loadOptimizedHttpImage(context, list.get(position).getUser().getAvatar32())
          .placeholder(R.drawable.side_user_avatar)
          .dontAnimate().into(holder.avatar32);
    } catch (Exception e) {
      e.printStackTrace();
    }
//        holder.dianzan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RsenUrlUtil.execute(RsenUrlUtil.URL_SUPPORT_QUESTION_ANSWER, new RsenUrlUtil
// .OnJsonResultListener<WendaXianqingInfo>() {
//                    @Override
//                    public void onNoNetwork(String msg) {
//                        ToastHelper.showToast(msg, Url.context);
//                    }
//
//                    @Override
//                    public Map getMap() {
//                        Map map = new HashMap();
//
//                        map.put("answerid", list.get(position).getId());
//                        return map;
//                    }
//
//                    @Override
//                    public void onParseJsonBean(List<WendaXianqingInfo> beans, JSONObject
// jsonObject) {
//                        try {
//                        } catch (Exception e) {
//
//                        }
//                    }
//
//                    @Override
//                    public void onResult(boolean state, List<WendaXianqingInfo> beans) {
//                        if (state) {
//                            ToastHelper.showToast("点赞了", Url.context);
//                        } else {
//                            ToastHelper.showToast("请求失败", Url.context);
//                        }
//                    }
//                });
//            }
//        });
//
//        holder.acept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RsenUrlUtil.execute(RsenUrlUtil.URL_SUPPORT_QUESTION_ANSWER, new RsenUrlUtil
// .OnJsonResultListener<WendaXianqingInfo>() {
//                    @Override
//                    public void onNoNetwork(String msg) {
//                        ToastHelper.showToast(msg, Url.context);
//                    }
//
//                    @Override
//                    public Map getMap() {
//                        Map map = new HashMap();
//
//                        map.put("answerid", list.get(position).getId());
//                        return map;
//                    }
//
//                    @Override
//                    public void onParseJsonBean(List<WendaXianqingInfo> beans, JSONObject
// jsonObject) {
//                        try {
//                        } catch (Exception e) {
//
//                        }
//                    }
//
//                    @Override
//                    public void onResult(boolean state, List<WendaXianqingInfo> beans) {
//                        if (state) {
//                            ToastHelper.showToast("采纳了", Url.context);
//                        } else {
//                            ToastHelper.showToast("请求失败", Url.context);
//                        }
//                    }
//                });
//            }
//        });

    return convertView;
  }


  static class ViewHolder {
    ImageView avatar32;
    ImageView dianzan;
    TextView nickname;
    TextView content;
    TextView creat_time;
    TextView reply_count;
    TextView acept;

  }


}
