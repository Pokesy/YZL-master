package com.thinksky.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.thinksky.tox.SetUserInfoActivity;
import com.tox.ToastHelper;
import com.tox.Url;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiao on 2016/2/18.
 */
public class UserPlaceFragment extends Fragment {
    View rootView;
    ListView myListView;
    private String uname;
    private String mid;
    private static final String ARG_PARAM1 = "param1";

    private String ids;

    public static UserPlaceFragment newInstance(String param1) {
        UserPlaceFragment fragment = new UserPlaceFragment();
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
        ids = getArguments().getString("key");
        initdata();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    private void initdata() {
        RsenUrlUtil.execute(getActivity(), RsenUrlUtil.URL_DQ, new RsenUrlUtil.OnJsonResultListener() {
            @Override
            public void onNoNetwork(String msg) {
                ToastHelper.showToast(msg, Url.context);
            }

            @Override
            public Map getMap() {
                Map map = new HashMap();
                map.put("count", "100");
                return map;

            }

            @Override
            public void onResult(boolean state, String result, JSONObject jsonObject) {
                if (state) {
                    final USBean usbean = JSON.parseObject(result, USBean.class);
                    for (int i = 0; i < usbean.getList().size(); i++) {

                        if (usbean.getList().get(i).getCity().get(0).getUpid().equals(ids)) {

                            uname=usbean.getList().get(i).getName();
                            mid=usbean.getList().get(i).getId();
                            myListView.setAdapter(new BKTAdapter(getActivity(), usbean.getList().get(i).getCity()));

                        }
                    }
                }


            }

            @Override
            public void onParseJsonBean(List beans, JSONObject jsonObject) {

            }

            @Override
            public void onResult(boolean state, List beans) {

            }
        });
    }

    public class BKTAdapter extends BaseAdapter {
        List<USBean.ListEntity.CityEntity> beans = new ArrayList<>();
        Context context;

        public BKTAdapter(Context context, List<USBean.ListEntity.CityEntity> beans) {
            this.context = context;
            this.beans = beans;
        }

        @Override
        public int getCount() {
            if (ids.equals("110000") || ids.equals("120000") || ids.equals("500000") || ids.equals("310000")) {
                return 1;
            } else {
                return beans.size();
            }
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
            final ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.fragment_baike_adapter_sub_item, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final USBean.ListEntity.CityEntity bean = beans.get(position);
            if (ids.equals("110000")) {
                ((TextView) viewHolder.itemView.findViewById(R.id.title_bkt)).setText("北京市");
            } else if (ids.equals("120000")) {
                ((TextView) viewHolder.itemView.findViewById(R.id.title_bkt)).setText("天津市");
            } else if (ids.equals("500000")) {
                ((TextView) viewHolder.itemView.findViewById(R.id.title_bkt)).setText("重庆市");
            } else if (ids.equals("310000")) {
                ((TextView) viewHolder.itemView.findViewById(R.id.title_bkt)).setText("上海市");
            } else {
                ((TextView) viewHolder.itemView.findViewById(R.id.title_bkt)).setText(bean.getName());
            }

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("province",mid);
                    bundle.putString("city",bean.getId());
                    bundle.putString("name1",uname);
                    bundle.putString("name2",((TextView) viewHolder.itemView.findViewById(R.id.title_bkt)).getText().toString());
                    Intent intent = new Intent(context, SetUserInfoActivity.class);
                    intent.putExtras(bundle);
                    getActivity().setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                }
            });
            return convertView;
        }
    }

    public static class ViewHolder {
        public View itemView;

        public ViewHolder(View itemView) {
            this.itemView = itemView;
        }
    }

    public static class USBean {

        /**
         * error_code : 0
         * list : [{"city":[{"id":"110100","level":"2","name":"北京市","upid":"110000"},{"id":"110200","level":"2","name":"县","upid":"110000"}],"id":"110000","level":"1","name":"北京市","upid":"0"},{"city":[{"id":"120100","level":"2","name":"市辖区","upid":"120000"},{"id":"120200","level":"2","name":"县","upid":"120000"}],"id":"120000","level":"1","name":"天津市","upid":"0"},{"city":[{"id":"130100","level":"2","name":"石家庄市","upid":"130000"},{"id":"130200","level":"2","name":"唐山市","upid":"130000"},{"id":"130300","level":"2","name":"秦皇岛市","upid":"130000"},{"id":"130400","level":"2","name":"邯郸市","upid":"130000"},{"id":"130500","level":"2","name":"邢台市","upid":"130000"},{"id":"130600","level":"2","name":"保定市","upid":"130000"},{"id":"130700","level":"2","name":"张家口市","upid":"130000"},{"id":"130800","level":"2","name":"承德市","upid":"130000"},{"id":"130900","level":"2","name":"沧州市","upid":"130000"},{"id":"131000","level":"2","name":"廊坊市","upid":"130000"},{"id":"131100","level":"2","name":"衡水市","upid":"130000"}],"id":"130000","level":"1","name":"河北省","upid":"0"},{"city":[{"id":"140100","level":"2","name":"太原市","upid":"140000"},{"id":"140200","level":"2","name":"大同市","upid":"140000"},{"id":"140300","level":"2","name":"阳泉市","upid":"140000"},{"id":"140400","level":"2","name":"长治市","upid":"140000"},{"id":"140500","level":"2","name":"晋城市","upid":"140000"},{"id":"140600","level":"2","name":"朔州市","upid":"140000"},{"id":"140700","level":"2","name":"晋中市","upid":"140000"},{"id":"140800","level":"2","name":"运城市","upid":"140000"},{"id":"140900","level":"2","name":"忻州市","upid":"140000"},{"id":"141000","level":"2","name":"临汾市","upid":"140000"},{"id":"141100","level":"2","name":"吕梁市","upid":"140000"}],"id":"140000","level":"1","name":"山西省","upid":"0"},{"city":[{"id":"150100","level":"2","name":"呼和浩特市","upid":"150000"},{"id":"150200","level":"2","name":"包头市","upid":"150000"},{"id":"150300","level":"2","name":"乌海市","upid":"150000"},{"id":"150400","level":"2","name":"赤峰市","upid":"150000"},{"id":"150500","level":"2","name":"通辽市","upid":"150000"},{"id":"150600","level":"2","name":"鄂尔多斯市","upid":"150000"},{"id":"150700","level":"2","name":"呼伦贝尔市","upid":"150000"},{"id":"150800","level":"2","name":"巴彦淖尔市","upid":"150000"},{"id":"150900","level":"2","name":"乌兰察布市","upid":"150000"},{"id":"152200","level":"2","name":"兴安盟","upid":"150000"},{"id":"152500","level":"2","name":"锡林郭勒盟","upid":"150000"},{"id":"152900","level":"2","name":"阿拉善盟","upid":"150000"}],"id":"150000","level":"1","name":"内蒙古","upid":"0"},{"city":[{"id":"210100","level":"2","name":"沈阳市","upid":"210000"},{"id":"210200","level":"2","name":"大连市","upid":"210000"},{"id":"210300","level":"2","name":"鞍山市","upid":"210000"},{"id":"210400","level":"2","name":"抚顺市","upid":"210000"},{"id":"210500","level":"2","name":"本溪市","upid":"210000"},{"id":"210600","level":"2","name":"丹东市","upid":"210000"},{"id":"210700","level":"2","name":"锦州市","upid":"210000"},{"id":"210800","level":"2","name":"营口市","upid":"210000"},{"id":"210900","level":"2","name":"阜新市","upid":"210000"},{"id":"211000","level":"2","name":"辽阳市","upid":"210000"},{"id":"211100","level":"2","name":"盘锦市","upid":"210000"},{"id":"211200","level":"2","name":"铁岭市","upid":"210000"},{"id":"211300","level":"2","name":"朝阳市","upid":"210000"},{"id":"211400","level":"2","name":"葫芦岛市","upid":"210000"}],"id":"210000","level":"1","name":"辽宁省","upid":"0"},{"city":[{"id":"220100","level":"2","name":"长春市","upid":"220000"},{"id":"220200","level":"2","name":"吉林市","upid":"220000"},{"id":"220300","level":"2","name":"四平市","upid":"220000"},{"id":"220400","level":"2","name":"辽源市","upid":"220000"},{"id":"220500","level":"2","name":"通化市","upid":"220000"},{"id":"220600","level":"2","name":"白山市","upid":"220000"},{"id":"220700","level":"2","name":"松原市","upid":"220000"},{"id":"220800","level":"2","name":"白城市","upid":"220000"},{"id":"222400","level":"2","name":"延边朝鲜族自治州","upid":"220000"}],"id":"220000","level":"1","name":"吉林省","upid":"0"},{"city":[{"id":"230100","level":"2","name":"哈尔滨市","upid":"230000"},{"id":"230200","level":"2","name":"齐齐哈尔市","upid":"230000"},{"id":"230300","level":"2","name":"鸡西市","upid":"230000"},{"id":"230400","level":"2","name":"鹤岗市","upid":"230000"},{"id":"230500","level":"2","name":"双鸭山市","upid":"230000"},{"id":"230600","level":"2","name":"大庆市","upid":"230000"},{"id":"230700","level":"2","name":"伊春市","upid":"230000"},{"id":"230800","level":"2","name":"佳木斯市","upid":"230000"},{"id":"230900","level":"2","name":"七台河市","upid":"230000"},{"id":"231000","level":"2","name":"牡丹江市","upid":"230000"},{"id":"231100","level":"2","name":"黑河市","upid":"230000"},{"id":"231200","level":"2","name":"绥化市","upid":"230000"},{"id":"232700","level":"2","name":"大兴安岭地区","upid":"230000"}],"id":"230000","level":"1","name":"黑龙江","upid":"0"},{"city":[{"id":"310100","level":"2","name":"市辖区","upid":"310000"},{"id":"310200","level":"2","name":"县","upid":"310000"}],"id":"310000","level":"1","name":"上海市","upid":"0"},{"city":[{"id":"320100","level":"2","name":"南京市","upid":"320000"},{"id":"320200","level":"2","name":"无锡市","upid":"320000"},{"id":"320300","level":"2","name":"徐州市","upid":"320000"},{"id":"320400","level":"2","name":"常州市","upid":"320000"},{"id":"320500","level":"2","name":"苏州市","upid":"320000"},{"id":"320600","level":"2","name":"南通市","upid":"320000"},{"id":"320700","level":"2","name":"连云港市","upid":"320000"},{"id":"320800","level":"2","name":"淮安市","upid":"320000"},{"id":"320900","level":"2","name":"盐城市","upid":"320000"},{"id":"321000","level":"2","name":"扬州市","upid":"320000"},{"id":"321100","level":"2","name":"镇江市","upid":"320000"},{"id":"321200","level":"2","name":"泰州市","upid":"320000"},{"id":"321300","level":"2","name":"宿迁市","upid":"320000"}],"id":"320000","level":"1","name":"江苏省","upid":"0"},{"city":[{"id":"330100","level":"2","name":"杭州市","upid":"330000"},{"id":"330200","level":"2","name":"宁波市","upid":"330000"},{"id":"330300","level":"2","name":"温州市","upid":"330000"},{"id":"330400","level":"2","name":"嘉兴市","upid":"330000"},{"id":"330500","level":"2","name":"湖州市","upid":"330000"},{"id":"330600","level":"2","name":"绍兴市","upid":"330000"},{"id":"330700","level":"2","name":"金华市","upid":"330000"},{"id":"330800","level":"2","name":"衢州市","upid":"330000"},{"id":"330900","level":"2","name":"舟山市","upid":"330000"},{"id":"331000","level":"2","name":"台州市","upid":"330000"},{"id":"331100","level":"2","name":"丽水市","upid":"330000"}],"id":"330000","level":"1","name":"浙江省","upid":"0"},{"city":[{"id":"340100","level":"2","name":"合肥市","upid":"340000"},{"id":"340200","level":"2","name":"芜湖市","upid":"340000"},{"id":"340300","level":"2","name":"蚌埠市","upid":"340000"},{"id":"340400","level":"2","name":"淮南市","upid":"340000"},{"id":"340500","level":"2","name":"马鞍山市","upid":"340000"},{"id":"340600","level":"2","name":"淮北市","upid":"340000"},{"id":"340700","level":"2","name":"铜陵市","upid":"340000"},{"id":"340800","level":"2","name":"安庆市","upid":"340000"},{"id":"341000","level":"2","name":"黄山市","upid":"340000"},{"id":"341100","level":"2","name":"滁州市","upid":"340000"},{"id":"341200","level":"2","name":"阜阳市","upid":"340000"},{"id":"341300","level":"2","name":"宿州市","upid":"340000"},{"id":"341500","level":"2","name":"六安市","upid":"340000"},{"id":"341600","level":"2","name":"亳州市","upid":"340000"},{"id":"341700","level":"2","name":"池州市","upid":"340000"},{"id":"341800","level":"2","name":"宣城市","upid":"340000"}],"id":"340000","level":"1","name":"安徽省","upid":"0"},{"city":[{"id":"350100","level":"2","name":"福州市","upid":"350000"},{"id":"350200","level":"2","name":"厦门市","upid":"350000"},{"id":"350300","level":"2","name":"莆田市","upid":"350000"},{"id":"350400","level":"2","name":"三明市","upid":"350000"},{"id":"350500","level":"2","name":"泉州市","upid":"350000"},{"id":"350600","level":"2","name":"漳州市","upid":"350000"},{"id":"350700","level":"2","name":"南平市","upid":"350000"},{"id":"350800","level":"2","name":"龙岩市","upid":"350000"},{"id":"350900","level":"2","name":"宁德市","upid":"350000"}],"id":"350000","level":"1","name":"福建省","upid":"0"},{"city":[{"id":"360100","level":"2","name":"南昌市","upid":"360000"},{"id":"360200","level":"2","name":"景德镇市","upid":"360000"},{"id":"360300","level":"2","name":"萍乡市","upid":"360000"},{"id":"360400","level":"2","name":"九江市","upid":"360000"},{"id":"360500","level":"2","name":"新余市","upid":"360000"},{"id":"360600","level":"2","name":"鹰潭市","upid":"360000"},{"id":"360700","level":"2","name":"赣州市","upid":"360000"},{"id":"360800","level":"2","name":"吉安市","upid":"360000"},{"id":"360900","level":"2","name":"宜春市","upid":"360000"},{"id":"361000","level":"2","name":"抚州市","upid":"360000"},{"id":"361100","level":"2","name":"上饶市","upid":"360000"}],"id":"360000","level":"1","name":"江西省","upid":"0"},{"city":[{"id":"370100","level":"2","name":"济南市","upid":"370000"},{"id":"370200","level":"2","name":"青岛市","upid":"370000"},{"id":"370300","level":"2","name":"淄博市","upid":"370000"},{"id":"370400","level":"2","name":"枣庄市","upid":"370000"},{"id":"370500","level":"2","name":"东营市","upid":"370000"},{"id":"370600","level":"2","name":"烟台市","upid":"370000"},{"id":"370700","level":"2","name":"潍坊市","upid":"370000"},{"id":"370800","level":"2","name":"济宁市","upid":"370000"},{"id":"370900","level":"2","name":"泰安市","upid":"370000"},{"id":"371000","level":"2","name":"威海市","upid":"370000"},{"id":"371100","level":"2","name":"日照市","upid":"370000"},{"id":"371200","level":"2","name":"莱芜市","upid":"370000"},{"id":"371300","level":"2","name":"临沂市","upid":"370000"},{"id":"371400","level":"2","name":"德州市","upid":"370000"},{"id":"371500","level":"2","name":"聊城市","upid":"370000"},{"id":"371600","level":"2","name":"滨州市","upid":"370000"},{"id":"371700","level":"2","name":"菏泽市","upid":"370000"}],"id":"370000","level":"1","name":"山东省","upid":"0"},{"city":[{"id":"410100","level":"2","name":"郑州市","upid":"410000"},{"id":"410200","level":"2","name":"开封市","upid":"410000"},{"id":"410300","level":"2","name":"洛阳市","upid":"410000"},{"id":"410400","level":"2","name":"平顶山市","upid":"410000"},{"id":"410500","level":"2","name":"安阳市","upid":"410000"},{"id":"410600","level":"2","name":"鹤壁市","upid":"410000"},{"id":"410700","level":"2","name":"新乡市","upid":"410000"},{"id":"410800","level":"2","name":"焦作市","upid":"410000"},{"id":"410900","level":"2","name":"濮阳市","upid":"410000"},{"id":"411000","level":"2","name":"许昌市","upid":"410000"},{"id":"411100","level":"2","name":"漯河市","upid":"410000"},{"id":"411200","level":"2","name":"三门峡市","upid":"410000"},{"id":"411300","level":"2","name":"南阳市","upid":"410000"},{"id":"411400","level":"2","name":"商丘市","upid":"410000"},{"id":"411500","level":"2","name":"信阳市","upid":"410000"},{"id":"411600","level":"2","name":"周口市","upid":"410000"},{"id":"411700","level":"2","name":"驻马店市","upid":"410000"}],"id":"410000","level":"1","name":"河南省","upid":"0"},{"city":[{"id":"420100","level":"2","name":"武汉市","upid":"420000"},{"id":"420200","level":"2","name":"黄石市","upid":"420000"},{"id":"420300","level":"2","name":"十堰市","upid":"420000"},{"id":"420500","level":"2","name":"宜昌市","upid":"420000"},{"id":"420600","level":"2","name":"襄樊市","upid":"420000"},{"id":"420700","level":"2","name":"鄂州市","upid":"420000"},{"id":"420800","level":"2","name":"荆门市","upid":"420000"},{"id":"420900","level":"2","name":"孝感市","upid":"420000"},{"id":"421000","level":"2","name":"荆州市","upid":"420000"},{"id":"421100","level":"2","name":"黄冈市","upid":"420000"},{"id":"421200","level":"2","name":"咸宁市","upid":"420000"},{"id":"421300","level":"2","name":"随州市","upid":"420000"},{"id":"422800","level":"2","name":"恩施土家族苗族自治州","upid":"420000"},{"id":"429000","level":"2","name":"省直辖行政单位","upid":"420000"}],"id":"420000","level":"1","name":"湖北省","upid":"0"},{"city":[{"id":"430100","level":"2","name":"长沙市","upid":"430000"},{"id":"430200","level":"2","name":"株洲市","upid":"430000"},{"id":"430300","level":"2","name":"湘潭市","upid":"430000"},{"id":"430400","level":"2","name":"衡阳市","upid":"430000"},{"id":"430500","level":"2","name":"邵阳市","upid":"430000"},{"id":"430600","level":"2","name":"岳阳市","upid":"430000"},{"id":"430700","level":"2","name":"常德市","upid":"430000"},{"id":"430800","level":"2","name":"张家界市","upid":"430000"},{"id":"430900","level":"2","name":"益阳市","upid":"430000"},{"id":"431000","level":"2","name":"郴州市","upid":"430000"},{"id":"431100","level":"2","name":"永州市","upid":"430000"},{"id":"431200","level":"2","name":"怀化市","upid":"430000"},{"id":"431300","level":"2","name":"娄底市","upid":"430000"},{"id":"433100","level":"2","name":"湘西土家族苗族自治州","upid":"430000"}],"id":"430000","level":"1","name":"湖南省","upid":"0"},{"city":[{"id":"440100","level":"2","name":"广州市","upid":"440000"},{"id":"440200","level":"2","name":"韶关市","upid":"440000"},{"id":"440300","level":"2","name":"深圳市","upid":"440000"},{"id":"440400","level":"2","name":"珠海市","upid":"440000"},{"id":"440500","level":"2","name":"汕头市","upid":"440000"},{"id":"440600","level":"2","name":"佛山市","upid":"440000"},{"id":"440700","level":"2","name":"江门市","upid":"440000"},{"id":"440800","level":"2","name":"湛江市","upid":"440000"},{"id":"440900","level":"2","name":"茂名市","upid":"440000"},{"id":"441200","level":"2","name":"肇庆市","upid":"440000"},{"id":"441300","level":"2","name":"惠州市","upid":"440000"},{"id":"441400","level":"2","name":"梅州市","upid":"440000"},{"id":"441500","level":"2","name":"汕尾市","upid":"440000"},{"id":"441600","level":"2","name":"河源市","upid":"440000"},{"id":"441700","level":"2","name":"阳江市","upid":"440000"},{"id":"441800","level":"2","name":"清远市","upid":"440000"},{"id":"441900","level":"2","name":"东莞市","upid":"440000"},{"id":"442000","level":"2","name":"中山市","upid":"440000"},{"id":"445100","level":"2","name":"潮州市","upid":"440000"},{"id":"445200","level":"2","name":"揭阳市","upid":"440000"},{"id":"445300","level":"2","name":"云浮市","upid":"440000"}],"id":"440000","level":"1","name":"广东省","upid":"0"},{"city":[{"id":"450100","level":"2","name":"南宁市","upid":"450000"},{"id":"450200","level":"2","name":"柳州市","upid":"450000"},{"id":"450300","level":"2","name":"桂林市","upid":"450000"},{"id":"450400","level":"2","name":"梧州市","upid":"450000"},{"id":"450500","level":"2","name":"北海市","upid":"450000"},{"id":"450600","level":"2","name":"防城港市","upid":"450000"},{"id":"450700","level":"2","name":"钦州市","upid":"450000"},{"id":"450800","level":"2","name":"贵港市","upid":"450000"},{"id":"450900","level":"2","name":"玉林市","upid":"450000"},{"id":"451000","level":"2","name":"百色市","upid":"450000"},{"id":"451100","level":"2","name":"贺州市","upid":"450000"},{"id":"451200","level":"2","name":"河池市","upid":"450000"},{"id":"451300","level":"2","name":"来宾市","upid":"450000"},{"id":"451400","level":"2","name":"崇左市","upid":"450000"}],"id":"450000","level":"1","name":"广西省","upid":"0"},{"city":[{"id":"460100","level":"2","name":"海口市","upid":"460000"},{"id":"460200","level":"2","name":"三亚市","upid":"460000"},{"id":"469000","level":"2","name":"省直辖县级行政单位","upid":"460000"}],"id":"460000","level":"1","name":"海南省","upid":"0"},{"city":[{"id":"500100","level":"2","name":"市辖区","upid":"500000"},{"id":"500200","level":"2","name":"县","upid":"500000"},{"id":"500300","level":"2","name":"市","upid":"500000"}],"id":"500000","level":"1","name":"重庆市","upid":"0"},{"city":[{"id":"510100","level":"2","name":"成都市","upid":"510000"},{"id":"510300","level":"2","name":"自贡市","upid":"510000"},{"id":"510400","level":"2","name":"攀枝花市","upid":"510000"},{"id":"510500","level":"2","name":"泸州市","upid":"510000"},{"id":"510600","level":"2","name":"德阳市","upid":"510000"},{"id":"510700","level":"2","name":"绵阳市","upid":"510000"},{"id":"510800","level":"2","name":"广元市","upid":"510000"},{"id":"510900","level":"2","name":"遂宁市","upid":"510000"},{"id":"511000","level":"2","name":"内江市","upid":"510000"},{"id":"511100","level":"2","name":"乐山市","upid":"510000"},{"id":"511300","level":"2","name":"南充市","upid":"510000"},{"id":"511400","level":"2","name":"眉山市","upid":"510000"},{"id":"511500","level":"2","name":"宜宾市","upid":"510000"},{"id":"511600","level":"2","name":"广安市","upid":"510000"},{"id":"511700","level":"2","name":"达州市","upid":"510000"},{"id":"511800","level":"2","name":"雅安市","upid":"510000"},{"id":"511900","level":"2","name":"巴中市","upid":"510000"},{"id":"512000","level":"2","name":"资阳市","upid":"510000"},{"id":"513200","level":"2","name":"阿坝藏族羌族自治州","upid":"510000"},{"id":"513300","level":"2","name":"甘孜藏族自治州","upid":"510000"},{"id":"513400","level":"2","name":"凉山彝族自治州","upid":"510000"}],"id":"510000","level":"1","name":"四川省","upid":"0"},{"city":[{"id":"520100","level":"2","name":"贵阳市","upid":"520000"},{"id":"520200","level":"2","name":"六盘水市","upid":"520000"},{"id":"520300","level":"2","name":"遵义市","upid":"520000"},{"id":"520400","level":"2","name":"安顺市","upid":"520000"},{"id":"522200","level":"2","name":"铜仁地区","upid":"520000"},{"id":"522300","level":"2","name":"黔西南布依族苗族自治州","upid":"520000"},{"id":"522400","level":"2","name":"毕节地区","upid":"520000"},{"id":"522600","level":"2","name":"黔东南苗族侗族自治州","upid":"520000"},{"id":"522700","level":"2","name":"黔南布依族苗族自治州","upid":"520000"}],"id":"520000","level":"1","name":"贵州省","upid":"0"},{"city":[{"id":"530100","level":"2","name":"昆明市","upid":"530000"},{"id":"530300","level":"2","name":"曲靖市","upid":"530000"},{"id":"530400","level":"2","name":"玉溪市","upid":"530000"},{"id":"530500","level":"2","name":"保山市","upid":"530000"},{"id":"530600","level":"2","name":"昭通市","upid":"530000"},{"id":"530700","level":"2","name":"丽江市","upid":"530000"},{"id":"530800","level":"2","name":"思茅市","upid":"530000"},{"id":"530900","level":"2","name":"临沧市","upid":"530000"},{"id":"532300","level":"2","name":"楚雄彝族自治州","upid":"530000"},{"id":"532500","level":"2","name":"红河哈尼族彝族自治州","upid":"530000"},{"id":"532600","level":"2","name":"文山壮族苗族自治州","upid":"530000"},{"id":"532800","level":"2","name":"西双版纳傣族自治州","upid":"530000"},{"id":"532900","level":"2","name":"大理白族自治州","upid":"530000"},{"id":"533100","level":"2","name":"德宏傣族景颇族自治州","upid":"530000"},{"id":"533300","level":"2","name":"怒江傈僳族自治州","upid":"530000"},{"id":"533400","level":"2","name":"迪庆藏族自治州","upid":"530000"}],"id":"530000","level":"1","name":"云南省","upid":"0"},{"city":[{"id":"540100","level":"2","name":"拉萨市","upid":"540000"},{"id":"542100","level":"2","name":"昌都地区","upid":"540000"},{"id":"542200","level":"2","name":"山南地区","upid":"540000"},{"id":"542300","level":"2","name":"日喀则地区","upid":"540000"},{"id":"542400","level":"2","name":"那曲地区","upid":"540000"},{"id":"542500","level":"2","name":"阿里地区","upid":"540000"},{"id":"542600","level":"2","name":"林芝地区","upid":"540000"}],"id":"540000","level":"1","name":"西　藏","upid":"0"},{"city":[{"id":"610100","level":"2","name":"西安市","upid":"610000"},{"id":"610200","level":"2","name":"铜川市","upid":"610000"},{"id":"610300","level":"2","name":"宝鸡市","upid":"610000"},{"id":"610400","level":"2","name":"咸阳市","upid":"610000"},{"id":"610500","level":"2","name":"渭南市","upid":"610000"},{"id":"610600","level":"2","name":"延安市","upid":"610000"},{"id":"610700","level":"2","name":"汉中市","upid":"610000"},{"id":"610800","level":"2","name":"榆林市","upid":"610000"},{"id":"610900","level":"2","name":"安康市","upid":"610000"},{"id":"611000","level":"2","name":"商洛市","upid":"610000"}],"id":"610000","level":"1","name":"陕西省","upid":"0"},{"city":[{"id":"620100","level":"2","name":"兰州市","upid":"620000"},{"id":"620200","level":"2","name":"嘉峪关市","upid":"620000"},{"id":"620300","level":"2","name":"金昌市","upid":"620000"},{"id":"620400","level":"2","name":"白银市","upid":"620000"},{"id":"620500","level":"2","name":"天水市","upid":"620000"},{"id":"620600","level":"2","name":"武威市","upid":"620000"},{"id":"620700","level":"2","name":"张掖市","upid":"620000"},{"id":"620800","level":"2","name":"平凉市","upid":"620000"},{"id":"620900","level":"2","name":"酒泉市","upid":"620000"},{"id":"621000","level":"2","name":"庆阳市","upid":"620000"},{"id":"621100","level":"2","name":"定西市","upid":"620000"},{"id":"621200","level":"2","name":"陇南市","upid":"620000"},{"id":"622900","level":"2","name":"临夏回族自治州","upid":"620000"},{"id":"623000","level":"2","name":"甘南藏族自治州","upid":"620000"}],"id":"620000","level":"1","name":"甘肃省","upid":"0"},{"city":[{"id":"630100","level":"2","name":"西宁市","upid":"630000"},{"id":"632100","level":"2","name":"海东地区","upid":"630000"},{"id":"632200","level":"2","name":"海北藏族自治州","upid":"630000"},{"id":"632300","level":"2","name":"黄南藏族自治州","upid":"630000"},{"id":"632500","level":"2","name":"海南藏族自治州","upid":"630000"},{"id":"632600","level":"2","name":"果洛藏族自治州","upid":"630000"},{"id":"632700","level":"2","name":"玉树藏族自治州","upid":"630000"},{"id":"632800","level":"2","name":"海西蒙古族藏族自治州","upid":"630000"}],"id":"630000","level":"1","name":"青海省","upid":"0"},{"city":[{"id":"640100","level":"2","name":"银川市","upid":"640000"},{"id":"640200","level":"2","name":"石嘴山市","upid":"640000"},{"id":"640300","level":"2","name":"吴忠市","upid":"640000"},{"id":"640400","level":"2","name":"固原市","upid":"640000"},{"id":"640500","level":"2","name":"中卫市","upid":"640000"}],"id":"640000","level":"1","name":"宁　夏","upid":"0"},{"city":[{"id":"650100","level":"2","name":"乌鲁木齐市","upid":"650000"},{"id":"650200","level":"2","name":"克拉玛依市","upid":"650000"},{"id":"652100","level":"2","name":"吐鲁番地区","upid":"650000"},{"id":"652200","level":"2","name":"哈密地区","upid":"650000"},{"id":"652300","level":"2","name":"昌吉回族自治州","upid":"650000"},{"id":"652700","level":"2","name":"博尔塔拉蒙古自治州","upid":"650000"},{"id":"652800","level":"2","name":"巴音郭楞蒙古自治州","upid":"650000"},{"id":"652900","level":"2","name":"阿克苏地区","upid":"650000"},{"id":"653000","level":"2","name":"克孜勒苏柯尔克孜自治州","upid":"650000"},{"id":"653100","level":"2","name":"喀什地区","upid":"650000"},{"id":"653200","level":"2","name":"和田地区","upid":"650000"},{"id":"654000","level":"2","name":"伊犁哈萨克自治州","upid":"650000"},{"id":"654200","level":"2","name":"塔城地区","upid":"650000"},{"id":"654300","level":"2","name":"阿勒泰地区","upid":"650000"},{"id":"659000","level":"2","name":"省直辖行政单位","upid":"650000"}],"id":"650000","level":"1","name":"新　疆","upid":"0"},{"city":[{"id":"710001","level":"2","name":"台北市","upid":"710000"},{"id":"710003","level":"2","name":"基隆市","upid":"710000"}],"id":"710000","level":"1","name":"台湾省","upid":"0"},{"city":[{"id":"810001","level":"2","name":"香港","upid":"810000"}],"id":"810000","level":"1","name":"香　港","upid":"0"},{"city":[{"id":"820001","level":"2","name":"澳门","upid":"820000"}],"id":"820000","level":"1","name":"澳　门","upid":"0"}]
         * message : 返回成功
         * success : true
         */

        private int error_code;
        private String message;
        private boolean success;
        /**
         * city : [{"id":"110100","level":"2","name":"北京市","upid":"110000"},{"id":"110200","level":"2","name":"县","upid":"110000"}]
         * id : 110000
         * level : 1
         * name : 北京市
         * upid : 0
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

        public static class ListEntity implements Serializable {
            private String id;
            private String level;
            private String name;
            private String upid;
            /**
             * id : 110100
             * level : 2
             * name : 北京市
             * upid : 110000
             */

            private ArrayList<CityEntity> city;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUpid() {
                return upid;
            }

            public void setUpid(String upid) {
                this.upid = upid;
            }

            public ArrayList<CityEntity> getCity() {
                return city;
            }

            public void setCity(ArrayList<CityEntity> city) {
                this.city = city;
            }

            public static class CityEntity {
                private String id;
                private String level;
                private String name;
                private String upid;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getLevel() {
                    return level;
                }

                public void setLevel(String level) {
                    this.level = level;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getUpid() {
                    return upid;
                }

                public void setUpid(String upid) {
                    this.upid = upid;
                }
            }
        }
    }
}
