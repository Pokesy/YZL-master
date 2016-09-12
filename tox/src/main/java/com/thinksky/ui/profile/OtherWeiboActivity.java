/*
 * 文件名: OtherWeiboActivity
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/8/17
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.thinksky.adapter.WeiboAdapter;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.info.AshamedInfo;
import com.thinksky.info.WeiboInfo;
import com.thinksky.myview.MyListView;
import com.thinksky.tox.R;
import com.thinksky.tox.WeiboDetailActivity;
import com.thinksky.ui.common.TitleBar;
import com.thinksky.utils.MyJson;
import com.tox.BaseApi;
import com.tox.Url;
import com.tox.WeiboApi;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/8/17]
 */
public class OtherWeiboActivity extends BaseBActivity {
  private static final String BUNDLE_KEY_USER_ID = "user_id";
  private static final int INIT_PAGE = 1;

  private static final String URL = Url.OTHER_WEIBO;
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
  private ProgressBar mAddMoreProgressBar;
  private int isAdd = 0;
  private BaseApi baseApi;

  private int mCurrentPage = INIT_PAGE;

  private String mUserId;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_other_weibo_list);
    ctx = this;
    myListView = new MyListView(ctx);
    baseApi = new BaseApi();
    mUserId = getIntent().getStringExtra(BUNDLE_KEY_USER_ID);
    initView();
  }


  private void initView() {

    TitleBar titleBar = (TitleBar) findViewById(R.id.title_bar);
    titleBar.setMiddleTitle(R.string.activity_other_weibo_title);
    titleBar.setLeftImgMenu(R.drawable.icon_title_bar_back, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    load_progressBar = (LinearLayout) findViewById(R.id.load_progressBar);
    mLinearLayout = (LinearLayout) findViewById(R.id.HomeGroup);
    myListView.setLayoutParams(new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.MATCH_PARENT));
    myListView.setDivider(null);
    myListView.setDividerHeight(5);
//        myListView.setFadingEdgeLength(10);
    mLinearLayout.addView(myListView);
    HomeNoValue = (TextView) findViewById(R.id.HomeNoValue);


    mAdapter = new WeiboAdapter(ctx, weiboList);
    //设置底部加载
    ListBottem = new Button(ctx);
    ListBottem.setBackgroundColor(getResources().getColor(R.color.mycolor));
    ListBottem.setText("点击加载更多");
    ListBottem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (flag && listBottemFlag) {
          weiboApi.setHandler(hand);
          weiboApi.listOtherWeibo(++mCurrentPage, mUserId);
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
    myListView.setonRefreshListener(new MyListView.OnRefreshListener() {
      @Override
      public void onRefresh() {
        if (loadflag == true) {
          mCurrentPage = INIT_PAGE;
          weiboApi.setHandler(hand);
          weiboApi.listOtherWeibo(mCurrentPage, mUserId);
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
    weiboApi.setHandler(hand);
    weiboApi.listOtherWeibo(1, mUserId);
  }

  public static Intent makeIntent(Context context, String mUserId) {
    Intent intent = new Intent(context, OtherWeiboActivity.class);
    intent.putExtra(BUNDLE_KEY_USER_ID, mUserId);
    return intent;
  }

  private class MainListOnItemClickListener implements AdapterView.OnItemClickListener {
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
            if (newList.size() >= 10) {
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
