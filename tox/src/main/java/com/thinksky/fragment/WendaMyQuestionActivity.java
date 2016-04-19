package com.thinksky.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WendaMyQuestionActivity extends AppCompatActivity {
    ListView mListView;

    ImageView back_menu;
    TextView tiwen;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_myquestion_common);
        mListView = (ListView) findViewById(R.id.listView);
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
                if (BaseFunction.isLogin()) {
                    Intent intent = new Intent(WendaMyQuestionActivity.this, SendQuestionActivity.class);
                    startActivity(intent);
                }else{

                }
            }
        });
        init();

    }

    private void init() {
        RsenUrlUtil.execute(this, RsenUrlUtil.URL_MY_WD,
                new RsenUrlUtil.OnMapListener() {
                    @Override
                    public Map getMap() {

                        Map map = new HashMap();
                        map.put("session_id", Url.SESSIONID);
                        return map;

                    }

                    @Override
                    public void onNoNetwork(String msg) {
                        ToastHelper.showToast(msg, Url.context);
                    }

                    @Override
                    public void onResult(boolean state, String result, JSONObject jsonObject) {
                        if (state) {
                            WendaFragment.WendaBean wendaBean = JSON.parseObject(result, WendaFragment.WendaBean.class);
                            mListView.setAdapter(new WendaListAdapter(WendaMyQuestionActivity.this, wendaBean.getList()));
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
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

            ResUtil.setRoundImage(RsenUrlUtil.URL_BASE + listEntity.getCover_url(), viewHolder.imgV(R.id.logo));
            String s = listEntity.getBest_answer();
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
