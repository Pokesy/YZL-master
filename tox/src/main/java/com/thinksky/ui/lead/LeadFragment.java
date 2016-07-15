package com.thinksky.ui.lead;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.thinksky.tox.R;

public class LeadFragment extends Fragment {
  static final int[] LEAD_IMG_RES = new int[]{R.drawable.new_feature_1, R.drawable.new_feature_2,
      R.drawable
          .new_feature_3};
  private static final String ARG_PAGE_INDEX = "page_index";
  private OnBtnClickListener mListener;

  @Bind(R.id.btn_enter)
  View mBtnEnter;
  @Bind(R.id.lead_img)
  ImageView mImgLead;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
  Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_lead, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    int index = getArguments().getInt(ARG_PAGE_INDEX);
    mBtnEnter.setVisibility(index == LEAD_IMG_RES.length - 1 ? View.VISIBLE : View.GONE);
    mImgLead.setImageResource(LEAD_IMG_RES[index]);
  }

  public static LeadFragment newInstance(int pageIndex) {
    LeadFragment fragment = new LeadFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_PAGE_INDEX, pageIndex);
    fragment.setArguments(args);
    return fragment;
  }

  @OnClick(R.id.btn_enter)
  void onClick() {
    if (null != mListener) {
      mListener.onClick();
    }
  }

  public void setOnBtnClickListener(OnBtnClickListener listener) {
    mListener = listener;
  }

  public interface OnBtnClickListener {
    void onClick();
  }

}