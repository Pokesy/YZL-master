package com.thinksky.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.tox.R;
import com.tox.ToastHelper;
import com.tox.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiao on 2016/2/18.
 */
public class BaikeTwoFragment extends Fragment {
    View rootView;
    ListView myListView;

    private static final String ARG_PARAM1 = "param1";


    public static BaikeTwoFragment newInstance(String param1) {
        BaikeTwoFragment fragment = new BaikeTwoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_baike_two, container, false);
        myListView = (ListView) rootView.findViewById(R.id.myListView);
        final String id = getArguments().getString("key");

        RsenUrlUtil.execute(RsenUrlUtil.URL_BKT, new RsenUrlUtil.OnJsonResultListener<BktBean>() {


            public void onNoNetwork(String msg) {
                ToastHelper.showToast(msg, Url.context);
            }

            @Override
            public Map getMap() {
                Map map = new HashMap();
                map.put("category", id);
                return map;

            }

//            @Override
//            public void onResult(boolean state, String result, JSONObject jsonObject) {
//                if (state) {
//                    try {
//                        ArrayList<BktBean> beans = parseJson(jsonObject.getJSONArray("list"));
//                        myListView.setAdapter(new BKTAdapter(getActivity(), beans));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }


            @Override
            public void onParseJsonBean(List<BktBean> beans, JSONObject jsonObject) {
                try {

                    BktBean bean = new BktBean();
                    bean.id = jsonObject.getString("id");
                    bean.sort = jsonObject.getString("sort");
                    bean.status = jsonObject.getString("status");
                    bean.title = jsonObject.getString("title");
                    bean.category = jsonObject.getString("category");
//                            jsonObject.getString("pid")

                    beans.add(bean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onResult(boolean state, List<BktBean> beans) {
                if (state) {
                    myListView.setAdapter(new BKTAdapter(getActivity(), (ArrayList<BktBean>) beans));
                } else {
                    ToastHelper.showToast("请求失败", Url.context);
                }

            }


        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    public class BKTAdapter extends BaseAdapter {
        ArrayList<BktBean> beans = new ArrayList<>();
        Context context;

        public BKTAdapter(Context context, ArrayList<BktBean> beans) {
            this.context = context;
            this.beans = beans;
        }

        @Override
        public int getCount() {
            return beans.size();
        }

        @Override
        public Object getItem(int position) {
            return beans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.fragment_baike_adapter_item, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            BktBean bean = beans.get(position);
            ((TextView) viewHolder.itemView.findViewById(R.id.title_bkt)).setText(bean.title);

            return convertView;
        }
    }

    public static class ViewHolder {
        public View itemView;

        public ViewHolder(View itemView) {
            this.itemView = itemView;
        }
    }

    public static ArrayList<BktBean> parseJson(JSONArray array) {
        ArrayList<BktBean> beans = new ArrayList<>();
        if (array != null) {
            for (int i = 0; i < array.length(); i++) {
                try {
                    JSONObject json = array.getJSONObject(i);
                    BktBean bean = new BktBean();
                    bean.id = json.getString("id");
                    bean.sort = json.getString("sort");
                    bean.status = json.getString("status");
                    bean.title = json.getString("title");
                    bean.category = json.getString("category");
//                            json.getString("pid")

                    beans.add(bean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return beans;
    }

    public static class BktBean {
        /**
         * "category": "0",
         * "content": "<p>sdfad</p>",
         * "cover": "",
         * "create_time": "02月14日 10:04",
         * "dead_line": "2016-02-18 15:29",
         * "id": "6",
         * "sort": "0",
         * "status": "1",
         * "title": "1111",
         * <p/>
         * "uid": "1",
         */
        public String id;
        public String sort;
        public String status;
        public String title;
        public String category;
    }
}
