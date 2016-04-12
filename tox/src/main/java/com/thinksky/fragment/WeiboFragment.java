package com.thinksky.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
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

import com.thinksky.adapter.WeiboAdapter;
import com.thinksky.info.AshamedInfo;
import com.thinksky.info.WeiboInfo;
import com.thinksky.myview.MyListView;
import com.thinksky.myview.MyListView.OnRefreshListener;
import com.thinksky.tox.R;
import com.thinksky.tox.SegmentControl;
import com.thinksky.tox.WeiboDetailActivity;
import com.thinksky.utils.MyJson;
import com.tox.BaseApi;
import com.tox.BaseFunction;
import com.tox.ToastHelper;
import com.tox.Url;
import com.tox.UserApi;
import com.tox.WeiboApi;

import net.tsz.afinal.FinalBitmap;

import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.aframe.bitmap.KJBitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * 热门的fragment
 */
public class WeiboFragment extends Fragment implements OnClickListener {
    private static final int TAB_INDEX_HOT = 0;
    private static final int TAB_INDEX_FOLLOW = 1;
    private static final int TAB_INDEX_MY = 2;

    private String hotUrl = Url.WEIBO;
    private int topMeunFlag = TAB_INDEX_HOT;
    private View view;
    private MyListView myListView;
    private LinearLayout mLinearLayout, load_progressBar;
    private TextView HomeNoValue;
    //    private HotFragmentCallBack mHotFragmentCallBack;
    private MyJson myJson = new MyJson();
    private List<AshamedInfo> list = new ArrayList<AshamedInfo>();
    private ArrayList<WeiboInfo> weiboList = new ArrayList<WeiboInfo>();
    private WeiboAdapter mAdapter = null;
    private Button ListBottem = null;
    private int mStart = 1;
    private boolean flag = true;
    private boolean loadflag = false;
    private boolean listBottemFlag = true;
    private Context ctx;
    private WeiboApi weiboApi = new WeiboApi();
    private FinalBitmap finalBitmap;
    private KJBitmap kjBitmap;
    private ProgressBar mAddMoreProgressBar;
    private int isAdd = 0;
    private String session_id;
    private String userUid;
    private BaseApi baseApi;

    private SegmentControl mTabs;

    //获取可用注册方式
    private ArrayList<String> ways = new ArrayList<String>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frame_home, null);
        ctx = view.getContext();
        myListView = new MyListView(ctx);
        finalBitmap = FinalBitmap.create(view.getContext());
        finalBitmap.configMemoryCacheSize((int) (Runtime.getRuntime().maxMemory() / 1024));
        finalBitmap.configBitmapLoadThreadSize(30);
        kjBitmap = KJBitmap.create();
        initView();
        return view;
    }

    private void initView() {
        //更新左侧的名字
//        mHotFragmentCallBack.callback(R.id.myUserName);
        mTabs = (SegmentControl) view.findViewById(R.id.segment_control);
        mTabs.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {
                switch (index) {
                    case TAB_INDEX_HOT:
                        if (topMeunFlag != TAB_INDEX_HOT) {
                            hotUrl = Url.WEIBO;
                            topMeunFlag = TAB_INDEX_HOT;
                            createListModel();
                        }
                        break;
                    case TAB_INDEX_FOLLOW:
                        if (topMeunFlag != TAB_INDEX_FOLLOW) {
                            hotUrl = Url.MYFOLLOWINGWEIBO;
                            topMeunFlag = TAB_INDEX_FOLLOW;
                            createListModel();
                        }
                        break;
                    case TAB_INDEX_MY:
                        if (topMeunFlag != TAB_INDEX_MY) {
                            hotUrl = Url.MYWEIBO;
                            topMeunFlag = TAB_INDEX_MY;
                            createListModel();
                        }
                        break;
                }
            }
        });

        mTabs.setTouchInterceptor(new SegmentControl.TouchInterceptor() {
            @Override
            public boolean onIntercept(int index) {
                boolean intercept = !BaseFunction.isLogin() && (index == TAB_INDEX_FOLLOW || index == TAB_INDEX_MY);
                if (intercept) {
                    ToastHelper.showToast(R.string.prompt_offline, ctx);
                }
                return intercept;
            }
        });

        load_progressBar = (LinearLayout) view
                .findViewById(R.id.load_progressBar);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.HomeGroup);
        myListView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        myListView.setDivider(null);
        myListView.setDividerHeight(0);
//        myListView.setFadingEdgeLength(10);
        mLinearLayout.addView(myListView);
        HomeNoValue = (TextView) view.findViewById(R.id.HomeNoValue);
        HomeNoValue.setVisibility(View.GONE);
        baseApi = new BaseApi();
        session_id = baseApi.getSeesionId();
        userUid = baseApi.getUid();
//        //设置导航部分按钮的背景
//        switch (topMeunFlag) {
//            case 1:
//                mTopMenuOne.setTextColor(getResources().getColor(R.color.black));
//                mTopMenuOne.setBackgroundColor(getResources().getColor(R.color.tab));
//                break;
//            case 2:
//                mTopMenuTwo.setTextColor(getResources().getColor(R.color.black));
//                mTopMenuTwo.setBackgroundResource(android.R.color.white);
//                break;
//            case 3:
//                mTopMenuThree.setTextColor(getResources().getColor(R.color.black));
//                mTopMenuThree.setBackgroundResource(android.R.color.white);
//                break;
//        }
        mAdapter = new WeiboAdapter(ctx, weiboList, finalBitmap, kjBitmap);
        //设置底部加载
        ListBottem = new Button(ctx);
        ListBottem.setBackgroundColor(getResources().getColor(R.color.mycolor));
        ListBottem.setText("点击加载更多");
        ListBottem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag && listBottemFlag) {
                    weiboApi.setHandler(hand);
                    weiboApi.listAllWeibo(mStart, 0 + "");
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
                    mStart = 1;
                    if (hotUrl.equals(Url.WEIBO)) {
                        weiboApi.setHandler(hand);
                        weiboApi.listAllWeibo(1, 0 + "");
                    } else if (hotUrl.equals(Url.MYFOLLOWINGWEIBO)) {

                        weiboApi.setHandler(hand);
                        weiboApi.listMyFollowingWeibo(1);
                    } else {
                        weiboApi.setHandler(hand);
                        weiboApi.listMyWeibo(1, 1 + "");

                    }
                    loadflag = false;
                } else {
                    Toast.makeText(ctx, "正在加载中，请勿重复刷新", Toast.LENGTH_LONG).show();
                }
            }
        });
        if (!BaseFunction.isLogin()) {
            autoLoign();
        } else {
            getWeiboList();
        }

    }

    private void listBottom() {
        mAddMoreProgressBar.setVisibility(View.VISIBLE);
        myListView.removeFooterView(ListBottem);
        myListView.addFooterView(mAddMoreProgressBar, null, false);
        isAdd++;
    }

    @Override
    public void onClick(View arg0) {
        int mID = arg0.getId();
        switch (mID) {
            case R.id.Menu:
//                mHotFragmentCallBack.callback(R.id.Menu);
                break;
            // TODO 需要标题栏处理
//            case R.id.SendWeibo:
////                mHotFragmentCallBack.callback(R.id.SendWeibo);.
//
//                if (!Url.SESSIONID.equals("")) {
//                    Intent intent = new Intent(getActivity(),
//                            UploadActivity.class);
//                    startActivity(intent);
//                } else {
//                    Log.e(">>>>>>>>>>>>>>>", "login Form WeiboFragment");
//
//                    String[] s = new String[ways.size()];
//                    s = ways.toArray(s);
//                    Intent intent = new Intent(getActivity(), LoginActivity.class);
//                    intent.putExtra("ways", s);
//                    intent.putExtra("entryActivity", ActivityModel.SENDWEIBO);
//                    startActivity(intent);
//                }
//                break;


            default:
                break;
        }
    }

    //加载对应的微博列表
    private void createListModel() {
        ListBottem.setVisibility(View.GONE);
        mLinearLayout.setVisibility(View.GONE);
        load_progressBar.setVisibility(View.VISIBLE);

        loadflag = false;
        mStart = 1;
        if (hotUrl.equals(Url.MYFOLLOWINGWEIBO)) {
            weiboApi.setHandler(hand);
            weiboApi.listMyFollowingWeibo(1);
        } else if (hotUrl.equals(Url.WEIBO)) {
            weiboApi.setHandler(hand);
            weiboApi.listAllWeibo(1, 0 + "");
        } else if (hotUrl.equals(Url.MYWEIBO)) {
            weiboApi.setHandler(hand);
            weiboApi.listMyWeibo(1, userUid);
        }
    }

    private class MainListOnItemClickListener implements OnItemClickListener {
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            Intent intent = new Intent(ctx, WeiboDetailActivity.class);
            Bundle bund = new Bundle();
            Log.e("被点击啦啦啦啦啦", arg2 + "");
            bund.putSerializable("WeiboInfo", weiboList.get(arg2 - 1));
            intent.putExtra("value", bund);
            startActivity(intent);
        }
    }


    Handler hand = new Handler() {
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);

            if (msg.what == 404) {
                Toast.makeText(ctx, "请求失败，服务器故障", Toast.LENGTH_LONG).show();
                HomeNoValue.setText("请求失败，服务器故障");
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
                            mStart += 1;
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
                    } else {
                        if (list.size() == 0)
                            HomeNoValue.setVisibility(View.VISIBLE);
                    }
                }
                mLinearLayout.setVisibility(View.VISIBLE);
                load_progressBar.setVisibility(View.GONE);
                myListView.onRefreshComplete();
                mAdapter.notifyDataSetChanged();
                loadflag = true;
            }
        }

        ;
    };

    @Override
    public void onStart() {
        super.onStart();
        //删除微博后刷新页面
        if (Url.activityFrom.equals("DeleteWeiBoActivity")) {
            Url.activityFrom = "";
            if (topMeunFlag != TAB_INDEX_HOT) {
                hotUrl = Url.MYFOLLOWINGWEIBO;
                createListModel();
            } else {
                hotUrl = Url.WEIBO;
                createListModel();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        insertWeibo();
    }

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

    private void autoLoign() {
        SharedPreferences sp = ctx.getSharedPreferences("userInfo", 0);
        Log.e("START AUTO LOGIN>>>>>>", sp.getString("username", "kongkong") + sp.getString("password", "kongkong"));
        if (!sp.getString("username", "").equals("")) {
            Log.e("START AUTO LOGIN>>>>", "111");
            UserApi userApi = new UserApi();
            userApi.setHandler(loginHandler);
            userApi.autoLogin(sp.getString("username", ""), sp.getString("password", ""));
        } else {
            weiboApi.setHandler(hand);
            weiboApi.listAllWeibo(1, 0 + "");
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
        weiboApi.listAllWeibo(1, 0 + "");
    }
}
