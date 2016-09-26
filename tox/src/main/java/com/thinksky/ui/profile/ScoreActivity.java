/*
 * 文件名: ScoreActivity
 * 版    权：  Copyright Hengrtech Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: zhaozeyang
 * 创建时间:16/9/20
 * 
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.thinksky.ui.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.thinksky.holder.BaseApplication;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.injection.GlobalModule;
import com.thinksky.net.UiRpcSubscriber1;
import com.thinksky.net.rpc.model.ScoreModel;
import com.thinksky.net.rpc.service.AppService;
import com.thinksky.serviceinjection.DaggerServiceComponent;
import com.thinksky.serviceinjection.ServiceModule;
import com.thinksky.tox.R;
import com.thinksky.ui.common.TitleBar;
import com.tox.Url;
import javax.inject.Inject;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author zhaozeyang
 * @version [Taobei Client V20160411, 16/9/20]
 */
public class ScoreActivity extends BaseBActivity {
  @Bind(R.id.title_bar)
  TitleBar titleBar;
  @Bind(R.id.current_time)
  TextView currentTime;
  @Bind(R.id.score)
  TextView score;
  @Bind(R.id.beat)
  TextView beat;

  @Inject
  AppService mAppService;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    inject();
    setContentView(R.layout.activity_score);
    ButterKnife.bind(this);
    initTitleBar();
    initDada();
  }


  private void inject() {
    DaggerServiceComponent.builder().globalModule(new GlobalModule((BaseApplication)
        getApplication())).serviceModule(new ServiceModule())
        .build().inject(this);
  }

  private void initDada() {
    manageRpcCall(mAppService.getScore(Url.SESSIONID), new UiRpcSubscriber1<ScoreModel>
        (ScoreActivity.this) {
      @Override
      protected void onSuccess(ScoreModel scoreModel) {
        currentTime.setText(scoreModel.getList().getDate());
        score.setText(scoreModel.getList().getScore1());
        beat.setText(getString(R.string.activity_score_label_beat, scoreModel.getList().getRatio
            () + "%"));
      }

      @Override
      protected void onEnd() {

      }
    });
  }

  private void initTitleBar() {
    titleBar.setMiddleTitle(R.string.activity_score_title);
    titleBar.setLeftImgMenu(R.drawable.icon_title_bar_back, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onBackPressed();
      }
    });
  }
}
