package com.thinksky.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.thinksky.info.WendaXianqingInfo;
import com.thinksky.rsen.ResUtil;
import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.tox.R;
import com.thinksky.utils.LoadImg;
import com.thinksky.utils.MyJson;
import com.tox.BaseApi;
import com.tox.BaseFunction;
import com.tox.ToastHelper;
import com.tox.Url;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class wentixiangqing extends AppCompatActivity implements View.OnClickListener {

    private TextView title;
    private TextView best_answer;
    private TextView money;
    private TextView creat_time;
    private TextView content;
    private TextView nickname;
    private TextView huida;
    private ListView listView;
    private Button btn_huida;
    List<WendaXianqingInfo> mListData;
    WentixiangqingListAdapter mListAdapter;
    private String mQuestionId;
    private BaseApi baseApi;
    private String session_id;
    private ImageView back_menu;
    public static boolean upFlag = false;
    public static boolean flag = false;
    private SharedPreferences sp = null;
    private LinearLayout reply_button;
    private LinearLayout reply_box;
    private EditText reply_editText;
    private TextView sendPostButtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wentixiangqing);
        title = (TextView) findViewById(R.id.title);
        best_answer = (TextView) findViewById(R.id.best_answer);
        money = (TextView) findViewById(R.id.money);
        creat_time = (TextView) findViewById(R.id.creat_time);
        content = (TextView) findViewById(R.id.content);
        nickname = (TextView) findViewById(R.id.nickname);
        huida = (TextView) findViewById(R.id.huida);
        listView = (ListView) findViewById(R.id.listView);
        btn_huida = (Button) findViewById(R.id.btn_huida);
        back_menu = (ImageView) findViewById(R.id.back_menu);

        reply_button = (LinearLayout) findViewById(R.id.reply_button);
        reply_box = (LinearLayout) findViewById(R.id.reply_box);
        reply_editText = (EditText) findViewById(R.id.reply_editText);
        sendPostButtn = (TextView) findViewById(R.id.sendPostButn);
        //发表回复文本框的事件监听器
        reply_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count != 0) {
                    sendPostButtn.setBackgroundResource(R.drawable.forum_enable_btn_send);
                    sendPostButtn.setTextColor(Color.WHITE);
                } else {
                    sendPostButtn.setBackgroundResource(R.drawable.border);
                    sendPostButtn.setTextColor(Color.parseColor("#A9ADB0"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    sendPostButtn.setBackgroundResource(R.drawable.forum_enable_btn_send);
                    sendPostButtn.setTextColor(Color.WHITE);
                } else {
                    sendPostButtn.setBackgroundResource(R.drawable.border);
                    sendPostButtn.setTextColor(Color.parseColor("#A9ADB0"));
                }
            }
        });

        back_menu.setOnClickListener(this);
        btn_huida.setOnClickListener(this);
        sendPostButtn.setOnClickListener(this);
        mListData = new ArrayList<WendaXianqingInfo>();
        baseApi = new BaseApi();
        session_id = baseApi.getSeesionId();
        mQuestionId = getIntent().getStringExtra("question_id");
        initXiangQingData();
    }


    private void initXiangQingData() {

        String url = RsenUrlUtil.URL_WENTI_XIANGQING;
        RsenUrlUtil.execute(url, new RsenUrlUtil.OnJsonResultListener<WendaXianqingInfo>() {
            @Override
            public void onNoNetwork(String msg) {
                ToastHelper.showToast(msg, Url.context);
            }

            @Override
            public Map getMap() {
                Map map = new HashMap();
                map.put("session_id", session_id);
                map.put("questionid", mQuestionId);
                return map;
            }

            @Override
            public void onParseJsonBean(List<WendaXianqingInfo> beans, JSONObject jsonObject) {
                try {

//                    WendaXianqingInfo.ListEntity listEntity = new WendaXianqingInfo.ListEntity();
//                    WendaXianqingInfo.UserEntity userEntity = new WendaXianqingInfo.UserEntity();
//
//
//                    ArrayList<WendaXianqingInfo.ListEntity> list =new ArrayList<WendaXianqingInfo.ListEntity>();
//
//                    jsonObject.optJSONArray("list");
//
//                    WendaXianqingInfo wendaXianqingInfo = new WendaXianqingInfo();
//                    wendaXianqingInfo.setList(list);
                    String result = jsonObject.toString();
                    WendaXianqingInfo wendaXianqingInfo = JSON.parseObject(result, WendaXianqingInfo.class);
                    beans.add(wendaXianqingInfo);

                } catch (Exception e) {

                }
            }

            @Override
            public void onResult(boolean state, List<WendaXianqingInfo> beans) {
                if (state) {

                    //回答
                    mListAdapter = new WentixiangqingListAdapter(wentixiangqing.this, beans.get(0).getQuestionAnswer());
                    listView.setAdapter(mListAdapter);

                    //问题
                    WendaXianqingInfo questionEntity = beans.get(0);
                    title.setText(questionEntity.getTitle());
                    if (questionEntity.getBest_answer().equals("0")) {
                        best_answer.setText("求助中");
                        flag = true;
                    } else {
                        best_answer.setText("已解决");
                        best_answer.setBackgroundColor(Color.GRAY);
                        flag = false;
                    }
                    money.setText(questionEntity.getScore());
                    creat_time.setText(questionEntity.getCreate_time());
                    content.setText(questionEntity.getDescription1());
                    nickname.setText(questionEntity.getCategory_title());
                    huida.setText(questionEntity.getAnswer_num()+"条回答");

                } else {
                    ToastHelper.showToast("请求失败", Url.context);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            //回复按钮
            case R.id.btn_huida:
                if (BaseFunction.isLogin()) {

                    reply_button.setVisibility(View.GONE);
                    reply_box.setVisibility(View.VISIBLE);
                    //自动打开软键盘并获取焦点
                    reply_editText.setFocusable(true);
                    reply_editText.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

                } else {
                    ToastHelper.showToast("请登录后操作", this);
                }
                break;
            //发表评论按钮
            case R.id.sendPostButn:
                if (reply_editText.getText().length() > 0 && !"".equals(reply_editText.getText().toString().trim())) {
                    sendPostButtn.setBackgroundResource(R.drawable.border);
                    sendPostButtn.setTextColor(Color.parseColor("#A9ADB0"));
                    reply_button.setVisibility(View.VISIBLE);
                    reply_box.setVisibility(View.GONE);
                    InputMethodManager imm2 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm2.isActive()) {
                        imm2.hideSoftInputFromWindow(reply_editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    Log.e("评论内容>>>>>>>>>>", reply_editText.getText().toString());
                    send();
//                    initFlag(false,true);
//                    groupApi.postComment(post_id+"",reply_editText.getText().toString());
                    reply_editText.setText(null);

                } else {
                    ToastHelper.showToast("评论不能为空", this);
                }
                break;

            case R.id.back_menu:
                finish();
                break;
            default:
                break;
        }
    }

    private void send() {

//        Map map = new HashMap();
//        map.put("session_id", session_id);
//        map.put("content", reply_editText.getText().toString().trim());
//        map.put("question_id", mQuestionId);


        RsenUrlUtil.execute(this, RsenUrlUtil.URL_SEND_QUESTION_ANSWER, new RsenUrlUtil.OnJsonResultListener<DiscoverFragment.FXBean>() {
            @Override
            public void onNoNetwork(String msg) {
                ToastHelper.showToast(msg, Url.context);
            }

            @Override
            public Map getMap() {
                Map map = new HashMap();
                map.put("session_id", session_id);
                map.put("content", reply_editText.getText().toString().trim());
                map.put("question_id", mQuestionId);
                return map;

            }

            @Override
            public void onParseJsonBean(List<DiscoverFragment.FXBean> beans, JSONObject jsonObject) {
                String result = jsonObject.toString();
                DiscoverFragment.FXBean discoverInfo = JSON.parseObject(result, DiscoverFragment.FXBean.class);
                beans.add(discoverInfo);
            }

            @Override
            public void onResult(boolean state, List beans) {

            }
        });

    }

    public class WentixiangqingListAdapter extends BaseAdapter {

        private Context context;

        private List<WendaXianqingInfo.QuestionAnswerEntity> list;
        private LoadImg loadImg;

        public WentixiangqingListAdapter(Context context, List<WendaXianqingInfo.QuestionAnswerEntity> list) {
            this.context = context;
            this.list = list;
            loadImg = new LoadImg(context);
        }


        @Override
        public int getCount() {
            if (list == null) {
                return 0;
            }
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.activity_wentixiangqing_list_item_zjw, null);
                holder = new ViewHolder();
                holder.avatar32 = (ImageView) convertView.findViewById(R.id.avatar32);
                holder.dianzan = (ImageView) convertView.findViewById(R.id.dianzan);
                holder.caina = (ImageView) convertView.findViewById(R.id.caina);
                holder.nickname = (TextView) convertView.findViewById(R.id.nickname);
                holder.content = (TextView) convertView.findViewById(R.id.content);
                holder.creat_time = (TextView) convertView.findViewById(R.id.creat_time);
                holder.reply_count = (TextView) convertView.findViewById(R.id.reply_count);
                holder.acept = (TextView) convertView.findViewById(R.id.acept);
                if (flag) {
                    holder.acept.setVisibility(View.VISIBLE);
                }
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
//            WendaXianqingInfo bean = beans.get(position);
//            list =bean.getQuestionAnswer();
            final WendaXianqingInfo.QuestionAnswerEntity bean = list.get(position);
            ResUtil.setRoundImage(RsenUrlUtil.URL_BASE +bean.getUser().getAvatar32(),  holder.avatar32);
            holder.nickname.setText(bean.getUser().getNickname());
            holder.content.setText(bean.getContent());
            holder.creat_time.setText(MyJson.getStandardDate(bean.getCreate_time()));
            holder.reply_count.setText(bean.getSupport_count());


            if (!(bean.getIs_supported().equals("0"))) {//已点赞
                holder.dianzan.setBackgroundResource(R.drawable.iconfontdianzan);
                upFlag = true;
            } else {
                holder.dianzan.setBackgroundResource(R.drawable.iconfontweidianzan);
                upFlag = false;
            }


            if (bean.getIsbest() != 0) {
                //采纳
                holder.caina.setVisibility(View.VISIBLE);
            } else {
                holder.caina.setVisibility(View.GONE);
            }

            BaseFunction.showImage(context, holder.avatar32, bean.getUser().getAvatar32(), loadImg, Url.IMGTYPE_HEAD);

            /*点击图标和文本,都支持点赞操作*/
            holder.dianzan.setOnClickListener(new DianZanListener(holder, bean));
            holder.reply_count.setOnClickListener(new DianZanListener(holder, bean));
//
            holder.reply_count.setOnClickListener(new DianZanListener(holder, bean));

            /*采纳*/
            holder.acept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (BaseFunction.isLogin()) {
                        if (bean.getUser().getUid().equals(Url.USERID)) {
                            RsenUrlUtil.execute(RsenUrlUtil.URL_SET_BEST_ANSWER, new RsenUrlUtil.OnJsonResultListener<DianzanBean>() {
                                @Override
                                public void onNoNetwork(String msg) {
                                    ToastHelper.showToast(msg, Url.context);
                                }

                                @Override
                                public Map getMap() {
                                    Map map = new HashMap();
                                    map.put("session_id", session_id);
                                    map.put("answerid", list.get(position).getId());
                                    return map;
                                }

                                @Override
                                public void onParseJsonBean(List<DianzanBean> beans, JSONObject jsonObject) {
                                    try {
                                        DianzanBean bean = new DianzanBean();
                                        bean.message = jsonObject.getString("message");
                                        bean.success = jsonObject.getString("success");
                                        beans.add(bean);
                                    } catch (Exception e) {

                                    }
                                }

                                @Override
                                public void onResult(boolean state, List<DianzanBean> beans) {
//                            if (state) {
//                                ToastHelper.showToast("采纳了", Url.context);
//                            } else {
//                                ToastHelper.showToast("请求失败", Url.context);
//                            }

                                    DianzanBean bean_bean = beans.get(0);
                                    if (bean_bean.success.equals("true")) {
                                        ToastHelper.showToast("采纳成功", Url.context);
                                        holder.caina.setVisibility(View.GONE);
                                        bean.setIssetbest(0);
                                        notifyDataSetChanged();
                                    }
                                }
                            });
                        } else {
                            holder.acept.setVisibility(View.GONE);
                            ToastHelper.showToast("您无采纳权限", Url.context);
                        }
                    } else {
                        ToastHelper.showToast("请先登录", wentixiangqing.this);
                    }
                }
            });

            return convertView;
        }

        /*点赞的实现方法*/
        public class DianZanListener implements View.OnClickListener {

            final ViewHolder holder;
            final WendaXianqingInfo.QuestionAnswerEntity bean;

            DianZanListener(ViewHolder holder, WendaXianqingInfo.QuestionAnswerEntity bean) {
                this.holder = holder;
                this.bean = bean;
            }


            @Override
            public void onClick(View v) {
                if (BaseFunction.isLogin()) {
                    if (!upFlag) {
                        upFlag = true;
                        holder.reply_count.setText(Integer.parseInt(bean.getSupport_count()) + 1 + "");
                        holder.dianzan.setBackgroundResource(R.drawable.iconfontdianzan);
//
                        RsenUrlUtil.execute(wentixiangqing.this, RsenUrlUtil.URL_SUPPORT_QUESTION_ANSWER, new RsenUrlUtil.OnJsonResultListener<DiscoverFragment.FXBean>() {
                            @Override
                            public void onNoNetwork(String msg) {
                                ToastHelper.showToast(msg, Url.context);
                            }

                            @Override
                            public Map getMap() {
                                Map map = new HashMap();
                                map.put("session_id", session_id);
                                map.put("answerid", bean.getId());
                                return map;

                            }

                            @Override
                            public void onParseJsonBean(List<DiscoverFragment.FXBean> beans, JSONObject jsonObject) {
                                String result = jsonObject.toString();
                                DiscoverFragment.FXBean discoverInfo = JSON.parseObject(result, DiscoverFragment.FXBean.class);
                                beans.add(discoverInfo);
                            }

                            @Override
                            public void onResult(boolean state, List beans) {

                            }
                        });
//                        Map map = new HashMap();
//                        map.put("session_id", session_id);
//                        map.put("answerid", bean.getId());
//
//                        RsenUrlUtil.executeGetWidthMap(wentixiangqing.this,RsenUrlUtil.URL_SUPPORT_QUESTION_ANSWER,map, new RsenUrlUtil.OnJsonResultListener<DianzanBean>() {
//                            @Override
//                            public void onNoNetwork(String msg) {
//                                ToastHelper.showToast(msg, Url.context);
//                            }
//                     @Override
//                            public void onParseJsonBean(List<DianzanBean> beans, JSONObject jsonObject) {
//                                try {
//                                    DianzanBean bean = new DianzanBean();
//                                    bean.message = jsonObject.getString("message");
//                                    bean.success = jsonObject.getString("success");
//                                    beans.add(bean);
//                                } catch (Exception e) {
//
//                                }
//                            }
//
//                            @Override
//                            public void onResult(boolean state, List<DianzanBean> beans) {
////                            if (state) {
////                                ToastHelper.showToast("采纳了", Url.context);
////                            } else {
////                                ToastHelper.showToast("请求失败", Url.context);
////                            }
//
//                                DianzanBean bean_bean = beans.get(0);
//                                if (bean_bean.success.equals("true")) {
//                                    ToastHelper.showToast("采纳成功", Url.context);
//                                    holder.caina.setVisibility(View.GONE);
//                                    bean.setIssetbest(0);
//                                    notifyDataSetChanged();
//                                }
//                            }
//                        });
                    } else {
                        ToastHelper.showToast("点赞失败，重复点赞", wentixiangqing.this);
                    }

                } else {
                    ToastHelper.showToast("请先登录", wentixiangqing.this);
                }
            }
        }

    }

    static class ViewHolder {
        ImageView avatar32;
        ImageView dianzan;
        ImageView caina;
        TextView nickname;
        TextView content;
        TextView creat_time;
        TextView reply_count;
        TextView acept;
    }

    public static class DianzanBean {
        public String success;
        public String message;

//        public

    }
}
