package com.thinksky.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ListView;

import com.thinksky.rsen.RsenUrlUtil;
import com.thinksky.rsen.activity.RBaseAppActivity;
import com.thinksky.tox.R;
import com.thinksky.tox.SegmentControl;
import com.tox.BaseApi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiao on 2016/3/13.
 */
public class WendaMyQuestionListActivity extends RBaseAppActivity {

    ListView mListView;

    private SegmentControl mSegmentControl2;
    private ViewPager mPager;
    private BaseApi baseApi;
    private String session_id;
    @Override
    protected void onCreateAfter() {
        setTitle("我的提问");

        mListView = (ListView) findViewById(R.id.listView);
        String whichActivity = getIntent().getStringExtra("whichActivity");

        initView();

        addRightMenu("提问", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击菜单,提问
                show("提问");
            }
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_wenda_myquestion;
    }

    private void initView() {
        baseApi = new BaseApi();
        session_id = baseApi.getSeesionId();
        mSegmentControl2 = (SegmentControl) findViewById(R.id.segment_control2);
        mPager = (ViewPager) findViewById(R.id.pager);
        List<Fragment> fragments = new ArrayList<>();

        WendaMyQuestionCommonFragment solutedFragment = new WendaMyQuestionCommonFragment();
        WendaMyQuestionCommonFragment noSolutedFragment = new WendaMyQuestionCommonFragment();

        solutedFragment.setUrl(RsenUrlUtil.URL_SOLUTE_WD);
        noSolutedFragment.setUrl(RsenUrlUtil.URL_MY_WD);

        Bundle args = new Bundle();
        args.putBoolean(WendaMyQuestionCommonFragment.KEY_NEED_ID, true);
        noSolutedFragment.setArguments(args);

        fragments.add(solutedFragment);
        fragments.add(noSolutedFragment);

        mPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), fragments));
        mSegmentControl2.setSelectedTextColor(getResources().getColor(android.R.color.black));
        mSegmentControl2.setOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {
                mPager.setCurrentItem(index);
            }
        });
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mSegmentControl2.setCurrentIndex(position);
                if (position % 2 != 0) {
                    mSegmentControl2.setSelectedBackgroundColors(getResources().getColor(android.R.color.darker_gray));
                } else {
                    mSegmentControl2.setSelectedBackgroundColors(0xff009688);
                }
            }
        });
    }

    private static class PagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;

        public PagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }


//    public class WendaListAdapter extends BaseAdapter {
//        private List<WendaFragment.WendaBean.ListEntity> datas;
//        private Context context;
//
//        public WendaListAdapter(Context context, List<WendaFragment.WendaBean.ListEntity> datas) {
//            this.datas = datas;
//            this.context = context;
//        }
//
//        @Override
//        public int getCount() {
//            if (datas == null) {
//                return 0;
//            }
//            return datas.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return datas.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//            RViewHolder viewHolder;
//            if (convertView == null) {
//                convertView = LayoutInflater.from(context).inflate(R.layout.fragment_wenda_adapter_item, parent, false);
//                viewHolder = new RViewHolder(convertView);
//                convertView.setTag(viewHolder);
//            } else {
//                viewHolder = (RViewHolder) convertView.getTag();
//            }
//
//            final WendaFragment.WendaBean.ListEntity listEntity = datas.get(position);
//
//            /*其他控件信息，自己添加 id， 然后从 listEntity对象中获取数据，填充就行了*/
//            ((TextView) viewHolder.itemView.findViewById(R.id.title)).setText(listEntity.getTitle());
//            ((TextView) viewHolder.itemView.findViewById(R.id.content)).setText(listEntity.getContent());
//            ((TextView) viewHolder.itemView.findViewById(R.id.nickname)).setText(listEntity.getUser().getNickname());
//            ((TextView) viewHolder.itemView.findViewById(R.id.answer_num)).setText(listEntity.getAnswer_num());
//            ((TextView) viewHolder.itemView.findViewById(R.id.creat_time)).setText(listEntity.getCreate_time());
//            String s = listEntity.getBest_answer();
//
//            if (s.equals("0")) {
//                ((TextView) viewHolder.itemView.findViewById(R.id.best_answer)).setText("求助中");
//            }else{
//                ((TextView) viewHolder.itemView.findViewById(R.id.best_answer)).setText("已解决");
//            }
//            /*点击事件响应*/
//            viewHolder.itemView.findViewById(R.id.item_layout).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    RsenCommonActivity.showActivity(context, RsenCommonActivity.TYPE_QDETAIL, null);
//                    //Toast.makeText(v.getContext(), "Click Me " + position, Toast.LENGTH_LONG).show();
//                    Bundle bundle = new Bundle();
//
//                    bundle.putString("question_id", listEntity.getId());
//
//                    Intent intent = new Intent(context, wentixiangqing.class);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                }
//            });
//            return convertView;
//        }
//    }
}
