/*
 * 文件名: QuestionListFragment
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/8/16
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.ui.question;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.otto.Subscribe;
import com.thinksky.fragment.QuestionDetailActivity;
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
import com.tox.Url;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import retrofit2.Response;
import rx.Observable;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/8/16]
 */
public class QuestionListFragment extends BasicFragment {
  private static final String BUNDLE_KEY_WHICH_ACTIVITY = "bundle_key_which_activity";
  private static final String BUNDLE_KYE_CATEGORY = "bundle_key_category";

  private static final int PAGE_LIMIT = 10;
  private static final int START_PAGE = 1;
  private static final int LOAD_MORE_COUNT = 3;

  PullToRefreshListView mListView;
  WendaListAdapter mListAdapter;
  private ImageView iv1, iv2, iv3;

  @Inject
  AppService mAppService;
  private int mCurrentPage = START_PAGE;
  private String mWhichActivity;
  private String mCategory;

  public static QuestionListFragment newInstance(String whichActivity, String category) {
    QuestionListFragment fragment = new QuestionListFragment();
    Bundle bundle = new Bundle();
    bundle.putString(BUNDLE_KEY_WHICH_ACTIVITY, whichActivity);
    bundle.putString(BUNDLE_KYE_CATEGORY, category);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    inject();
    mWhichActivity = getArguments().getString(BUNDLE_KEY_WHICH_ACTIVITY);
    mCategory = getArguments().getString(BUNDLE_KYE_CATEGORY);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.activity_wenda_common, container, false);
    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mListView = (PullToRefreshListView) view.findViewById(R.id.listView);
    mListView.setPageCount(PAGE_LIMIT);
    mListAdapter = new WendaListAdapter(getActivity());
    mListView.getRefreshListView().setAdapter(mListAdapter);
    mListView.setScrollToLoadListener(new PullToRefreshListView.ScrollToLoadListener() {
      @Override
      public void onPullUpLoadData() {
        initData(mWhichActivity);
      }

      @Override
      public void onPullDownLoadData() {
        mCurrentPage = 0;
        initData(mWhichActivity);
      }
    }, LOAD_MORE_COUNT);
    inject();
    initData(mWhichActivity);
  }

  private void initData(String whichActivity) {
    Observable<Response<WendaModel>> observable = null;
    switch (whichActivity) {
      case QuestionListActivity.TYPE_HOT://热门
        observable = mAppService.getHotQuestionList(mCurrentPage, PAGE_LIMIT, mCategory);
        break;
      case QuestionListActivity.TYPE_MAX_AWARD://悬赏
        observable = mAppService.getMonQuestionList(mCurrentPage, PAGE_LIMIT, mCategory);
        break;
      case QuestionListActivity.TYPE_SOLUTION://已解决
        observable = mAppService.getSoluteQuestionList(mCurrentPage, PAGE_LIMIT, mCategory);
        break;
      case QuestionListActivity.TYPE_MINE:
        observable = mAppService.getMyQuestionList(Url.SESSIONID, mCurrentPage, PAGE_LIMIT,
            mCategory);
        break;
    }

    if (null == observable) {
      return;
    }
    manageRpcCall(observable, new UiRpcSubscriberSimple<WendaModel>(getActivity()) {

      @Override
      protected void onSuccess(WendaModel wendaModel) {
        if (null == wendaModel.getList() || wendaModel.getList().size() < PAGE_LIMIT) {
          mListView.setPullUpToRefresh(false);
        } else {
          mListView.setPullUpToRefresh(true);
        }
        if (mCurrentPage <= 1) {
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

  @Subscribe
  public void handleAnswerChangeEvent(QuestionDetailActivity.AnswerChangedEvent event) {
    mCurrentPage = 1;
    initData(mWhichActivity);
  }

  @Subscribe
  public void handleQusetionSendEvent(SendQuestionActivity.QuestionSendEvent event) {
    mCurrentPage = 1;
    initData(mWhichActivity);
  }

  private void inject() {
    DaggerServiceComponent.builder().serviceModule(new ServiceModule()).globalModule(new
        GlobalModule(BaseApplication.getApplication()))
        .build().inject(this);
  }


  public class WendaListAdapter extends BaseAdapter {
    private static final String CATEGORY_HONGYU = "5";
    private static final String CATEGORY_LONGYU = "6";
    private static final String CATEGORY_HUYU = "9";
    private static final String CATEGORY_QICAISHENXIAN = "7";
    private static final String CATEGORY_OTHER = "8";
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
      datas.clear();
    }

    public void addAll(List<WendaModel.ListBean> data) {
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
        convertView = LayoutInflater.from(context).inflate(R.layout.fragment_wenda_adapter_item,
            parent, false);
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
      ((TextView) viewHolder.itemView.findViewById(R.id.nickname)).setText(listEntity.getUser()
          .getNickname());
      ((TextView) viewHolder.itemView.findViewById(R.id.answer_num)).setText(listEntity
          .getAnswer_num());
      ((TextView) viewHolder.itemView.findViewById(R.id.creat_time)).setText(listEntity
          .getCreate_time());
      ((TextView) viewHolder.itemView.findViewById(R.id.score)).setText(listEntity
          .getScore());
      switch (listEntity.getCategory()) {
        case CATEGORY_HONGYU:
          ((TextView) viewHolder.itemView.findViewById(R.id.category)).setText(R.string
              .fish_category_hongyu);
          break;
        case CATEGORY_HUYU:
          ((TextView) viewHolder.itemView.findViewById(R.id.category)).setText(R.string
              .fish_category_huyu);
          break;
        case CATEGORY_LONGYU:
          ((TextView) viewHolder.itemView.findViewById(R.id.category)).setText(R.string
              .fish_category_longyu);
          break;
        case CATEGORY_QICAISHENXIAN:
          ((TextView) viewHolder.itemView.findViewById(R.id.category)).setText(R.string
              .fish_category_qicaishenxian);
          break;
        case CATEGORY_OTHER:
          ((TextView) viewHolder.itemView.findViewById(R.id.category)).setText(R.string
              .fish_category_other);
          break;
        default:
          ((TextView) viewHolder.itemView.findViewById(R.id.category)).setText(R.string
              .fish_category_other);
          break;
      }
      String s = listEntity.getBest_answer();
//            if (s.equals("1")) {
//                ((TextView) viewHolder.itemView.findViewById(R.id.best_answer)).setText("已解决");
//            }
      try {
        ImageLoader.loadOptimizedHttpImage(getActivity(), RsenUrlUtil.URL_BASE +
            listEntity.getUser()
                .getAvatar32()).bitmapTransform(new CropCircleTransformation(getActivity()))
            .error(R.drawable.side_user_avatar).error(R.drawable.side_user_avatar).into(viewHolder
            .imgV(R.id.logo));
      } catch (Exception e) {
        e.printStackTrace();
      }
      if (s.equals("0")) {
        ((TextView) viewHolder.itemView.findViewById(R.id.best_answer)).setText("求助中");
        viewHolder.itemView.findViewById(R.id.best_answer).setSelected(false);
      } else {
        ((TextView) viewHolder.itemView.findViewById(R.id.best_answer)).setText("已解决");
        viewHolder.itemView.findViewById(R.id.best_answer).setSelected(true);
      }
      if (listEntity.getImgList().size() != 0 && !listEntity.getImgList()
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
            /*点击事件响应*/
      viewHolder.itemView.findViewById(R.id.item_layout).setOnClickListener(new View
          .OnClickListener() {
        @Override
        public void onClick(View v) {
//                    RsenCommonActivity.showActivity(context, RsenCommonActivity.TYPE_QDETAIL,
// null);
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
