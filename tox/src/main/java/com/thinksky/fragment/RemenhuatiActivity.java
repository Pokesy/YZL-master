package com.thinksky.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.thinksky.utils.imageloader.ImageLoader;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RemenhuatiActivity extends BaseBActivity {
  private List<String> pictureListUrls;

  ListView listView;

  ImageView back_menu;
  private static final String ARG_PARAM1 = "param1";
  RecyclerView recyclerView;
  private RemenhuatiAdapter adapter;
  private boolean isWeGroup = true;
  private List<ImageView> imgViewList;
  private ImageView iv_1;
  private ImageView iv_2;
  private ImageView iv_3;

  @Inject
  AppService mAppService;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_remenhuti_layout);
    listView = (ListView) findViewById(R.id.listView);
    back_menu = (ImageView) findViewById(R.id.back_menu);
    iv_1 = (ImageView) findViewById(R.id.iv_1);
    iv_2 = (ImageView) findViewById(R.id.iv_2);
    iv_3 = (ImageView) findViewById(R.id.iv_3);
    imgViewList = new ArrayList<ImageView>();
    imgViewList.add(iv_1);
    imgViewList.add(iv_2);
    imgViewList.add(iv_3);
    //默认设置不显示图片
    //        imgViewList.get(0).setVisibility(View.GONE);
    //        imgViewList.get(1).setVisibility(View.GONE);
    //        imgViewList.get(2).setVisibility(View.GONE);
    recyclerView = (RecyclerView) findViewById(R.id.recycler);
    recyclerView.setLayoutManager(
        new LinearLayoutManager(RemenhuatiActivity.this, LinearLayoutManager.VERTICAL, false));
    adapter = new RemenhuatiAdapter(RemenhuatiActivity.this);
    recyclerView.setAdapter(adapter);
    back_menu.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    inject();
    initViewData();
  }

  private void inject() {
    DaggerServiceComponent.builder().globalModule(new GlobalModule(BaseApplication.getApplication
        ())).serviceModule(new ServiceModule())
        .build().inject(this);
  }

  private void initViewData() {
    manageRpcCall(mAppService.getHotPostAll(), new UiRpcSubscriberSimple<HotPostModel>
        (this) {
      @Override
      protected void onSuccess(HotPostModel hotPostModel) {
        adapter.resetData(hotPostModel.getList());
      }

      @Override
      protected void onEnd() {

      }
    });

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
  public class RemenhuatiAdapter extends RBaseAdapter<HotPostModel.HotPostBean> {
    Context context;

    public RemenhuatiAdapter(Context context) {
      super(context);
    }

    public RemenhuatiAdapter(Context context, List<HotPostModel.HotPostBean> datas) {
      super(context, datas);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
      return R.layout.fragment_remen_ylq_adapter_item;
    }

    @Override
    protected void onBindView(RViewHolder holder, int position, final HotPostModel.HotPostBean
        bean) {
      holder.tV(R.id.title).setText(bean.getTitle());
      if (!TextUtils.isEmpty(bean.getContent())) {
        holder.tV(R.id.content).setText(bean.getContent().replace("\\n", "\n"));
      } else {
        holder.tV(R.id.content).setVisibility(View.GONE);
      }
      holder.tV(R.id.supportCount).setText(bean.getSupportCount());
      holder.tV(R.id.nickname).setText(bean.getUser().getNickname());
      holder.tV(R.id.reply_count).setText(bean.getReply_count());
      ImageLoader.loadOptimizedHttpImage(RemenhuatiActivity.this, bean.getUser().getAvatar64())
          .dontAnimate().placeholder(R.drawable.side_user_avatar).bitmapTransform(new
          CropCircleTransformation(RemenhuatiActivity.this))
          .into(holder.imgV(R.id.user_logo));
      if (bean.getImgList() != null && bean.getImgList().size() > 0) {
        holder.v(R.id.img_layout).setVisibility(View.VISIBLE);

        int size = bean.getImgList().size();
        holder.v(R.id.iv_1).setVisibility(size > 0 ? View.VISIBLE : View.GONE);
        holder.v(R.id.iv_2).setVisibility(size > 1 ? View.VISIBLE : View.GONE);
        holder.v(R.id.iv_3).setVisibility(size > 2 ? View.VISIBLE : View.GONE);
        int height = getResources().getDimensionPixelSize(R.dimen.grid_img_height_three);
        if (size == 1) {
          height = getResources().getDimensionPixelSize(R.dimen.grid_img_height_single);
          RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup
              .LayoutParams
              .MATCH_PARENT, height);
          holder.v(R.id.images).setLayoutParams(params);
        } else if (size == 2) {
          height = getResources().getDimensionPixelSize(R.dimen.grid_img_height_two);
          RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup
              .LayoutParams
              .MATCH_PARENT, height);
          holder.v(R.id.images).setLayoutParams(params);
        } else {
          RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup
              .LayoutParams
              .MATCH_PARENT, height);
          holder.v(R.id.images).setLayoutParams(params);
        }

        for (int i = 0; i < size; i++) {
          String url = RsenUrlUtil.URL_BASE + bean.getImgList().get(i);

          ImageView imageView = null;
          if (i == 0) {
            imageView = holder.imgV(R.id.iv_1);
          } else if (i == 1) {
            imageView = holder.imgV(R.id.iv_2);
          } else if (i == 2) {
            imageView = holder.imgV(R.id.iv_3);
          }

          if (imageView != null) {
            try {
              ImageLoader.loadOptimizedHttpImage(RemenhuatiActivity.this, url).into(imageView);
            } catch (Exception e) {
              e.printStackTrace();
            }
            //ImageLoader.getInstance().displayImage(url, imageView);
            final int in = i;
            imageView.setOnClickListener(new View.OnClickListener() {

              @Override
              public void onClick(View v) {
                Intent intent = new Intent(RemenhuatiActivity.this, ImagePagerActivity.class);
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
        holder.v(R.id.img_layout).setVisibility(View.GONE);
      }

      holder.v(R.id.root_layout).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          //                    Bundle bundle = new Bundle();
          launch(mContext, isWeGroup, bean);
          //                    for (int i = 0; i < bean.imgList.size(); i++) {
          //                        String url = RsenUrlUtil.URL_BASE + bean.imgList.get(i);
          //                        imgViewList.get(i).setVisibility(View.VISIBLE);
          //                        kjBitmap.display(imgViewList.get(i), url);
          //                        final int in = i;
          //                        imgViewList.get(in).setOnClickListener(new View
          // .OnClickListener() {
          //
          //                            @Override
          //                            public void onClick(View v) {
          //                                Intent intent = new Intent(getActivity(),
          // ImagePagerActivity.class);
          //                                Bundle bundle = new Bundle();
          //                                bundle.putStringArrayList("image_urls",
          // (ArrayList<String>) bean.imgList);
          //                                bundle.putInt("image_index", in);
          //                                intent.putExtras(bundle);
          //                                startActivity(intent);
          //                            }
          //                        });
          //                    }
        }
      });
    }
  }
}
