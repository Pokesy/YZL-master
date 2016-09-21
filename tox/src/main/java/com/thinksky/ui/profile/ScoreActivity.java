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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.thinksky.holder.BaseBActivity;
import com.thinksky.tox.R;
import com.thinksky.ui.common.TitleBar;

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
  @Bind(R.id.assessment)
  TextView assessment;
  @Bind(R.id.sore_login)
  TextView soreLogin;
  @Bind(R.id.score_question)
  TextView scoreQuestion;
  @Bind(R.id.sore_best_answer)
  TextView soreBestAnswer;
  @Bind(R.id.score_send_activity)
  TextView scoreSendActivity;
  @Bind(R.id.score_send_post)
  TextView scoreSendPost;
  @Bind(R.id.score_set_question_award)
  TextView scoreSetQuestionAward;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_score);
    ButterKnife.bind(this);
    initTitleBar();
  }

  private void initTitleBar() {
    titleBar.setMiddleTitle(R.string.activity_score_title);
    titleBar.setLeftImgMenu(R.drawable.icon_title_bar_back, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onBackPressed();
      }
    });
    titleBar.setRightTextBtn(R.string.activity_score_title_bar_btn, new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(ScoreActivity.this, ScoreRuleActivity.class));
      }
    });
  }
}
