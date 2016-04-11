package com.thinksky.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.thinksky.info.WendaXianqingInfo;
import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.rsen.fragment.AnswerDialogFragment;
import com.thinksky.tox.R;
import com.thinksky.utils.LoadImg;
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
        btn_huida.setOnClickListener(this);
        back_menu.setOnClickListener(this);
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
                        flag = false;
                    }
                    money.setText(questionEntity.getScore());
                    creat_time.setText(questionEntity.getCreate_time());
                    content.setText(questionEntity.getDescription1());
                    nickname.setText(questionEntity.getUid());
                    huida.setText(questionEntity.getAnswer_num());

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
            case R.id.btn_huida:
                AnswerDialogFragment.show(getSupportFragmentManager(), new AnswerDialogFragment.ArgBean());
                break;

            case R.id.back_menu:
                finish();
                break;
            default:
                break;
        }
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
            holder.nickname.setText(bean.getUser().getNickname());
            holder.content.setText(bean.getContent());
            holder.creat_time.setText(bean.getCreate_time());
            holder.reply_count.setText(bean.getSupport_count());
            if (!(bean.getIs_supported().equals("0"))) {//已点赞
                holder.dianzan.setBackgroundResource(R.drawable.iconfontdianzan);
                upFlag = true;
            } else {
                holder.dianzan.setBackgroundResource(R.drawable.iconfontweidianzan);
                upFlag = false;
            }

//            if (bean.isDianZan) {
//                holder.dianzan.setImageResource(R.drawable.iconfontdianzan);
//            } else {
//                holder.dianzan.setImageResource(R.drawable.iconfontweidianzan);
//            }
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
//                @Override
//                public void onClick(View v) {
//                    if (BaseFunction.isLogin()) {
//                        if (bean.getIs_supported().equals("0")) {
//                            holder.reply_count.setText(Integer.parseInt(bean.getSupport_count()) + 1 + "");
//                            holder.dianzan.setBackgroundResource(R.drawable.iconfontdianzan);
////                            mDetail_Up_Img.setImageBitmap(BitmapUtiles.drawableTobitmap(R.drawable.heart, wentixiangqing.this));
////                            support();
//                            RsenUrlUtil.execute(RsenUrlUtil.URL_SUPPORT_QUESTION_ANSWER, new RsenUrlUtil.OnJsonResultListener<DianzanBean>() {
//                                @Override
//                                public void onNoNetwork(String msg) {
//                                    ToastHelper.showToast(msg, Url.context);
//                                }
//
//                                @Override
//                                public Map getMap() {
//                                    Map map = new HashMap();
//                                    map.put("session_id", session_id);
//                                    map.put("answerid", list.get(position).getId());
//                                    return map;
//                                }
//
//                                @Override
//                                public void onParseJsonBean(List<DianzanBean> beans, JSONObject jsonObject) {
//                                    try {
//                                        DianzanBean bean = new DianzanBean();
//                                        bean.message = jsonObject.getString("message");
//                                        bean.success = jsonObject.getString("success");
//                                        beans.add(bean);
//                                    } catch (Exception e) {
//
//                                    }
//                                }
//
//                                @Override
//                                public void onResult(boolean state, List<DianzanBean> beans) {
////                            if (state) {
////                                ToastHelper.showToast("采纳了", Url.context);
////                            } else {
////                                ToastHelper.showToast("请求失败", Url.context);
////                            }
//
//                                    DianzanBean bean_bean = beans.get(0);
//                                    if (bean_bean.success.equals("true")) {
//                                        ToastHelper.showToast("点赞成功", Url.context);
//                                        holder.caina.setVisibility(View.GONE);
//                                        bean.setIssetbest(0);
//                                        notifyDataSetChanged();
//                                    }
//                                }
//                            });
//                        } else {
//                            holder.acept.setVisibility(View.GONE);
//                            ToastHelper.showToast("点赞失败", Url.context);
//                        }
//                    } else {
//                        ToastHelper.showToast("请先登录", wentixiangqing.this);
//                    }
//                }
//                {
//                    if (BaseFunction.isLogin()) {
//                        sp = getSharedPreferences("userInfo", 0);
//                        Log.e(sp.getString("avatar", "空空空"), "");
//                        session_id = sp.getString("session_id", Url.SESSIONID);
//                        if ((bean.getIs_supported().equals("0"))) {
//
//                            holder.reply_count.setText(Integer.parseInt(bean.getSupport_count()) + 1 + "");
//                            holder.dianzan.setBackgroundResource(R.drawable.iconfontdianzan);
////                            mDetail_Up_Img.setImageBitmap(BitmapUtiles.drawableTobitmap(R.drawable.heart, wentixiangqing.this));
////                            support();
//                            RsenUrlUtil.execute(RsenUrlUtil.URL_SUPPORT_QUESTION_ANSWER, new RsenUrlUtil.OnJsonResultListener<DianzanBean>() {
//                                @Override
//                                public void onNoNetwork(String msg) {
//                                    ToastHelper.showToast(msg, Url.context);
//                                }
//
//                                @Override
//                                public Map getMap() {
//                                    Map map = new HashMap();
//                                    map.put("session_id", session_id);
//                                    map.put("answerid", list.get(position).getId());
//                                    return map;
//                                }
//
//                                @Override
//                                public void onParseJsonBean(List<DianzanBean> beans, JSONObject jsonObject) {
//                                    try {
//                                        DianzanBean bean = new DianzanBean();
//                                        bean.message = jsonObject.getString("message");
//                                        bean.success = jsonObject.getString("success");
//                                        beans.add(bean);
//                                    } catch (Exception e) {
//
//                                    }
//                                }
//
//                                @Override
//                                public void onResult(boolean state, List<DianzanBean> beans) {
////                                    if (state) {
////                                        ToastHelper.showToast("采纳了", Url.context);
////                                    } else {
////                                        ToastHelper.showToast("请求失败", Url.context);
////                                    }
//
//                                    DianzanBean bean_bean = beans.get(0);
//                                    if (bean_bean.success.equals("true")) {
//                                        ToastHelper.showToast("点赞成功", Url.context);
//
//                                        bean.setIssetbest(0);
//                                        notifyDataSetChanged();
//                                    }
//                                }
//                            });
//                        } else {
//                            holder.acept.setVisibility(View.GONE);
//                            ToastHelper.showToast("点赞失败，重复点赞", Url.context);
//                        }
//                    } else {
//                        ToastHelper.showToast("请先登录", wentixiangqing.this);
//                    }
//                }
//            });
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
//                            mDetail_Up_Img.setImageBitmap(BitmapUtiles.drawableTobitmap(R.drawable.heart, wentixiangqing.this));
//                            support();
                        RsenUrlUtil.execute(RsenUrlUtil.URL_SUPPORT_QUESTION_ANSWER, new RsenUrlUtil.OnJsonResultListener<DianzanBean>() {
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
                            public void onParseJsonBean(List<DianzanBean> beans, JSONObject jsonObject) {
                                try {
                                    DianzanBean bean2 = new DianzanBean();
                                    bean2.message = jsonObject.getString("message");
                                    bean2.success = jsonObject.getString("success");
                                    beans.add(bean2);
                                } catch (Exception e) {

                                }
                            }

                            @Override
                            public void onResult(boolean state, List<DianzanBean> beans) {


                                DianzanBean bean1 = beans.get(0);
                                if (bean1.success.equals("true")) {
                                    ToastHelper.showToast("点赞成功", Url.context);
                                    holder.dianzan.setImageResource(R.drawable.iconfontdianzan);
                                    bean.setIs_supported("0");
                                    notifyDataSetChanged();
                                }
//                                if (state) {
//                                    ToastHelper.showToast("点赞了", Url.context);
//                                } else {
//                                    ToastHelper.showToast("请求失败", Url.context);
//                                }
                            }
                        });

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
