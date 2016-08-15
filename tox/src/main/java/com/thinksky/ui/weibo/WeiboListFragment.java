package com.thinksky.ui.weibo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.otto.Subscribe;
import com.thinksky.adapter.WeiboAdapter;
import com.thinksky.info.AshamedInfo;
import com.thinksky.info.WeiboInfo;
import com.thinksky.log.Logger;
import com.thinksky.myview.MyListView;
import com.thinksky.myview.MyListView.OnRefreshListener;
import com.thinksky.tox.R;
import com.thinksky.tox.UploadActivity;
import com.thinksky.tox.WeiboDetailActivity;
import com.thinksky.ui.basic.BasicFragment;
import com.thinksky.utils.MyJson;
import com.tox.BaseApi;
import com.tox.BaseFunction;
import com.tox.Url;
import com.tox.UserApi;
import com.tox.WeiboApi;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.FinalBitmap;
import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.aframe.bitmap.KJBitmap;

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
  private MyListView myListView;
  private LinearLayout mLinearLayout, load_progressBar;
  private TextView HomeNoValue;
  private MyJson myJson = new MyJson();
  private List<AshamedInfo> list = new ArrayList<AshamedInfo>();
  private ArrayList<WeiboInfo> weiboList = new ArrayList<WeiboInfo>();
  private WeiboAdapter mAdapter = null;
  private Button ListBottem = null;
  private boolean flag = true;
  private boolean loadflag = false;
  private boolean listBottemFlag = true;
  private Context ctx;
  private WeiboApi weiboApi = new WeiboApi();
  private FinalBitmap finalBitmap;
  private ProgressBar mAddMoreProgressBar;
  private int isAdd = 0;
  private String userUid;
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
        // TODO 替换成最近动态的url
        hotUrl = Url.WEIBO;
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
    myListView = new MyListView(ctx);
    finalBitmap = FinalBitmap.create(view.getContext());
    finalBitmap.configMemoryCacheSize((int) (Runtime.getRuntime().maxMemory() / 1024));
    finalBitmap.configBitmapLoadThreadSize(30);
    baseApi = new BaseApi();
    userUid = baseApi.getUid();
    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initView();
  }

  private void initView() {

    load_progressBar = (LinearLayout) view
        .findViewById(R.id.load_progressBar);
    mLinearLayout = (LinearLayout) view.findViewById(R.id.HomeGroup);
    myListView.setLayoutParams(new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.MATCH_PARENT));
    myListView.setDivider(null);
    myListView.setDividerHeight(5);
//        myListView.setFadingEdgeLength(10);
    mLinearLayout.addView(myListView);
    HomeNoValue = (TextView) view.findViewById(R.id.HomeNoValue);


    mAdapter = new WeiboAdapter(ctx, weiboList);
    //设置底部加载
    ListBottem = new Button(ctx);
    ListBottem.setBackgroundColor(getResources().getColor(R.color.mycolor));
    ListBottem.setText("点击加载更多");
    ListBottem.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (flag && listBottemFlag) {
//                    weiboApi.setHandler(hand);
//                    weiboApi.listMyWeibo(mStart, userUid);
          if (hotUrl.equals(Url.WEIBO)) {
            weiboApi.setHandler(hand);
            weiboApi.listAllWeibo(++mCurrentPage, 0 + "");
          } else if (hotUrl.equals(Url.MYFOLLOWINGWEIBO)) {

            weiboApi.setHandler(hand);
            weiboApi.listMyFollowingWeibo(++mCurrentPage, Url.SESSIONID);
          } else if (hotUrl.equals(Url.MYWEIBO)) {
            weiboApi.setHandler(hand);
            weiboApi.listMyWeibo(++mCurrentPage, Url.USERID);

          }
          listBottom();
          listBottemFlag = false;
        } else if (!listBottemFlag)
          Toast.makeText(ctx, "正在加载中...", Toast.LENGTH_LONG).show();
      }
    });
    mAddMoreProgressBar = new ProgressBar(ctx);
    mAddMoreProgressBar.setIndeterminate(false);
    mAddMoreProgressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progress_bg));
    mAddMoreProgressBar.setVisibility(View.GONE);
    myListView.addFooterView(ListBottem, null, false);
    ListBottem.setVisibility(View.GONE);

    myListView.setAdapter(mAdapter);
    myListView.setOnItemClickListener(new MainListOnItemClickListener());


		/*url = Url.WEIBO ;//+ "start=" + mStart + "&end=" + mEnd;
        ThreadPoolUtils.execute(new HttpGetThread(hand, url));*/
    myListView.setonRefreshListener(new OnRefreshListener() {
      @Override
      public void onRefresh() {
        if (loadflag == true) {
          mCurrentPage = INIT_PAGE;
          if (hotUrl.equals(Url.WEIBO)) {
            weiboApi.setHandler(hand);
            weiboApi.listAllWeibo(mCurrentPage, 0 + "");
          } else if (hotUrl.equals(Url.MYFOLLOWINGWEIBO)) {

            weiboApi.setHandler(hand);
            weiboApi.listMyFollowingWeibo(mCurrentPage, Url.SESSIONID);
          } else if (hotUrl.equals(Url.MYWEIBO)) {
            weiboApi.setHandler(hand);
            weiboApi.listMyWeibo(mCurrentPage, Url.USERID);

          }
          loadflag = false;
        } else {
          Toast.makeText(ctx, "正在加载中，请勿重复刷新", Toast.LENGTH_LONG).show();
        }
      }
    });
    //if (!BaseFunction.isLogin()) {
    //  autoLogin();
    //} else {
      getWeiboList();
    //}

  }

  private void listBottom() {
    mAddMoreProgressBar.setVisibility(View.VISIBLE);
    myListView.removeFooterView(ListBottem);
    myListView.addFooterView(mAddMoreProgressBar, null, false);
    isAdd++;
  }

  //加载对应的微博列表
  private void createListModel() {
    HomeNoValue.setVisibility(View.GONE);
    ListBottem.setVisibility(View.GONE);
    mLinearLayout.setVisibility(View.GONE);
    load_progressBar.setVisibility(View.VISIBLE);

    loadflag = false;
    mCurrentPage = INIT_PAGE;
    if (hotUrl.equals(Url.MYFOLLOWINGWEIBO)) {
      weiboApi.setHandler(hand);
      if(BaseFunction.isLogin()) {
        weiboApi.listMyFollowingWeibo(1, Url.SESSIONID);
      }
    } else if (hotUrl.equals(Url.WEIBO)) {
      weiboApi.setHandler(hand);
      weiboApi.listAllWeibo(1, 0 + "");
    } else if (hotUrl.equals(Url.MYWEIBO)) {
      weiboApi.setHandler(hand);
      if(BaseFunction.isLogin()) {
        weiboApi.listMyWeibo(1, Url.USERID);
      }
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
        HomeNoValue.setText(R.string.network_not_normal);
        load_progressBar.setVisibility(View.GONE);
        HomeNoValue.setVisibility(View.VISIBLE);

        ListBottem.setVisibility(View.GONE);
        listBottemFlag = true;
      } else if (msg.what == 100) {
        Toast.makeText(ctx, "传输失败", Toast.LENGTH_LONG).show();
        listBottemFlag = true;
      } else if (msg.what == 0) {
        String result = (String) msg.obj;
//                Log.e("WeiboResult:", result);
        if (isAdd != 0) {
          myListView.removeFooterView(mAddMoreProgressBar);
          mAddMoreProgressBar.setVisibility(View.GONE);
          isAdd = isAdd - 1;
          myListView.addFooterView(ListBottem);
        }
        if (result != null) {
          List<WeiboInfo> newList = myJson.getWeiboList(result);
//                    ToastHelper.showToast("微博个数"+newList.size(),ctx);
          if (newList != null) {
            if (newList.size() == 10) {
              ListBottem.setVisibility(View.VISIBLE);
            } else if (newList.size() == 0) {
              if (list.size() == 0)
                HomeNoValue.setVisibility(View.VISIBLE);
              ListBottem.setVisibility(View.GONE);
              Toast.makeText(ctx, "已经没有了...", Toast.LENGTH_LONG).show();
            } else {
              ListBottem.setVisibility(View.GONE);
            }
            if (!loadflag) {
              weiboList.removeAll(weiboList);
            }
            for (WeiboInfo info : newList) {
              weiboList.add(info);
            }
            listBottemFlag = true;
            mLinearLayout.setVisibility(View.VISIBLE);
          } else {
            if (list.size() == 0) {
              HomeNoValue.setVisibility(View.VISIBLE);
              mLinearLayout.setVisibility(View.GONE);
            }
          }

        }

        load_progressBar.setVisibility(View.GONE);
        myListView.onRefreshComplete();
        mAdapter.notifyDataSetChanged();
        loadflag = true;
      }
    }

  };

  private void insertWeibo() {
    if (Url.is2InsertWeibo) {
      weiboList.add(null);
      for (int i = weiboList.size() - 1; i > 0; i--) {

        weiboList.set(i, weiboList.get(i - 1));
      }
      weiboList.set(0, Url.weiboInfo);
      mAdapter.notifyDataSetChanged();
      Url.is2InsertWeibo = false;

    }
  }

  @Subscribe
  public void handleWeiboSendEvent(UploadActivity.WeiboSendSuccessEvent event) {
    if (TextUtils.equals(hotUrl, Url.MYWEIBO) || TextUtils.equals(hotUrl, Url.WEIBO)) {
      createListModel();
    }
  }

  @Subscribe
  public void handleDeleteWeiboEvent(WeiboDetailActivity.DeleteWeiboEvent event) {
    if (TextUtils.equals(hotUrl, Url.MYWEIBO) || TextUtils.equals(hotUrl, Url.WEIBO)) {
      createListModel();
    }
  }

  private void autoLogin() {
    SharedPreferences sp = ctx.getSharedPreferences("userInfo", 0);
    Log.e("START AUTO LOGIN>>>>>>", sp.getString("username", "kongkong") + sp.getString
        ("password", "kongkong"));
    if (!sp.getString("username", "").equals("")) {
      Log.e("START AUTO LOGIN>>>>", "111");
      UserApi userApi = new UserApi();
      userApi.setHandler(loginHandler);
      userApi.autoLogin(sp.getString("username", ""), sp.getString("password", ""));
    } else {
      weiboApi.setHandler(hand);
      //weiboApi.listAllWeibo(mCurrentPage, 0 + "");
      //createListModel();
    }
  }

  Handler loginHandler = new Handler() {
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      if (msg.what == 404) {
        Toast.makeText(ctx, "服务器出错", Toast.LENGTH_LONG).show();
      } else {
        if (msg.what == 0) {
          SharedPreferences sp = ctx.getSharedPreferences("userInfo", 0);
          sp.edit().putString("session_id", myJson.getUserSessionID((String) msg.obj)).commit();
          sp.edit().putString("uid", myJson.getUserID((String) msg.obj)).commit();
          String session_id = myJson.getUserSessionID((String) msg.obj);
          Url.SESSIONID = myJson.getUserSessionID((String) msg.obj);
          Url.LASTPOSTTIME = System.currentTimeMillis();
          try {
            JSONObject jsonObject = new JSONObject((String) msg.obj);
            Url.WEIBOWORDS = Integer.parseInt(jsonObject.getString("weibo_words_limit"));
          } catch (JSONException e) {

          }


          SharedPreferences sharedPreferences = ctx.getSharedPreferences("Parameters", 0);
          sharedPreferences.edit().putString("weiboWordsLimit", Url.WEIBOWORDS + "").commit();
          Url.USERID = sp.getString("uid", "0");
          getWeiboList();
          //SplashActivity.this.finish();
        } else {
          Toast.makeText(ctx, "autoLogin false", Toast.LENGTH_SHORT).show();
          getWeiboList();
          //SplashActivity.this.finish();
        }
      }

    }
  };

  private void getWeiboList() {
    weiboList.removeAll(weiboList);
    weiboApi.setHandler(hand);
    //weiboApi.listAllWeibo(mCurrentPage, Url.USERID);
    createListModel();
  }

  @Override
  protected void onLogin() {
    super.onLogin();
    getWeiboList();
  }
}
