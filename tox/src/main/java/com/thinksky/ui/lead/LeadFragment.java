package com.thinksky.ui.lead;

import android.content.Intent;
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
import com.thinksky.model.ActivityModel;
import com.thinksky.tox.LoginActivity;
import com.thinksky.tox.R;
import com.thinksky.tox.UserInfoActivity;
import com.tox.Url;

public class LeadFragment extends Fragment {
  static final int[] LEAD_IMG_RES = new int[]{R.drawable.new_feature_1, R.drawable.new_feature_2,
      R.drawable
          .new_feature_3};
  private static final String ARG_PAGE_INDEX = "page_index";

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
    if (!Url.SESSIONID.equals("")) {
      Intent intent = new Intent(getActivity(),
          UserInfoActivity.class);
      intent.putExtra("userUid", Url.USERID);
      startActivityForResult(intent, 0);
    } else {
      Intent intent = new Intent(getActivity(), LoginActivity.class);
      intent.putExtra("entryActivity", ActivityModel.ACTIVITY);
      startActivity(intent);
    }
    getActivity().finish();
  }

}