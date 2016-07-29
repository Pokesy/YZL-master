package com.thinksky.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.squareup.otto.Subscribe;
import com.thinksky.holder.BaseApplication;
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
import com.thinksky.ui.basic.BasicFragment;
import com.thinksky.ui.common.PullToRefreshListView;
import com.thinksky.utils.imageloader.ImageLoader;
import com.tox.BaseFunction;
import com.tox.ToastHelper;
import com.tox.Url;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import org.kymjs.aframe.bitmap.KJBitmap;

public class WendaFragment extends BasicFragment {
  private static final int PAGE_LIMIT = 10;
  private static final int START_PAGE = 1;
  private static final int LOAD_MORE_COUNT = 3;

  private static final String ARG_PARAM1 = "param1";

  private String mParam1;
  private TextView textView;
  KJBitmap kjBitmap;
  View rootView;
  PullToRefreshListView listView;
  private ImageView back_menu, iv1, iv2, iv3, wutu;
  private WendaListAdapter mListAdapter;
  private int mCurrentPage = START_PAGE;

  @Inject
  AppService mAppService;

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {

    rootView = inflater.inflate(R.layout.fragment_wenda, container, false);

    listView = (PullToRefreshListView) rootView.findViewById(R.id.listView);
    mListAdapter = new WendaListAdapter(WendaFragment.this.getActivity());
    listView.getRefreshListView().setAdapter(mListAdapter);
    listView.setPageCount(PAGE_LIMIT);
    listView.setScrollToLoadListener(new PullToRefreshListView.ScrollToLoadListener() {
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
    addHeaderView();
    ((RadioGroup) rootView.findViewById(R.id.main_radio)).setOnCheckedChangeListener(
        new RadioGroup.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
              case R.id.rb_rmht:
                Intent intent = new Intent(getContext(), WendaListActivity.class);
                intent.putExtra("whichActivity", "HOT");
                startActivity(intent);
                cleanCheck();
                break;
              case R.id.rb_zgxs:
                Intent intent1 = new Intent(getContext(), WendaListActivity.class);
                intent1.putExtra("whichActivity", "MON");
                startActivity(intent1);
                cleanCheck();
                break;
              case R.id.rb_wdtw:
                if (BaseFunction.isLogin()) {
                  Intent intent2 = new Intent(getContext(), WendaMyQuestionActivity.class);
                  startActivity(intent2);
                } else {
                  ToastHelper.showToast("请登录", Url.context);
                }
                cleanCheck();
                break;
              case R.id.rb_yjj:
                Intent intent3 = new Intent(getContext(), WendaListActivity.class);
                intent3.putExtra("whichActivity", "SOLUTION");
                startActivity(intent3);
                cleanCheck();
                break;
              default:

                break;
            }
          }
        });
    init();
    return rootView;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    inject();
  }

  private void inject() {
    DaggerServiceComponent.builder().globalModule(new GlobalModule(BaseApplication.getApplication
        ())).serviceModule(new ServiceModule())
        .build().inject(this);
  }

  @Subscribe
  public void handleQuestionSendEvent(SendQuestionActivity.QuestionSendEvent event) {
    init();
  }

  @Subscribe
  public void handleAnswerChangeEvent(QuestionDetailActivity.AnswerChangedEvent event) {
    init();
  }

  private void init() {
    manageRpcCall(mAppService.getQuestionList(mCurrentPage, PAGE_LIMIT), new
        UiRpcSubscriberSimple<WendaModel>(getActivity()) {


          @Override
          protected void onSuccess(WendaModel wendaModel) {
            if (null == wendaModel.getList() || wendaModel.getList().size() < PAGE_LIMIT) {
              listView.setPullUpToRefresh(false);
            } else {
              listView.setPullUpToRefresh(true);
            }
            if (mCurrentPage == 0) {
              mListAdapter.clear();
            }
            mListAdapter.addData(wendaModel.getList());
            mListAdapter.notifyDataSetChanged();
            mCurrentPage++;
          }

          @Override
          protected void onEnd() {
            listView.resetPullStatus();
          }
        });
  }

  private void addHeaderView() {
    FrameLayout header = new FrameLayout(getActivity());
    header.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        getResources().getDimensionPixelOffset(R.dimen.list_header_height_wenda)));
    listView.getRefreshListView().addHeaderView(header);
  }

  private void cleanCheck() {
    ((RadioGroup) rootView.findViewById(R.id.main_radio)).check(R.id.hide);
  }

  @Override
  public void onResume() {
    super.onResume();

  }

  public static WendaFragment newInstance(String param1) {
    WendaFragment fragment = new WendaFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    fragment.setArguments(args);
    return fragment;
  }

  public WendaFragment() {
    // Required empty public constructor
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

    public void setData(List<WendaModel.ListBean> datas) {
      this.datas = datas;
    }

    public void addData(List<WendaModel.ListBean> data) {
      if (null != data) {
        datas.addAll(data);
      }
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
      ((TextView) viewHolder.itemView.findViewById(R.id.content)).setText(
          listEntity.getDescription1());
      ((TextView) viewHolder.itemView.findViewById(R.id.nickname)).setText(
          listEntity.getUser().getNickname());
      ((TextView) viewHolder.itemView.findViewById(R.id.answer_num)).setText(
          listEntity.getAnswer_num());
      ((TextView) viewHolder.itemView.findViewById(R.id.creat_time)).setText(
          listEntity.getCreate_time());
      ((TextView) viewHolder.itemView.findViewById(R.id.score)).setText(listEntity.getScore());
      if (listEntity.getCategory().equals("1")) {
        ((TextView) viewHolder.itemView.findViewById(R.id.category)).setText("龙鱼");
      } else {
        ((TextView) viewHolder.itemView.findViewById(R.id.category)).setText("魟鱼");
      }
      //            ((TextView) viewHolder.itemView.findViewById(R.id.nickname)).setDrawablel
      // (listEntity.getUser().getNickname());
      //ResUtil.setRoundImage(RsenUrlUtil.URL_BASE + listEntity.getUser().getAvatar32(),
      //    ((ImageView) viewHolder.itemView.findViewById(R.id.logo)));
      try {
        ImageLoader.loadOptimizedHttpImage(getActivity(),
            RsenUrlUtil.URL_BASE + listEntity.getUser().getAvatar32()).
            bitmapTransform(new CropCircleTransformation(getActivity())).into((ImageView)
            viewHolder.itemView.findViewById(R.id.logo));
      } catch (Exception e) {
        e.printStackTrace();
      }
      //            //为图片控件加载数据
      //            kjBitmap = KJBitmap.create();
      //            kjBitmap.display(((ImageView) viewHolder.itemView.findViewById(R.id.logo)),
      // RsenUrlUtil.URL_BASE + listEntity.getUser().getAvatar32());
      String s = listEntity.getBest_answer();
      //            if (s.equals("1")) {
      //                ((TextView) viewHolder.itemView.findViewById(R.id.best_answer)).setText
      // ("已解决");
      //            }
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
              ImageLoader.loadOptimizedHttpImage(getActivity(), url).placeholder(R.drawable
                  .picture_no).error(R.drawable.picture_no).into(imageView);
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
      if (s.equals("0")) {
        ((TextView) viewHolder.itemView.findViewById(R.id.best_answer)).setText("求助中");
        viewHolder.itemView.findViewById(R.id.best_answer).setSelected(false);
      } else {
        ((TextView) viewHolder.itemView.findViewById(R.id.best_answer)).setText("已解决");
        viewHolder.itemView.findViewById(R.id.best_answer).setSelected(true);
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

              Intent intent = new Intent(getActivity(), QuestionDetailActivity.class);
              intent.putExtras(bundle);
              startActivity(intent);
            }
          });
      return convertView;
    }
  }

  //获取屏幕的宽度
  public static int getScreenWidth(Context context) {
    WindowManager manager = (WindowManager) context
        .getSystemService(Context.WINDOW_SERVICE);
    Display display = manager.getDefaultDisplay();
    return display.getWidth();
  }

  //获取屏幕的高度
  public static int getScreenHeight(Context context) {
    WindowManager manager = (WindowManager) context
        .getSystemService(Context.WINDOW_SERVICE);
    Display display = manager.getDefaultDisplay();
    return display.getHeight();
  }

}
