package com.thinksky.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.otto.Subscribe;
import com.thinksky.holder.BaseApplication;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.injection.GlobalModule;
import com.thinksky.net.UiRpcSubscriberSimple;
import com.thinksky.net.rpc.model.WendaModel;
import com.thinksky.net.rpc.service.AppService;
import com.thinksky.rsen.RViewHolder;
import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.serviceinjection.DaggerServiceComponent;
import com.thinksky.serviceinjection.ServiceModule;
import com.thinksky.tox.ImagePagerActivity;
import com.thinksky.tox.R;
import com.thinksky.tox.SendQuestionActivity;
import com.thinksky.ui.common.PullToRefreshListView;
import com.thinksky.utils.imageloader.ImageLoader;
import com.tox.ToastHelper;
import com.tox.Url;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class WendaMyQuestionActivity extends BaseBActivity {
  private static final int PAGE_LIMIT = 10;
  private static final int START_PAGE = 1;
  private static final int LOAD_MORE_COUNT = 3;
  PullToRefreshListView mListView;

  ImageView back_menu;
  TextView tiwen;
  private ImageView iv1, iv2, iv3;
  private WendaListAdapter mListAdapter;

  @Inject
  AppService mAppService;
  private int mCurrentPage = START_PAGE;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.fragment_myquestion_common);
    mListView = (PullToRefreshListView) findViewById(R.id.listView);
    back_menu = (ImageView) findViewById(R.id.back_menu);
    tiwen = (TextView) findViewById(R.id.tiwen);
    back_menu.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    tiwen.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!TextUtils.isEmpty(Url.USERID)) {
          Intent intent = new Intent(WendaMyQuestionActivity.this, SendQuestionActivity.class);
          startActivity(intent);
        } else {
          ToastHelper.showToast("请登录", WendaMyQuestionActivity.this);
        }
      }
    });
    mListAdapter = new WendaListAdapter(WendaMyQuestionActivity.this);
    mListView.getRefreshListView().setAdapter(mListAdapter);
    mListView.setPageCount(PAGE_LIMIT);
    mListView.setScrollToLoadListener(new PullToRefreshListView.ScrollToLoadListener() {
      @Override
      public void onPullUpLoadData() {
        init();
      }

      @Override
      public void onPullDownLoadData() {
        mCurrentPage = 0;
        init();
      }
    }, LOAD_MORE_COUNT);
    inject();
    init();
  }

  private void inject() {
    DaggerServiceComponent.builder().globalModule(new GlobalModule(BaseApplication.getApplication
        ())).serviceModule(new ServiceModule())
        .build().inject(this);
  }

  @Subscribe
  public void handleQuestionSendEvent(SendQuestionActivity.QuestionSendEvent event) {
    mCurrentPage = 0;
    init();
  }

  @Subscribe
  public void handleAnswerChangeEvent(QuestionDetailActivity.AnswerChangedEvent event) {
    mCurrentPage = 0;
    init();
  }

  private void init() {
    manageRpcCall(mAppService.getMyQuestionList(Url.SESSIONID, mCurrentPage, PAGE_LIMIT), new
        UiRpcSubscriberSimple<WendaModel>(this) {


          @Override
          protected void onSuccess(WendaModel wendaModel) {
            if (null == wendaModel.getList() || wendaModel.getList().size() < PAGE_LIMIT) {
              mListView.setPullUpToRefresh(false);
            } else {
              mListView.setPullUpToRefresh(true);
            }
            if (mCurrentPage == 0) {
              mListAdapter.clear();
            }
            mListAdapter.addAll(wendaModel.getList());
            mListAdapter.notifyDataSetChanged();
            mCurrentPage++;
          }

          @Override
          protected void onEnd() {
            mListView.resetPullStatus();
          }
        });
  }

  @Override
  public void onResume() {
    super.onResume();
  }

  public class WendaListAdapter extends BaseAdapter {
    private List<WendaModel.ListBean> datas = new ArrayList<>();
    private Context context;

    public WendaListAdapter(Context context) {
      this.context = context;
    }

    @Override
    public int getCount() {
      if (datas == null) {
        return 0;
      }
      return datas.size();
    }

    public void clear() {
      if (null != datas) {
        datas.clear();
      }
    }

    public void addAll(List<WendaModel.ListBean> data) {
      datas.addAll(data);
    }

    @Override
    public Object getItem(int position) {
      return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
      return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
      RViewHolder viewHolder;
      if (convertView == null) {
        convertView = LayoutInflater.from(context)
            .inflate(R.layout.fragment_wenda_adapter_item, parent, false);
        viewHolder = new RViewHolder(convertView);
        convertView.setTag(viewHolder);
      } else {
        viewHolder = (RViewHolder) convertView.getTag();
      }

      final WendaModel.ListBean listEntity = datas.get(position);

            /*其他控件信息，自己添加 id， 然后从 listEntity对象中获取数据，填充就行了*/
      ((TextView) viewHolder.itemView.findViewById(R.id.title)).setText(listEntity.getTitle());
      ((TextView) viewHolder.itemView.findViewById(R.id.content)).setText(listEntity
          .getDescription1());
      ((TextView) viewHolder.itemView.findViewById(R.id.nickname)).setText(
          listEntity.getUser().getNickname());
      ((TextView) viewHolder.itemView.findViewById(R.id.answer_num)).setText(
          listEntity.getAnswer_num());
      ((TextView) viewHolder.itemView.findViewById(R.id.creat_time)).setText(
          listEntity.getCreate_time());
      //            ((TextView) viewHolder.itemView.findViewById(R.id.category)).setText
      // (listEntity.getAnswer_num());
      ((TextView) viewHolder.itemView.findViewById(R.id.score)).setText(listEntity.getScore());
      if (listEntity.getCategory().equals("1")) {
        ((TextView) viewHolder.itemView.findViewById(R.id.category)).setText("龙鱼");
      } else {
        ((TextView) viewHolder.itemView.findViewById(R.id.category)).setText("魟鱼");
      }
      try {
        ImageLoader.loadOptimizedHttpImage(WendaMyQuestionActivity.this, RsenUrlUtil.URL_BASE +
            listEntity.getUser().getAvatar32())
            .placeholder(R.drawable.side_user_avatar).error(R.drawable.side_user_avatar)
            .bitmapTransform(new CropCircleTransformation(WendaMyQuestionActivity.this))
            .dontAnimate().into(viewHolder.imgV(R.id.logo));
      } catch (Exception e) {
        e.printStackTrace();
      }
      String s = listEntity.getBest_answer();
      if (s.equals("0")) {
        ((TextView) viewHolder.itemView.findViewById(R.id.best_answer)).setText("求助中");
        viewHolder.itemView.findViewById(R.id.best_answer).setSelected(false);
      } else {
        ((TextView) viewHolder.itemView.findViewById(R.id.best_answer)).setText("已解决");
        viewHolder.itemView.findViewById(R.id.best_answer).setSelected(true);
      }
      if (listEntity.getImgList() != null && !listEntity.getImgList()
          .contains("Public/images/nopic.png")) {
        viewHolder.itemView.findViewById(R.id.img_layout).setVisibility(View.VISIBLE);

        int size = listEntity.getImgList().size();
        iv1 = viewHolder.imgV(R.id.iv_1);
        iv2 = viewHolder.imgV(R.id.iv_2);
        iv3 = viewHolder.imgV(R.id.iv_3);
        //LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) iv1
        // .getLayoutParams();
        //linearParams.width = (getScreenWidth(context)-45)/3; // 当控件的高强制设成365象素
        //linearParams.height=(getScreenWidth(context)-60)/3;
        //iv1.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件aaa
        //iv2.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件aaa
        //iv3.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件aaa
        iv1.setVisibility(size > 0 ? View.VISIBLE : View.GONE);
        iv2.setVisibility(size > 1 ? View.VISIBLE : View.GONE);
        iv3.setVisibility(size > 2 ? View.VISIBLE : View.GONE);
        //DisplayMetrics metric = new DisplayMetrics();
        //getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        //int width = metric.widthPixels;     // 屏幕宽度（像素）
        //int height = metric.heightPixels;   // 屏幕高度（像素）
        //float density = metric.density;      // 屏幕密度（0.75 / 1.0 / 1.5）
        //int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）

        for (int i = 0; i < size; i++) {
          String url = RsenUrlUtil.URL_BASE + listEntity.getImgList().get(i);

          ImageView imageView = null;
          if (i == 0) {
            imageView = iv1;
          } else if (i == 1) {
            imageView = iv2;
          } else if (i == 2) {
            imageView = iv3;
          }

          if (imageView != null) {
            try {
              ImageLoader.loadOptimizedHttpImage(WendaMyQuestionActivity.this, url).into(imageView);
            } catch (Exception e) {
              e.printStackTrace();
            }
            //ImageLoader.getInstance().displayImage(url, imageView);
            final int in = i;
            imageView.setOnClickListener(new View.OnClickListener() {

              @Override
              public void onClick(View v) {
                Intent intent = new Intent(WendaMyQuestionActivity.this, ImagePagerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("image_urls",
                    (ArrayList<String>) listEntity.getImgList());
                bundle.putInt("image_index", in);
                intent.putExtras(bundle);
                startActivity(intent);
              }
            });
          }
        }
      } else {
        viewHolder.itemView.findViewById(R.id.img_layout).setVisibility(View.GONE);
      }
            /*点击事件响应*/
      viewHolder.itemView.findViewById(R.id.item_layout)
          .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //                    RsenCommonActivity.showActivity(context, RsenCommonActivity
              // .TYPE_QDETAIL, null);
              //Toast.makeText(v.getContext(), "Click Me " + position, Toast.LENGTH_LONG).show();
              Bundle bundle = new Bundle();

              bundle.putString("question_id", listEntity.getId());

              Intent intent = new Intent(context, QuestionDetailActivity.class);
              intent.putExtras(bundle);
              startActivity(intent);
            }
          });
      return convertView;
    }
  }
}
