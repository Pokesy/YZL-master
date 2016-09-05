/*
 * 文件名: PostCollectionFragment
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/8/10
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.ui.profile;

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
import com.thinksky.serviceinjection.DaggerServiceComponent;
import com.thinksky.serviceinjection.ServiceModule;
import com.thinksky.tox.GroupPostInfoActivity;
import com.thinksky.tox.ImagePagerActivity;
import com.thinksky.tox.R;
import com.thinksky.ui.basic.BasicListAdapter;
import com.thinksky.ui.basic.BasicPullToRefreshFragment;
import com.thinksky.ui.common.PullToRefreshListView;
import com.thinksky.utils.imageloader.ImageLoader;
import com.tox.Url;
import java.util.ArrayList;
import javax.inject.Inject;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/8/10]
 */
public class PostCollectionFragment extends BasicPullToRefreshFragment {
  @Bind(R.id.list)
  PullToRefreshListView mListView;

  @Inject
  AppService mAppService;
  private PostCollectionAdapter mAdapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    inject();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
  Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_question_collection, container, false);
    ButterKnife.bind(this, view);
    mAdapter = new PostCollectionAdapter();
    mListView.getRefreshListView().setAdapter(mAdapter);
    mListView.getRefreshListView().setDividerHeight(1);
    return view;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }


  private void inject() {
    DaggerServiceComponent.builder().serviceModule(new ServiceModule()).globalModule(new
        GlobalModule(BaseApplication.getApplication()))
        .build().inject(this);
  }

  @Override
  protected PullToRefreshListView getListView() {
    return mListView;
  }

  @Override
  protected void loadData() {
    manageRpcCall(mAppService.getMyCollectPostList(Url.SESSIONID, getCurrentPage(), PAGE_COUNT),
        new UiRpcSubscriberSimple<HotPostModel>(getActivity()) {


          @Override
          protected void onSuccess(HotPostModel model) {
            if (getCurrentPage() == 0) {
              mAdapter.clear();
            }
            mAdapter.addAll(model.getList());
            mAdapter.notifyDataSetChanged();
            onRefreshLoaded(model.getList().size() >= PAGE_COUNT);
          }

          @Override
          protected void onEnd() {
            resetRefreshStatus();
          }
        });
  }

  @Subscribe
  public void handlePostDataChageEvent(GroupPostInfoActivity.PostDataChangeEvent event) {
    resetCurrentPage();
    loadData();
  }

  private void launch(Context context, boolean isWeGroup, HotPostModel.HotPostBean bean) {
    Bundle bundle = new Bundle();

    bundle.putString(GroupPostInfoActivity.BUNDLE_KEY_POST_ID, bean.getId());
    bundle.putBoolean("isWeGroup", isWeGroup);
    Intent intent = new Intent(context, GroupPostInfoActivity.class);
    intent.putExtras(bundle);
    context.startActivity(intent);
  }

  class PostCollectionAdapter extends BasicListAdapter<HotPostModel.HotPostBean> {

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
        holder.content.setText(bean.getContent().replaceAll("\\n", "\n"));
      } else {
        holder.content.setVisibility(View.GONE);
      }
      holder.supportCount.setText(bean.getSupportCount());
      holder.replyCount.setText(bean.getReply_count());
      holder.nickname.setText(bean.getUser().getNickname());
      try {
        ImageLoader.loadOptimizedHttpImage(getActivity(), bean.getUser().getAvatar32())
            .bitmapTransform(new CropCircleTransformation(getActivity()))
            .into(holder.userLogo);
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

        for (int i = 0; i < bean.getImgList().size(); i++) {
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
              ImageLoader.loadOptimizedHttpImage(getActivity(), url).placeholder(R.drawable
                  .picture_no)
                  .error(R.drawable.picture_no).into(imageView);
            } catch (Exception e) {
              e.printStackTrace();
            }
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
          if (null != bean) {
            launch(getActivity(), true, bean);
          }
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
