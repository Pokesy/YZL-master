package com.thinksky.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.thinksky.rsen.RBaseAdapter;
import com.thinksky.rsen.RViewHolder;
import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.tox.IssueDetail;
import com.thinksky.tox.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ZhuanjiFenLeiActivity extends AppCompatActivity {

    View rootView;
    ListView listView;
    private isseuAdapter adapter;
    ImageView back_menu;
    String tit;
    String isseu_id;
    TextView group_name;
    private RecyclerView gridView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhuanji_fenlei_activity);
        listView = (ListView) findViewById(R.id.listView);
        back_menu = (ImageView) findViewById(R.id.back_menu);
        gridView = (RecyclerView) findViewById(R.id.gridView);
        tit = getIntent().getExtras().getString("title");
        isseu_id = String.valueOf(getIntent().getExtras().getInt("id"));
        group_name = (TextView) findViewById(R.id.group_name);
        group_name.setText(tit);

        adapter = new isseuAdapter(this);
        gridView.setAdapter(adapter);
        back_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        RsenUrlUtil.execute(this, RsenUrlUtil.URL_ZJ_FL, new RsenUrlUtil.OnJsonResultListener<ZjFLBean>() {

            @Override
            public void onNoNetwork(String msg) {

            }
            @Override
            public Map getMap() {
                Map map = new HashMap();
                map.put("issue", isseu_id);
                return map;

            }
            @Override
            public void onParseJsonBean(List<ZjFLBean> beans, JSONObject jsonObject) {


                try {
                    ZjFLBean bean = new ZjFLBean();

                    bean.title = jsonObject.getString("title");//title 赋值
                    bean.id = jsonObject.getInt("id");
                    bean.reply_count = jsonObject.getString("reply_count");
                    bean.support_count = jsonObject.getString("support_count");
                    bean.cover_url = jsonObject.getString("cover_url");
                    bean.content = jsonObject.getString("content");
                    //其他字段。。。赋值

                    beans.add(bean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onResult(boolean state, List<ZjFLBean> beans) {
                adapter.resetData(beans);

            }

        });
    }

    @Override
    public void onResume() {

        super.onResume();

    }

    /*数据适配器*/
    public class isseuAdapter extends RBaseAdapter<ZjFLBean> {
        Context context;

        public isseuAdapter(Context context) {
            super(context);
        }

        public isseuAdapter(Context context, List<ZjFLBean> datas) {
            super(context, datas);
        }

        @Override
        protected int getItemLayoutId(int viewType) {
            return R.layout.fragment_zhuanji_fenlei_item;
        }

        @Override
        protected void onBindView(RViewHolder holder, int position, final ZjFLBean bean) {
            holder.tV(R.id.title).setText(bean.title);

            holder.tV(R.id.content).setText(bean.content);
            holder.tV(R.id.reply_count).setText(bean.reply_count);
            holder.tV(R.id.support_count).setText(bean.support_count);
            ImageLoader.getInstance().displayImage(RsenUrlUtil.URL_BASE + bean.cover_url,

                    holder.imgV(R.id.imageView));
            holder.v(R.id.root_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", bean.id);
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
