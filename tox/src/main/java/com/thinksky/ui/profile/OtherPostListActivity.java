package com.thinksky.ui.profile;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.thinksky.holder.BaseApplication;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.injection.GlobalModule;
import com.thinksky.net.UiRpcSubscriberSimple;
import com.thinksky.net.rpc.model.HotPostModel;
import com.thinksky.net.rpc.service.AppService;
import com.thinksky.rsen.RBaseAdapter;
import com.thinksky.rsen.RViewHolder;
import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.serviceinjection.DaggerServiceComponent;
import com.thinksky.serviceinjection.ServiceModule;
import com.thinksky.tox.GroupPostInfoActivity;
import com.thinksky.tox.ImagePagerActivity;
import com.thinksky.tox.R;
import com.thinksky.ui.basic.BasicListAdapter;
import com.thinksky.ui.common.PullToRefreshListView;
import com.thinksky.ui.common.TitleBar;
import com.thinksky.utils.imageloader.ImageLoader;
import java.util.ArrayList;
import javax.inject.Inject;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class OtherPostListActivity extends BaseBActivity {
  private static final String BUNDLE_KEY_USER_ID = "user_id";
  private static final int PAGE_COUNT = 20;
  private static final int START_PAGE = 1;
  @Bind(R.id.title_bar)
  TitleBar titleBar;
  private int mCurrentPage;
  @Bind(R.id.recycler)
  PullToRefreshListView mListView;
  private PostItemAdapter adapter;
  private boolean isWeGroup = true;

  @Inject
  AppService mAppService;

  private String mUid;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    inject();
    setContentView(R.layout.activity_other_post);
    ButterKnife.bind(this);
    mUid = getIntent().getStringExtra(BUNDLE_KEY_USER_ID);
    initView();
    initViewData();
  }


  public static Intent makeIntent(Context context, String userId) {
    Intent intent = new Intent(context, OtherPostListActivity.class);
    intent.putExtra(BUNDLE_KEY_USER_ID, userId);
    return intent;
  }

  private void inject() {
    DaggerServiceComponent.builder().globalModule(new GlobalModule(BaseApplication.getApplication
        ())).serviceModule(new ServiceModule())
        .build().inject(this);
  }

  private void initView() {
    titleBar.setLeftImgMenu(R.drawable.icon_title_bar_back, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    titleBar.setMiddleTitle(R.string.activity_other_post_title);

    adapter = new PostItemAdapter();
    mListView.getRefreshListView().setAdapter(adapter);
    mListView.setScrollToLoadListener(new PullToRefreshListView.ScrollToLoadListener() {
      @Override
      public void onPullUpLoadData() {
        initViewData();
      }

      @Override
      public void onPullDownLoadData() {
        mCurrentPage = START_PAGE;
        initViewData();
      }
    }, 3);
  }

  private void initViewData() {
    manageRpcCall(mAppService.getHerPostList(mUid, mCurrentPage, PAGE_COUNT), new
        UiRpcSubscriberSimple<HotPostModel>
            (this) {
          @Override
          protected void onSuccess(HotPostModel hotPostModel) {
            if (mCurrentPage == START_PAGE) {
              adapter.clear();
            }
            adapter.addAll(hotPostModel.getList());
            mCurrentPage++;
            adapter.notifyDataSetChanged();
          }

          @Override
          protected void onEnd() {
            mListView.resetPullStatus();
          }
        });
  }


  public static void launch(Context context, boolean isWeGroup, HotPostModel.HotPostBean bean) {
    Bundle bundle = new Bundle();

    bundle.putString(GroupPostInfoActivity.BUNDLE_KEY_POST_ID, bean.getId());
    bundle.putBoolean(GroupPostInfoActivity.BUNDLE_KEY_IS_WE_GROUP, isWeGroup);
    Intent intent = new Intent(context, GroupPostInfoActivity.class);
    intent.putExtras(bundle);
    context.startActivity(intent);
  }

  class PostItemAdapter extends BasicListAdapter<HotPostModel.HotPostBean> {
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      ViewHolder holder;
      if (null == convertView) {
        convertView = LayoutInflater.from(OtherPostListActivity.this).inflate(R.layout
            .fragment_remen_ylq_adapter_item, parent, false);
        holder = new ViewHolder(convertView);
        convertView.setTag(holder);
      } else {
        holder = (ViewHolder) convertView.getTag();
      }
      final HotPostModel.HotPostBean bean = getItem(position);
      holder.title.setText(bean.getTitle());
      if (!TextUtils.isEmpty(bean.getContent())) {
        holder.content.setText(bean.getContent().replaceAll("\\n", "\n"));
      } else {
        holder.content.setVisibility(View.GONE);
      }
      holder.supportCount.setText(bean.getSupportCount());
      holder.nickname.setText(bean.getUser().getNickname());
      holder.replyCount.setText(bean.getReply_count());
      try {
        ImageLoader.loadOptimizedHttpImage(OtherPostListActivity.this,
            bean.getUser().getAvatar32()).
            bitmapTransform(new CropCircleTransformation(OtherPostListActivity.this)).into(holder
            .userLogo);
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (bean.getImgList() != null && bean.getImgList().size() > 0) {
        holder.imgLayout.setVisibility(View.VISIBLE);

        int size = bean.getImgList().size();
        holder.iv1.setVisibility(size > 0 ? View.VISIBLE : View.GONE);
        holder.iv2.setVisibility(size > 1 ? View.VISIBLE : View.GONE);
        holder.iv3.setVisibility(size > 2 ? View.VISIBLE : View.GONE);
        int height = getResources().getDimensionPixelSize(R.dimen.grid_img_height_three);
        if (size == 1) {
          height = getResources().getDimensionPixelSize(R.dimen.grid_img_height_single);
          RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup
              .LayoutParams
              .MATCH_PARENT, height);
          holder.images.setLayoutParams(params);
        } else if (size == 2) {
          height = getResources().getDimensionPixelSize(R.dimen.grid_img_height_two);
          RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup
              .LayoutParams
              .MATCH_PARENT, height);
          holder.images.setLayoutParams(params);
        } else {
          RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup
              .LayoutParams
              .MATCH_PARENT, height);
          holder.images.setLayoutParams(params);
        }

        for (int i = 0; i < size; i++) {
          String url = RsenUrlUtil.URL_BASE + bean.getImgList().get(i);

          ImageView imageView = null;
          if (i == 0) {
            imageView = holder.iv1;
          } else if (i == 1) {
            imageView = holder.iv2;
          } else if (i == 2) {
            imageView = holder.iv3;
          }

          if (imageView != null) {
            try {
              ImageLoader.loadOptimizedHttpImage(OtherPostListActivity.this, url).into(imageView);
            } catch (Exception e) {
              e.printStackTrace();
            }
            //ImageLoader.getInstance().displayImage(url, imageView);
            final int in = i;
            imageView.setOnClickListener(new View.OnClickListener() {

              @Override
              public void onClick(View v) {
                Intent intent = new Intent(OtherPostListActivity.this, ImagePagerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("image_urls", (ArrayList<String>) bean.getImgList());
                bundle.putInt("image_index", in);
                intent.putExtras(bundle);
                startActivity(intent);
              }
            });
          }
        }
      } else {
        holder.imgLayout.setVisibility(View.GONE);
      }

      holder.rootLayout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          //                    Bundle bundle = new Bundle();
          launch(OtherPostListActivity.this, isWeGroup, bean);

        }
      });
      return convertView;
    }

    class ViewHolder {
      @Bind(R.id.title)
      TextView title;
      @Bind(R.id.content)
      TextView content;
      @Bind(R.id.iv_1)
      ImageView iv1;
      @Bind(R.id.iv_2)
      ImageView iv2;
      @Bind(R.id.iv_3)
      ImageView iv3;
      @Bind(R.id.images)
      LinearLayout images;
      @Bind(R.id.img_layout)
      RelativeLayout imgLayout;
      @Bind(R.id.user_logo)
      ImageView userLogo;
      @Bind(R.id.nickname)
      TextView nickname;
      @Bind(R.id.reply_count)
      TextView replyCount;
      @Bind(R.id.supportCount)
      TextView supportCount;
      @Bind(R.id.item_layout)
      LinearLayout itemLayout;
      @Bind(R.id.root_layout)
      RelativeLayout rootLayout;

      ViewHolder(View view) {
        ButterKnife.bind(this, view);
      }
    }
  }

  /*数据适配器*/
  public class PostAdapter extends RBaseAdapter<HotPostModel.HotPostBean> {
    Context context;

    public PostAdapter(Context context) {
      super(context);
      this.context = context;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
      return R.layout.fragment_remen_ylq_adapter_item;
    }

    @Override
    protected void onBindView(RViewHolder holder, int position, final HotPostModel.HotPostBean
        bean) {

    }
  }
}
