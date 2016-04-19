package com.thinksky.fragment;

import android.app.Activity;
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
import com.thinksky.rsen.RViewHolder;
import com.thinksky.rsen.ResUtil;
import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.tox.R;
import com.thinksky.tox.SendQuestionActivity;
import com.tox.BaseFunction;
import com.tox.ToastHelper;
import com.tox.Url;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by jiao on 2016/3/13.
 */
public class WendaListActivity extends Activity {

    ListView mListView;
    WendaListAdapter mAdapter;
    private ImageView back_menu;
    private TextView mTitleView;
    private TextView   tiwen;
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
                if (BaseFunction.isLogin()) {
                    Intent intent = new Intent(WendaListActivity.this, SendQuestionActivity.class);
                    startActivity(intent);
                }else{
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
                    WendaFragment.WendaBean wendaBean = JSON.parseObject(result, WendaFragment.WendaBean.class);
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
                convertView = LayoutInflater.from(context).inflate(R.layout.fragment_wenda_adapter_item, parent, false);
                viewHolder = new RViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (RViewHolder) convertView.getTag();
            }

            final WendaFragment.WendaBean.ListEntity listEntity = datas.get(position);

            /*其他控件信息，自己添加 id， 然后从 listEntity对象中获取数据，填充就行了*/
            ((TextView) viewHolder.itemView.findViewById(R.id.title)).setText(listEntity.getTitle());
            ((TextView) viewHolder.itemView.findViewById(R.id.content)).setText(listEntity.getContent());
            ((TextView) viewHolder.itemView.findViewById(R.id.nickname)).setText(listEntity.getUser().getNickname());
            ((TextView) viewHolder.itemView.findViewById(R.id.answer_num)).setText(listEntity.getAnswer_num());
            ((TextView) viewHolder.itemView.findViewById(R.id.creat_time)).setText(listEntity.getCreate_time());
            String s = listEntity.getBest_answer();
//            if (s.equals("1")) {
//                ((TextView) viewHolder.itemView.findViewById(R.id.best_answer)).setText("已解决");
//            }
            ResUtil.setRoundImage(RsenUrlUtil.URL_BASE + listEntity.getCover_url(), viewHolder.imgV(R.id.logo));
            if (s.equals("0")) {
                ((TextView) viewHolder.itemView.findViewById(R.id.best_answer)).setText("求助中");
                viewHolder.itemView.findViewById(R.id.best_answer).setSelected(false);
            } else {
                ((TextView) viewHolder.itemView.findViewById(R.id.best_answer)).setText("已解决");
                viewHolder.itemView.findViewById(R.id.best_answer).setSelected(true);
            }
            /*点击事件响应*/
            viewHolder.itemView.findViewById(R.id.item_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    RsenCommonActivity.showActivity(context, RsenCommonActivity.TYPE_QDETAIL, null);
                    //Toast.makeText(v.getContext(), "Click Me " + position, Toast.LENGTH_LONG).show();
                    Bundle bundle = new Bundle();

                    bundle.putString("question_id", listEntity.getId());

                    Intent intent = new Intent(context, wentixiangqing.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            return convertView;
        }
    }
}
