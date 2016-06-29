package com.thinksky.tox;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thinksky.ParallaxHeaderViewPagerForum.SampleListFragment;
import com.thinksky.ParallaxHeaderViewPagerForum.ScrollTabHolder;
import com.thinksky.ParallaxHeaderViewPagerForum.ScrollTabHolderFragment;
import com.thinksky.adapter.PostAdapter;
import com.thinksky.fragment.LuntanFragment;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.info.ForumInfo;
import com.thinksky.info.PostInfo;
import com.thinksky.ui.common.PullToRefreshListView;
import com.thinksky.utils.LoadImg;
import com.thinksky.utils.MyJson;
import com.tox.BaseFunction;
import com.tox.ForumApi;
import com.tox.IssueApi;
import com.tox.ToastHelper;
import com.tox.Url;

import net.tsz.afinal.FinalBitmap;

import org.kymjs.aframe.bitmap.KJBitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaozeyang on 16/4/12.
 */
public class ForumDetailActivity extends BaseBActivity implements View.OnClickListener {

    public static final String BUNDLE_KEY_FORUM_INFO = "forum";
    private static final int PAGE_COUNT = 8;
    private static final int REFRESH_THRESHOLD = 3;
    private static final int START_INDEX = 1;

    private static AccelerateDecelerateInterpolator sSmoothInterpolator = new AccelerateDecelerateInterpolator();
    private static boolean GETFORUMPOST = false;
    private static boolean GETFORUMS = true;
    private static boolean ADDFORUMPOST = false;
    private static boolean REFRESH = false;
    private static boolean SENDPOSTCOMMENT = false;

    private PullToRefreshListView mRefreshListView;
    private ListView mListView;
    private View mHeaderView;
    private KJBitmap kjBitmap;
    private Button ListBottom = null;
    private boolean isInitView = false;
    private SampleListFragment.SampListCallBack sampListCallBack;

    private LinearLayout mEditBox, mForumBackground;
    private int mActionBarHeight;
    private int mMinHeaderHeight;
    private RelativeLayout floatingRelative, mForumBody, mForumLoading;
    private TypedValue mTypedValue = new TypedValue();
    private EditText editText;
    private SpannableString mSpannableString;
    private ImageView mMenu, mWritePost, mForumLogo;
    private TextView mForumSendCom, mPostCountView, mTopicCountView, mForumLastReply, mForumNameView;
    private ForumApi forumApi = new ForumApi();
    private MyJson myJson = new MyJson();
    //第几个论坛
    private int fragPage = 0;
    private LoadImg imgLoad;
    private boolean addFlag = false;
    //声明session_id
    private String session_id;
    private String forumTitle;

    private LuntanFragment.ForumBean mForumBean;
    private List<PostInfo> mPostInfos = new ArrayList<PostInfo>();

    private PostAdapter mAdapter;

    private int mCurrentIndex = START_INDEX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_forum_detail);

        mMinHeaderHeight = getResources().getDimensionPixelSize(
                R.dimen.min_header_height);

        //获取session_id
        IssueApi issueApi = new IssueApi();
        session_id = issueApi.getSeesionId();

        mForumBean = (LuntanFragment.ForumBean) getIntent().getSerializableExtra(BUNDLE_KEY_FORUM_INFO);
        forumTitle = mForumBean.title;

        initView();
        initListView();
        initForumData();
        getInitData();

    }

    private void initView() {
        imgLoad = new LoadImg(this);
        mForumBody = (RelativeLayout) findViewById(R.id.Forum_body);
        mForumLoading = (RelativeLayout) findViewById(R.id.Forum_loading);

        mRefreshListView = (PullToRefreshListView) findViewById(R.id.listView);
        mRefreshListView.setPageCount(PAGE_COUNT);
        mListView = mRefreshListView.getRefreshListView();
        mHeaderView = LayoutInflater.from(this).inflate(R.layout.forum_detail_list_header, mListView, false);
        mListView.addHeaderView(mHeaderView);

        mEditBox = (LinearLayout) findViewById(R.id.Forum_editBox);
        mForumLastReply = (TextView) mHeaderView.findViewById(R.id.Forum_lastReply);
        mPostCountView = (TextView) mHeaderView.findViewById(R.id.post_count);
        mTopicCountView = (TextView) mHeaderView.findViewById(R.id.topic_count);
        mForumLogo = (ImageView) mHeaderView.findViewById(R.id.Forum_logo);
        mForumBackground = (LinearLayout) mHeaderView.findViewById(R.id.Forum_backgroundImg);
        mForumNameView = (TextView) mHeaderView.findViewById(R.id.forum_name);
        //test
        FragmentManager fragmentManager = getSupportFragmentManager();

        mSpannableString = new SpannableString(
                getString(R.string.app_name));
        mMenu = (ImageView) findViewById(R.id.Menu);
        mWritePost = (ImageView) findViewById(R.id.Forum_writePost);
        mMenu.setOnClickListener(this);
        mWritePost.setOnClickListener(this);
        editText = (EditText) findViewById(R.id.Forum_index_edittext);
        editText.addTextChangedListener(watcher);
        mForumSendCom = (TextView) findViewById(R.id.Forum_index_send_com);
//        mForumSendCom.setOnClickListener(this);
    }

    private void initListView() {
//        mListView.setOnScrollListener(this);
        FinalBitmap finalBitmap = FinalBitmap.create(this);
        finalBitmap.configMemoryCacheSize((int) (Runtime.getRuntime().maxMemory() / 1024));
        finalBitmap.configBitmapLoadThreadSize(30);
        kjBitmap = KJBitmap.create();
        List<PostInfo> postInfos = new ArrayList();
        PostInfo postInfo = new PostInfo();
        for (int i = 0; i <= 10; i++) {
            postInfos.add(postInfo);
        }

        mRefreshListView.setScrollToLoadListener(new PullToRefreshListView.ScrollToLoadListener() {
            @Override
            public void onPullUpLoadData() {
                forumApi.getPosts(mForumBean.id, String.valueOf(mCurrentIndex), String.valueOf(PAGE_COUNT));
            }

            @Override
            public void onPullDownLoadData() {
                mCurrentIndex = START_INDEX;
                initFlag(false, false, true, false, false);
                getForumPost(mForumBean.id);
            }
        }, REFRESH_THRESHOLD);

            /*mListView.addFooterView(ListBottom, null, false);
            ListBottom.setVisibility(View.VISIBLE);*/
        mAdapter = new PostAdapter(mPostInfos, this, mEditBox, kjBitmap, finalBitmap);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new MyListItemClickListener());
        isInitView = true;
        //mListView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_item, android.R.id.text1, mListItems));
    }


    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (count != 0) {

                mForumSendCom.setBackgroundDrawable(getResources().getDrawable(R.drawable.forum_enable_btn_send));
                mForumSendCom.setTextColor(Color.WHITE);
            } else {
                mForumSendCom.setBackgroundDrawable(getResources().getDrawable(R.drawable.border));
                mForumSendCom.setTextColor(Color.parseColor("#A9ADB0"));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() != 0) {

                mForumSendCom.setBackgroundDrawable(getResources().getDrawable(R.drawable.forum_enable_btn_send));
                mForumSendCom.setTextColor(Color.WHITE);
            } else {
                mForumSendCom.setBackgroundDrawable(getResources().getDrawable(R.drawable.border));
                mForumSendCom.setTextColor(Color.parseColor("#A9ADB0"));
            }
        }
    };


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public int getActionBarHeight() {
        if (mActionBarHeight != 0) {
            return mActionBarHeight;
        }

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            getTheme().resolveAttribute(android.R.attr.actionBarSize,
                    mTypedValue, true);
        } else {
            getTheme()
                    .resolveAttribute(R.attr.actionBarSize, mTypedValue, true);
        }

        mActionBarHeight = TypedValue.complexToDimensionPixelSize(
                mTypedValue.data, getResources().getDisplayMetrics());
        return mActionBarHeight;
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.Menu:
                super.onBackPressed();
                break;
            case R.id.writePost:
                break;
            case R.id.floating_relativeLayout:
//                ToastHelper.showToast("refresh刷新",ctx);
                mCurrentIndex = 1;
                initFlag(false, false, true, false, false);
                getForumPost(mForumBean.id);
                break;
            case R.id.Forum_index_send_com:
                //ToastHelper.showToast("发送评论",this);
                String com = editText.getText().toString().trim();
                if ("".equals(com)) {
                    Toast.makeText(ForumDetailActivity.this, "评论不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ("".equals(session_id)) {
                    Toast.makeText(ForumDetailActivity.this, "未登录", Toast.LENGTH_SHORT).show();
                }
                initFlag(false, false, false, false, true);
                forumApi.sendPostComment(Url.PostID, com);
                break;
            case R.id.Forum_writePost:
                Intent intent = new Intent(ForumDetailActivity.this, SendPostActivity.class);
                intent.putExtra("forumId", mForumBean.id);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        initFlag(false, false, false, false, false);
    }

    public class PagerAdapter extends FragmentPagerAdapter {

        private SparseArrayCompat<ScrollTabHolder> mScrollTabHolders;

        private ScrollTabHolder mListener;
        private List<ScrollTabHolderFragment> fragmentList = new ArrayList<ScrollTabHolderFragment>();
        private List<ForumInfo> list;

        public PagerAdapter(FragmentManager fm, List<ScrollTabHolderFragment> fragmentList, List<ForumInfo> list) {
            super(fm);
            this.fragmentList = fragmentList;
            this.list = list;
            mScrollTabHolders = new SparseArrayCompat<ScrollTabHolder>();
        }

        public void setTabHolderScrollingContent(ScrollTabHolder listener) {
            mListener = listener;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return list.get(position).getTitle();
//            return TITLES[position];
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Fragment getItem(int position) {
            /*ScrollTabHolderFragment fragment = (ScrollTabHolderFragment) SampleListFragment
                    .newInstance(position);*/
            ScrollTabHolderFragment fragment = fragmentList.get(position);
            mScrollTabHolders.put(position, fragment);
            if (mListener != null) {
                fragment.setScrollTabHolder(mListener);
            }
            Log.e("postion", position + "");

            return fragment;
        }

        public SparseArrayCompat<ScrollTabHolder> getScrollTabHolders() {
            return mScrollTabHolders;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 404) {
                ToastHelper.showToast("请求失败", ForumDetailActivity.this);
                mRefreshListView.resetPullStatus();

            } else if (msg.what == 0) {
                String result = (String) msg.obj;
                if (result != null) {
                    if (ADDFORUMPOST) {

                    }
                    List<PostInfo> newList;
                    if (GETFORUMPOST) {
                        newList = myJson.getPostInfos(result, forumTitle);
                        if (newList.size() > 0) {
                            updatePosts(newList, null != newList && newList.size() == PAGE_COUNT);
                            mRefreshListView.setPullUpToRefresh(newList.size() >= PAGE_COUNT);
                        } else {
                            ToastHelper.showToast("暂无数据", ForumDetailActivity.this);
                        }
                        mRefreshListView.resetPullStatus();
                    }
                    if (REFRESH) {
                        newList = myJson.getPostInfos(result, forumTitle);
                        if (newList.size() > 0) {
                            updatePosts(newList, null != newList && newList.size() == PAGE_COUNT);
                        } else {
                            ToastHelper.showToast("暂无数据", ForumDetailActivity.this);
                        }
                        mRefreshListView.resetPullStatus();
                    }
                    if (SENDPOSTCOMMENT) {
                        if (myJson.getSuccess(result)) {

                            ToastHelper.showToast("发送成功", ForumDetailActivity.this, R.drawable.checkmark);
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                            mEditBox.setVisibility(View.GONE);
                        }
                    }
                }
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        Log.e("回来了", "所以resume");

//        insertPost();

    }

    /**
     * 获取论坛的板块数据
     */
    private void getInitData() {
        initFlag(false, false, false, true, false);
        forumApi.setHandler(handler);
        getForumPost(mForumBean.id);
    }

    /**
     * @param fourmId
     */
    private void getForumPost(String fourmId) {
        initFlag(false, true, false, false, false);
        forumApi.getPosts(fourmId, String.valueOf(mCurrentIndex), String.valueOf(PAGE_COUNT));

    }

    /**
     * 设置请求的类型
     *
     * @param addForumPost
     * @param getForumPost
     * @param refresh
     * @param getForums
     */
    private void initFlag(boolean addForumPost, boolean getForumPost, boolean refresh, boolean getForums, boolean sendPostCom) {
        ADDFORUMPOST = addForumPost;
        GETFORUMS = getForums;
        REFRESH = refresh;
        GETFORUMPOST = getForumPost;
        SENDPOSTCOMMENT = sendPostCom;
        addFlag = false;
    }

    public void updatePosts(List<PostInfo> list, boolean hasMoreData) {
        //ToastHelper.showToast("fragment postion"+this.getArguments().getInt(ARG_POSITION),ctx);
        if (mCurrentIndex == START_INDEX) {
            mPostInfos.removeAll(mPostInfos);
        }
        if (hasMoreData) {
            mCurrentIndex++;
        }

        mPostInfos.addAll(list);
        mAdapter.notifyDataSetChanged();

        if (list.size() < 10 && list.size() > 0) {
            Button noBtn = new Button(this);
            noBtn.setText("没有更多了111");
            noBtn.setBackgroundColor(getResources().getColor(R.color.forumAdd));
            noBtn.setTextColor(getResources().getColor(R.color.black));

            noBtn.setPadding(20, 10, 20, 10);
//                mListView.addFooterView(noBtn);

        } else if (list.size() >= 10) {
            mListView.removeFooterView(ListBottom);
            mListView.addFooterView(ListBottom);
        }
        if (!addFlag) {
            mListView.setSelection(0);
        }
    }

    /**
     * 显示论坛发的基本信息
     */
    private void initForumData() {
        mTopicCountView.setText(String.valueOf(mForumBean.reply_count));
        mPostCountView.setText(String.valueOf(mForumBean.topic_count));

        mForumLastReply.setText("最后回复时间：" + mForumBean.last_reply_time);
        mForumNameView.setText(mForumBean.title);
        BaseFunction.showImage(this, mForumLogo, mForumBean.logo, imgLoad, Url.IMGTYPE_WEIBO);
        BaseFunction.setLayoutBackGround(mForumLogo, this, mForumBackground, mForumBean.background, imgLoad);
    }

    private class MyListItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ToastHelper.showToast(position+"",ctx);
            if (position <= 0) {
                return;
            }
            Intent intent = new Intent(ForumDetailActivity.this, PostDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("postInfo", mPostInfos.get(position - 1));
            intent.putExtra("post"                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           , bundle);
            startActivity(intent);
        }
    }
}
