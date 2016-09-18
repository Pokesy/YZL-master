package com.thinksky.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.thinksky.adapter.MyListAdapter;
import com.thinksky.tox.WeiboDetailActivity;
import com.thinksky.tox.R;
import com.thinksky.info.WeiboInfo;
import com.tox.Url;
import com.thinksky.myview.MyListView;
import com.thinksky.myview.MyListView.OnRefreshListener;
import com.thinksky.net.ThreadPoolUtils;
import com.thinksky.thread.HttpGetThread;
import com.thinksky.utils.MyJson;

import net.tsz.afinal.FinalBitmap;

/**
 * 有图有真相的fragment
 */
public class PictureFragment extends Fragment implements OnClickListener {

    private String niceUrl = Url.YINGCAI;
    private int topMeunFlag = 1;
    private View view;
    private ImageView mTopImg;
    private ImageView mSendAshamed;
    private TextView mTopMenuOne, mTopMenuTwo;
    private MyListView myListView;
    private LinearLayout mLinearLayout, load_progressBar;
    private TextView HomeNoValue;
    private PictureFragmentCallBack mPictureFragmentCallBack;
    private MyJson myJson = new MyJson();
    private List<WeiboInfo> list = new ArrayList<WeiboInfo>();
    private MyListAdapter mAdapter = null;
    private Button ListBottem = null;
    private int mStart = 0;
    private int mEnd = 5;
    private String url = null;
    private boolean flag = true;
    private boolean loadflag = false;
    private boolean listBottemFlag = true;
    private Context ctx;
    private FinalBitmap finalBitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frame_picture, null);
        ctx = view.getContext();
        myListView = new MyListView(ctx);
        finalBitmap = FinalBitmap.create(ctx);
        initView();
        return view;
    }

    private void initView() {
        load_progressBar = (LinearLayout) view
                .findViewById(R.id.load_progressBar);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.HomeGroup);
        myListView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        myListView.setDivider(null);
        mLinearLayout.addView(myListView);
        mTopImg = (ImageView) view.findViewById(R.id.Menu);
        mSendAshamed = (ImageView) view.findViewById(R.id.SendAshamed);
        mTopMenuOne = (TextView) view.findViewById(R.id.TopMenuOne);
        mTopMenuTwo = (TextView) view.findViewById(R.id.TopMenuTwo);
        HomeNoValue = (TextView) view.findViewById(R.id.HomeNoValue);
        mTopImg.setOnClickListener(this);
        mSendAshamed.setOnClickListener(this);
        mTopMenuOne.setOnClickListener(this);
        mTopMenuTwo.setOnClickListener(this);
        createTextColor();
        switch (topMeunFlag) {
            case 1:
                mTopMenuOne.setTextColor(Color.WHITE);
                mTopMenuOne.setBackgroundResource(R.drawable.top_tab_active);
                break;
            case 2:
                mTopMenuTwo.setTextColor(Color.WHITE);
                mTopMenuTwo.setBackgroundResource(R.drawable.top_tab_active);
                break;
        }
        mAdapter = new MyListAdapter(ctx, list, finalBitmap);
        ListBottem = new Button(ctx);
        ListBottem.setText("点击加载更多");
        ListBottem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag && listBottemFlag) {
                    url = niceUrl + "start=" + mStart + "&end=" + mEnd;
                    ThreadPoolUtils.execute(new HttpGetThread(hand, url));
                    listBottemFlag = false;
                } else if (!listBottemFlag)
                    Toast.makeText(ctx, "加载中请稍候...", Toast.LENGTH_SHORT).show();
            }
        });
        myListView.addFooterView(ListBottem, null, false);
        ListBottem.setVisibility(View.GONE);
        myListView.setAdapter(mAdapter);
        myListView.setOnItemClickListener(new MainListOnItemClickListener());
        // ƴ���ַ����
        url = Url.YINGCAI + "start=" + mStart + "&end=" + mEnd;
        ThreadPoolUtils.execute(new HttpGetThread(hand, url));
        myListView.setonRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (loadflag == true) {
                    mStart = 0;
                    mEnd = 5;
                    url = niceUrl + "start=" + mStart + "&end=" + mEnd;
                    ListBottem.setVisibility(View.GONE);
                    ThreadPoolUtils.execute(new HttpGetThread(hand, url));
                    loadflag = false;
                } else {
                    Toast.makeText(ctx, "正在刷新，请勿重复刷新", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View arg0) {
        int mID = arg0.getId();
        switch (mID) {
            case R.id.Menu:
                mPictureFragmentCallBack.callback(R.id.Menu);
                break;
            case R.id.SendAshamed:
                mPictureFragmentCallBack.callback(R.id.SendAshamed);
                break;
            case R.id.TopMenuOne:
                createTextColor();
                mTopMenuOne.setTextColor(Color.WHITE);
                mTopMenuOne.setBackgroundResource(R.drawable.top_tab_active);
                if (topMeunFlag != 1) {
                    niceUrl = Url.YINGCAI;
                    topMeunFlag = 1;
                    createListModel();
                }
                break;
            case R.id.TopMenuTwo:
                createTextColor();
                mTopMenuTwo.setTextColor(Color.WHITE);
                mTopMenuTwo.setBackgroundResource(R.drawable.top_tab_active);
                if (topMeunFlag != 2) {
                    niceUrl = Url.SHILING;
                    topMeunFlag = 2;
                    createListModel();
                }
                break;
            default:
                break;
        }
    }

    private void createListModel() {
        ListBottem.setVisibility(View.GONE);
        mLinearLayout.setVisibility(View.GONE);
        load_progressBar.setVisibility(View.VISIBLE);
        loadflag = false;
        mStart = 0;
        mEnd = 5;
        url = niceUrl + "start=" + mStart + "&end=" + mEnd;
        ThreadPoolUtils.execute(new HttpGetThread(hand, url));
    }

    private class MainListOnItemClickListener implements OnItemClickListener {
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            Intent intent = new Intent(ctx, WeiboDetailActivity.class);
            Bundle bund = new Bundle();
            bund.putSerializable("AshamedInfo", list.get(arg2 - 1));
            intent.putExtra("value", bund);
            startActivity(intent);
        }
    }

    @SuppressWarnings("deprecation")
    private void createTextColor() {
        Drawable background = new BitmapDrawable();
        mTopMenuOne.setTextColor(Color.parseColor("#5b85bb"));
        mTopMenuTwo.setTextColor(Color.parseColor("#5b85bb"));
        mTopMenuOne.setBackgroundDrawable(background);
        mTopMenuTwo.setBackgroundDrawable(background);
        HomeNoValue.setVisibility(View.GONE);
    }

    public void setCallBack(PictureFragmentCallBack mPictureFragmentCallBack) {
        this.mPictureFragmentCallBack = mPictureFragmentCallBack;
    }

    public interface PictureFragmentCallBack {
        void callback(int flag);
    }

    Handler hand = new Handler() {
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            if (msg.what == 404) {
                Toast.makeText(ctx, "找不到地址", Toast.LENGTH_SHORT).show();
                listBottemFlag = true;
            } else if (msg.what == 100) {
                Toast.makeText(ctx, "传输失败", Toast.LENGTH_SHORT).show();
                listBottemFlag = true;
            } else if (msg.what == 200) {
                String result = (String) msg.obj;
                if (result != null) {
                    List<WeiboInfo> newList = myJson.getWeiboList(result);
                    if (newList != null) {
                        if (newList.size() == 5) {
                            ListBottem.setVisibility(View.VISIBLE);
                            mStart += 5;
                            mEnd += 5;
                        } else if (newList.size() == 0) {
                            if (list.size() == 0)
                                HomeNoValue.setVisibility(View.VISIBLE);
                            ListBottem.setVisibility(View.GONE);
                            Toast.makeText(ctx, "已经没有了...", Toast.LENGTH_SHORT).show();
                        } else {
                            ListBottem.setVisibility(View.GONE);
                        }
                        if (!loadflag) {
                            list.removeAll(list);
                        }
                        for (WeiboInfo info : newList) {
                            list.add(info);
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

    };

}
