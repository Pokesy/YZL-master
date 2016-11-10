package com.thinksky.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.thinksky.holder.BaseApplication;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.injection.GlobalModule;
import com.thinksky.net.UiRpcSubscriber1;
import com.thinksky.net.rpc.model.VideoModel;
import com.thinksky.net.rpc.service.AppService;
import com.thinksky.rsen.RBaseAdapter;
import com.thinksky.rsen.RViewHolder;
import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.serviceinjection.DaggerServiceComponent;
import com.thinksky.serviceinjection.ServiceModule;
import com.thinksky.tox.IssueDetail;
import com.thinksky.tox.R;
import com.thinksky.utils.imageloader.ImageLoader;
import java.util.List;
import javax.inject.Inject;


public class ZhuanjiFenLeiActivity extends BaseBActivity {

  View rootView;
  ListView listView;
  private IssueAdapter adapter;
  ImageView back_menu;
  String tit;
  String isseu_id;
  TextView group_name;
  private RecyclerView gridView;

  @Inject
  AppService mAppService;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    inject();
    setContentView(R.layout.zhuanji_fenlei_activity);
    listView = (ListView) findViewById(R.id.listView);
    back_menu = (ImageView) findViewById(R.id.back_menu);
    gridView = (RecyclerView) findViewById(R.id.gridView);
    tit = getIntent().getExtras().getString("title");
    isseu_id = String.valueOf(getIntent().getExtras().getInt("id"));
    group_name = (TextView) findViewById(R.id.group_name);
    group_name.setText(tit);

    adapter = new IssueAdapter(this);
    gridView.setAdapter(adapter);
    back_menu.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });


    manageRpcCall(mAppService.getVideoList(isseu_id), new UiRpcSubscriber1<VideoModel>
        (ZhuanjiFenLeiActivity.this) {
      @Override
      protected void onSuccess(VideoModel videoModel) {
        adapter.resetData(videoModel.getList());
      }

      @Override
      protected void onEnd() {

      }
    });

  }

  private void inject() {
    DaggerServiceComponent.builder().serviceModule(new ServiceModule()).globalModule(new
        GlobalModule((BaseApplication) getApplication()))
        .build().inject(this);
  }

  /*数据适配器*/
  public class IssueAdapter extends RBaseAdapter<VideoModel.ListBean> {
    Context context;

    public IssueAdapter(Context context) {
      super(context);
      this.context = context;
    }

    public IssueAdapter(Context context, List<VideoModel.ListBean> datas) {
      super(context, datas);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
      return R.layout.fragment_zhuanji_fenlei_item;
    }

    @Override
    protected void onBindView(RViewHolder holder, int position, final VideoModel.ListBean bean) {
      holder.tV(R.id.title).setText(bean.getTitle());

      holder.tV(R.id.content).setText(bean.getContent().replace("\\n", "\n"));
      holder.tV(R.id.reply_count).setText(bean.getReply_count());
      holder.tV(R.id.support_count).setText(bean.getSupport_count());
      //ImageLoader.getInstance().displayImage(RsenUrlUtil.URL_BASE + bean.cover_url,
      //        holder.imgV(R.id.imageView));
      try {
        ImageLoader.loadOptimizedHttpImage(context, RsenUrlUtil.URL_BASE + bean.getCover_url()).into
            (holder.imgV(R.id.imageView));
      } catch (Exception e) {
        e.printStackTrace();
      }
      holder.v(R.id.root_layout).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Bundle bundle = new Bundle();
          bundle.putInt("id", Integer.parseInt(bean.getId()));
          Intent intent1 = new Intent(ZhuanjiFenLeiActivity.this, IssueDetail.class);
          intent1.putExtras(bundle);
          startActivity(intent1);
        }
      });
    }
  }


  public static class ZjFLBean {
    public String cover_url;
    public String title;
    public int id;
    public String content;
    public String support_count;
    public String reply_count;
  }


}
