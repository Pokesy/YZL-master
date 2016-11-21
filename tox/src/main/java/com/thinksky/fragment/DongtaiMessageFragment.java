package com.thinksky.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.tox.R;
import com.tox.BaseApi;
import com.tox.ToastHelper;
import com.tox.Url;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DongtaiMessageFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private String mParam1;
    private String session_id;
    private String userUid;
    private BaseApi baseApi;
    private int module;
    private ListView listView;
    private TextView text;

    public static DongtaiMessageFragment newInstance(String param1) {
        DongtaiMessageFragment fragment = new DongtaiMessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public DongtaiMessageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);

            if (mParam1.equals("label1")) {
                module = 4;
            } else if (mParam1.equals("label2")) {
                module = 16;
            } else if (mParam1.equals("label3")) {
                module = 23;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dongtai_message, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        text = (TextView) view.findViewById(R.id.text);
        baseApi = new BaseApi();
        session_id = baseApi.getSeesionId();
        init();
        return view;
    }

    private void init() {

        RsenUrlUtil.execute(getActivity(), RsenUrlUtil.URL_XX,new RsenUrlUtil.OnJsonResultListener<MsBean>() {

            public void onNoNetwork(String msg) {
                ToastHelper.showToast(msg, Url.context);
            }

            @Override
            public Map getMap() {
                Map map = new HashMap();

                map.put("module", module + "");

                map.put("session_id", session_id);
                return map;
            }

            public void onResult(boolean state, String result, JSONObject jsonObject) {
                if (state) {

                    MsBean bean = JSON.parseObject(result, MsBean.class);
                    if (bean.getList()!=null) {
                        listView.setAdapter(new DraftDailyAdapter(getActivity(), bean.getList()));
                    } else {

                        text.setVisibility(View.VISIBLE);

                    }
                }
            }

            @Override
            public void onParseJsonBean(List<MsBean> beans, JSONObject jsonObject) {

            }

            @Override
            public void onResult(boolean state, List<MsBean> beans) {

            }
        });
    }

    public class DraftDailyAdapter extends BaseAdapter {

        public List<MsBean.ListEntity> list;
        private Context context;
        LayoutInflater inflater;


        public DraftDailyAdapter(Context context, List<MsBean.ListEntity> list) {
            super();
            this.list = list;
            this.context = context;

            inflater = LayoutInflater.from(this.context);

        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public Object getItem(int location) {
            return list.get(location);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder = new ViewHolder();
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.message_item, null);
                holder.title = (TextView) convertView.findViewById(R.id.title);
                holder.time = (TextView) convertView.findViewById(R.id.time);
                holder.content = (TextView) convertView.findViewById(R.id.content);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();


            }

            holder.title.setText(list.get(position).content.getTitle());

            holder.content.setText(list.get(position).content.getContent());
            holder.time.setText(list.get(position).getCreate_time());
//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    Toast.makeText(inflater.getContext(), "" + position, Toast.LENGTH_SHORT).show();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("id", list.get(position).getId());
//                    Intent intent = new Intent(mContext, BaikeItemActivity.class);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                }
//            });
            return convertView;
        }

        public final class ViewHolder {
            public TextView title;
            public TextView time;
            public TextView content;

        }
    }

    public static class MsBean {

        /**
         * success : true
         * error_code : 0
         * message : 返回成功
         * list : [{"id":"194","content_id":"245","from_uid":"106","to_uid":"1","create_time":"04月15日 16:02","is_read":"1","last_toast":"1460707354","status":"1","module":"4","content":{"id":"245","from_id":"106","title":"评论消息","content":"评论内容：Sdfsdfsd","url":"Weibo/Index/weiboDetail","args":"{\"id\":80}","type":"1","create_time":"1460707353","status":"1"}},{"id":"226","content_id":"261","from_uid":"121","to_uid":"1","create_time":"04月20日 15:13","is_read":"0","last_toast":"0","status":"1","module":"4","content":{"id":"261","from_id":"121","title":"红小豆转发了您的动态！","content":"转发提醒","url":"Weibo/Index/weiboDetail","args":"{\"id\":null}","type":"1","create_time":"1461136435","status":"1"}}]
         */

        private boolean success;
        private int error_code;
        private String message;
        /**
         * id : 194
         * content_id : 245
         * from_uid : 106
         * to_uid : 1
         * create_time : 04月15日 16:02
         * is_read : 1
         * last_toast : 1460707354
         * status : 1
         * module : 4
         * content : {"id":"245","from_id":"106","title":"评论消息","content":"评论内容：Sdfsdfsd","url":"Weibo/Index/weiboDetail","args":"{\"id\":80}","type":"1","create_time":"1460707353","status":"1"}
         */

        private List<ListEntity> list;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public int getError_code() {
            return error_code;
        }

        public void setError_code(int error_code) {
            this.error_code = error_code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public static class ListEntity {
            private String id;
            private String content_id;
            private String from_uid;
            private String to_uid;
            private String create_time;
            private String is_read;
            private String last_toast;
            private String status;
            private String module;
            /**
             * id : 245
             * from_id : 106
             * title : 评论消息
             * content : 评论内容：Sdfsdfsd
             * url : Weibo/Index/weiboDetail
             * args : {"id":80}
             * type : 1
             * create_time : 1460707353
             * status : 1
             */

            private ContentEntity content;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getContent_id() {
                return content_id;
            }

            public void setContent_id(String content_id) {
                this.content_id = content_id;
            }

            public String getFrom_uid() {
                return from_uid;
            }

            public void setFrom_uid(String from_uid) {
                this.from_uid = from_uid;
            }

            public String getTo_uid() {
                return to_uid;
            }

            public void setTo_uid(String to_uid) {
                this.to_uid = to_uid;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getIs_read() {
                return is_read;
            }

            public void setIs_read(String is_read) {
                this.is_read = is_read;
            }

            public String getLast_toast() {
                return last_toast;
            }

            public void setLast_toast(String last_toast) {
                this.last_toast = last_toast;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getModule() {
                return module;
            }

            public void setModule(String module) {
                this.module = module;
            }

            public ContentEntity getContent() {
                return content;
            }

            public void setContent(ContentEntity content) {
                this.content = content;
            }

            public static class ContentEntity {
                private String id;
                private String from_id;
                private String title;
                private String content;
                private String url;
                private String args;
                private String type;
                private String create_time;
                private String status;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getFrom_id() {
                    return from_id;
                }

                public void setFrom_id(String from_id) {
                    this.from_id = from_id;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getArgs() {
                    return args;
                }

                public void setArgs(String args) {
                    this.args = args;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getCreate_time() {
                    return create_time;
                }

                public void setCreate_time(String create_time) {
                    this.create_time = create_time;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }
            }
        }
    }
}
