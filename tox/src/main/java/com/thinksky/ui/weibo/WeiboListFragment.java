package com.thinksky.ui.weibo;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import com.squareup.otto.Subscribe;
import com.thinksky.adapter.WeiboAdapter;
import com.thinksky.info.WeiboInfo;
import com.thinksky.tox.R;
import com.thinksky.tox.SendCommentActivity;
import com.thinksky.tox.UploadActivity;
import com.thinksky.tox.WeiboDetailActivity;
import com.thinksky.ui.basic.BasicFragment;
import com.thinksky.ui.common.PullToRefreshListView;
import com.thinksky.utils.MyJson;
import com.tox.BaseApi;
import com.tox.BaseFunction;
import com.tox.Url;
import com.tox.WeiboApi;
import java.util.ArrayList;
import java.util.List;

/**
 * 热门的fragment
 */
public class WeiboListFragment extends BasicFragment {
  private static final int TAB_INDEX_LATEST = 0;
  private static final int TAB_INDEX_HOT = 1;
  private static final int TAB_INDEX_FOLLOW = 2;
  private static final int TAB_INDEX_MY = 3;
  private static final int INIT_PAGE = 1;

  private static final String KEY_INDEX = "index";

  private String hotUrl;
  private View view;
  private PullToRefreshListView myListView;
  private MyJson myJson = new MyJson();
  private ArrayList<WeiboInfo> weiboList = new ArrayList<>();
  private WeiboAdapter mAdapter = null;
  private Context ctx;
  private WeiboApi weiboApi = new WeiboApi();
  private BaseApi baseApi;

  private int mCurrentPage = INIT_PAGE;

  public static WeiboListFragment newInstance(int index) {

    Bundle args = new Bundle();
    args.putInt(KEY_INDEX, index);
    WeiboListFragment fragment = new WeiboListFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    int index = getArguments().getInt(KEY_INDEX);
    switch (index) {
      case TAB_INDEX_LATEST:
        hotUrl = Url.NEWEST_WEIBO;
        break;
      case TAB_INDEX_HOT:
        hotUrl = Url.WEIBO;
        break;
      case TAB_INDEX_FOLLOW:
        hotUrl = Url.MYFOLLOWINGWEIBO;
        break;
      case TAB_INDEX_MY:
        hotUrl = Url.MYWEIBO;
        break;
    }
  }

  //获取可用注册方式
  private ArrayList<String> ways = new ArrayList<String>();

  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_weibo_list, null);
    ctx = view.getContext();
    baseApi = new BaseApi();
    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    myListView = (PullToRefreshListView) view.findViewById(R.id.listView);
    initView();
  }

  private void initView() {

    mAdapter = new WeiboAdapter(ctx, weiboList);
    myListView.setAdapter(mAdapter);

    myListView.setScrollToLoadListener(new PullToRefreshListView.ScrollToLoadListener() {
      @Override
      public void onPullUpLoadData() {
        if (hotUrl.equals(Url.WEIBO)) {
          weiboApi.setHandler(hand);
          weiboApi.listAllWeibo(++mCurrentPage, "0");
        } else if (hotUrl.equals(Url.MYFOLLOWINGWEIBO)) {

          weiboApi.setHandler(hand);
          weiboApi.listMyFollowingWeibo(++mCurrentPage, Url.SESSIONID);
        } else if (hotUrl.equals(Url.MYWEIBO)) {
          weiboApi.setHandler(hand);
          weiboApi.listMyWeibo(++mCurrentPage, Url.USERID);

        } else if (TextUtils.equals(Url.NEWEST_WEIBO, hotUrl)) {
          weiboApi.setHandler(hand);
          weiboApi.listNewestWeibo(++mCurrentPage, Url.USERID);
        }
      }

      @Override
      public void onPullDownLoadData() {
        createListModel();
      }
    }, 3);
    //设置底部加载

    myListView.getRefreshListView().setOnItemClickListener(new MainListOnItemClickListener());
    myListView.getRefreshListView().setDivider(new ColorDrawable(getResources().getColor(R.color
        .item_divider)));
    myListView.getRefreshListView().setDividerHeight(1);

    createListModel();
  }


  //加载对应的动态列表
  private void createListModel() {
    mCurrentPage = INIT_PAGE;
    if (hotUrl.equals(Url.MYFOLLOWINGWEIBO)) {
      weiboApi.setHandler(hand);
      if (BaseFunction.isLogin()) {
        weiboApi.listMyFollowingWeibo(mCurrentPage, Url.SESSIONID);
      }
    } else if (hotUrl.equals(Url.WEIBO)) {
      weiboApi.setHandler(hand);
      weiboApi.listAllWeibo(mCurrentPage, "0");
    } else if (hotUrl.equals(Url.MYWEIBO)) {
      weiboApi.setHandler(hand);
      if (BaseFunction.isLogin()) {
        weiboApi.listMyWeibo(mCurrentPage, Url.USERID);
      }
    } else if (TextUtils.equals(hotUrl, Url.NEWEST_WEIBO)) {
      weiboApi.setHandler(hand);
      weiboApi.listNewestWeibo(mCurrentPage, Url.USERID);
    }
  }

  private class MainListOnItemClickListener implements OnItemClickListener {
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                            long arg3) {
      Intent intent = new Intent(ctx, WeiboDetailActivity.class);
      Bundle bund = new Bundle();
      bund.putSerializable("WeiboInfo", weiboList.get(arg2 - 1));
      intent.putExtra("value", bund);
      startActivity(intent);
    }
  }


  Handler hand = new Handler() {
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      if (msg.what == 404) {
        Toast.makeText(ctx, R.string.network_not_normal, Toast.LENGTH_LONG).show();
      } else if (msg.what == 100) {
        Toast.makeText(ctx, R.string.network_not_normal, Toast.LENGTH_LONG).show();
      } else if (msg.what == 0) {
        String result = (String) msg.obj;
//                Log.e("WeiboResult:", result);
        if (result != null) {
          List<WeiboInfo> newList = myJson.getWeiboList(result);
//                    ToastHelper.showToast("动态个数"+newList.size(),ctx);
          if (newList != null) {
            if (newList.size() >= 10) {
              myListView.setPullUpToRefresh(true);
            } else {
              myListView.setPullUpToRefresh(false);
            }
            if (mCurrentPage == INIT_PAGE) {
              weiboList.clear();
            }
            for (WeiboInfo info : newList) {
              weiboList.add(info);
            }
          }

        }

        myListView.resetPullStatus();
        mAdapter.notifyDataSetChanged();
      }
    }

  };

  @Subscribe
  public void handleWeiboSendEvent(UploadActivity.WeiboSendSuccessEvent event) {
    createListModel();
  }

  @Subscribe
  public void handleSendCommentSuccessEvent(SendCommentActivity.SendCommentSuccessEvent event) {
    createListModel();
  }

  @Subscribe
  public void handleDeleteWeiboEvent(WeiboDetailActivity.DeleteWeiboEvent event) {
    if (TextUtils.equals(hotUrl, Url.MYWEIBO) || TextUtils.equals(hotUrl, Url.WEIBO)) {
      createListModel();
    }
  }

  @Override
  protected void onLogin() {
    super.onLogin();
    createListModel();
  }
}
