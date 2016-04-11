package com.thinksky.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.thinksky.rsen.RViewHolder;
import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.rsen.activity.RBaseAppActivity;
import com.thinksky.tox.R;
import com.tox.ToastHelper;
import com.tox.Url;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WendaMyQuestionCommonFragment extends Fragment {
    private String mUrl;
    private List<String> pictureListUrls;
    View rootView;
    ListView mListView;
    private Context ctx;

    public static final String KEY_NEED_ID = "need_id";
    private boolean needId = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            needId = arguments.getBoolean(KEY_NEED_ID, false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //这里写
        rootView = inflater.inflate(R.layout.fragment_myquestion_common, container, false);
        mListView = (ListView) rootView.findViewById(R.id.listView);
        ctx = rootView.getContext();
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
//            /*脏数据*/
//            if (BuildConfig.DEBUG && needId) {
//                Url.USERID = "rsen";
//                Url.SESSIONID = "h7caek829m8j11no5o7vtkvin4";
//            }

            if (TextUtils.isEmpty(Url.USERID) && needId) {
                RBaseAppActivity.show("请先登录...");
            } else {
                RsenUrlUtil.execute(this.getActivity(), mUrl,
                        new RsenUrlUtil.OnMapListener() {
                            @Override
                            public Map getMap() {
                                if (needId) {
                                    Map map = new HashMap();
                                    map.put("session_id", Url.SESSIONID);
                                    return map;
                                }
                                return null;
                            }

                            @Override
                            public void onNoNetwork(String msg) {
                                ToastHelper.showToast(msg, Url.context);
                            }

                            @Override
                            public void onResult(boolean state, String result, JSONObject jsonObject) {
                                if (state) {
                                    WendaFragment.WendaBean wendaBean = JSON.parseObject(result, WendaFragment.WendaBean.class);
                                    mListView.setAdapter(new WendaListAdapter(getActivity(), wendaBean.getList()));
                                }
                            }
                        });
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private ArrayList<String> mDatas;

    public void setUrl(String url) {
        mUrl = url;
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
            if (s.equals("0")) {
                ((TextView) viewHolder.itemView.findViewById(R.id.best_answer)).setText("求助中");
            }else{
                ((TextView) viewHolder.itemView.findViewById(R.id.best_answer)).setText("已解决");
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
