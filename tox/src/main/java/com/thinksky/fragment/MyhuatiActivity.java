package com.thinksky.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.tox.BaseApi;
import com.tox.Url;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class MyhuatiActivity extends BaseBActivity {
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

  private String session_id;
  private String userUid;
  private BaseApi baseApi;

  @Inject
  AppService mAppService;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_wodehuti_layout);
    baseApi = new BaseApi();
    session_id = baseApi.getSeesionId();
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
    recyclerView.setLayoutManager(new LinearLayoutManager(MyhuatiActivity.this,
        LinearLayoutManager.VERTICAL, false));
    adapter = new RemenhuatiAdapter(MyhuatiActivity.this);
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
    manageRpcCall(mAppService.getMyPostAll(Url.SESSIONID), new
        UiRpcSubscriberSimple<HotPostModel>(this) {


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

    bundle.putString(GroupPostInfoActivity.BUNDLE_KEY_POST_ID, bean.getId());
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
      this.context = context;
    }

    public RemenhuatiAdapter(Context context, List<HotPostModel.HotPostBean> datas) {
      super(context, datas);
      this.context = context;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
      return R.layout.fragment_remen_ylq_adapter_item;
    }

    @Override
    protected void onBindView(RViewHolder holder, int position, final HotPostModel.HotPostBean
        bean) {
      holder.tV(R.id.title).setText(bean.getTitle());
      holder.tV(R.id.content).setText(bean.getContent());
      holder.tV(R.id.supportCount).setText(bean.getSupportCount());
      holder.tV(R.id.nickname).setText(bean.getUser().getNickname());
      holder.tV(R.id.reply_count).setText(bean.getReply_count());
      try {
        ImageLoader.loadOptimizedHttpImage(context,
            bean.getUser().getAvatar32()).
            bitmapTransform(new CropCircleTransformation(context)).into(holder.imgV(R.id
            .user_logo));
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (bean.getImgList() != null && bean.getImgList().size() > 0) {
        holder.v(R.id.img_layout).setVisibility(View.VISIBLE);

        int size = bean.getImgList().size();
        holder.v(R.id.iv_1).setVisibility(size > 0 ? View.VISIBLE : View.GONE);
        holder.v(R.id.iv_2).setVisibility(size > 1 ? View.VISIBLE : View.GONE);
        holder.v(R.id.iv_3).setVisibility(size > 2 ? View.VISIBLE : View.GONE);

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

            //ImageLoader.getInstance().displayImage(url, imageView);
            try {
              ImageLoader.loadOptimizedHttpImage(MyhuatiActivity.this, url).into
                  (imageView);
            } catch (Exception e) {
              e.printStackTrace();
            }
            final int in = i;
            imageView.setOnClickListener(new View.OnClickListener() {

              @Override
              public void onClick(View v) {
                Intent intent = new Intent(MyhuatiActivity.this, ImagePagerActivity.class);
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
          launch(mContext, isWeGroup, bean);
        }
      });
    }
  }
}
