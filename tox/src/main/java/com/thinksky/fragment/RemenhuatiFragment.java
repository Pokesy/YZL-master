package com.thinksky.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.squareup.otto.Subscribe;
import com.thinksky.holder.BaseApplication;
import com.thinksky.injection.GlobalModule;
import com.thinksky.net.UiRpcSubscriberSimple;
import com.thinksky.net.rpc.model.HotPostModel;
import com.thinksky.net.rpc.service.AppService;
import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.rsen.fragment.RBaseFragment;
import com.thinksky.serviceinjection.DaggerServiceComponent;
import com.thinksky.serviceinjection.ServiceModule;
import com.thinksky.tox.GroupPostInfoActivity;
import com.thinksky.tox.ImagePagerActivity;
import com.thinksky.tox.R;
import com.thinksky.ui.basic.BasicListAdapter;
import com.thinksky.ui.common.PullToRefreshListView;
import com.thinksky.utils.imageloader.ImageLoader;
import java.util.ArrayList;
import javax.inject.Inject;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class RemenhuatiFragment extends RBaseFragment {
  private static final int PAGE_COUNT = 20;
  private static final int INIT_PAGE = 1;

  private static final String ARG_PARAM1 = "param1";
  PullToRefreshListView mListView;
  private RemenhuatiAdapter adapter;
  private boolean isWeGroup = true;

  @Inject
  AppService mAppService;

  private int mCurrentIndex;
  private RemenhuatiAdapter mAdapter = new RemenhuatiAdapter();

  public static RemenhuatiFragment newInstance(String param1) {
    RemenhuatiFragment fragment = new RemenhuatiFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    inject();
  }

  @Override
  protected int getBaseLayoutId() {
    return R.layout.fragment_remenhuti_layout;
  }

  private void inject() {
    DaggerServiceComponent.builder().globalModule(new GlobalModule(BaseApplication.getApplication
        ())).serviceModule(new ServiceModule())
        .build().inject(this);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  @Override
  protected void initViewData() {
    manageRpcCall(mAppService.getHotPostAll(mCurrentIndex, PAGE_COUNT), new
        UiRpcSubscriberSimple<HotPostModel>
            (getActivity()) {
          @Override
          protected void onSuccess(HotPostModel hotPostModel) {
            if (null == hotPostModel && null == hotPostModel.getList()) {
              mListView.setPullUpToRefresh(false);
              return;
            }
            if (mCurrentIndex == INIT_PAGE) {
              mAdapter.clear();
            }
            mAdapter.addAll(hotPostModel.getList());
            mAdapter.notifyDataSetChanged();
            if (mAdapter.getAllList().size() >= PAGE_COUNT) {
              mListView.setPullUpToRefresh(true);
              mCurrentIndex++;
            } else {
              mListView.setPullUpToRefresh(false);
            }
          }

          @Override
          protected void onEnd() {
            mListView.resetPullStatus();
          }
        });
  }


  @Override
  protected void initView(View rootView) {
    mListView = (PullToRefreshListView) rootView.findViewById(R.id.recycler);
    mListView.setScrollToLoadListener(new PullToRefreshListView.ScrollToLoadListener() {
      @Override
      public void onPullUpLoadData() {
        initViewData();
      }

      @Override
      public void onPullDownLoadData() {
        mCurrentIndex = INIT_PAGE;
        initViewData();
      }
    }, 3);
    mListView.getRefreshListView().setAdapter(mAdapter);
  }

  @Subscribe
  public void handlePostDataChangeEvent(GroupPostInfoActivity.PostDataChangeEvent event) {
    initViewData();
  }

  public static void launch(Context context, boolean isWeGroup, HotPostModel.HotPostBean bean) {
    Bundle bundle = new Bundle();

    bundle.putSerializable(GroupPostInfoActivity.BUNDLE_KEY_POST, bean);
    bundle.putBoolean(GroupPostInfoActivity.BUNDLE_KEY_IS_WE_GROUP, isWeGroup);
    Intent intent = new Intent(context, GroupPostInfoActivity.class);
    intent.putExtras(bundle);
    context.startActivity(intent);
  }

  /*数据适配器*/
  public class RemenhuatiAdapter extends BasicListAdapter<HotPostModel.HotPostBean> {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      ViewHolder holder;
      if (null == convertView) {
        convertView = LayoutInflater.from(getActivity()).inflate(R.layout
            .fragment_remen_ylq_adapter_item, parent, false);
        holder = new ViewHolder(convertView);
        convertView.setTag(holder);
      } else {
        holder = (ViewHolder) convertView.getTag();
      }
      final HotPostModel.HotPostBean bean = getItem(position);
      holder.title.setText(bean.getTitle());
      if (!TextUtils.isEmpty(bean.getContent())) {
        holder.content.setText(bean.getContent().replace("\\n", "\n"));
      } else {
        holder.content.setVisibility(View.GONE);
      }
      holder.supportCount.setText(bean.getSupportCount());
      holder.nickname.setText(bean.getUser().getNickname());
      holder.replyCount.setText(bean.getReply_count());
      try {
        ImageLoader.loadOptimizedHttpImage(getActivity(),
            bean.getUser().getAvatar32()).
            bitmapTransform(new CropCircleTransformation(getActivity())).into(holder.userLogo);
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
              ImageLoader.loadOptimizedHttpImage(getActivity(), url).into(imageView);
            } catch (Exception e) {
              e.printStackTrace();
            }
            //ImageLoader.getInstance().displayImage(url, imageView);
            final int in = i;
            imageView.setOnClickListener(new View.OnClickListener() {

              @Override
              public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImagePagerActivity.class);
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
          launch(getActivity(), isWeGroup, bean);

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
}
