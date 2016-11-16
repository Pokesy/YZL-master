package com.thinksky.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.thinksky.rsen.RViewHolder;
import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.tox.IssueDetail;
import com.thinksky.tox.R;
import com.thinksky.utils.imageloader.ImageLoader;
import com.tox.ToastHelper;
import com.tox.Url;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ZhuanjiFragment extends Fragment {
  private List<String> pictureListUrls;
  View rootView;
  ListView listView;
  private Context ctx;
  private SlideShowView mSlideShowView;

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
      Bundle savedInstanceState) {

    rootView = inflater.inflate(R.layout.fragment_zhuanji, container, false);
    listView = (ListView) rootView.findViewById(R.id.listView);
    ctx = rootView.getContext();
    initHeaderSlideView();
    return rootView;
  }

  private void initHeaderSlideView() {
    mSlideShowView = new SlideShowView(ctx);
    mSlideShowView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        getResources().getDimensionPixelOffset(R.dimen.slide_view_height)));
    listView.addHeaderView(mSlideShowView);
  }

  @Override
  public void onResume() {
    super.onResume();
    RsenUrlUtil.execute(this.getActivity(), RsenUrlUtil.URL_ZJ, new RsenUrlUtil
        .OnNetHttpResultListener() {
      @Override
      public void onNoNetwork(String msg) {
        ToastHelper.showToast(msg, Url.context);
      }


      @Override
      public void onResult(boolean state, String result, JSONObject jsonObject) {
        if (state) {
          ArrayList<ZjBean> beans = parseJson(jsonObject);
          listView.setAdapter(new ZjAdapter(getActivity(), beans));
        }
      }
    });
  }

  public static ArrayList<ZjBean> parseJson(JSONObject object) {
    ArrayList<ZjBean> beans = new ArrayList<>();
    if (object != null) {
      try {
        JSONArray array = object.getJSONArray("list");
        for (int i = 0; i < array.length(); i++) {
          ZjBean bean = new ZjBean();
          JSONObject jsonObject = array.getJSONObject(i);
          bean.title = jsonObject.getString("title");//title 赋值
          bean.id = jsonObject.getInt("id");
          //其他字段。。。赋值

          // TODO: 2016/2/17

          ArrayList<IssueBean> issueBeens = new ArrayList<>();
          JSONArray issueList = jsonObject.optJSONArray("$IssueList");
          for (int j = 0; null != issueList && j < issueList.length(); j++) {
            JSONObject issueListJSONObject = issueList.getJSONObject(j);
            IssueBean issueBean = new IssueBean();
            issueBean.title = issueListJSONObject.optString("title");// issue title 赋值
            issueBean.cover_url = issueListJSONObject.optString("cover_url");// issue cover_url 赋值
            issueBean.id = issueListJSONObject.optInt("id");
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

  private ArrayList<String> mDatas;

  private static final String ARG_PARAM1 = "param1";

  private String mParam1;


  public static ZhuanjiFragment newInstance(String param1) {
    ZhuanjiFragment fragment = new ZhuanjiFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    fragment.setArguments(args);
    return fragment;
  }

  public static class ZjAdapter extends BaseAdapter {

    Context context;
    ArrayList<ZjBean> beans;

    public ZjAdapter(Context context, ArrayList<ZjBean> beans) {
      this.context = context;
      this.beans = beans;
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
      RViewHolder viewHolder;
      if (convertView == null) {
        convertView = LayoutInflater.from(context).inflate(R.layout
            .fragment_zhuanji_adapter_item, parent, false);
        viewHolder = new RViewHolder(convertView);
        convertView.setTag(viewHolder);
      } else {
        viewHolder = (RViewHolder) convertView.getTag();
      }


      final ZjBean bean = beans.get(position);
      ((TextView) viewHolder.itemView.findViewById(R.id.title)).setText(bean.title);
      viewHolder.itemView.findViewById(R.id.xianshi).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Bundle bundle = new Bundle();
          bundle.putInt("id", bean.id);
          bundle.putString("title", bean.title);
          Intent intent = new Intent(context, ZhuanjiFenLeiActivity.class);
          intent.putExtras(bundle);
          context.startActivity(intent);
        }
      });
      ((GridView) viewHolder.itemView.findViewById(R.id.gridView)).setAdapter(new ZjGridAdapter
          (context, bean.IssueList));

      return convertView;
    }
  }

  public static class ZjGridAdapter extends BaseAdapter {

    Context context;
    ArrayList<IssueBean> beans;

    public ZjGridAdapter(Context context, ArrayList<IssueBean> beans) {
      this.context = context;
      this.beans = beans;
    }

    @Override
    public int getCount() {
      if (beans == null) {
        return 0;
      }
      return beans.size() > 4 ? 4 : beans.size();
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
      RViewHolder viewHolder;
      if (convertView == null) {
        convertView = LayoutInflater.from(context).inflate(R.layout
            .fragment_zhuanji_adapter_issue_item, parent, false);
        viewHolder = new RViewHolder(convertView);
        convertView.setTag(viewHolder);
      } else {
        viewHolder = (RViewHolder) convertView.getTag();
      }

      final IssueBean bean = beans.get(position);
      ((TextView) viewHolder.itemView.findViewById(R.id.title)).setText(bean.title);
      //ImageLoader.getInstance().displayImage(RsenUrlUtil.URL_BASE + bean.cover_url,
      //        (ImageView) viewHolder.itemView.findViewById(R.id.imageView));
      try {
        ImageLoader.loadOptimizedHttpImage(context, RsenUrlUtil.URL_BASE + bean.cover_url).into(
            (ImageView) viewHolder.itemView.findViewById(R.id.imageView));
      } catch (Exception e) {
        e.printStackTrace();
      }
      convertView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Bundle bundle = new Bundle();
          bundle.putInt("id", bean.id);
          Intent intent = new Intent(context, IssueDetail.class);
          intent.putExtras(bundle);
          context.startActivity(intent);
//                    IssueDetailFragmentActivity1.launch(context, bean.id);
        }
      });
      return convertView;
    }
  }

  public static class ZjBean {
    //需要什么字段，自己添加


    public String title;
    public int id;
    public ArrayList<IssueBean> IssueList;
  }

  public static class IssueBean {
    public String title;
    public String cover_url;//需要加上 URL_BASE
    public int id;
    public String create_time;
    public String reply_count;
    public String support_count;
  }


}
