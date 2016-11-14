package com.thinksky.tox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import com.thinksky.adapter.ChildAdapter;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.model.ActivityModel;
import com.thinksky.ui.common.TitleBar;
import java.util.ArrayList;
import java.util.List;

public class ImageChooseListActivity extends BaseBActivity {
  public static final int RESULT_CODE_CHOOSE_IMG_SUCCESS = 999;

  private GridView mGridView;
  private List<String> list;
  private ChildAdapter adapter;
  private TitleBar mTitleBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.show_image_activity);
    mGridView = (GridView) findViewById(R.id.child_grid);
    mTitleBar = (TitleBar) findViewById(R.id.title_bar);
    mTitleBar.setLeftImgMenu(R.drawable.icon_title_bar_back, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
    mTitleBar.setRightTextBtn(R.string.btn_confirm, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        pic_confirm();
      }
    });
    mTitleBar.setMiddleTitle("选取图片");
    list = getIntent().getStringArrayListExtra("data");

    adapter = new ChildAdapter(this, list, mGridView);
    mGridView.setAdapter(adapter);
    //如果
    if (getIntent().getIntExtra("fromActivity", 0) == ActivityModel.UPLOADACTIVITY) {
      adapter.setImgSelected();
    }
  }

  /**
   * 确认选择图片，并返回
   */
  private void pic_confirm() {
    Intent data = new Intent();
    List<Integer> selectedID = adapter.getSelectItems();
    List<String> selectedImgPath = new ArrayList<String>();
    for (int i = 0; i < selectedID.size(); i++) {
      selectedImgPath.add(list.get(selectedID.get(i)));
    }
    data.putStringArrayListExtra("data", (ArrayList<String>) selectedImgPath);
    setResult(RESULT_CODE_CHOOSE_IMG_SUCCESS, data);
    finish();
  }

}
