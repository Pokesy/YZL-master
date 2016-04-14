package com.thinksky.rsen;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.thinksky.net.IsNet1;
import com.thinksky.net.ThreadPoolUtils;
import com.thinksky.thread.HttpPostThread;
import com.thinksky.tox.BuildConfig;
import com.tox.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by jiao on 2016/2/17.
 */
public class RsenUrlUtil {

    public static String URL_BASE = BuildConfig.URL_BASE;
    public static String URL_SUB_BASE = "/opensns/api.php?s=";
    //发现
    public static String URL_FX = URL_BASE + URL_SUB_BASE + "user/getUserList";
    //百科服务器地址
    public static String URL_BK = URL_BASE + URL_SUB_BASE + "Paper/getCategory";
    //百科二级菜单
    public static String URL_BKT = URL_BASE + URL_SUB_BASE + "Paper/getPaperAll&category=";
    //专家
    public static String URL_ZJ = URL_BASE + URL_SUB_BASE + "Issue/getIssList&session_id=&issue_id=";
    //专家详情
    public static String URL_ZJXQ = URL_BASE + URL_SUB_BASE + "Issue/getIssueDetail&id=";
    //问答
    public static String URL_WD = URL_BASE + URL_SUB_BASE + "Question/getQuestionList";
    //论坛
    public static String URL_LT = URL_BASE + URL_SUB_BASE + "forum/getForumModules";
    //热门话题
    public static String URL_REMEN_HUATI = URL_BASE + URL_SUB_BASE + "group/getHotPostAll";

    public static String SENDDISCOVER = URL_BASE + URL_SUB_BASE + "user/setProfile";
    //小组精选
    public static String URL_XIAOZU_JINGXUAN = URL_BASE + URL_SUB_BASE + "group/getGroupChoice";
    //我的小组
    public static String URL_WODE = URL_BASE + URL_SUB_BASE + "group/getWeGroupAll";
    //小组详情
    public static String URL_XIAOZU_XIANGQING = URL_BASE + URL_SUB_BASE + "group/getGroupDetail&group_id=";
    //小组详情帖子
    public static String URL_XIAOZU_XIANGQINGTZ = URL_BASE + URL_SUB_BASE + "group/getPostAll&group_id=";

    //问题详情
    public static String URL_WENTI_XIANGQING = URL_BASE + URL_SUB_BASE + "Question/getQuestionDetail&page=&count=&questionid=";
    //热门问答
    public static String URL_HOT_WD = URL_BASE + URL_SUB_BASE + "Question/getHotQuestionList";
    //最高悬赏问答
    public static String URL_MON_WD = URL_BASE + URL_SUB_BASE + "Question/getMonQuestionList";
    //我的问答列表
    public static String URL_MY_WD = URL_BASE + URL_SUB_BASE + "Question/getMyQuestionList";
    //已解决列表
    public static String URL_SOLUTE_WD = URL_BASE + URL_SUB_BASE + "Question/getSoluteQuestionList";
    //问题提问
    public static String URL_SEND_QUESTION = URL_BASE + URL_SUB_BASE + "/question/sendQuestion";
    //问题回答
    public static String URL_SEND_QUESTION_ANSWER = URL_BASE + URL_SUB_BASE + "Question/sendQuestionAnswer&session_id=&question_id=&content=";
    //回答点赞
    public static String URL_SUPPORT_QUESTION_ANSWER = URL_BASE + URL_SUB_BASE + "/Question/supportQuestionAnswer";
    //设置最佳答案
    public static String URL_SET_BEST_ANSWER = URL_BASE + URL_SUB_BASE + "/Question/setBestAnswer&answerid=&score=";
    //问答分类
    public static String URL_GET_QUESTIO_NCATEGORY = URL_BASE + URL_SUB_BASE + "question/getQuestionCategory";

    //获取所有资讯信息
    public static String NEWSALL = URL_BASE + URL_SUB_BASE + "/news/getNewsAll";

    public static void execute(final OnHttpResultListener listener) {
        execute(URL_BK, listener);
    }


    public static void execute(final String url, final OnHttpResultListener listener) {
        execute(Url.context, url, listener);
    }

    public static void executeGetWidthMap(Context context, final String url,
                                          final Map<String, String> map,
                                          final OnHttpResultListener listener) {
        StringBuilder paramsBuilder = new StringBuilder();
        if (map != null) {
            Set keys = map.entrySet();
            Iterator it = keys.iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String name = (String) entry.getKey();
                String value = (String) entry.getValue();
                paramsBuilder.append("&");
                paramsBuilder.append(name);
                paramsBuilder.append("=");
                paramsBuilder.append(value);
            }
            if (url.endsWith("&") && paramsBuilder.length() > 1) {
                paramsBuilder.delete(0, 1);
            }
        }

        execute(context, url + paramsBuilder.toString(), listener);
    }

    public static void execute(Context context, final String url, final OnHttpResultListener listener) {
        if (IsNet1.IsConnect(context)) {
            ThreadPoolUtils.execute(new HttpPostThread(new Handler() {
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    String data = (String) msg.obj;

                    if (msg.what == 0) {
                        try {
                            Log.e(RsenUrlUtil.class.getSimpleName(), "url-->" + url);
                            Log.e(RsenUrlUtil.class.getSimpleName(), "data-->" + data);
                            JSONObject jsonObject = new JSONObject(data);
                            if (listener != null) {
                                listener.onResult(true, data, jsonObject);
                            }
                        } catch (JSONException E) {
                            if (listener != null) {
                                listener.onResult(false, data, null);
                            }
                        }
                    } else {
                        if (listener != null && listener instanceof OnNetHttpResultListener) {
                            ((OnNetHttpResultListener) listener).onNoNetwork(msg.what + "-->" + data);
                        }
                    }
                }
            }, url) {
                @Override
                public Runnable setMapValue() {
                    if (listener != null && listener instanceof OnMapListener) {
                        this.mapValue = ((OnMapListener) listener).getMap();
                    }
                    return this;
                }
            }.setMapValue());
        } else {
            if (listener != null && listener instanceof OnNetHttpResultListener) {
                ((OnNetHttpResultListener) listener).onNoNetwork("无网络连接,请稍后重试");
            }
        }
    }

    public interface OnHttpResultListener {
        /**
         * state: 请求是否成功
         * jsonObject: 请求成功时，返回的json对象数据
         */
        void onResult(boolean state, String result, JSONObject jsonObject);
    }

    public interface OnNetHttpResultListener extends OnHttpResultListener {
        void onNoNetwork(String msg);
    }

    public interface OnMapListener extends OnNetHttpResultListener {
        Map getMap();
    }

    public abstract static class OnJsonResultListener<T> implements OnMapListener {

        private ArrayList<T> beans;

        public Map getMap() {
            return null;
        }

        @Override
        public void onResult(boolean state, String result, JSONObject jsonObject) {
            if (state) {
                try {
                    parseJsonObject(jsonObject);
                    onResult(true, beans);
                } catch (Exception e) {
                    onResult(false, null);
                }
            } else {
                onResult(false, null);
            }
        }

        public ArrayList<T> parseJsonObject(JSONObject object) {
            beans = new ArrayList<>();
            if (object != null) {
                try {
                    JSONArray array;
                    try {
                        array = object.getJSONArray("list");
                    } catch (JSONException e) {
                        array = new JSONArray();
                        try {
                            array.put(object.getJSONObject("list"));
                        } catch (JSONException e1) {
                            onParseJsonBean(beans, object);
                            return beans;
                        }
                    }
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        onParseJsonBean(beans, jsonObject);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return beans;
        }

        public abstract void onParseJsonBean(List<T> beans, JSONObject jsonObject);

        public abstract void onResult(boolean state, List<T> beans);
    }
}