package com.thinksky.tox;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nineoldandroids.view.ViewHelper;
import com.thinksky.ParallaxHeaderViewPagerForum.SampleListFragment;
import com.thinksky.ParallaxHeaderViewPagerForum.ScrollTabHolder;
import com.thinksky.ParallaxHeaderViewPagerForum.ScrollTabHolderFragment;
import com.thinksky.adapter.PostAdapter;
import com.thinksky.fragment.LuntanFragment;
import com.thinksky.info.ForumInfo;
import com.thinksky.info.PostInfo;
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
public class ForumDetailActivity extends ActionBarActivity implements View.OnClickListener {

    public static final String BUNDLE_KEY_FORUM_INFO = "forum";

    private static AccelerateDecelerateInterpolator sSmoothInterpolator = new AccelerateDecelerateInterpolator();
    private static boolean GETFORUMPOST = false;
    private static boolean GETFORUMS = true;
    private static boolean ADDFORUMPOST = false;
    private static boolean REFRESH = false;
    private static boolean SENDPOSTCOMMENT = false;

    private ListView mListView;
    private View mHeaderView;
    private KJBitmap kjBitmap;
    private FinalBitmap finalBitmap;
    private Button ListBottom = null;
    private ProgressBar mAddMoreProgressBar;
    private boolean isInitView = false;
    private SampleListFragment.SampListCallBack sampListCallBack;

    private LinearLayout mEditBox, mForumBackground;
    private int mActionBarHeight;
    private int mMinHeaderHeight;
    private int mHeaderHeight;
    private int mMinHeaderTranslation;
    private RelativeLayout mForumTop;
    private RelativeLayout mParentlayout;
    private RectF mRect1 = new RectF();
    private RectF mRect2 = new RectF();
    private ImageView floatImg;
    private RelativeLayout floatingRelative, mForumBody, mForumLoading;
    private TypedValue mTypedValue = new TypedValue();
    private EditText editText;
    private SpannableString mSpannableString;
    private ImageView mMenu, mWritePost, mForumLogo;
    private TextView mForumSendCom, mPostCountView, mTopicCountView, mForumLastReply, mForumNameView;
    private ForumApi forumApi = new ForumApi();
    private MyJson myJson = new MyJson();
    //当前论坛的加载页数
    private int page = 1;
    private List<PostInfo> mPostList = new ArrayList<PostInfo>();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_forum_detail);

        mMinHeaderHeight = getResources().getDimensionPixelSize(
                R.dimen.min_header_height);
        mHeaderHeight = getResources().getDimensionPixelSize(
                R.dimen.header_height);
        mMinHeaderTranslation = -mMinHeaderHeight;

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
        mForumTop = (RelativeLayout) findViewById(R.id.Forum_top);
        mParentlayout = (RelativeLayout) findViewById(R.id.Forum_parent_layout);

        mListView = (ListView) findViewById(R.id.listView);
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
        floatImg = (ImageView) findViewById(R.id.floating_view);
        floatingRelative = (RelativeLayout) findViewById(R.id.floating_relativeLayout);
        floatingRelative.setAlpha(80f);
        floatingRelative.setOnClickListener(this);
        editText = (EditText) findViewById(R.id.Forum_index_edittext);
        editText.addTextChangedListener(watcher);
        mForumSendCom = (TextView) findViewById(R.id.Forum_index_send_com);
        mForumSendCom.setOnClickListener(this);
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
        //设置底部加载
        ListBottom = new Button(this);
        ListBottom.setBackgroundColor(getResources().getColor(R.color.forumAdd));
        ListBottom.setTextColor(getResources().getColor(R.color.black));
        ListBottom.setText("点击加载更多");
//            ListBottom.setPadding(20,10,20,10);
        ListBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sampListCallBack.callback(19, page);
                listBottom();
            }
        });
        mAddMoreProgressBar = new ProgressBar(this);
        mAddMoreProgressBar.setIndeterminate(false);
        mAddMoreProgressBar.setBackgroundColor(getResources().getColor(R.color.forumAdd));
        mAddMoreProgressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progress_bg));
        mAddMoreProgressBar.setVisibility(View.GONE);
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
                AnimationSet animationSet = new AnimationSet(true);
                RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setDuration(2000);
                animationSet.addAnimation(rotateAnimation);
                floatImg.startAnimation(animationSet);
                initFlag(false, false, true, false, false);
                getForumPost(mForumBean.id, "1", "10", fragPage);
                break;
            case R.id.Forum_index_send_com:
                //ToastHelper.showToast("发送评论",this);
                String com = editText.getText().toString().trim();
                if (com.equals("")) {
                    Toast.makeText(ForumDetailActivity.this, "评论不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (session_id.equals("")) {
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

    public static void showEditBox(int position) {

    }

    private void showEditbox(int position) {
        // ToastHelper.showToast("show position:"+position,ctx);
        mEditBox.setVisibility(View.VISIBLE);
    }

    private class MySampleListCallBack implements SampleListFragment.SampListCallBack {

        @Override
        public void callback(int flag, int page) {
            switch (flag) {
                case R.id.Post_comImg:

                    break;
                case 19:
                    // ToastHelper.showToast("callback被点击了,当前page +"+page,ctx);
                    addFlag = true;
                    forumApi.getPosts(mForumBean.id, page + "", "10");
                    break;
            }
        }

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 404) {
                ToastHelper.showToast("请求失败", ForumDetailActivity.this);

            } else if (msg.what == 0) {
                String result = (String) msg.obj;
                if (result != null) {
                    if (ADDFORUMPOST) {

                    }
                    if (GETFORUMPOST) {
                        mPostList = myJson.getPostInfos(result, forumTitle);
                        if (mPostList.size() > 0) {
                            updatePosts(mPostList, addFlag);
                            addFlag = false;
                        } else {
                            ToastHelper.showToast("数据请求失败", ForumDetailActivity.this);
                        }
                    }
                    if (REFRESH) {
                        mPostList = myJson.getPostInfos(result, forumTitle);
                        if (mPostList.size() > 0) {
                            updatePosts(mPostList, addFlag);
                            floatImg.clearAnimation();
                        } else {
                            ToastHelper.showToast("数据请求失败", ForumDetailActivity.this);
                        }
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
        getForumPost(mForumBean.id, "1", "count", 0);
    }

    /**
     * @param fourmId
     * @param page
     * @param count
     * @param fragPostion
     */
    private void getForumPost(String fourmId, String page, String count, int fragPostion) {
        initFlag(false, true, false, false, false);
        forumApi.getPosts(fourmId, "1", "10");

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

    public void updatePosts(List<PostInfo> list, boolean addFlag) {
        //ToastHelper.showToast("fragment postion"+this.getArguments().getInt(ARG_POSITION),ctx);
        if (!addFlag) {
            mPostInfos.removeAll(mPostInfos);
            page = 2;
        } else {
            page++;
        }
        for (PostInfo post : list) {
            mPostInfos.add(post);
        }

        mAdapter.notifyDataSetChanged();

        if (list.size() < 10 && list.size() > 0) {
            mListView.removeFooterView(mAddMoreProgressBar);
            mListView.removeFooterView(ListBottom);
            Button noBtn = new Button(this);
            noBtn.setText("没有更多了111");
            noBtn.setBackgroundColor(getResources().getColor(R.color.forumAdd));
            noBtn.setTextColor(getResources().getColor(R.color.black));

            noBtn.setPadding(20, 10, 20, 10);
//                mListView.addFooterView(noBtn);

        } else if (list.size() >= 10) {
            mListView.removeFooterView(mAddMoreProgressBar);
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
        mTopicCountView.setText(String.valueOf(mForumBean.topic_count));
        mPostCountView.setText(String.valueOf(mForumBean.post_count));
        mForumLastReply.setText("最后回复时间：" + mForumBean.last_reply_time);
        mForumNameView.setText(mForumBean.title);
        BaseFunction.showImage(this, mForumLogo, mForumBean.logo, imgLoad, Url.IMGTYPE_WEIBO);
        BaseFunction.setLayoutBackGround(mForumLogo, this, mForumBackground, mForumBean.background, imgLoad);
    }

    private void listBottom() {
        mAddMoreProgressBar.setVisibility(View.VISIBLE);
        mListView.removeFooterView(ListBottom);
        mListView.addFooterView(mAddMoreProgressBar, null, false);
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
            intent.putExtra("post", bundle);
            startActivity(intent);
        }
    }
}
