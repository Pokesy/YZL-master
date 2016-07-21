package com.thinksky.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.rsen.RViewHolder;
import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.tox.ImagePagerActivity;
import com.thinksky.tox.R;
import com.thinksky.tox.SendQuestionActivity;
import com.thinksky.utils.imageloader.ImageLoader;
import com.tox.ToastHelper;
import com.tox.Url;
import java.util.ArrayList;
import java.util.List;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import org.json.JSONObject;

/**
 * Created by jiao on 2016/3/13.
 */
public class WendaListActivity extends BaseBActivity {

  ListView mListView;
  WendaListAdapter mAdapter;
  private ImageView back_menu;
  private TextView mTitleView;
  private TextView tiwen;
  private ImageView iv1, iv2, iv3;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_wenda_common);
    mListView = (ListView) findViewById(R.id.listView);
    tiwen = (TextView) findViewById(R.id.tiwen);
    back_menu = (ImageView) findViewById(R.id.back_menu);
    tiwen.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (null != Url.MYUSERINFO) {
          Intent intent = new Intent(WendaListActivity.this, SendQuestionActivity.class);
          startActivity(intent);
        } else {
          ToastHelper.showToast("请登录", Url.context);
        }
      }
    });
    String whichActivity = getIntent().getStringExtra("whichActivity");
    initView();
    initData(whichActivity);
  }

  private void initView() {
    mTitleView = (TextView) findViewById(R.id.group_name);
  }

  private void initData(String whichActivity) {
    back_menu.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    String url = null;
    switch (whichActivity) {
      case "HOT"://热门
        url = RsenUrlUtil.URL_HOT_WD;
        mTitleView.setText(R.string.activity_wenda_title_hot);
        break;
      case "MON"://悬赏
        url = RsenUrlUtil.URL_MON_WD;
        mTitleView.setText(R.string.activity_wenda_title_mon);
        break;
      case "SOLUTION"://已解决
        url = RsenUrlUtil.URL_SOLUTE_WD;
        mTitleView.setText(R.string.activity_wenda_title_solution);
        break;
    }

    RsenUrlUtil.execute(WendaListActivity.this, url, new RsenUrlUtil.OnNetHttpResultListener() {
      @Override
      public void onNoNetwork(String msg) {
        ToastHelper.showToast(msg, Url.context);
      }


      @Override
      public void onResult(boolean state, String result, JSONObject jsonObject) {
        if (state) {
          WendaFragment.WendaBean wendaBean = JSON.parseObject(result, WendaFragment.WendaBean
              .class);
          mListView.setAdapter(new WendaListAdapter(WendaListActivity.this, wendaBean.getList()));
        }
      }
    });
  }


  public class WendaListAdapter extends BaseAdapter {
    private List<WendaFragment.WendaBean.ListEntity> datas;
    private Context context;

    public WendaListAdapter(Context context, List<WendaFragment.WendaBean.ListEntity> datas) {
      this.datas = datas;
      this.context = context;
    }

    @Override
    public int getCount() {
      if (datas == null) {
        return 0;
      }
      return datas.size();
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

      final WendaFragment.WendaBean.ListEntity listEntity = datas.get(position);

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
//            ((TextView) viewHolder.itemView.findViewById(R.id.category)).setText(listEntity
// .getAnswer_num());
      ((TextView) viewHolder.itemView.findViewById(R.id.score)).setText(listEntity.getScore());
      if (listEntity.getCategory().equals("1")) {
        ((TextView) viewHolder.itemView.findViewById(R.id.category)).setText("龙鱼");
      } else {
        ((TextView) viewHolder.itemView.findViewById(R.id.category)).setText("魟鱼");
      }
      String s = listEntity.getBest_answer();
//            if (s.equals("1")) {
//                ((TextView) viewHolder.itemView.findViewById(R.id.best_answer)).setText("已解决");
//            }
      ImageLoader.loadOptimizedHttpImage(WendaListActivity.this, RsenUrlUtil.URL_BASE +
          listEntity.getUser()
              .getAvatar32()).bitmapTransform(new CropCircleTransformation(WendaListActivity.this))
          .error(R.drawable.side_user_avatar).error(R.drawable.side_user_avatar).into(viewHolder
          .imgV(R.id.logo));
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
            ImageLoader.loadOptimizedHttpImage(WendaListActivity.this, url).placeholder(R.drawable
                .picture_no).error(R.drawable.picture_no).into(imageView);
            //ImageLoader.getInstance().displayImage(url, imageView);
            final int in = i;
            imageView.setOnClickListener(new View.OnClickListener() {

              @Override
              public void onClick(View v) {
                Intent intent = new Intent(WendaListActivity.this, ImagePagerActivity.class);
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
