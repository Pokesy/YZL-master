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
import com.thinksky.tox.R;
import com.thinksky.tox.SendQuestionActivity;
import com.tox.ToastHelper;
import com.tox.Url;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by jiao on 2016/3/13.
 */
public class QuestionSelectActivity extends BaseBActivity {

    ListView mListView;
    WendaListAdapter mAdapter;
    private ImageView back_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_adapter_select);
        mListView = (ListView) findViewById(R.id.listView);

        initData();

    }

    private void initData() {

        String url = RsenUrlUtil.URL_GET_QUESTIO_NCATEGORY;


        RsenUrlUtil.execute(QuestionSelectActivity.this, url, new RsenUrlUtil.OnNetHttpResultListener() {
            @Override
            public void onNoNetwork(String msg) {
                ToastHelper.showToast(msg, Url.context);
            }


            @Override
            public void onResult(boolean state, String result, JSONObject jsonObject) {
                if (state) {
                    QuestionSelectBean wendaBean = JSON.parseObject(result, QuestionSelectBean.class);
                    mListView.setAdapter(new WendaListAdapter(QuestionSelectActivity.this, wendaBean.getList()));
                }
            }
        });
    }


    public class WendaListAdapter extends BaseAdapter {
        private List<QuestionSelectBean.ListEntity> datas;
        private Context context;

        public WendaListAdapter(Context context, List<QuestionSelectBean.ListEntity> datas) {
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
                convertView = LayoutInflater.from(context).inflate(R.layout.activity_select_question_adapter, parent, false);
                viewHolder = new RViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (RViewHolder) convertView.getTag();
            }

            final QuestionSelectBean.ListEntity listEntity = datas.get(position);

            /*其他控件信息，自己添加 id， 然后从 listEntity对象中获取数据，填充就行了*/
            ((TextView) viewHolder.itemView.findViewById(R.id.title)).setText(listEntity.getTitle());


            viewHolder.itemView.findViewById(R.id.item_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();

                    bundle.putString("category_id", listEntity.getId());
                    bundle.putString("title", listEntity.getTitle());
                    Intent intent = new Intent(context, SendQuestionActivity.class);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            return convertView;
        }
    }

    public static class QuestionSelectBean {


        /**
         * error_code : 0
         * list : [{"create_time":"","id":"1","pid":"0","sort":"1","status":"1","title":"龙鱼","update_time":""},{"create_time":"","id":"4","pid":"0","sort":"0","status":"1","title":"魟鱼","update_time":""}]
         * message : 返回成功
         * success : true
         */

        private int error_code;
        private String message;
        private boolean success;
        /**
         * create_time :
         * id : 1
         * pid : 0
         * sort : 1
         * status : 1
         * title : 龙鱼
         * update_time :
         */

        private List<ListEntity> list;

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

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public static class ListEntity {
            private String create_time;
            private String id;
            private String pid;
            private String sort;
            private String status;
            private String title;
            private String update_time;

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }
        }
    }
}
